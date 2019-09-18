package demo.testCase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.testCase.mapper.TestEventMapper;
import demo.testCase.pojo.bo.TestEventBO;
import demo.testCase.pojo.bo.TestEventDemoBO;
import demo.testCase.pojo.po.TestCase;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.po.TestedProject;
import demo.testCase.service.TestCaseService;
import demo.testCase.service.TestEventService;
import demo.testCase.service.TestedProjectService;

@Service
public class TestEventServiceImpl extends CommonService implements TestEventService {

	@Autowired
	private TestCaseService caseService;
	@Autowired
	private TestedProjectService projectService;
	@Autowired
	private TestEventMapper eventMapper;
	
	@Override
	public Integer insertSelective(TestEvent po) {
		return eventMapper.insertSelective(po);
	}
	
	@Override
	public TestEvent runNewTestEvent(TestEventBO bo) {
		TestCase casePO = caseService.findByCaseCode(bo.getCaseCode());
		TestedProject project = projectService.findByProjectName(bo.getProjectName());
		Long newEventId = snowFlake.getNextId();
		TestEvent testEvent = new TestEvent();
		if(casePO != null) {
			testEvent.setCaseId(casePO.getId());
		}
		if(project != null) {
			testEvent.setProjectId(project.getId());
		}
		testEvent.setId(newEventId);
		eventMapper.insertSelective(testEvent);
		
		return testEvent;
	}
	
	public static void main(String[] args) {
		TestEventServiceImpl t = new TestEventServiceImpl();
		TestEventDemoBO b = new TestEventDemoBO().build();
		t.runNewTestEvent(b);
	}
	
}
