package demo.clawing.bingDemo.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.bingDemo.po.result.InsertBingDemoEventResult;
import demo.clawing.bingDemo.pojo.dto.BingDemoDTO;

public interface BingDemoService {

	InsertTestEventResult insertclawingEvent(String keyword);

	CommonResultBBT clawing(TestEvent te);

	InsertBingDemoEventResult demo(BingDemoDTO dto);

}
