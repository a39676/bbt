package demo.clawing.dailySign.service.impl;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import at.pojo.bo.XpathBuilderBO;
import at.pojo.dto.JsonReportDTO;
import at.pojo.dto.TakeScreenshotSaveDTO;
import at.pojo.result.ScreenshotSaveResult;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.dailySign.pojo.bo.DailySignAccountBO;
import demo.clawing.dailySign.pojo.type.DailySignCaseType;
import demo.clawing.dailySign.service.CdBaoDailySignService;
import demo.selenium.service.impl.SeleniumCommonService;
import demo.selenium.service.pojo.bo.BuildTestEventBO;
import image.pojo.result.UploadImageToCloudinaryResult;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class CdBaoDailySignServiceImpl extends SeleniumCommonService implements CdBaoDailySignService {

	@Autowired
	private FileUtilCustom ioUtil;
	
	private String dailySignEventName = "cdBaoDailySign";
	
	private String userDataFileName = "cdBaoDailySign.json";
	
	private TestModuleType testModuleType = TestModuleType.dailySign;
	private DailySignCaseType testCastType = DailySignCaseType.cdBao;

	

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
		
		String screenshotPath = getScreenshotSaveingPath(dailySignEventName);
		String reportOutputFolderPath = getReportOutputPath(dailySignEventName);
		
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());
		
		try {
			
			d = webDriverService.buildFireFoxWebDriver();
			
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
			
			try {
				d.get(dailySignBO.getMainUrl());
				jsonReporter.appendContent(reportDTO, "get");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}
			
			XpathBuilderBO x = new XpathBuilderBO();
			
			x.start("a").addAttribute("class", "nousername");
			
			d.findElement(By.xpath(x.getXpath())).click();
			
			x.start("span").addAttribute("id", "vseccode_cS").findChild("img").addAttribute("class", "vm");
			By captchaImgBy = By.xpath(x.getXpath());
			WebElement captchaCodeImg = null;
			WebElement captchaInput = d.findElement(By.id("seccodeverify_cS"));
			int captchaCount = 0;
			String captcha = null;
//			TODO
			x.start("input").addAttribute("name", "username").addAttribute("type", "text").addAttribute("class", "px p_fre");
			WebElement usernameInput = d.findElement(By.xpath(x.getXpath()));
			usernameInput.click();
			usernameInput.clear();
			usernameInput.sendKeys(dailySignBO.getUsername());
			
			while(captchaCount < 25 && !detectCaptchaImgFlag(d)) {
				captchaCodeImg = d.findElement(captchaImgBy);
				captchaCodeImg.click();
				Thread.sleep(800L);
				captchaCodeImg = d.findElement(captchaImgBy);
				Point p = captchaCodeImg.getLocation();
				int width = captchaCodeImg.getSize().width + p.getX();
				int height = captchaCodeImg.getSize().height + p.getY();
				Thread.sleep(300L);
				captcha = auxTool.captchaHandle(d, p.getX(), p.getY(), width, height, te);
				System.out.println(captcha);
				captchaInput.click();
				captchaInput.clear();
				captchaInput.sendKeys(captcha);
				usernameInput.click();
				captchaCount++;
			}
			
			if(!detectCaptchaImgFlag(d)) {
				jsonReporter.appendContent(reportDTO, "无法识别验证码");
				throw new Exception();
			}
			
			x.start("input").addAttribute("type", "password").addAttribute("name", "password").addAttribute("class", "px p_fre");
			WebElement pwdInput = d.findElement(By.xpath(x.getXpath()));
			pwdInput.click();
			pwdInput.clear();
			pwdInput.sendKeys(dailySignBO.getPwd());
			
			x.start("table")
			.findChild("tbody")
			.findChild("tr")
			.findChild("td")
			.findChild("button").addAttribute("name", "loginsubmit").addAttribute("class", "pn pnc")
			;
			WebElement loginButton = d.findElement(By.xpath(x.getXpath()));
			loginButton.click();
			
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			String htmlStr = jsUtil.getHtmlSource(d);
			TakeScreenshotSaveDTO screenshotDTO = new TakeScreenshotSaveDTO();
			screenshotDTO.setDriver(d);
			ScreenshotSaveResult screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath,
					null);
			
			UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			jsonReporter.appendContent(reportDTO, htmlStr);
			
		} finally {
			tryQuitWebDriver(d, reportDTO);
			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
		}
		
		return r;
	}
	
	private boolean detectCaptchaImgFlag(WebDriver d) {
		XpathBuilderBO x = new XpathBuilderBO();
		x.start("span").addAttribute("id", "checkseccodeverify_cS")
		.findChild("img");
		
		WebElement flagImg = null;
		try {
			flagImg = d.findElement(By.xpath(x.getXpath()));
			if(flagImg != null && flagImg.isDisplayed()) {
				String src = flagImg.getAttribute("src");
				if(src != null && src.contains("right")) {
					return true;
				}
			}
		} catch (Exception e) {
			return false;
		}
		
		return false;
	}
}
