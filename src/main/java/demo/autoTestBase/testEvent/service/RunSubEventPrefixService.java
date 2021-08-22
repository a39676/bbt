package demo.autoTestBase.testEvent.service;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;

public interface RunSubEventPrefixService {

	TestEventBO runSubEvent(TestEvent te);
	
}
