package demo.scriptCore.scheduleClawing.cryptoCoin.service;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface CryptoCoinPrefixService {

	TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto);

	TestEventBO runSubEvent(TestEventBO bo);

}
