package demo.clawing.demo.service;

import autoTest.testEvent.pojo.dto.InsertSearchingDemoTestEventDTO;
import autoTest.testEvent.pojo.result.InsertSearchingDemoEventResult;

public interface SearchingDemoManagerService {

	InsertSearchingDemoEventResult insert(InsertSearchingDemoTestEventDTO dto);

}
