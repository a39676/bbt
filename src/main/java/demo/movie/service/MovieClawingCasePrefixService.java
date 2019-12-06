package demo.movie.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface MovieClawingCasePrefixService {

	CommonResultBBT runSubEvent(TestEvent te);

}