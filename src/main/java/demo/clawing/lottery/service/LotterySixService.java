package demo.clawing.lottery.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface LotterySixService {

	InsertTestEventResult insertTaskEvent();

	CommonResultBBT clawTask(TestEvent te);

}
