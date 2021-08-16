package demo.clawing.demo.service;

import autoTest.testEvent.searchingDemo.pojo.dto.ATBingDemoDTO;
import autoTest.testEvent.searchingDemo.pojo.result.InsertSearchingDemoEventResult;

public interface SearchingDemoManagerService {

	InsertSearchingDemoEventResult insert(ATBingDemoDTO dto);

}
