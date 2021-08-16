package demo.clawing.collecting.jandan.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;

public interface JianDanCollectingService {

	InsertTestEventResult insertCollectingJianDanEvent();

	CommonResult collecting(TestEvent te);

}
