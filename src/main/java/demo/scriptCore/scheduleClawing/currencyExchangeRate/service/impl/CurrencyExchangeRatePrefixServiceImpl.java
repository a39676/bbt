package demo.scriptCore.scheduleClawing.currencyExchangeRate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.currencyExchangeRate.pojo.type.CurrencyExchangeRateFlowType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.scheduleClawing.currencyExchangeRate.service.CurrencyExchangeRatePrefixService;
import demo.scriptCore.scheduleClawing.currencyExchangeRate.service.CurrencyExchangeRateService;

@Service
public class CurrencyExchangeRatePrefixServiceImpl extends AutomationTestCommonService
		implements CurrencyExchangeRatePrefixService {

	@Autowired
	private CurrencyExchangeRateService currencyExchangeRateService;
	
	@Override
	public TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto) {
		TestEventBO bo = buildTestEventBOPreHandle(dto);

		TestModuleType modultType = TestModuleType.getType(dto.getTestModuleType());
		bo.setModuleType(modultType);
		CurrencyExchangeRateFlowType caseType = CurrencyExchangeRateFlowType.getType(dto.getFlowType());
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

		if (CurrencyExchangeRateFlowType.DAILY_DATA.getId().equals(flowId)) {
			return currencyExchangeRateService.getDailyData(bo);
		}

		return new TestEventBO();
	}
}
