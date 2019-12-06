package demo.autoTestBase.testEvent.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testCase.pojo.po.TestCase;
import demo.autoTestBase.testCase.service.TestCaseService;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.constant.TestEventOptionConstant;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.autoTestBase.testModule.pojo.type.TestModuleType;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.badJoke.sms.service.BadJokeCasePrefixService;
import demo.clawing.bingDemo.service.BingDemoService;
import demo.clawing.dailySign.service.DailySignPrefixService;
import demo.clawing.movie.service.MovieClawingCasePrefixService;

@Service
public class TestEventServiceImpl extends TestEventCommonService implements TestEventService {

	@Autowired
	private TestCaseService caseService;
	
	@Autowired
	private MovieClawingCasePrefixService movieClawingCasePrefixService;
	@Autowired
	private BadJokeCasePrefixService badJokeCasePrefixService;
	@Autowired
	private BingDemoService bingDemoService;
	@Autowired
	private DailySignPrefixService dailySignPrefixService;
	
	@Override
	public InsertTestEventResult insertTestEvent(TestEvent po) {
		InsertTestEventResult r = new InsertTestEventResult();
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
			if(te.getAppointment() != null && te.getAppointment().isAfter(LocalDateTime.now())) {
				startEvent(te);
				r = runSubEvent(te);
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
			return bingDemoService.clawing(te);
		} else if (TestModuleType.dailySign.getId().equals(moduleId)) {
			return dailySignPrefixService.runSubEvent(te);
		}
		return null;
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
}
