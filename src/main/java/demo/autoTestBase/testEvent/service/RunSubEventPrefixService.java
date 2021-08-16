package demo.autoTestBase.testEvent.service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;

public interface RunSubEventPrefixService {

	CommonResult runSubEvent(TestEvent te);
	
}
