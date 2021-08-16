package demo.scriptCore.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.scriptCore.demo.pojo.type.testEvent.SearchingDemoEventType;
import demo.scriptCore.demo.service.BingDemoService;

@Service
public class SearchingDemoPrefixServiceImpl implements RunSubEventPrefixService {

	@Autowired
	private BingDemoService bingDemoService;
	
	@Override
	public CommonResult runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();
		if(caseId == null) {
			return null;
		}
		
		if (SearchingDemoEventType.bingDemo.getId().equals(caseId)) {
			return bingDemoService.testing(te);
		}
		return null;
	}
}
