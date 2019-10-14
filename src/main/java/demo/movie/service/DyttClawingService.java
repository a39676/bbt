package demo.movie.service;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.testCase.pojo.po.TestEvent;

/**
 * 电影天堂
 */
public interface DyttClawingService {

	CommonResultBBT clawing(TestEvent te);

	Integer insertclawingEvent();

}
