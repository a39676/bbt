package demo.clawing.service.impl;

import demo.baseCommon.service.CommonService;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.type.TestCaseType;

public abstract class ClawingCommonService extends CommonService {

	protected TestEvent buildTestEvent(TestCaseType t) {
		TestEvent te = new TestEvent();
		te.setCaseId(t.getId());
		te.setId(snowFlake.getNextId());
		te.setEventName(t.getEventName());
		return te;
	}
	
}
