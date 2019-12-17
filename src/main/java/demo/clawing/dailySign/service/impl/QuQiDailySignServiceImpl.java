package demo.clawing.dailySign.service.impl;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import at.pojo.bo.XpathBuilderBO;
import at.pojo.dto.JsonReportDTO;
import at.pojo.dto.TakeScreenshotSaveDTO;
import at.pojo.result.ScreenshotSaveResult;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.dailySign.pojo.type.DailySignCaseType;
import demo.clawing.dailySign.service.QuQiDailySignService;
import demo.selenium.service.impl.SeleniumCommonService;
import demo.selenium.service.pojo.bo.BuildTestEventBO;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service
public class QuQiDailySignServiceImpl extends SeleniumCommonService implements QuQiDailySignService {

	private String mainUrl = "https://www.quqi.com/";
	
	private String eventName = "quqiDailySign";
	
	private TestEvent buildTestEvent() {
		BuildTestEventBO bo = new BuildTestEventBO();
		DailySignCaseType t = DailySignCaseType.quqi;
		bo.setTestModuleType(TestModuleType.ATDemo);
		bo.setCaseId(t.getId());
		bo.setEventName(t.getEventName());
//		TODO
		bo.setParameterFilePath("");
		return buildTestEvent(bo);
	}
	
	@Override
	public InsertTestEventResult insertDailySignEvent() {
		TestEvent te = buildTestEvent();
		return testEventService.insertTestEvent(te);
	}
	
	@Override
	public CommonResultBBT clawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		
		JsonReportDTO reportDTO = new JsonReportDTO();
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		
		String screenshotPath = getScreenshotSaveingPath(eventName);
		String reportOutputFolderPath = getReportOutputPath(eventName);
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());
		
		try {
			try {
				d.get(mainUrl);
				jsonReporter.appendContent(reportDTO, "get");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}
			
			
			
			XpathBuilderBO x = new XpathBuilderBO();
			
			x.start("div").addAttribute("class", "pure-menu pure-menu-horizontal")
			.findChild("ul").addAttribute("class", "pure-menu-list")
			.findChild("li").addAttribute("class", "pure-menu-item")
			.findChild("button").addAttribute("class", "pure-button pure-button-primary login-btn");
			
			WebElement loginPageButton = d.findElement(By.xpath(x.getXpath()));
			
			try {
				loginPageButton.click();
				jsonReporter.appendContent(reportDTO, "click loginPageButton");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
			}
			
			jsonReporter.appendContent(reportDTO, "find login form");
			
			x.start("form").addAttribute("id", "phone-password-login-form")
			.findChild("div").addAttribute("class", "form-item")
			.findChild("div").addAttribute("class", "form-field")
			.findChild("input").addAttribute("name", "phone");
			
			WebElement phoneInput = d.findElement(By.xpath(x.getXpath()));
			phoneInput.click();
			phoneInput.clear();
			phoneInput.sendKeys("18022379435");
			
			jsonReporter.appendContent(reportDTO, "input username");
			
			x.start("form").addAttribute("id", "phone-password-login-form")
			.findChild("div").addAttribute("class", "form-item")
			.findChild("div").addAttribute("class", "form-field")
			.findChild("input").addAttribute("name", "password");
			
			WebElement pwdInput = d.findElement(By.xpath(x.getXpath()));
			pwdInput.click();
			pwdInput.clear();
			pwdInput.sendKeys("GJ1621828228");
			
			jsonReporter.appendContent(reportDTO, "input pwd");
			
			WebElement loginButton = d.findElement(By.id("btn-signup"));
			
			jsonReporter.appendContent(reportDTO, "find login button");
			try {
				loginButton.click();
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
			}
			
			jsonReporter.appendContent(reportDTO, "click login button");
			
			Thread.sleep(13000L);
			jsonReporter.appendContent(reportDTO, "after click login button sleep");

			x.start("a").addAttribute("webix_l_id", "personal_center_menu")
			.findChild("span").addAttribute("class", "personal-arrow-down");
			WebElement personMenuArrowDown = d.findElement(By.xpath(x.getXpath()));
			jsonReporter.appendContent(reportDTO, "find personMenuArrowDown");
			personMenuArrowDown.click();
			
			jsonReporter.appendContent(reportDTO, "arrow down click");
			
			Thread.sleep(13000L);
			
			WebElement dailySignButton = null;
			x.start("div").addAttribute("view_id", "$submenu1")
			.findChild("div").addAttribute("class", "webix_win_content")
			.findChild("div").addAttribute("class", "webix_win_body")
			.findChild("div").addAttribute("class", "webix_scroll_cont")
			.findChild("a").addAttribute("webix_l_id", "check_in");
			dailySignButton = d.findElement(By.xpath(x.getXpath()));
			
			jsonReporter.appendContent(reportDTO, "found sign button");
			
			if(dailySignButton != null) {
				dailySignButton.click();
				r.setIsSuccess();
				jsonReporter.appendContent(reportDTO, "sign success");
			}
			
		} catch (Exception e) {
			String htmlStr = jsUtil.getHtmlSource(d);
			TakeScreenshotSaveDTO screenshotDTO = new TakeScreenshotSaveDTO();
			screenshotDTO.setDriver(d);
			ScreenshotSaveResult screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath, null);
			
			UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			jsonReporter.appendContent(reportDTO, htmlStr);
			
			if(jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
			
		} finally {
			tryQuitWebDriver(d, reportDTO);
		}
		
		return r;
	}
}
