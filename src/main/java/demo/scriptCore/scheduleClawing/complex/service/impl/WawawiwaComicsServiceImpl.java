package demo.scriptCore.scheduleClawing.complex.service.impl;
//package demo.clawing.scheduleClawing.service.impl;
//
//import java.io.File;
//import java.time.LocalDateTime;
//import java.util.List;
//
//import org.apache.commons.lang3.StringUtils;
//import org.openqa.selenium.By;
//import org.openqa.selenium.TimeoutException;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.springframework.stereotype.Service;
//
//import com.google.gson.Gson;
//
//import at.report.pojo.dto.JsonReportDTO;
//import at.screenshot.pojo.result.ScreenshotSaveResult;
//import at.xpath.pojo.bo.XpathBuilderBO;
//import autoTest.testModule.pojo.type.TestModuleType;
//import demo.autoTestBase.testEvent.pojo.po.TestEvent;
//import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
//import demo.baseCommon.pojo.result.CommonResultBBT;
//import demo.clawing.scheduleClawing.pojo.bo.WuYiJobClawingBO;
//import demo.clawing.scheduleClawing.pojo.type.ScheduleClawingType;
//import demo.clawing.scheduleClawing.service.WawawiwaComicsService;
//import demo.selenium.pojo.bo.BuildTestEventBO;
//import demo.selenium.service.impl.SeleniumCommonService;
//import image.pojo.result.ImageSavingResult;
//import selenium.pojo.constant.SeleniumConstant;
//
//@Service
//public class WawawiwaComicsServiceImpl extends SeleniumCommonService implements WawawiwaComicsService {
//
//	private TestModuleType testModuleType = TestModuleType.scheduleClawing;
//	private ScheduleClawingType wawawiwaComic = ScheduleClawingType.wawawiwaComic;
//	
//	private TestEvent buildNewEvent() {
//		BuildTestEventBO bo = new BuildTestEventBO();
//		bo.setTestModuleType(testModuleType);
//		bo.setCaseId(wawawiwaComic.getId());
//		bo.setEventName(wawawiwaComic.getEventName());
//		return buildTestEvent(bo);
//	}
//
//	@Override
//	public InsertTestEventResult insertNewEvent() {
//		TestEvent te = buildNewEvent();
//		return testEventService.insertTestEvent(te);
//	}
//	
//	public CommonResultBBT clawing(TestEvent te) {
//		CommonResultBBT r = new CommonResultBBT();
//
//		JsonReportDTO reportDTO = new JsonReportDTO();
//		WebDriver d = null;
//
//		String reportOutputFolderPath = getReportOutputPath(te.getEventName());
//		LocalDateTime screenshotImageValidTime = LocalDateTime.now().plusMonths(SeleniumConstant.maxHistoryMonth);
//
//		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());
//
//		try {
//
//			d = webDriverService.buildFireFoxWebDriver();
//
//			jsonReporter.appendContent(reportDTO, "create driver");
//			
//			try {
//				d.get("https://www.wawawiwacomics.com/");
//				jsonReporter.appendContent(reportDTO, "get home page");
//			} catch (TimeoutException e) {
//				jsUtil.windowStop(d);
//				jsonReporter.appendContent(reportDTO, "get home page but timeout");
//			}
//			
//			xPathBuilder.start("div").addClass("post-info");
//			
//			WebElement targetDiv = d.findElement(By.xpath(xPathBuilder.getXpath()));
//			
//			targetDiv.click();
//
//			r.setIsSuccess();
//
//		} catch (Exception e) {
//			e.printStackTrace();
////			String htmlStr = jsUtil.getHtmlSource(d);
//
//			ScreenshotSaveResult screenSaveResult = screenshot(d, te.getEventName());
//
//			ImageSavingResult uploadImgResult = saveImgToCX(screenSaveResult.getSavingPath(),
//					screenSaveResult.getFileName(), screenshotImageValidTime);
//			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
//			jsonReporter.appendContent(reportDTO, "异常: " + e.toString());
////			jsonReporter.appendContent(reportDTO, htmlStr);
//
//		} finally {
//			tryQuitWebDriver(d, reportDTO);
//			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
//				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
//			}
//		}
//
//		return r;
//	}
//}
