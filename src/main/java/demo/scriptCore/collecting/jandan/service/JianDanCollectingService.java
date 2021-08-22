package demo.scriptCore.collecting.jandan.service;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;

public interface JianDanCollectingService {

	InsertTestEventResult insertCollectingJianDanEvent();

	TestEventBO collecting(TestEvent te);

}
