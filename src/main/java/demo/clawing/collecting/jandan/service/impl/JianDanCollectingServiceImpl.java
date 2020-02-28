package demo.clawing.collecting.jandan.service.impl;

import java.io.File;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import at.pojo.dto.JsonReportDTO;
import at.pojo.dto.TakeScreenshotSaveDTO;
import at.pojo.result.ScreenshotSaveResult;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.collecting.jandan.pojo.type.CollectingCaseType;
import demo.clawing.collecting.jandan.service.JianDanCollectingService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service
public class JianDanCollectingServiceImpl extends SeleniumCommonService implements JianDanCollectingService {

	private static final TestModuleType TEST_MODULE_TYPE = TestModuleType.collecting;
	private static final CollectingCaseType TEST_CAST_TYPE = CollectingCaseType.jianDan;
	private static final String COLLECT_EVENT_NAME = "jiandanCollect";
	private static final String HOME_URL = "https://jandan.net/";
	private static final String PIC_URL = "https://jandan.net/pic";
	
	private TestEvent buildCollectingEvent() {
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(TEST_MODULE_TYPE);
		bo.setCaseId(TEST_CAST_TYPE.getId());
		bo.setEventName(TEST_CAST_TYPE.getEventName());
		return buildTestEvent(bo);
	}

	@Override
	public InsertTestEventResult insertCollectingJianDanEvent() {
		TestEvent te = buildCollectingEvent();
		return testEventService.insertTestEvent(te);
	}
	
	@Override
	public CommonResultBBT collecting(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		
		/*
		 * TODO
		 */
		
		String screenshotPath = getScreenshotSaveingPath(COLLECT_EVENT_NAME);
		String reportOutputFolderPath = getReportOutputPath(COLLECT_EVENT_NAME);
		
		JsonReportDTO reportDTO = new JsonReportDTO();
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());
		WebDriver d = null;
		
		try {
			d = webDriverService.buildFireFoxWebDriver();
			
			tryLoadPic(d, reportDTO);
			
		} catch (Exception e) {
			e.printStackTrace();
			TakeScreenshotSaveDTO screenshotDTO = new TakeScreenshotSaveDTO();
			screenshotDTO.setDriver(d);
			ScreenshotSaveResult screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath,
					null);
			
			UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			jsonReporter.appendContent(reportDTO, "异常: " + e.toString());
			
		} finally {
			tryQuitWebDriver(d, reportDTO);
			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
		}
		
		r.setIsSuccess();
		return r;
		
	}
	
	private void tryLoadPic(WebDriver d, JsonReportDTO reportDTO) {
		try {
			d.get(HOME_URL);
			jsonReporter.appendContent(reportDTO, "get home page");
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
			jsonReporter.appendContent(reportDTO, "get home page but timeout");
		}
		
		try {
			d.get(PIC_URL);
			jsonReporter.appendContent(reportDTO, "get home page");
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
			jsonReporter.appendContent(reportDTO, "get home page but timeout");
		}
	}
}
