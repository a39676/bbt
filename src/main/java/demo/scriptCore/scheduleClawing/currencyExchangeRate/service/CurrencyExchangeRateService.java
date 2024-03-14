package demo.scriptCore.scheduleClawing.currencyExchangeRate.service;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface CurrencyExchangeRateService {

	TestEventBO getDailyData(TestEventBO tbo);

	AutomationTestInsertEventDTO sendDailyDataQuery();

	AutomationTestInsertEventDTO sendDataQuery(Boolean isDailyQuery);

}
