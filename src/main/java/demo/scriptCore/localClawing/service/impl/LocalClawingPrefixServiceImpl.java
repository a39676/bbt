package demo.scriptCore.localClawing.service.impl;

import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.baseCommon.service.CommonService;
import demo.scriptCore.localClawing.pojo.type.LocalClawingCaseType;

@Service
public class LocalClawingPrefixServiceImpl extends CommonService implements RunSubEventPrefixService {


	@Override
	public CommonResult runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();

		if (LocalClawingCaseType.demo.getId().equals(caseId)) {
			
		}
		return new CommonResult();
	}

}
