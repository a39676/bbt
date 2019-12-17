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
import demo.clawing.dailySign.service.WuYiJobDailySignService;
import demo.selenium.service.impl.SeleniumCommonService;
import demo.selenium.service.pojo.bo.BuildTestEventBO;
import image.pojo.result.UploadImageToCloudinaryResult;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class WuYiJobDailySignServiceImpl extends SeleniumCommonService implements WuYiJobDailySignService {

	@Autowired
	private FileUtilCustom ioUtil;
	
	private String dailySignEventName = "wuYiJobDailySign";
	
	private String userDataFileName = "51jobDailySign.json";
	
	private TestModuleType testModuleType = TestModuleType.dailySign;
	private DailySignCaseType testCastType = DailySignCaseType.wuYiJob;

	/*
	 * TODO 
	 * 待修改 QuQi 读取账号密码方式 改成读取本地文件, 
	 * 
	 * 已经新建 test_process 表, 但批量增加 test_event 并有顺序要求的情况, 难以封装, 后期可能视具体情况, 固定代码实现
	 * 
	 * 因每个任务的 参数BO 均不同, 不再封装公共方法
	 * 
	 * 考虑在 SeleniumCommonService 新建以参数文件数量, 对应新建多个子任务的方法
	 * 每个 testEvent 很可能对应多个参数文件, 那么加入任务的时候, 如果不声明指定的参数文件,
	 * 则应该所有参数配置都跑一次 但当时新建任务时 if(无指定参数文件的任务 && 对应的参数文件夹下有多个参数搭配文件) { 就应该同时建立N个子任务,
	 * 不是等运行时才建立子任务, 保证数据排序, 并且对应多个子任务有多个报告 新建子任务后, 就可以生成一份报告, 报告内是对应各个子报告的链接 考虑选用
	 * processId 做关联? }
	 */

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
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		
		String screenshotPath = getScreenshotSaveingPath(dailySignEventName);
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
			
			try {
				d.get(dailySignBO.getMainUrl());
				jsonReporter.appendContent(reportDTO, "get");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}
			
			XpathBuilderBO x = new XpathBuilderBO();
			
			x.start("input").addAttribute("id", "loginname");
			
			WebElement usernameInput = d.findElement(By.xpath(x.getXpath()));
			usernameInput.click();
			usernameInput.clear();
			usernameInput.sendKeys(dailySignBO.getUsername());
			
			jsonReporter.appendContent(reportDTO, "input username");
			
			x.start("input").addAttribute("id", "password");
			
			WebElement pwdInput = d.findElement(By.xpath(x.getXpath()));
			pwdInput.click();
			pwdInput.clear();
			pwdInput.sendKeys(dailySignBO.getPwd());
			
			jsonReporter.appendContent(reportDTO, "input pwd");
			
			WebElement loginButton = d.findElement(By.id("login_btn"));
			
			jsonReporter.appendContent(reportDTO, "find login button");
			try {
				loginButton.click();
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
			}
			
			jsonReporter.appendContent(reportDTO, "click login button");
			
			Thread.sleep(5000L);
			
			jsonReporter.appendContent(reportDTO, "after click login button sleep");
			
			x.start("a").addAttribute("track-type", "trackIndexClick").addAttribute("href",
					"//i.51job.com/resume/resume_center.php?lang=c");
			WebElement resumeCenter = d.findElement(By.xpath(x.getXpath()));
			jsonReporter.appendContent(reportDTO, "find resume center button");
			resumeCenter.click();
			
			jsonReporter.appendContent(reportDTO, "resume center button click");
			
			Thread.sleep(5000L);
			
			WebElement targetResumeButton = null;
			x.start("div").addAttribute("class", "rbox")
			.findChild("div", 4).addAttribute("class", "rli")
			.findChild("ul").addAttribute("class", "clearfix")
			.findChild("li")
			.findChild("a")
			;
			targetResumeButton = d.findElement(By.xpath(x.getXpath()));
			
			jsonReporter.appendContent(reportDTO, "found resume button");
			
			if (targetResumeButton != null) {
				targetResumeButton.click();
				jsonReporter.appendContent(reportDTO, "找到目标简历");
			} else {
				jsonReporter.appendContent(reportDTO, "简历失踪了");
			}
			
			x.start("div").addAttribute("class", "con")
			.findChild("div", 6)
			.findChild("div");
			
			WebElement intentionDetailDiv = d.findElement(By.xpath(x.getXpath()));
			String now = localDateTimeHandler.dateToStr(LocalDateTime.now());
			String timeMarkStr = "自动签到时间: " + now;
			String intentionDetailSourceStr = intentionDetailDiv.getText();
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
			
			jsUtil.execute(d, "document.getElementById('intention_edit').style.display='block';");
			x.start("span").addAttribute("id", "intention_edit");
			WebElement intentionEditButton = d.findElement(By.xpath(x.getXpath()));
			intentionEditButton.click();
			
			WebElement intentionEditor = d.findElement(By.id("int_selfintro"));
			intentionEditor.clear();
			intentionEditor.sendKeys(sb.toString());
			
			d.findElement(By.id("intention_save")).click();
			
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
			
			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
			
		} finally {
			tryQuitWebDriver(d, reportDTO);
		}
		
		return r;
	}

}
