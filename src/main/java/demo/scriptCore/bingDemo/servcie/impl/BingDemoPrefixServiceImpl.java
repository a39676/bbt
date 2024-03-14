package demo.scriptCore.bingDemo.servcie.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.type.BingDemoSearchFlowType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.bingDemo.servcie.BingDemoPrefixService;
import demo.scriptCore.bingDemo.servcie.BingDemoService;
import demo.selenium.service.impl.AutomationTestCommonService;

@Service
public class BingDemoPrefixServiceImpl extends AutomationTestCommonService
		implements BingDemoPrefixService {

	@Autowired
	private BingDemoService bingDemoService;
	
	@Override
	public TestEventBO runSubEvent(TestEventBO te) {
		Long flowId = te.getFlowId();
		if (flowId == null) {
			return null;
		}

		if (BingDemoSearchFlowType.SEARCH_IN_HOMEPAGE.getId().equals(flowId)) {
			return bingDemoService.searchInHomepage(te);
		} else if (BingDemoSearchFlowType.BING_IMAGE_DEMO.getId().equals(flowId)) {
//			return bingDemoService.testing(te);
		}
		return null;
	}
}
