package demo.clawing.localClawing.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface BossZhiPinLocalClawingService {

	InsertTestEventResult insertLocalClawingEvent();

	CommonResultBBT localClawing(TestEvent te);

}
