package demo.autoTestBase.testEvent.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.pojo.type.AutomationTestFlowResultType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.scriptCore.cryptoCoin.service.impl.CryptoCoinPrefixServiceImpl;
import demo.scriptCore.demo.service.BingDemoPrefixService;
import demo.scriptCore.demo.service.impl.BingDemoPrefixServiceImpl;
import demo.scriptCore.localClawing.service.impl.LocalClawingPrefixServiceImpl;
import demo.scriptCore.scheduleClawing.service.impl.ScheduleClawingPrefixServiceImpl;

@Service
public class TestEventServiceImpl extends TestEventCommonService implements TestEventService {

	@Autowired
	private BingDemoPrefixServiceImpl searchingDemoService;
	@Autowired
	private BingDemoPrefixService bingDemoPrefixService;
	@Autowired
	private ScheduleClawingPrefixServiceImpl scheduleClawingPrefixService;
	@Autowired
	private LocalClawingPrefixServiceImpl localClawingPrefixService;
	@Autowired
	private CryptoCoinPrefixServiceImpl cryptoCoinPrefixService;

	@Override
	public TestEventBO reciveTestEventAndRun(AutomationTestInsertEventDTO dto) {
		if (dto == null) {
			return new TestEventBO();
		}
		
		TestEventBO tbo = null;
		
		if(checkIsFailLimitEvent(dto)) {
			return new TestEventBO();
		}

		if (canInsertRuningEvent()) {
			return new TestEventBO();
		}

		if (TestModuleType.ATDemo.getId().equals(dto.getTestModuleType())) {
			tbo = bingDemoPrefixService.receiveAndBuildTestEventBO(dto);
		} else if (TestModuleType.CRYPTO_COIN.getId().equals(dto.getTestModuleType())) {
			tbo = cryptoCoinPrefixService.receiveAndBuildTestEventBO(dto);
		} else if (TestModuleType.SCHEDULE_CLAWING.getId().equals(dto.getTestModuleType())) {
//			return scheduleClawingPrefixService.
		}

		tbo.setEndTime(LocalDateTime.now());
		tbo = runEvent(tbo);
		if(!tbo.isPass()) {
			markFailedEvent(tbo.getEventId());
		}
		
		return tbo;
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
		} else if (TestModuleType.localClawing.getId().equals(moduleId)) {
			return localClawingPrefixService.runSubEvent(te);
		} else if (TestModuleType.CRYPTO_COIN.getId().equals(moduleId)) {
			return cryptoCoinPrefixService.runSubEvent(te);
		}
		return new TestEventBO();
	}

	@Override
	public boolean checkExistsRuningEvent() {
		return existsRuningEvent();
	}

	@Override
	public void fixRuningEventStatusByManual() {
		fixRuningEventStatus();
	}
	
	@Override
	public List<String> getRunningEventNameList() {
		return super.getRunningEventList();
	}

	private void markFailedEvent(Long eventId) {
		if (constantService.getFailedTestResultMap().containsKey(eventId)) {
			constantService.getFailedTestResultMap().get(eventId).add(LocalDateTime.now());
		} else {
			constantService.getFailedTestResultMap().put(eventId, Arrays.asList(LocalDateTime.now()));
		}
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
