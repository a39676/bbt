package demo.scriptCore.collecting.jandan.service.impl;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import at.report.pojo.dto.JsonReportDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.scriptCore.collecting.jandan.pojo.type.CollectingFlowType;
import demo.scriptCore.collecting.jandan.service.JianDanCollectingService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;

@Service
public class JianDanCollectingServiceImpl extends SeleniumCommonService implements JianDanCollectingService {

	private static final TestModuleType TEST_MODULE_TYPE = TestModuleType.collecting;
	private static final CollectingFlowType TEST_FLOW_TYPE = CollectingFlowType.jianDan;
	@SuppressWarnings("unused")
	private static final String COLLECT_EVENT_NAME = "jiandanCollect";
	private static final String HOME_URL = "https://jandan.net/";
	private static final String PIC_URL = "https://jandan.net/pic";
	
	private TestEvent buildCollectingEvent() {
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(TEST_MODULE_TYPE);
		bo.setEventId(TEST_FLOW_TYPE.getId());
		bo.setFlowName(TEST_FLOW_TYPE.getEventName());
		return buildTestEvent(bo);
	}

	@Override
	public InsertTestEventResult insertCollectingJianDanEvent() {
		TestEvent te = buildCollectingEvent();
		return testEventService.insertExecuteTestEvent(te);
	}
	
	@Override
	public CommonResult collecting(TestEvent te) {
		CommonResult r = new CommonResult();
		
		/*
		 * TODO JianDanCollectingServiceImpl collecting
		 */
		
		
		
		r.setIsSuccess();
		return r;
		
	}
	
	@SuppressWarnings("unused")
	private void tryLoadPic(WebDriver d, JsonReportDTO reportDTO) {
		try {
			d.get(HOME_URL);
			reportService.appendContent(reportDTO, "get home page");
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
			reportService.appendContent(reportDTO, "get home page but timeout");
		}
		
		try {
			d.get(PIC_URL);
			reportService.appendContent(reportDTO, "get home page");
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
			reportService.appendContent(reportDTO, "get home page but timeout");
		}
	}
}
