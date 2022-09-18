package demo.scriptCore.scheduleClawing.educationInfo.service;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface EducationInfoCollectionService {

	TestEventBO clawing(TestEventBO tbo);

	void deleteOldUrls();

}
