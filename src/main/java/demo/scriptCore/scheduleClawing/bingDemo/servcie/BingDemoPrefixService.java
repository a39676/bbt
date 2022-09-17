package demo.scriptCore.scheduleClawing.bingDemo.servcie;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.scheduleClawing.bingDemo.pojo.dto.InsertBingSearchDemoDTO;

public interface BingDemoPrefixService {

	void insertSearchInHomeEvent(InsertBingSearchDemoDTO dto);

	TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto);

	TestEventBO runSubEvent(TestEventBO te);
	
}
