package demo.scriptCore.demo.service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.searchingDemo.pojo.result.InsertSearchingDemoEventResult;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.scriptCore.demo.pojo.dto.InsertBingSearchDemoDTO;

public interface BingDemoPrefixService {

	InsertSearchingDemoEventResult insertSearchInHomeEvent(InsertBingSearchDemoDTO dto);

	InsertTestEventResult insertSearchInHomeEvent(AutomationTestInsertEventDTO dto);
	
}
