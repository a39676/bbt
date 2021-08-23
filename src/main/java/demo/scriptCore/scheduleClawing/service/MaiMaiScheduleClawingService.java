package demo.scriptCore.scheduleClawing.service;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;

public interface MaiMaiScheduleClawingService {

	InsertTestEventResult insertClawingEvent();

	/**
	 * maimai 定时任务bug
	WebDriver 退出异常, 经常导致服务器 cpu 使用暴增 & 崩溃; 原因未明;
	严重怀疑服务器内存不足导致崩溃
	 * @param te
	 * @return
	 */
	TestEventBO clawing(TestEvent te);

}
