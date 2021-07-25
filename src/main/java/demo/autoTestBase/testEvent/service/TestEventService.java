package demo.autoTestBase.testEvent.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import net.sf.json.JSONObject;

public interface TestEventService {

	InsertTestEventResult insertTestEvent(TestEvent po);
	InsertTestEventResult insertTestEvent(TestEvent po, JSONObject paramJson);
	
	int countWaitingEvent();

	int updateTestEventReportPath(TestEvent te, String reportPath);

	CommonResultBBT reciveTestEventAndRun(TestEvent te);

	boolean checkExistsRuningEvent();

	void fixRuningEventStatusManual();

	void deleteOldTestEvent();

}
