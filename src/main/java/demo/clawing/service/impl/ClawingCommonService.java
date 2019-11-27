package demo.clawing.service.impl;

import demo.baseCommon.service.CommonService;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.type.TestModuleType;

public abstract class ClawingCommonService extends CommonService {

	protected TestEvent buildTestEvent(TestModuleType t, Long caseId) {
		if(t == null || caseId == null) {
			return null;
		}
		TestEvent te = new TestEvent();
		te.setCaseId(t.getId());
		te.setModuleId(t.getId());
		te.setId(snowFlake.getNextId());
		te.setEventName(t.getEventName());
		return te;
	}
	
}
