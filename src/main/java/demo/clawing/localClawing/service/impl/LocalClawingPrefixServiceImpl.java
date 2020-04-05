package demo.clawing.localClawing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.baseCommon.service.CommonService;
import demo.clawing.localClawing.pojo.type.LocalClawingCaseType;

@Service
public class LocalClawingPrefixServiceImpl extends CommonService implements RunSubEventPrefixService {

	@Autowired
	private BossZhiPinLocalClawingServiceImpl bossZhiPinLocalClawingServiceImpl;
	
	@Override
	public CommonResultBBT runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();
		
		if (LocalClawingCaseType.bossZhiPin.getId().equals(caseId)) {
			return bossZhiPinLocalClawingServiceImpl.localClawing(te);
		}
		return new CommonResultBBT();
	}
}
