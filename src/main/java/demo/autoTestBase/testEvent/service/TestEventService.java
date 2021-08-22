package demo.autoTestBase.testEvent.service;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import net.sf.json.JSONObject;

public interface TestEventService {

	InsertTestEventResult insertExecuteTestEvent(TestEvent po);
	InsertTestEventResult insertTestEvent(TestEvent po, JSONObject paramJson);
	
	int countWaitingEvent();

	int updateTestEventReportPath(TestEvent te, String reportPath);

	TestEventBO reciveTestEventAndRun(TestEvent te);

	boolean checkExistsRuningEvent();

	void fixRuningEventStatusManual();

	void deleteOldTestEvent();

}
