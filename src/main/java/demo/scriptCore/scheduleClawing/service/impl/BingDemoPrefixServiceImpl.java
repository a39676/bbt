package demo.scriptCore.scheduleClawing.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.searchingDemo.pojo.type.BingDemoSearchFlowType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.scheduleClawing.pojo.dto.InsertBingSearchDemoDTO;
import demo.scriptCore.scheduleClawing.service.BingDemoPrefixService;
import demo.scriptCore.scheduleClawing.service.BingDemoService;
import net.sf.json.JSONObject;

@Service
public class BingDemoPrefixServiceImpl extends AutomationTestCommonService
		implements BingDemoPrefixService {

	@Autowired
	private BingDemoService bingDemoService;

	@Override
	public TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto) {
		TestEventBO bo = buildTestEventBOPreHandle(dto);

		TestModuleType modultType = TestModuleType.getType(dto.getTestModuleType());
		bo.setModuleType(modultType);
		BingDemoSearchFlowType caseType = BingDemoSearchFlowType.getType(dto.getFlowType());
		bo.setFlowName(caseType.getFlowName());
		bo.setFlowId(caseType.getId());
		bo.setEventId(dto.getTestEventId());
		bo.setAppointment(dto.getAppointment());
		bo.setParamStr(dto.getParamStr());
		
		return bo;
	}

	@Override
	public void insertSearchInHomeEvent(InsertBingSearchDemoDTO dto) {
		AutomationTestInsertEventDTO mainDTO = new AutomationTestInsertEventDTO();
		BingDemoSearchFlowType t = BingDemoSearchFlowType.SEARCH_IN_HOMEPAGE;
		mainDTO.setTestModuleType(TestModuleType.ATDemo.getId());
		mainDTO.setFlowType(t.getId());
		mainDTO.setTestEventId(snowFlake.getNextId());
		mainDTO.setParamStr(JSONObject.fromObject(dto).toString());
		receiveAndBuildTestEventBO(mainDTO);
	}

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
