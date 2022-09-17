package demo.scriptCore.scheduleClawing.currencyExchangeRate.service;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface CurrencyExchangeRatePrefixService {

	TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto);

	TestEventBO runSubEvent(TestEventBO bo);

}
