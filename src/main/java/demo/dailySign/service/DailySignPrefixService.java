package demo.dailySign.service;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.testCase.pojo.po.TestEvent;

public interface DailySignPrefixService {

	CommonResultBBT runSubEvent(TestEvent te);

}
