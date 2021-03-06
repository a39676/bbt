package demo.clawing.scheduleClawing.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;

public interface CryptoCoinPriceService {

	InsertTestEventResult insertCryptoCoinMinuteDataCollectEvent();

	CommonResultBBT cryptoCoinMinuteDataAPI(TestEvent te);

	InsertTestEventResult insertCryptoCoinDailyDataCollectEvent(CryptoCoinDailyDataQueryDTO dto);

	CommonResultBBT cryptoCoinDailyDataAPI(TestEvent te);

}
