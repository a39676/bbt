package demo.badJoke.sms.service;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.testCase.pojo.po.TestEvent;

public interface BadJokeCasePrefixService {

	CommonResultBBT runSubEvent(TestEvent te);

}
