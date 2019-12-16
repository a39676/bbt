package demo.clawing.dailySign.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import demo.clawing.dailySign.pojo.bo.DailySignAccountBO;
import demo.clawing.dailySign.pojo.type.DailySignCaseType;
import demo.clawing.dailySign.service.WuYiJobDailySignService;
import demo.selenium.service.impl.SeleniumCommonService;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service	
public class WuYiJobDailySignServiceImpl extends SeleniumCommonService implements WuYiJobDailySignService {

	private String loginUrl = "https://login.51job.com/login.php";
	
	private String eventName = "wuYiJobDailySign";
	
	
	/*
	 * TODO
	 * 待修改 QuQi 读取账号密码方式
	 * 改成读取本地文件, 为 selenium 系列新建读取参数 json 文件的公共方法
	 *     乱:
	 *         需要增加 testEvent 任务时, 就指定好参数文件地址?
	 *         每个 testEvent 很可能对应多个参数文件, 
	 *         那么加入任务的时候, 如果不声明指定的参数文件, 则应该所有参数配置都跑一次
	 *         但当时新建任务时
	 *         if(无指定参数文件的任务 && 对应的参数文件夹下有多个参数搭配文件) { 
	 *             就应该同时建立N个子任务, 不是等运行时才建立子任务, 
	 *             保证数据排序, 并且对应多个子任务有多个报告
	 *             新建子任务后, 就可以生成一份报告, 报告内是对应各个子报告的链接
	 *             考虑选用 processId 做关联?
	 *	       }      
	 */
	
	private TestEvent buildTestEvent() {
		DailySignCaseType t = DailySignCaseType.wuYiJob;
		return buildTestEvent(TestModuleType.dailySign, t.getId(), t.getEventName());
	}
	
	@Override
	public InsertTestEventResult insertclawingEvent() {
		TestEvent te = buildTestEvent();
		return testEventService.insertTestEvent(te);
	}
	
	@Override
	public CommonResultBBT clawing(TestEvent te) {
		List<DailySignAccountBO> accountList = new ArrayList<DailySignAccountBO>();
//		TODO
//		待实现指定任务的参数读取方式
		subSign(te, accountList.get(0));
		return null;
 	}
	
//	TestEventRunParamDTO
	private CommonResultBBT subSign(TestEvent te, DailySignAccountBO accountPO) {
		CommonResultBBT r = new CommonResultBBT();
		
		JsonReportDTO reportDTO = new JsonReportDTO();
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		
		String screenshotPath = getScreenshotSaveingPath(eventName);
		String reportOutputFolderPath = getReportOutputPath(eventName);
//		TODO
//		待实现指定任务的参数读取方式
//		String parameterPath = getParameterSaveingPath(eventName);
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());
		
		try {
			try {
				d.get(loginUrl);
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
			usernameInput.sendKeys(accountPO.getUserName());
			
			jsonReporter.appendContent(reportDTO, "input username");
			
			x.start("input").addAttribute("id", "password");
			
			WebElement pwdInput = d.findElement(By.xpath(x.getXpath()));
			pwdInput.click();
			pwdInput.clear();
			pwdInput.sendKeys(accountPO.getPwd());
			
			jsonReporter.appendContent(reportDTO, "input pwd");
			
			WebElement loginButton = d.findElement(By.id("login_btn"));
			
			jsonReporter.appendContent(reportDTO, "find login button");
			try {
				loginButton.click();
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
			}
			
			jsonReporter.appendContent(reportDTO, "click login button");
			
			Thread.sleep(13000L);
			jsonReporter.appendContent(reportDTO, "after click login button sleep");

			x.start("a").addAttribute("track-type", "trackIndexClick").addAttribute("href", "//i.51job.com/resume/resume_center.php?lang=c");
			WebElement resumeCenter = d.findElement(By.xpath(x.getXpath()));
			jsonReporter.appendContent(reportDTO, "find resume center button");
			resumeCenter.click();
			
			jsonReporter.appendContent(reportDTO, "resume center button click");
			
			Thread.sleep(13000L);
			
			WebElement targetResumeButton = null;
			x.start("div").addAttribute("id", "resume_398934495");
			targetResumeButton = d.findElement(By.xpath(x.getXpath()));
			
			jsonReporter.appendContent(reportDTO, "found resume button");
			
			if(targetResumeButton != null) {
				targetResumeButton.click();
				jsonReporter.appendContent(reportDTO, "找到目标简历");
			} else {
				jsonReporter.appendContent(reportDTO, "简历失踪了");
			}
			
			x.start("span").addAttribute("onclick", "refreshResume()");
			WebElement refreshButton = d.findElement(By.xpath(x.getXpath()));
			refreshButton.click();
			
			x.start("div").addAttribute("class", "con")
			.findChild("div").addAttribute("class", "e")
			.findChild("div");
			
			WebElement intentionDetailDiv = d.findElement(By.xpath(x.getXpath()));
			String now = localDateTimeHandler.dateToStr(LocalDateTime.now());
			String timeMarkStr = "自动签到时间: " + now;
			String intentionDetailStr = intentionDetailDiv.getText();
			
			WebElement intentionEditButton = d.findElement(By.id("intention_edit"));
			intentionEditButton.click();
			
			WebElement intentionEditor = d.findElement(By.id("int_selfintro"));
			intentionEditor.clear();
			intentionEditor.sendKeys(timeMarkStr + System.lineSeparator() + intentionDetailStr);
			
			d.findElement(By.id("intention_save")).click();
			
			r.setIsSuccess();
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
