package demo.scriptCore.scheduleClawing.service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.scheduleClawing.pojo.dto.InsertBingSearchDemoDTO;

public interface BingDemoPrefixService {

	void insertSearchInHomeEvent(InsertBingSearchDemoDTO dto);

	TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto);

	TestEventBO runSubEvent(TestEventBO te);
	
}
