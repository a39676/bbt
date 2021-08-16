package demo.clawing.demo.service;

import autoTest.testEvent.searchingDemo.pojo.dto.ATBingDemoDTO;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;

public interface BingDemoService {

	CommonResult testing(TestEvent te);

	InsertTestEventResult insertSearchDemoEvent();

	InsertTestEventResult insertSearchDemoEvent(ATBingDemoDTO dto);

}
