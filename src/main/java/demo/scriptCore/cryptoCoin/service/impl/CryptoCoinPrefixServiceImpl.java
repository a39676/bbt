package demo.scriptCore.cryptoCoin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.cryptoCoin.pojo.type.CryptoCoinFlowType;
import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.cryptoCoin.service.CryptoCoinPrefixService;
import demo.scriptCore.scheduleClawing.service.CryptoCoinPriceService;

@Service
public class CryptoCoinPrefixServiceImpl extends AutomationTestCommonService
		implements CryptoCoinPrefixService, RunSubEventPrefixService {

	@Autowired
	private CryptoCoinPriceService cryptoCoinPriceService;

	@Override
	public TestEventBO buildTestEventBO(AutomationTestInsertEventDTO dto) {
		TestEventBO bo = buildTestEventBOPreHandle(dto, false);

		TestModuleType modultType = TestModuleType.getType(dto.getTestModuleType());
		bo.setModuleType(modultType);
		CryptoCoinFlowType caseType = CryptoCoinFlowType.getType(dto.getFlowType());
		bo.setFlowName(caseType.getFlowName());
		bo.setFlowId(caseType.getId());
		bo.setEventId(dto.getTestEventId());
		bo.setAppointment(dto.getAppointment());
		bo.setParamStr(dto.getParamStr());

		return testEventService.receiveTestEventAndRun(bo);
	}

	@Override
	public TestEventBO runSubEvent(TestEventBO bo) {
		Long caseId = bo.getFlowId();

		if (CryptoCoinFlowType.DAILY_DATA.getId().equals(caseId)) {
			return cryptoCoinPriceService.cryptoCoinDailyDataAPI(bo);
		}

		return new TestEventBO();
	}
}
