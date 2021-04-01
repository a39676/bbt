package demo.autoTestBase.testEvent.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testCase.pojo.po.TestCase;
import demo.autoTestBase.testCase.service.TestCaseService;
import demo.autoTestBase.testEvent.mq.TestEventAckProducer;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.constant.TestEventOptionConstant;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.po.TestEventExample;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.badJoke.sms.service.impl.BadJokeCasePrefixServiceImpl;
import demo.clawing.collecting.jandan.service.impl.ClawCollectPrefixServiceImpl;
import demo.clawing.demo.service.impl.SearchingDemoPrefixServiceImpl;
import demo.clawing.localClawing.service.impl.LocalClawingPrefixServiceImpl;
import demo.clawing.lottery.service.impl.LotteryPrefixServiceImpl;
import demo.clawing.movie.service.impl.MovieClawingCasePrefixServiceImpl;
import demo.clawing.scheduleClawing.service.impl.ScheduleClawingPrefixServiceImpl;
import demo.tool.pojo.type.MailType;
import demo.tool.service.MailService;
import net.sf.json.JSONObject;
import selenium.pojo.constant.SeleniumConstant;

@Service
public class TestEventServiceImpl extends TestEventCommonService implements TestEventService {

	@Autowired
	private MailService mailService;
	@Autowired
	private TestCaseService caseService;
	@Autowired
	private TestEventAckProducer testEventAckProducer;
	
	@Autowired
	private MovieClawingCasePrefixServiceImpl movieClawingCasePrefixService;
	@Autowired
	private BadJokeCasePrefixServiceImpl badJokeCasePrefixService;
	@Autowired
	private SearchingDemoPrefixServiceImpl searchingDemoService;
	@Autowired
	private ScheduleClawingPrefixServiceImpl scheduleClawingPrefixService;
	@Autowired
	private LotteryPrefixServiceImpl lotteryPrefixService;
	@Autowired
	private ClawCollectPrefixServiceImpl clawCollectPrefixService;
	@Autowired
	private LocalClawingPrefixServiceImpl localClawingPrefixService;
	
	private String pauseWordRedisKey = "testEventPauseWord";
	private String safeWord = "breakNow";
	
	private String findPauseWord() {
		return constantService.getValByName(pauseWordRedisKey, true);
	}
	
	@Override
	public InsertTestEventResult insertTestEvent(TestEvent po) {
		return insertTestEvent(po, null);
	}
	
	@Override
	public InsertTestEventResult insertTestEvent(TestEvent po, JSONObject paramJson) {
		InsertTestEventResult r = new InsertTestEventResult();
		if(po == null || po.getId() == null || po.getCaseId() == null || po.getModuleId() == null) {
			return r;
		}
		r.setNewTestEventId(po.getId());
		r.setCode(po.getEventName());
		r.setModuleId(po.getModuleId());
		r.setCaseId(po.getCaseId());
		
		if(paramJson != null && StringUtils.isNotBlank(paramJson.toString())) {
			constantService.setValByName(TestEventOptionConstant.TEST_EVENT_REDIS_PARAM_KEY_PREFIX + "_" + po.getId(), paramJson.toString(), 1L, TimeUnit.DAYS);
		}
		testEventAckProducer.send(po);
		
		r.setIsSuccess();
		return r;
	}
	
	@Override
	public CommonResultBBT reciveTestEventAndRun(TestEvent te) {
		
		if(te == null) {
			return new CommonResultBBT();
		}
		
		if(existsRuningEvent()) {
			return new CommonResultBBT();
		}
		
		return runEvent(te);
	}
	
	private CommonResultBBT runEvent(TestEvent event) {
		String breakWord = findPauseWord();
		if(safeWord.equals(breakWord)) {
			return new CommonResultBBT();
		}
		
		CommonResultBBT runResult = null;
		if(event.getAppointment() == null || event.getAppointment().isBefore(LocalDateTime.now())) {
			startEvent(event);
			try {
				runResult = runSubEvent(event);
			} catch (Exception e) {
				runResult = new CommonResultBBT();
			}
			endEvent(event, runResult.isSuccess(), runResult.getMessage());
		}
		
		return runResult;
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
		} else if (TestModuleType.scheduleClawing.getId().equals(moduleId)) {
			return scheduleClawingPrefixService.runSubEvent(te);
		} else if(TestModuleType.lottery.getId().equals(moduleId)) {
			return lotteryPrefixService.runSubEvent(te);
		} else if(TestModuleType.collecting.getId().equals(moduleId)) {
			return clawCollectPrefixService.runSubEvent(te);
		} else if(TestModuleType.localClawing.getId().equals(moduleId)) {
			return localClawingPrefixService.runSubEvent(te);
		}
		return new CommonResultBBT();
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
	public CommonResultBBT sendFailReports() {
		LocalDateTime now = LocalDateTime.now();
		return sendFailReports(now.minusDays(2L), now);
	}
	
	@Override
	public CommonResultBBT sendFailReports(LocalDateTime startTime, LocalDateTime endTime) {
		CommonResultBBT r = new CommonResultBBT();
		TestEventExample example = new TestEventExample();
		example.createCriteria()
		.andIsDeleteEqualTo(false)
		.andIsPassEqualTo(false)
		.andEndTimeIsNotNull()
		.andStartTimeBetween(startTime, endTime)
		;
		List<TestEvent> failEventList = eventMapper.selectByExample(example);
		r.addMessage("failEventListSize : " + failEventList.size() + "\n");
		
		
		LocalDateTime now = LocalDateTime.now();
		String nowStr = localDateTimeHandler.dateToStr(now);
		CommonResult mailResult = mailService.sendSimpleMail(0L, constantService.getValByName(SystemConstantStore.managerMail), ("截至: " + nowStr + " 最近2天有" + failEventList.size() + "个失败任务"), (buildSendFailReportContent(failEventList)), null, MailType.sandFailTaskReport);
		if(mailResult.isSuccess()) {
			r.addMessage("send mail success" + "\n");
		} else {
			r.addMessage("send mail fali" + "\n");
			r.addMessage(mailResult.getMessage());
		}
		
		return r;
	}
	
	private String buildSendFailReportContent(List<TestEvent> failEventList) {
		Map<String, Integer> sumMap = new HashMap<String, Integer>();
		for(TestEvent po : failEventList) {
			if(sumMap.containsKey(po.getEventName())) {
				sumMap.put(po.getEventName(), sumMap.get(po.getEventName()) + 1);
			} else {
				sumMap.put(po.getEventName(), 1);
			}
		}
		
		LocalDateTime now = LocalDateTime.now();
		String nowStr = localDateTimeHandler.dateToStr(now);
		StringBuffer sb = new StringBuffer("截至: " + nowStr + " 最近2天有" + failEventList.size() + "个失败任务 \n");
		
		for(Entry<String, Integer> entry : sumMap.entrySet()) {
			sb.append(entry.getKey() + " : " + entry.getValue() + " 个 \n");
		}
		return sb.toString();
	}

	@Override
	public boolean checkExistsRuningEvent() {
		return existsRuningEvent();
	}
	
	@Override
	public void fixRuningEventStatusManual() {
		constantService.setValByName(runningEventRedisKey, "false");
	}

	@Override
	public void deleteOldTestEvent() {
		LocalDateTime deleteLine = LocalDateTime.now().plusMonths(SeleniumConstant.maxHistoryMonth);
		TestEventExample example = new TestEventExample();
		example.createCriteria().andIsDeleteEqualTo(false).andStartTimeGreaterThan(deleteLine);
		eventMapper.deleteByExample(example);
	}
}
