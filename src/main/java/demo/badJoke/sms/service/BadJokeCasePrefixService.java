package demo.badJoke.sms.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface BadJokeCasePrefixService {

	CommonResultBBT runSubEvent(TestEvent te);

}
