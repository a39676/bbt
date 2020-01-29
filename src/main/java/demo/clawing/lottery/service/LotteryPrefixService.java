package demo.clawing.lottery.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface LotteryPrefixService {

	CommonResultBBT runSubEvent(TestEvent te);

}
