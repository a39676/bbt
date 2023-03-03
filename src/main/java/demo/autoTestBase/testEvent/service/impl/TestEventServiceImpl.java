package demo.autoTestBase.testEvent.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfFlowDTO;
import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.common.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.common.pojo.type.AutomationTestFlowResultType;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.type.BingDemoSearchFlowType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.scriptCore.bingDemo.servcie.BingDemoPrefixService;
import demo.scriptCore.scheduleClawing.complex.service.ScheduleClawingPrefixService;

@Service
public class TestEventServiceImpl extends TestEventCommonService implements TestEventService {

	@Autowired
	private BingDemoPrefixService searchingDemoService;
	@Autowired
	private ScheduleClawingPrefixService scheduleClawingPrefixService;
	

	@Override
	public TestEventBO reciveTestEventAndRun(AutomationTestInsertEventDTO dto) {
		if (dto == null) {
			return new TestEventBO();
		}
		
		TestEventBO tbo = null;
		
		if(checkIsFailLimitEvent(dto)) {
			return new TestEventBO();
		}

		if (!canInsertRuningEvent()) {
			return new TestEventBO();
		}

		if (TestModuleType.ATDemo.getId().equals(dto.getTestModuleType())) {
			BingDemoSearchFlowType caseType = BingDemoSearchFlowType.getType(dto.getFlowType());
			tbo = receiveAndBuildTestEventBO(dto, caseType.getFlowName(), caseType.getId());
		} else if (TestModuleType.SCHEDULE_CLAWING.getId().equals(dto.getTestModuleType())) {
			ScheduleClawingType caseType = ScheduleClawingType.getType(dto.getFlowType());
			tbo = receiveAndBuildTestEventBO(dto, caseType.getFlowName(), caseType.getId());
		}

		tbo = runEvent(tbo);
		if(!tbo.isPass()) {
			constantService.addFailedTestResult(tbo.getEventId());
		}
		
		return tbo;
	}

	private TestEventBO buildTestEventBOPreHandle(AutomationTestInsertEventDTO dto) {
		TestEventBO tbo = new TestEventBO();
		tbo.setStartTime(LocalDateTime.now());
		
		JsonReportOfFlowDTO reportDTO = new JsonReportOfFlowDTO();
		tbo.setReport(reportDTO);

		return tbo;
	}
	
	private TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto, String flowName, Long eventId) {
		TestEventBO bo = buildTestEventBOPreHandle(dto);

		TestModuleType modultType = TestModuleType.getType(dto.getTestModuleType());
		bo.setModuleType(modultType);
		bo.setFlowName(flowName);
		bo.setFlowId(eventId);
		bo.setEventId(dto.getTestEventId());
		bo.setAppointment(dto.getAppointment());
		bo.setParamStr(dto.getParamStr());

		return bo;
	}
	
	private TestEventBO runEvent(TestEventBO event) {
		TestEventBO runResult = null;
		if (constantService.getBreakFlag()) {
			runResult = new TestEventBO();
			AutomationTestCaseResult caseResult = new AutomationTestCaseResult();
			caseResult.setResultType(AutomationTestFlowResultType.BREAK_BY_FLAG);
			caseResult.setCaseName("Running break");
			runResult.getCaseResultList().add(caseResult);
			return runResult;
		}

		
		if (event.getAppointment() == null || event.getAppointment().isBefore(LocalDateTime.now())) {
			startEvent(event);
			try {
				runResult = runSubEvent(event);
			} catch (Exception e) {
				runResult = new TestEventBO();
			}
			endEvent(event);
		}

		return runResult;
	}

	private TestEventBO runSubEvent(TestEventBO te) {
		Long moduleId = te.getModuleType().getId();
		if (moduleId == null) {
			return null;
		}

		if (TestModuleType.ATDemo.getId().equals(moduleId)) {
			return searchingDemoService.runSubEvent(te);
		} else if (TestModuleType.SCHEDULE_CLAWING.getId().equals(moduleId)) {
			return scheduleClawingPrefixService.runSubEvent(te);
		}
		return new TestEventBO();
	}

	@Override
	public boolean checkExistsRuningEvent() {
		return existsRuningEvent();
	}
	
	@PostConstruct
	@Override
	public void fixRuningEventStatusByManual() {
		fixRuningEventStatus();
	}
	
	@Override
	public List<String> getRunningEventNameList() {
		return super.getRunningEventList();
	}

	private boolean checkIsFailLimitEvent(AutomationTestInsertEventDTO dto) {
		Long eventId = dto.getTestEventId();
		if (!constantService.getFailedTestResultMap().containsKey(eventId)) {
			return false;
		}
		
		int failCcounting = constantService.getFailedTestResultMap().get(eventId).size();
		
		return constantService.getEventFailLimitCounting() <= failCcounting;
	}
	
	@Override
	public void cleanExpiredFailEventCounting() {
		List<LocalDateTime> tmpTimeList = null;
		LocalDateTime limitLiveTime = LocalDateTime.now().minusMinutes(constantService.getFailCountLiveMinutes());
		
		for(Long key : constantService.getFailedTestResultMap().keySet()) {
			tmpTimeList = constantService.getFailedTestResultMap().get(key);
			for(int i = 0; i < tmpTimeList.size(); i++) {
				if(tmpTimeList.get(i).isBefore(limitLiveTime)) {
					tmpTimeList.remove(i);
					i--;
				}
			}
		}
	}

	@Override
	public Boolean setBreakFlag(Integer flag) {
		constantService.setBreakFlag("1".equals(String.valueOf(flag)));
		return constantService.getBreakFlag();
	}
}
