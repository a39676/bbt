package demo.autoTestBase.testEvent.service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import net.sf.json.JSONObject;

public interface TestEventService {

	InsertTestEventResult insertExecuteTestEvent(TestEvent po);
	
	TestEventBO reciveTestEventAndRun(TestEvent te);

	boolean checkExistsRuningEvent();

	void fixRuningEventStatusManual();

	void insertTestEvent(AutomationTestInsertEventDTO dto);

}
