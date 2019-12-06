package demo.clawing.movie.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;

/**
 * 电影天堂
 */
public interface DyttClawingService {

	CommonResultBBT clawing(TestEvent te);

	InsertTestEventResult insertclawingEvent();

}
