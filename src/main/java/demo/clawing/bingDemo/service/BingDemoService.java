package demo.clawing.bingDemo.service;

import autoTest.testEvent.pojo.dto.InsertBingDemoTestEventDTO;
import autoTest.testEvent.pojo.result.InsertBingDemoEventResult;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface BingDemoService {

	CommonResultBBT clawing(TestEvent te);

	InsertBingDemoEventResult insert(InsertBingDemoTestEventDTO dto);

}
