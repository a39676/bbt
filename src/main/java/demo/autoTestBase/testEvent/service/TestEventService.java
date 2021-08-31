package demo.autoTestBase.testEvent.service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface TestEventService {

	/**
	 * 从 MQ 获取任务
	 */
	TestEventBO reciveTestEventAndRun(AutomationTestInsertEventDTO dto);

	/**
	 * 应用自建任务
	 */
	TestEventBO receiveTestEventAndRun(TestEventBO bo);
	
	boolean checkExistsRuningEvent();

	void fixRuningEventStatusManual();

}
