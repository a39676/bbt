package demo.clawing.dailySign.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface QuQiDailySignService {

	InsertTestEventResult insertDailySignEvent();

	CommonResultBBT clawing(TestEvent te);

}
