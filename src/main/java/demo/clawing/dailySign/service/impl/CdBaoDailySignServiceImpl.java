package demo.clawing.dailySign.service.impl;

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
import demo.selenium.service.impl.AuxiliaryToolServiceImpl;
import demo.selenium.service.impl.SeleniumCommonService;
import demo.selenium.service.pojo.bo.BuildTestEventBO;
import image.pojo.result.UploadImageToCloudinaryResult;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class CdBaoDailySignServiceImpl extends SeleniumCommonService implements CdBaoDailySignService {

	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private AuxiliaryToolServiceImpl auxTool;
	
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
			
			x.start("img").addAttribute("onclick", "updateseccode('cS')");
			WebElement captchaCodeImg = null;
			int captchaCount = 0;
//			TODO
			
			while(captchaCount < 15) {
				captchaCodeImg = d.findElement(By.xpath(x.getXpath()));
				auxTool.captchaHandle(d, captchaCodeImg, te);
				captchaCount++;
			}
			
			
			x.start("input").addAttribute("id", "loginname");
			WebElement usernameInput = d.findElement(By.xpath(x.getXpath()));
			usernameInput.click();
			usernameInput.clear();
			usernameInput.sendKeys(dailySignBO.getUsername());
			
			x.start("input").addAttribute("id", "password");
			WebElement pwdInput = d.findElement(By.xpath(x.getXpath()));
			pwdInput.click();
			pwdInput.clear();
			pwdInput.sendKeys(dailySignBO.getPwd());
			
			x.start("button").addAttribute("id", "login_btn");
			WebElement loginButton = d.findElement(By.xpath(x.getXpath()));
			loginButton.click();
			
			try {
				d.get("https://m.51job.com/my/my51job.php");
				jsonReporter.appendContent(reportDTO, "get");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}
			
			try {
				d.get("https://m.51job.com/resume/myresume.php");
				jsonReporter.appendContent(reportDTO, "get");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}
			
			try {
				d.get("https://m.51job.com/resume/detail.php?userid=398934495");
				jsonReporter.appendContent(reportDTO, "get");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}
			
			try {
				d.get("https://m.51job.com/resume/jobintent.php?userid=398934495");
				jsonReporter.appendContent(reportDTO, "get");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}
			
			x.start("textarea").addAttribute("id", "intro");
			WebElement intentionDetailTextarea = d.findElement(By.xpath(x.getXpath()));
			String now = localDateTimeHandler.dateToStr(LocalDateTime.now());
			String timeMarkStr = "自动签到时间: " + now;
			String intentionDetailSourceStr = intentionDetailTextarea.getText();
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
			
			intentionDetailTextarea.clear();
			intentionDetailTextarea.sendKeys(sb.toString());
			
			d.findElement(By.id("saveresumefour")).click();
			
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
	

}
