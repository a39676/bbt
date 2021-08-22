package demo.scriptCore.collecting.jandan.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.baseCommon.service.CommonService;
import demo.scriptCore.collecting.jandan.pojo.type.CollectingFlowType;
import demo.scriptCore.collecting.jandan.service.JianDanCollectingService;

@Service
public class ClawCollectPrefixServiceImpl extends CommonService implements RunSubEventPrefixService {

	@Autowired
	private JianDanCollectingService jianDanCollectingService;
	
	@Override
	public TestEventBO runSubEvent(TestEvent te) {
		Long caseId = te.getFlowId();
		
		if (CollectingFlowType.jianDan.getId().equals(caseId)) {
			return jianDanCollectingService.collecting(te);
		} 
		return new TestEventBO();
	}
}
