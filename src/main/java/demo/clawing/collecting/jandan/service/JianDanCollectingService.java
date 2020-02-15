package demo.clawing.collecting.jandan.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface JianDanCollectingService {

	InsertTestEventResult insertCollectingJianDanEvent();

	CommonResultBBT collecting(TestEvent te);

}
