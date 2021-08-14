package demo.clawing.collecting.jandan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.baseCommon.service.CommonService;
import demo.clawing.collecting.jandan.pojo.type.CollectingCaseType;
import demo.clawing.collecting.jandan.service.JianDanCollectingService;

@Service
public class ClawCollectPrefixServiceImpl extends CommonService implements RunSubEventPrefixService {

	@Autowired
	private JianDanCollectingService jianDanCollectingService;
	
	@Override
	public CommonResult runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();
		
		if (CollectingCaseType.jianDan.getId().equals(caseId)) {
			return jianDanCollectingService.collecting(te);
		} 
		return new CommonResult();
	}
}
