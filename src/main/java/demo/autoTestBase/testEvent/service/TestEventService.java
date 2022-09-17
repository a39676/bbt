package demo.autoTestBase.testEvent.service;

import java.util.List;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface TestEventService {

	/**
	 * 从 MQ 获取任务
	 */
	TestEventBO reciveTestEventAndRun(AutomationTestInsertEventDTO dto);

	boolean checkExistsRuningEvent();

	void fixRuningEventStatusByManual();

	void cleanExpiredFailEventCounting();

	List<String> getRunningEventNameList();

	Boolean setBreakFlag(Integer flag);

}
