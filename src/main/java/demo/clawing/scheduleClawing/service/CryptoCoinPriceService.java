package demo.clawing.scheduleClawing.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface CryptoCoinPriceService {

	InsertTestEventResult insertHistoryCryptoCoinPriceEvent();

	CommonResultBBT cryptoCoinHistoryPriceAPI(TestEvent te);

}
