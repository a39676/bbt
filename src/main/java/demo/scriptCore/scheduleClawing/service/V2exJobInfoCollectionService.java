package demo.scriptCore.scheduleClawing.service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface V2exJobInfoCollectionService {

	TestEventBO clawing(TestEventBO tbo);

	TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto);

	void deleteOldUrls();

}
