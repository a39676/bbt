package demo.testCase.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.testCase.mapper.TestEventMapper;
import demo.testCase.pojo.bo.TestEventBO;
import demo.testCase.pojo.po.TestCase;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.service.TestCaseService;
import demo.testCase.service.TestEventService;

@Service
public class TestEventServiceImpl extends CommonService implements TestEventService {

	@Autowired
	private TestCaseService caseService;
	@Autowired
	private TestEventMapper eventMapper;
	
	@Override
	public Integer insertSelective(TestEvent po) {
		return eventMapper.insertSelective(po);
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