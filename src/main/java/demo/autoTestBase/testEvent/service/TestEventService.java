package demo.autoTestBase.testEvent.service;

import java.time.LocalDateTime;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface TestEventService {

	InsertTestEventResult insertTestEvent(TestEvent po);

	TestEvent runNewTestEvent(TestEventBO bo);

	/**
	 * 个别异常情况下, testEvent 数据表未记录正常结束, 会导致其他 event 无法进行, 需要手动修正状态
	 * @return 
	 */
	int fixMovieClawingTestEventStatus();

	void findTestEventAndRun();

	int countWaitingEvent();

	int updateTestEventReportPath(TestEvent te, String reportPath);

	CommonResultBBT sendFailReports();

	CommonResultBBT sendFailReports(LocalDateTime startTime, LocalDateTime endTime);

}
