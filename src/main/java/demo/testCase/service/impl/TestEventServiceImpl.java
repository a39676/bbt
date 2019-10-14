package demo.testCase.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.movie.service.DyttClawingService;
import demo.movie.service.HomeFeiClawingService;
import demo.testCase.mapper.TestEventMapper;
import demo.testCase.pojo.bo.TestEventBO;
import demo.testCase.pojo.po.TestCase;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.type.MovieTestCaseType;
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
	
	public void findTestEventAndRun() {
		List<TestEvent> events = findTestEventNotRunYet();
		if(events == null || events.size() < 1) {
			return;
		}
		
		runEventQueue(events);
	}
	
	private void runEventQueue(List<TestEvent> events) {
		CommonResultBBT r = null;
		for(TestEvent te : events) {
			r = runSubEvent(te);
			endEvent(te, r.isSuccess(), r.getMessage());
		}
	}
	
	private CommonResultBBT runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();
		if(caseId == null) {
			return null;
		} else if (MovieTestCaseType.dytt.getId().equals(caseId)) {
//			TODO
			dyttService.toString();
//			return dyttService.clawing(te);
		} else if (MovieTestCaseType.homeFeiCollection.getId().equals(caseId)) {
			return homeFeiService.collection(te);
		} else if (MovieTestCaseType.homeFeiDownload.getId().equals(caseId)) {
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
	
	@Override
	public Integer endTestEvent(TestEvent po, boolean success, String remark) {
		po.setIsPass(success);
		po.setEndTime(LocalDateTime.now());
		po.setRemark(remark);
		return eventMapper.updateByPrimaryKeySelective(po);
	}
	
	
}
