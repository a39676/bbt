package demo.scriptCore.scheduleClawing.jobInfo.service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface EleDuckJobInfoCollectionService {

	TestEventBO clawing(TestEventBO tbo);

	TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto);

	void deleteOldUrls();

}
