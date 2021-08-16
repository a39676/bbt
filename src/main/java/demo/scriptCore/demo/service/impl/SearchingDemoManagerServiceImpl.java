package demo.scriptCore.demo.service.impl;

import org.springframework.stereotype.Service;

import autoTest.testEvent.searchingDemo.pojo.dto.ATBingDemoDTO;
import autoTest.testEvent.searchingDemo.pojo.result.InsertSearchingDemoEventResult;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.scriptCore.demo.pojo.type.testEvent.SearchingDemoEventType;
import demo.scriptCore.demo.service.SearchingDemoManagerService;
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
	public InsertSearchingDemoEventResult insert(ATBingDemoDTO dto) {
		InsertSearchingDemoEventResult ir = new InsertSearchingDemoEventResult();

		SearchingDemoEventType t = SearchingDemoEventType.bingDemo;
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
	
	private InsertTestEventResult insertclawingEvent(ATBingDemoDTO dto, SearchingDemoEventType t) {
		TestEvent te = buildTestEvent(t);
		// TODO 插入任务时, 建议以文件形式, 另存参数, 并配定时清除参数文件的任务
		te.setRemark(dto.getSearchKeyWord());
		te.setAppointment(dto.getAppointment());
		te.setCaseId(SearchingDemoEventType.bingDemo.getId());
		return testEventService.insertTestEvent(te);
	}
}
