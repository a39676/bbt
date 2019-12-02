package demo.dailySign.service.impl;

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
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.dailySign.pojo.type.DailySignCaseType;
import demo.dailySign.service.QuQiDailySignService;
import demo.selenium.service.impl.SeleniumCommonService;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.result.InsertTestEventResult;
import demo.testCase.pojo.type.TestModuleType;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service
public class QuQiDailySignServiceImpl extends SeleniumCommonService implements QuQiDailySignService {

	private String mainUrl = "https://www.quqi.com/";
	
	private String eventName = "quqiDailySign";
	
	private TestEvent buildTestEvent() {
		DailySignCaseType t = DailySignCaseType.quqi;
		return buildTestEvent(TestModuleType.dailySign, t.getId(), t.getEventName());
	}
	
	private String getScreenshotSaveingPath() {
		String path = globalOptionService.getScreenshotSavingFolder() + File.separator + eventName;
		globalOptionService.checkFolderExists(path);
		return path;
	}
	
	private String getReportOutputPath() {
		String path = globalOptionService.getReportOutputFolder() + File.separator + eventName;
		globalOptionService.checkFolderExists(path);
		return path;
	}
	
	@Override
	public InsertTestEventResult insertclawingEvent() {
		TestEvent te = buildTestEvent();
		return testEventService.insertTestEvent(te);
	}
	
	@Override
	public CommonResultBBT clawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		
		JsonReportDTO reportDTO = new JsonReportDTO();
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		
		String screenshotPath = getScreenshotSaveingPath();
		String reportOutputFolderPath = getReportOutputPath();
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		try {
			d.get(mainUrl);
			
			jsonReporter.appendContent(reportDTO, "get");
			
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
			
			x.start("button").addAttribute("id", "btn-signup");
			WebElement loginButton = d.findElement(By.xpath(x.getXpath()));
			
			jsonReporter.appendContent(reportDTO, "find login button");
			try {
				loginButton.click();
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
			}
			
			jsonReporter.appendContent(reportDTO, "click login button");
			
			Thread.sleep(800L);

			x.start("a").addAttribute("webix_l_id", "personal_center_menu")
			.findChild("span").addAttribute("class", "personal-arrow-down");
			WebElement personMenuArrowDown = d.findElement(By.xpath(x.getXpath()));
			personMenuArrowDown.click();
			
			jsonReporter.appendContent(reportDTO, "arrow down click");
			
			Thread.sleep(800L);
			
			
			WebElement dailySignButton = null;
			try {
				x.start("div").addAttribute("view_id", "$submenu1")
				.findChild("div").addAttribute("class", "webix_win_content")
				.findChild("div").addAttribute("class", "webix_win_body")
				.findChild("div").addAttribute("class", "webix_scroll_cont")
				.findChild("a").addAttribute("webix_l_id", "check_in");
				dailySignButton = d.findElement(By.xpath(x.getXpath()));
				
				jsonReporter.appendContent(reportDTO, "found sign button");
				
			} catch (Exception e) {
				String htmlStr = jsUtil.getHtmlSource(d);
				TakeScreenshotSaveDTO screenshotDTO = new TakeScreenshotSaveDTO();
				screenshotDTO.setDriver(d);
				ScreenshotSaveResult screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath, null);
				
				UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
				jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
				jsonReporter.appendContent(reportDTO, htmlStr);
				
			}
			
//			WebElement dailySignButton = d.findElement(By.linkText("签到赚经验值"));
			if(dailySignButton != null) {
//				dailySignButton.click();
				r.setIsSuccess();
			}
			
			String reportOutputPath = reportDTO.getOutputReportPath() + File.separatorChar + te.getId() + ".json";
			if(jsonReporter.outputReport(reportDTO, reportOutputPath)) {
				updateTestEventReportPath(te, reportOutputPath);
			}

		} catch (Exception e) {
			
		} finally {
			if (d != null) {
				d.quit();
			}
		}
		
		return r;
	}
}
