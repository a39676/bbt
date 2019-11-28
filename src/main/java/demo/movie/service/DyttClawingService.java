package demo.movie.service;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.result.InsertTestEventResult;

/**
 * 电影天堂
 */
public interface DyttClawingService {

	CommonResultBBT clawing(TestEvent te);

	InsertTestEventResult insertclawingEvent();

}
