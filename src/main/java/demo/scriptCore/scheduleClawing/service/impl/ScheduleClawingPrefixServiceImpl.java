package demo.scriptCore.scheduleClawing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.baseCommon.service.CommonService;
import demo.scriptCore.scheduleClawing.service.WuYiJobRefreshService;

@Service
public class ScheduleClawingPrefixServiceImpl extends CommonService implements RunSubEventPrefixService {

	@Autowired
	private WuYiJobRefreshService wuYiSign;
	
	@Override
	public TestEventBO runSubEvent(TestEventBO te) {
		Long caseId = te.getFlowId();

		if (ScheduleClawingType.WU_YI_JOB.getId().equals(caseId)) {
			return wuYiSign.clawing(te);
		}

		return new TestEventBO();
	}
}
