package demo.scriptCore.scheduleClawing.cryptoCoin.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.cryptoCoin.pojo.type.CryptoCoinFlowType;
import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.scheduleClawing.cryptoCoin.service.CryptoCoinPrefixService;
import demo.scriptCore.scheduleClawing.cryptoCoin.service.CryptoCoinPriceService;

@Service
public class CryptoCoinPrefixServiceImpl extends AutomationTestCommonService
		implements CryptoCoinPrefixService {

	@Autowired
	private CryptoCoinPriceService cryptoCoinPriceService;

	@Override
	public TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto) {
		TestEventBO bo = buildTestEventBOPreHandle(dto);

		TestModuleType modultType = TestModuleType.getType(dto.getTestModuleType());
		bo.setModuleType(modultType);
		CryptoCoinFlowType caseType = CryptoCoinFlowType.getType(dto.getFlowType());
		bo.setFlowName(caseType.getFlowName());
		bo.setFlowId(caseType.getId());
		bo.setEventId(dto.getTestEventId());
		bo.setAppointment(dto.getAppointment());
		bo.setParamStr(dto.getParamStr());

		return bo;
	}

	@Override
	public TestEventBO runSubEvent(TestEventBO bo) {
		Long flowId = bo.getFlowId();

		if (CryptoCoinFlowType.DAILY_DATA.getId().equals(flowId)) {
			return cryptoCoinPriceService.cryptoCoinDailyDataAPI(bo);
		}

		return new TestEventBO();
	}
}
