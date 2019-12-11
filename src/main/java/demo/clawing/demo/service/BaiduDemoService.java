package demo.clawing.demo.service;

import autoTest.testEvent.pojo.dto.InsertSearchingDemoTestEventDTO;
import autoTest.testEvent.pojo.result.InsertSearchingDemoEventResult;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface BaiduDemoService {

	CommonResultBBT clawing(TestEvent te);

	InsertSearchingDemoEventResult insert(InsertSearchingDemoTestEventDTO dto);

}
