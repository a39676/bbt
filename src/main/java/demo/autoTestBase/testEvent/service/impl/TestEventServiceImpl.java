package demo.autoTestBase.testEvent.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
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

		if (existsRuningEvent()) {
			return new TestEventBO();
		}

		TestEventBO tbo = null;

		if (TestModuleType.ATDemo.getId().equals(dto.getTestModuleType())) {
			tbo = bingDemoPrefixService.receiveAndBuildTestEventBO(dto);
		} else if (TestModuleType.CRYPTO_COIN.getId().equals(dto.getTestModuleType())) {
			return cryptoCoinPrefixService.receiveAndBuildTestEventBO(dto);
		} else if (TestModuleType.SCHEDULE_CLAWING.getId().equals(dto.getTestModuleType())) {
//			return scheduleClawingPrefixService.
		}

		tbo.setEndTime(LocalDateTime.now());
		tbo = runEvent(tbo);
		
		return tbo;
	}

	private TestEventBO runEvent(TestEventBO event) {
		if (constantService.getBreakFlag()) {
			return new TestEventBO();
		}

		TestEventBO runResult = null;
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
	public void fixRuningEventStatusManual() {
		redisConnectService.setValByName(runningEventRedisKey, "false");
	}

}
