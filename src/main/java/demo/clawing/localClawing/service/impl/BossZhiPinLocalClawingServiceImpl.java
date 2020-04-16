package demo.clawing.localClawing.service.impl;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import at.report.pojo.dto.JsonReportDTO;
import at.screenshot.pojo.dto.TakeScreenshotSaveDTO;
import at.screenshot.pojo.result.ScreenshotSaveResult;
import at.xpath.pojo.bo.XpathBuilderBO;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.common.service.JobClawingCommonService;
import demo.clawing.localClawing.pojo.type.LocalClawingCaseType;
import demo.clawing.localClawing.service.BossZhiPinLocalClawingService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service
public class BossZhiPinLocalClawingServiceImpl extends JobClawingCommonService implements BossZhiPinLocalClawingService {

	private String eventName = "bossZhiPinLocalClawing";
	
	private TestModuleType testModuleType = TestModuleType.localClawing;
	private LocalClawingCaseType testCastType = LocalClawingCaseType.bossZhiPin;
	
	private TestEvent buildLocalClawingEvent() {
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(testModuleType);
		bo.setCaseId(testCastType.getId());
		bo.setEventName(testCastType.getEventName());
		return buildTestEvent(bo);
	}
	
	@Override
	public InsertTestEventResult insertLocalClawingEvent() {
		TestEvent te = buildLocalClawingEvent();
		return testEventService.insertTestEvent(te);
	}

	@Override
	public CommonResultBBT localClawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		
		JsonReportDTO reportDTO = new JsonReportDTO();
		WebDriver d = null;
		
		String screenshotPath = getScreenshotSaveingPath(eventName);
		String reportOutputFolderPath = getReportOutputPath(eventName);
		
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());
		
		try {
			
			d = webDriverService.buildOperaWebDriver();
			
//			d.get("https://www.zhipin.com/job_detail/46fe02ecaf4013f80HZ939u7EVQ~.html");
			
			try {
				d.get("https://login.zhipin.com/");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
			}
			
			boolean operatorFlag = false;
			tryLogin(d, reportDTO);
//			if(!operatorFlag) {
//				throw new Exception("登录异常");
//			}
			
			try {
				d.get("https://www.zhipin.com/?ka=header-home-logo");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
			}
			
			operatorFlag = inputSearch(d, reportDTO, "测试");
			if(!operatorFlag) {
				throw new Exception("搜索异常");
			}
			
			System.out.println("end");
			
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
		
		return r;
	}
	
	private boolean tryLogin(WebDriver d, JsonReportDTO reportDTO) throws InterruptedException {
		try {
			d.get("https://login.zhipin.com/?ka=header-login");
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
		}
		
		XpathBuilderBO x = new XpathBuilderBO();
		x.start("div").addClass("btn-switch login-tab-switch scan-switch");
		
		String switchXpath = x.getXpath();
		
		x.start("img").addAttribute("data-url", "https://www.zhipin.com/qrscan/dispatcher?qrId=");
		String qrCodeXpath = x.getXpath();
		
		WebElement switchButton = null;
		WebElement qrCodeImg = null;
		
		try {
			switchButton = d.findElement(By.xpath(switchXpath));
			qrCodeImg = d.findElement(By.xpath(qrCodeXpath));
		} catch (Exception e) {
			return false;
		}
		
		int switchCount = 0;
		while(!qrCodeImg.isDisplayed() && switchCount < 5) {
			switchButton.click();
			threadSleepRandomTime();
			qrCodeImg = d.findElement(By.xpath(qrCodeXpath));
			switchCount++;
		}
		
		if(!qrCodeImg.isDisplayed()) {
			return false;
		}
		
		Thread.sleep(10000L);
		
		try {
			qrCodeImg = null;
			qrCodeImg = d.findElement(By.xpath(qrCodeXpath));
		} catch (Exception e) {
		}
		
		if(qrCodeImg != null) {
			return false;
		} else {
			return true;
		}
	}

	private boolean inputSearch(WebDriver d, JsonReportDTO reportDTO, String keyword) {
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("input").addType("text").addAttribute("name", "query");
		
		WebElement searchInput = null;
		try {
			searchInput = d.findElement(By.xpath(x.getXpath()));
		} catch (Exception e) {
			return false;
		}
		
		searchInput.click();
		searchInput.clear();
		searchInput.sendKeys(keyword);
		
		searchInput.sendKeys(Keys.ENTER);
		
//		x.start("button").addClass("btn btn-search");
//		WebElement searchButton = null;
//		try {
//			searchButton = d.findElement(By.xpath(x.getXpath()));
//		} catch (Exception e) {
//			return false;
//		}
//		
//		searchButton.click();
		
		
		return true;
	}
}
