package demo.clawing.demo.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.demo.pojo.type.SearchingDemoCaseType;
import demo.clawing.demo.service.BaiduDemoService;
import demo.clawing.demo.service.BingDemoService;

@Service
public class SearchingDemoPrefixServiceImpl implements RunSubEventPrefixService {

	@Autowired
	private BingDemoService bingDemoService;
	@Autowired
	private BaiduDemoService baiduDemoService;
	
	@Override
	public CommonResultBBT runSubEvent(TestEvent te) {
		Long caseId = te.getCaseId();
		if(caseId == null) {
			return null;
		}
		
		if (SearchingDemoCaseType.baiduDemo.getId().equals(caseId)) {
			return baiduDemoService.clawing(te);
		} else if (SearchingDemoCaseType.bingDemo.getId().equals(caseId)) {
			return bingDemoService.clawing(te);
		}
		return null;
	}
}
