package demo.dailySign.service;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.result.InsertTestEventResult;

public interface QuQiDailySignService {

	InsertTestEventResult insertclawingEvent();

	CommonResultBBT clawing(TestEvent te);

}
