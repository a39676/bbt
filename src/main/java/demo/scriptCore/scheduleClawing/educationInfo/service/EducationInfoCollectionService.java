package demo.scriptCore.scheduleClawing.educationInfo.service;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface EducationInfoCollectionService {

	TestEventBO clawing(TestEventBO tbo);

	TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto);

	void deleteOldUrls();

}
