package demo.autoTestBase.testEvent.service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface TestEventService {

	/**
	 * 从 MQ 获取任务
	 */
	TestEventBO reciveTestEventAndRun(AutomationTestInsertEventDTO dto);

	boolean checkExistsRuningEvent();

	void fixRuningEventStatusManual();

	void cleanExpiredFailEventCounting();

}
