package demo.clawing.demo.service.impl;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.pojo.bo.XpathBuilderBO;
import at.pojo.dto.JsonReportDTO;
import at.pojo.dto.TakeScreenshotSaveDTO;
import at.pojo.result.ScreenshotSaveResult;
import autoTest.testEvent.pojo.dto.InsertSearchingDemoTestEventDTO;
import autoTest.testEvent.pojo.result.InsertSearchingDemoEventResult;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.demo.pojo.type.SearchingDemoCaseType;
import demo.clawing.demo.service.BaiduDemoService;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.impl.SeleniumCommonService;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service
public class BaiduDemoServiceImpl extends SeleniumCommonService implements BaiduDemoService {
	
	private String eventName = "baidu search demo";
	
	@Autowired
	private SeleniumGlobalOptionService globalOptionService;

	private TestEvent buildTestEvent() {
		SearchingDemoCaseType t = SearchingDemoCaseType.baiduDemo;
		return buildTestEvent(TestModuleType.ATDemo, t.getId(), t.getEventName());
	}
	
	private String getScreenshotSaveingPath() {
		return globalOptionService.getScreenshotSavingFolder() + File.separator + eventName;
	}
	
	private String getReportOutputPath() {
		return globalOptionService.getReportOutputFolder() + File.separator + eventName;
	}
	
	public InsertTestEventResult insertclawingEvent(InsertSearchingDemoTestEventDTO dto) {
		TestEvent te = buildTestEvent();
		te.setRemark(dto.getSearchKeyWord());
		te.setAppointment(dto.getAppointment());
		return testEventService.insertTestEvent(te);
	}
	
	@Override
	public CommonResultBBT clawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		JsonReportDTO reportDTO = new JsonReportDTO();
		
		String screenshotPath = getScreenshotSaveingPath();
		String reportOutputFolderPath = getReportOutputPath();
		
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		WebDriver d = webDriverService.buildFireFoxWebDriver();

		String mainUrl = "https://www.baidu.com/";
		
		
		try {
			try {
				d.get(mainUrl);
				jsonReporter.appendContent(reportDTO, "打开: " + mainUrl);
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "访问超时");
			}
			
			XpathBuilderBO x = new XpathBuilderBO();
			
			TakeScreenshotSaveDTO screenshotDTO = new TakeScreenshotSaveDTO();
			screenshotDTO.setDriver(d);
			ScreenshotSaveResult screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath, null);
			
			UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
			x.start("input").addAttribute("id", "kw");
			WebElement keywordInput = d.findElement(By.xpath(x.getXpath()));
			keywordInput.click();
			keywordInput.clear();
			keywordInput.sendKeys(te.getRemark());
			
			if(StringUtils.isBlank(te.getRemark())) {
				jsonReporter.appendContent(reportDTO, "输入空白关键词");
			} else {
				jsonReporter.appendContent(reportDTO, "输入关键词: " + te.getRemark());
			}
			
			screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath, null);
			uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
			x.start("input").addAttribute("id", "su");
			WebElement searchButton = d.findElement(By.xpath(x.getXpath()));
			searchButton.click();
			
			jsonReporter.appendContent(reportDTO, "点击搜索");
			
			screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath, null);
			uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
			Thread.sleep(1500L);
			
			/*
			 * TODO
			 */
//			x.start("a").addAttribute("class", "OP_LOG_LINK");
//			List<WebElement> orgAList = d.findElements(By.xpath(x.getXpath()));
//			jsonReporter.appendContent(reportDTO, "找到: " + orgAList.size() + "个官网标记");
			
			jsonReporter.appendContent(reportDTO, "完成");
			
			r.setIsSuccess();
		} catch (Exception e) {
			jsonReporter.appendContent(reportDTO, e.getMessage());
			
		} finally {
			r.setMessage(reportDTO.getContent());
			tryQuitWebDriver(d, reportDTO);
			String reportOutputPath = reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json";
			if(jsonReporter.outputReport(reportDTO, reportOutputPath)) {
				updateTestEventReportPath(te, reportOutputPath);
			}
		}
		
		return r;
	}

	@Override
	public InsertSearchingDemoEventResult insert(InsertSearchingDemoTestEventDTO dto) {
		InsertTestEventResult r = insertclawingEvent(dto);
		int waitingEventCount = testEventService.countWaitingEvent();
		Long eventId = r.getNewTestEventId();
		
		InsertSearchingDemoEventResult ir = new InsertSearchingDemoEventResult();
		ir.setCode(r.getCode());
		ir.setSuccess(r.isSuccess());
		ir.setMessage(r.getMessage());
		ir.setWaitingEventCount(waitingEventCount);
		ir.setEventId(eventId);

		return ir;
	}
	
}
