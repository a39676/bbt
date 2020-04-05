package demo.autoTestBase.testEvent.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface RunSubEventPrefixService {

	CommonResultBBT runSubEvent(TestEvent te);
	
}
