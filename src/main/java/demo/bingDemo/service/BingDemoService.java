package demo.bingDemo.service;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.bingDemo.po.result.InsertBingDemoEventResult;
import demo.bingDemo.pojo.dto.BingDemoDTO;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.result.InsertTestEventResult;

public interface BingDemoService {

	InsertTestEventResult insertclawingEvent(String keyword);

	CommonResultBBT clawing(TestEvent te);

	InsertBingDemoEventResult demo(BingDemoDTO dto);

}
