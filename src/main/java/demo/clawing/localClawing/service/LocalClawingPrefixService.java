package demo.clawing.localClawing.service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface LocalClawingPrefixService {

	CommonResultBBT runSubEvent(TestEvent te);

}
