package demo.clawing.demo.service.impl;

import org.springframework.stereotype.Service;

import autoTest.testEvent.pojo.dto.InsertSearchingDemoTestEventDTO;
import autoTest.testEvent.pojo.result.InsertSearchingDemoEventResult;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.clawing.demo.pojo.type.testEvent.SearchingDemoEventType;
import demo.clawing.demo.service.SearchingDemoManagerService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;

@Service
public class SearchingDemoManagerServiceImpl extends SeleniumCommonService implements SearchingDemoManagerService {

	private TestEvent buildTestEvent(SearchingDemoEventType t) {
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(TestModuleType.ATDemo);
		bo.setCaseId(t.getId());
		bo.setEventName(t.getCaseName());
		return buildTestEvent(bo);
	}
	
	@Override
	public InsertSearchingDemoEventResult insert(InsertSearchingDemoTestEventDTO dto) {
		InsertSearchingDemoEventResult ir = new InsertSearchingDemoEventResult();

		SearchingDemoEventType t = SearchingDemoEventType.getType(dto.getCaseId());
		if(t == null) {
			ir.failWithMessage("数据异常");
			return ir;
		}
		
		InsertTestEventResult r = insertclawingEvent(dto, t);
		int waitingEventCount = testEventService.countWaitingEvent();
		Long eventId = r.getNewTestEventId();
		
		ir.setCode(r.getCode());
		ir.setSuccess(r.isSuccess());
		ir.setMessage(r.getMessage());
		ir.setWaitingEventCount(waitingEventCount);
		ir.setEventId(eventId);

		return ir;
	}
	
	private InsertTestEventResult insertclawingEvent(InsertSearchingDemoTestEventDTO dto, SearchingDemoEventType t) {
		TestEvent te = buildTestEvent(t);
		te.setRemark(dto.getSearchKeyWord());
		te.setAppointment(dto.getAppointment());
		te.setCaseId(dto.getCaseId());
		return testEventService.insertTestEvent(te);
	}
}
