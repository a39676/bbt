package demo.scriptCore.scheduleClawing.complex.service;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface ScheduleClawingPrefixService {

	TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto);

	TestEventBO runSubEvent(TestEventBO te);

}
