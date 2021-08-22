package demo.scriptCore.scheduleClawing.service;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;

public interface CryptoCoinPriceService {

	InsertTestEventResult insertCryptoCoinDailyDataCollectEvent(CryptoCoinDailyDataQueryDTO dto);

	TestEventBO cryptoCoinDailyDataAPI(TestEvent te);

}
