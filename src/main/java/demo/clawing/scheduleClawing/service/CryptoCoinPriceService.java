package demo.clawing.scheduleClawing.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface CryptoCoinPriceService {

	InsertTestEventResult insertNewCryptoCoinPriceEvent();

	CommonResultBBT cryptoCoinNewPriceAPI(TestEvent te);

	InsertTestEventResult insertHistoryCryptoCoinPriceEvent();

	CommonResultBBT cryptoCoinHistoryPriceAPI(TestEvent te);

}
