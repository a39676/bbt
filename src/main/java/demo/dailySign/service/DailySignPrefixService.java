package demo.dailySign.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface DailySignPrefixService {

	CommonResultBBT runSubEvent(TestEvent te);

}
