package demo.clawing.scheduleClawing.service.impl;

import java.io.File;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import at.report.pojo.dto.JsonReportDTO;
import at.screenshot.pojo.dto.TakeScreenshotSaveDTO;
import at.screenshot.pojo.result.ScreenshotSaveResult;
import at.xpath.pojo.bo.XpathBuilderBO;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.scheduleClawing.pojo.bo.DailySignAccountBO;
import demo.clawing.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.clawing.scheduleClawing.service.LiePinDailySignService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import image.pojo.result.UploadImageToCloudinaryResult;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class LiePinDailySignServiceImpl extends SeleniumCommonService implements LiePinDailySignService {

	@Autowired
	private FileUtilCustom ioUtil;
	
	private String dailySignEventName = "liePinDailySign";
	
	private String userDataFileName = "liePinDailySign.json";
	
	private TestModuleType testModuleType = TestModuleType.scheduleClawing;
	private ScheduleClawingType testCastType = ScheduleClawingType.LIE_PIN;

	private TestEvent buildDailySignEvent() {
		String paramterFolderPath = getParameterSaveingPath(dailySignEventName);
		File paramterFile = new File(paramterFolderPath + File.separator + userDataFileName);
		if (!paramterFile.exists()) {
//			TODO
			return null;
		}
		
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(testModuleType);
		bo.setCaseId(testCastType.getId());
		bo.setEventName(testCastType.getEventName());
		bo.setParameterFilePath(paramterFile.getAbsolutePath());
		return buildTestEvent(bo);
	}

	@Override
	public InsertTestEventResult insertDailySignEvent() {
		TestEvent te = buildDailySignEvent();
		return testEventService.insertTestEvent(te);
	}

	@Override
	public CommonResultBBT dailySign(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		
		JsonReportDTO reportDTO = new JsonReportDTO();
		WebDriver d = null;
		
		String reportOutputFolderPath = getReportOutputPath(dailySignEventName);
		
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());
		
		try {
			
			String jsonStr = ioUtil.getStringFromFile(te.getParameterFilePath());
			if(StringUtils.isBlank(jsonStr)) {
				jsonReporter.appendContent(reportDTO, "参数文件读取异常");
				throw new Exception();
			}
			
			DailySignAccountBO dailySignBO = null;
			try {
				dailySignBO = new Gson().fromJson(jsonStr, DailySignAccountBO.class);
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			d = webDriverService.buildFireFoxWebDriver();
			
			try {
				d.get(dailySignBO.getMainUrl());
				jsonReporter.appendContent(reportDTO, "get: " + dailySignBO.getMainUrl());
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}

			XpathBuilderBO x = new XpathBuilderBO();
			
			x.start("div").addAttribute("class", "form-box form-container").addAttribute("growing-ignore", "true")
			.findChild("div").addAttribute("class", "form-content").addAttribute("data-selector", "form-content")
			.findChild("div").addAttribute("class", "operation-title")
			.findChild("span", 2)
			;
			d.findElement(By.xpath(x.getXpath())).click();
			
			x.start("input").addAttribute("type", "text").addAttribute("data-nick", "login_user");
			WebElement usernameInput = d.findElement(By.xpath(x.getXpath()));
			usernameInput.click();
			usernameInput.clear();
			usernameInput.sendKeys(dailySignBO.getUsername());
			Thread.sleep(1200L);
			
			x.start("input").addAttribute("type", "password").addAttribute("data-selector", "user_pwd");
			WebElement pwdInput = d.findElement(By.xpath(x.getXpath()));
			pwdInput.click();
			pwdInput.clear();
			pwdInput.sendKeys(dailySignBO.getPwd());
			Thread.sleep(1300L);
			
//			x.start("button").addAttribute("type", "button").addAttribute("data-selector", "submit-btn");
			x.start("input").addAttribute("type", "submit").addAttribute("class", "btn btn-login hiddingCodeImg");
			WebElement loginButton = d.findElement(By.xpath(x.getXpath()));
			loginButton.click();
			
//			刷新简历按钮--可能会弹出广告
//			x.start("a").addAttribute("class", "icons24 icons24-refresh");
//			WebElement refreshResumeButton = d.findElement(By.xpath(x.getXpath()));
//			if(!refreshResumeButton.isDisplayed()) {
//				return r;
//			}
//			refreshResumeButton.click();

			// 点击进入简历编辑
			x.start("div").addAttribute("class", "user-menu-wrap")
			.findChild("ul").addAttribute("class", "clearfix")
			.findChild("li", 2);
			WebElement editResumeButton = d.findElement(By.xpath(x.getXpath()));
			editResumeButton.click();
			
			// 获取个人简介原内容, 并替换最开始一行的内容为签到时间
			x.start("dl").addAttribute("id", "resume-self-assess")
			.findChild("dd").addAttribute("class", "resume-content selfassess-view-wrap")
			.findChild("div").addAttribute("class", "text-content")
			;
			String now = localDateTimeHandler.dateToStr(LocalDateTime.now());
			String timeMarkStr = "自动签到时间: " + now;
			String intentionDetailSourceStr = d.findElement(By.xpath(x.getXpath())).getText();
			String lineBreak = null;
			if(intentionDetailSourceStr.contains(System.lineSeparator())) {
				lineBreak = System.lineSeparator();
			} else if(intentionDetailSourceStr.contains("\r\n")) {
				lineBreak = "\r\n";
			} else if(intentionDetailSourceStr.contains("\n")) {
				lineBreak = "\n";
			} else if(intentionDetailSourceStr.contains("\r")) {
				lineBreak = "\r";
			}
			String[] sourceLines = intentionDetailSourceStr.split(lineBreak); 
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < sourceLines.length; i++) {
				if(i == 0) {
					sb.append(timeMarkStr);
				} else {
					sb.append(sourceLines[i]);
				}
				sb.append(lineBreak);
			}
			
			
			// 点击开始编辑个人简介
			x.start("dl").addAttribute("id", "resume-self-assess")
			.findChild("dd").addAttribute("class", "resume-content selfassess-view-wrap")
			.findChild("span").addAttribute("class", "btns")
			.findChild("i").addAttribute("class", "text-icon icon-edit")
			;
			d.findElement(By.xpath(x.getXpath())).click();
			
			x.start("dl").addAttribute("id", "resume-self-assess")
			.findChild("dd").addAttribute("class", "resume-content resume-edit selfassess-edit-wrap")
			.findChild("div").addAttribute("class", "violet-tooltip violet-tooltip-block")
			.findChild("div").addAttribute("class", "violet-tooltip-children-box")
			.findChild("div").addAttribute("class", "violet-textarea-wrap")
			.findChild("div").addAttribute("class", "violet-textarea-content")
			.findChild("textarea").addAttribute("data-nick", "selfassess-selfinfo")
			;
			WebElement intentionDetailTextarea = d.findElement(By.xpath(x.getXpath()));
			
			intentionDetailTextarea.clear();
			intentionDetailTextarea.sendKeys(sb.toString());
			
			x.start("dl").addAttribute("id", "resume-self-assess")
			.findChild("dd").addAttribute("class", "resume-content resume-edit selfassess-edit-wrap")
			.findChild("fieldset").addAttribute("class", "text-center")
			.findChild("button").addAttribute("data-nick", "selfassess-save");
			d.findElement(By.xpath(x.getXpath())).click();
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			TakeScreenshotSaveDTO screenshotDTO = new TakeScreenshotSaveDTO();
			screenshotDTO.setDriver(d);
			ScreenshotSaveResult screenSaveResult = screenshot(d, dailySignEventName);
			
			UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
		} finally {
			tryQuitWebDriver(d, reportDTO);
			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
		}
		
		return r;
	}
	
}
