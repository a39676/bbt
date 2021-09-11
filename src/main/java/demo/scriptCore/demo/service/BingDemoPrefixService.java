package demo.scriptCore.demo.service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.demo.pojo.dto.InsertBingSearchDemoDTO;

public interface BingDemoPrefixService {

	void insertSearchInHomeEvent(InsertBingSearchDemoDTO dto);

	TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto);
	
}
