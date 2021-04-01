package demo.autoTestBase.testEvent.service;

import java.time.LocalDateTime;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import net.sf.json.JSONObject;

public interface TestEventService {

	InsertTestEventResult insertTestEvent(TestEvent po);
	InsertTestEventResult insertTestEvent(TestEvent po, JSONObject paramJson);
	
	TestEvent runNewTestEvent(TestEventBO bo);

	int countWaitingEvent();

	int updateTestEventReportPath(TestEvent te, String reportPath);

	CommonResultBBT sendFailReports();

	CommonResultBBT sendFailReports(LocalDateTime startTime, LocalDateTime endTime);

	CommonResultBBT reciveTestEventAndRun(TestEvent te);

	boolean checkExistsRuningEvent();

	void fixRuningEventStatusManual();

	void deleteOldTestEvent();

}
