package demo.scriptCore.scheduleClawing.jobInfo.service;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface V2exJobInfoCollectionService {

	TestEventBO clawing(TestEventBO tbo);

	TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto);

}
