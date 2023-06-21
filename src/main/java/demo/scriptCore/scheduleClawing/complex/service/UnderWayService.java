package demo.scriptCore.scheduleClawing.complex.service;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface UnderWayService {

	TestEventBO monthTest(TestEventBO tbo);

	TestEventBO trainProject(TestEventBO tbo);

	void checkAndSendCourseDoneRequest();

}
