package demo.autoTestBase.testEvent.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testCase.pojo.po.TestCase;
import demo.autoTestBase.testCase.service.TestCaseService;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.constant.TestEventOptionConstant;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.po.TestEventExample;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.badJoke.sms.service.BadJokeCasePrefixService;
import demo.clawing.dailySign.service.DailySignPrefixService;
import demo.clawing.demo.service.SearchingDemoPrefixService;
import demo.clawing.lottery.service.LotteryPrefixService;
import demo.clawing.movie.service.MovieClawingCasePrefixService;
import demo.tool.service.MailService;

@Service
public class TestEventServiceImpl extends TestEventCommonService implements TestEventService {

	@Autowired
	private MailService mailService;
	@Autowired
	private TestCaseService caseService;
	
	@Autowired
	private MovieClawingCasePrefixService movieClawingCasePrefixService;
	@Autowired
	private BadJokeCasePrefixService badJokeCasePrefixService;
	@Autowired
	private SearchingDemoPrefixService searchingDemoService;
	@Autowired
	private DailySignPrefixService dailySignPrefixService;
	@Autowired
	private LotteryPrefixService lotteryPrefixService;
	
	private String pauseWordRedisKey = "testEventPauseWord";
	private String safeWord = "breakNow";
	
	private String findPauseWord() {
		return constantService.getValByName(pauseWordRedisKey, true);
	}
	
	@Override
	public InsertTestEventResult insertTestEvent(TestEvent po) {
		InsertTestEventResult r = new InsertTestEventResult();
		if(po == null || po.getCaseId() == null || po.getModuleId() == null) {
			return r;
		}
		r.setCode(po.getEventName());
		r.setModuleId(po.getModuleId());
		r.setCaseId(po.getCaseId());
		r.setInsertCount(eventMapper.insertSelective(po));
		if(r.getInsertCount() > 0) {
			r.setIsSuccess();
		}
		r.setNewTestEventId(po.getId());
		return r;
	}
	
	@Override
	public void findTestEventAndRun() {
		
		List<TestEvent> events = findTestEventNotRunYet();
		if(events == null || events.size() < 1) {
			return;
		}
		
		if(TestEventOptionConstant.enableMultipleTestEvent) {
			int runingEventCount = eventMapper.countRuningEvent();
			if(runingEventCount >= TestEventOptionConstant.multipleRunTestEventCount) {
				return;
			}
		} else {
			if(eventMapper.existsRuningEvent() > 0) {
				return;
			}
		}
		runEventQueue(events);
	}
	
	private void runEventQueue(List<TestEvent> events) {
		/*
		 * 2019/10/15
		 * 未处理 如果允许多个 testEvent 同时运行的情况
		 */
		CommonResultBBT r = null;
		for(TestEvent te : events) {
			
			String breakWord = findPauseWord();
			if(safeWord.equals(breakWord)) {
				return;
			}
			
			if(te.getAppointment() == null || te.getAppointment().isBefore(LocalDateTime.now())) {
				startEvent(te);
				try {
					r = runSubEvent(te);
				} catch (Exception e) {
					r = new CommonResultBBT();
				}
				endEvent(te, r.isSuccess(), r.getMessage());
			}
		}
	}
	
	private CommonResultBBT runSubEvent(TestEvent te) {
		Long moduleId = te.getModuleId();
		if(moduleId == null) {
			return null;
		}
		
		if (TestModuleType.movieClawing.getId().equals(moduleId)) {
			return movieClawingCasePrefixService.runSubEvent(te);
		} else if (TestModuleType.badJoke.getId().equals(moduleId)) {
			return badJokeCasePrefixService.runSubEvent(te);
		} else if (TestModuleType.ATDemo.getId().equals(moduleId)) {
			return searchingDemoService.runSubEvent(te);
		} else if (TestModuleType.dailySign.getId().equals(moduleId)) {
			return dailySignPrefixService.runSubEvent(te);
		} else if(TestModuleType.lottery.getId().equals(moduleId)) {
			return lotteryPrefixService.runSubEvent(te);
		}
		return new CommonResultBBT();
	}
	
	@Override
	public int fixMovieClawingTestEventStatus() {
		return eventMapper.fixMovieClawingTestEventStatus();
	}
	
	@Override
	public TestEvent runNewTestEvent(TestEventBO bo) {
		TestCase casePO = caseService.findByCaseCode(bo.getCaseCode());
		Long newEventId = snowFlake.getNextId();
		TestEvent testEvent = new TestEvent();
		if(casePO != null) {
			testEvent.setCaseId(casePO.getId());
		}
		testEvent.setId(newEventId);
		eventMapper.insertSelective(testEvent);
		
		return testEvent;
	}
	
	@Override
	public int countWaitingEvent() {
		return eventMapper.countWaitingEvent();
	}

	@Override
	public int updateTestEventReportPath(TestEvent te, String reportPath) {
		if(te.getId() == null) {
			return 0;
		}
		te.setReportPath(reportPath);
		return eventMapper.updateByPrimaryKeySelective(te);
	}
	
	@Override
	public void sendFailReports() {
		LocalDateTime now = LocalDateTime.now();
		sendFailReports(now.minusDays(2L), now);
	}
	
	@Override
	public void sendFailReports(LocalDateTime startTime, LocalDateTime endTime) {
		TestEventExample example = new TestEventExample();
		example.createCriteria()
		.andIsDeleteEqualTo(false).andIsPassEqualTo(false)
		.andEndTimeIsNotNull()
		.andStartTimeBetween(startTime, endTime)
		;
		example.setOrderByClause(" start_time desc ");
		List<TestEvent> failEventList = eventMapper.selectByExample(example);
		
		List<Long> failEventIdList = failEventList.stream().map(TestEvent::getId).collect(Collectors.toList());
		
		mailService.sandFailTaskReport(0L, failEventIdList, constantService.getValByName(SystemConstantStore.managerMail));
	}
}
