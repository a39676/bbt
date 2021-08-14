package demo.clawing.demo.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.clawing.demo.pojo.dto.ATBingDemoDTO;

public interface BingDemoService {

	CommonResult testing(TestEvent te);

	InsertTestEventResult insertSearchDemoEvent();

	InsertTestEventResult insertSearchDemoEvent(ATBingDemoDTO dto);

}
