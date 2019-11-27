package demo.testCase.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.movie.service.DyttClawingService;
import demo.movie.service.HomeFeiClawingService;
import demo.testCase.mapper.TestEventMapper;
import demo.testCase.pojo.bo.TestEventBO;
import demo.testCase.pojo.constant.TestEventOptionConstant;
import demo.testCase.pojo.po.TestCase;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.type.TestCaseType;
import demo.testCase.service.TestCaseService;
import demo.testCase.service.TestEventService;

@Service
public class TestEventServiceImpl extends TestEventCommonService implements TestEventService {

	@Autowired
	private TestCaseService caseService;
	@Autowired
	private TestEventMapper eventMapper;
	
	@Autowired
	private HomeFeiClawingService homeFeiService;
	@Autowired
	private DyttClawingService dyttService;
	
	@Override
	public Integer insertSelective(TestEvent po) {
		return eventMapper.insertSelective(po);
	}
	
	@Override
	public void findTestEventAndRun() {
		/*
		 * TODO
		 * 需要改变逻辑
		 * 各模块的testService 需要一个前置逻辑, 确认指定任务
		 */
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
			startEvent(te);
			r = runSubEvent(te);
			endEvent(te, r.isSuccess(), r.getMessage());
		}
	}
	
	private CommonResultBBT runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();
		if(caseId == null) {
			return null;
		}  
		if (TestCaseType.dytt.getId().equals(caseId)) {
			return dyttService.clawing(te);
		} else if (TestCaseType.homeFeiCollection.getId().equals(caseId)) {
			return homeFeiService.collection(te);
		} else if (TestCaseType.homeFeiDownload.getId().equals(caseId)) {
			return homeFeiService.download(te);
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
	
}
