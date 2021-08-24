package demo.scriptCore.demo.service.impl;

import java.nio.file.Path;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.searchingDemo.pojo.result.InsertSearchingDemoEventResult;
import autoTest.testEvent.searchingDemo.pojo.type.BingDemoSearchFlowType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.autoTestBase.testEvent.service.RunSubEventPrefixService;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.demo.pojo.dto.InsertBingSearchDemoDTO;
import demo.scriptCore.demo.service.BingDemoPrefixService;
import demo.scriptCore.demo.service.BingDemoService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import net.sf.json.JSONObject;

@Service
public class BingDemoPrefixServiceImpl extends AutomationTestCommonService
		implements BingDemoPrefixService, RunSubEventPrefixService {

	@Autowired
	private BingDemoService bingDemoService;

	@Override
	public InsertTestEventResult insertSearchInHomeEvent(AutomationTestInsertEventDTO dto) {
		BuildTestEventBO bo = new BuildTestEventBO();

		Long newEventId = dto.getTestEventId();
		if (newEventId == null) {
			newEventId = snowFlake.getNextId();
		}

		bo.setTestModuleType(TestModuleType.ATDemo);
		BingDemoSearchFlowType caseType = BingDemoSearchFlowType.getType(dto.getFlowType());
		bo.setFlowName(caseType.getFlowName());
		bo.setFlowId(caseType.getId());
		bo.setEventId(newEventId);

		Path paramSavePath = savingTestEventDynamicParam(bo, dto.getParamStr());
		bo.setParameterFilePath(paramSavePath.toString());
		TestEvent te = buildTestEvent(bo);
		te.setAppointment(dto.getAppointment());
		return testEventService.insertExecuteTestEvent(te);
	}

	@Override
	public InsertSearchingDemoEventResult insertSearchInHomeEvent(InsertBingSearchDemoDTO dto) {
		InsertSearchingDemoEventResult ir = new InsertSearchingDemoEventResult();

		BingDemoSearchFlowType t = BingDemoSearchFlowType.SEARCH_IN_HOMEPAGE;

		InsertTestEventResult r = insertSearchEvent(dto, t);
		int waitingEventCount = 0;
		Long eventId = r.getNewTestEventId();

		ir.setCode(r.getCode());
		ir.setSuccess(r.isSuccess());
		ir.setMessage(r.getMessage());
		ir.setWaitingEventCount(waitingEventCount);
		ir.setEventId(eventId);

		return ir;
	}

	private InsertTestEventResult insertSearchEvent(InsertBingSearchDemoDTO dto, BingDemoSearchFlowType t) {
		long newEventId = snowFlake.getNextId();
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(TestModuleType.ATDemo);
		bo.setFlowName(t.getFlowName());
		bo.setFlowId(t.getId());
		bo.setEventId(newEventId);
		JSONObject jsonParam = new JSONObject();
		jsonParam.put(dto.getBingSearchInHomePageDTO().getClass().getSimpleName(),
				JSONObject.fromObject(dto.getBingSearchInHomePageDTO()));
		Path paramSavePath = savingTestEventDynamicParam(bo, jsonParam.toString());
		bo.setParameterFilePath(paramSavePath.toString());
		TestEvent te = buildTestEvent(bo);
		te.setAppointment(dto.getAppointment());
		return testEventService.insertExecuteTestEvent(te);
	}

	@Override
	public TestEventBO runSubEvent(TestEvent te) {
		Long flowId = te.getFlowId();
		if (flowId == null) {
			return null;
		}

		if (BingDemoSearchFlowType.SEARCH_IN_HOMEPAGE.getId().equals(flowId)) {
			return bingDemoService.testing(te);
		}
		return null;
	}
}
