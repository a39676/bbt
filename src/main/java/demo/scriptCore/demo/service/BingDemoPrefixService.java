package demo.scriptCore.demo.service;

import autoTest.testEvent.searchingDemo.pojo.result.InsertSearchingDemoEventResult;
import demo.scriptCore.demo.pojo.dto.InsertBingSearchDemoDTO;

public interface BingDemoPrefixService {

	InsertSearchingDemoEventResult insertSearchInHomeEvent(InsertBingSearchDemoDTO dto);
	
}
