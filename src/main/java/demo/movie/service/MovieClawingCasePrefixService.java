package demo.movie.service;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.testCase.pojo.po.TestEvent;

public interface MovieClawingCasePrefixService {

	CommonResultBBT runSubEvent(TestEvent te);

}