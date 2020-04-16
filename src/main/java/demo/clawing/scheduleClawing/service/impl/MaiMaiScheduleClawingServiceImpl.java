package demo.clawing.scheduleClawing.service.impl;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import demo.clawing.common.service.JobClawingCommonService;
import demo.clawing.localClawing.pojo.bo.MaiMaiClawingBO;
import demo.clawing.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.clawing.scheduleClawing.service.MaiMaiScheduleClawingService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service
public class MaiMaiScheduleClawingServiceImpl extends JobClawingCommonService implements MaiMaiScheduleClawingService {

	private String eventName = "maiMaiLocalClawing";
	private String userDataFileName = "maiMaiSign.json";
	
	private TestModuleType testModuleType = TestModuleType.scheduleClawing;
	private ScheduleClawingType testCastType = ScheduleClawingType.maiMai;
	
	
	private TestEvent buildLocalClawingEvent() {
		String paramterFolderPath = getParameterSaveingPath(eventName);
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
			
			String jsonStr = ioUtil.getStringFromFile(te.getParameterFilePath());
			if(StringUtils.isBlank(jsonStr)) {
				jsonReporter.appendContent(reportDTO, "参数文件读取异常");
				throw new Exception();
			}
			
			MaiMaiClawingBO clawingEventBO = null;
			try {
				clawingEventBO = new Gson().fromJson(jsonStr, MaiMaiClawingBO.class);
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			if(clawingEventBO == null) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			d = webDriverService.buildFireFoxWebDriver();
			
			try {
				d.get(clawingEventBO.getMainUrl());
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
			}
			
			boolean operatorFlag = tryLogin(d, reportDTO, clawingEventBO);
			if(!operatorFlag) {
				throw new Exception("登录异常");
			}
			
			threadSleepRandomTime();
			
			operatorFlag = changeToExploer(d);
			if(!operatorFlag) {
				throw new Exception("查找'发现'按钮异常");
			}
			
			threadSleepRandomTime(3000L, 5000L);
			
			for(int i = 1; i <= clawingEventBO.getPageCount(); i++) {
				System.out.println("in page: " + i);
				clickLikes(d);
				skipToPageEnd(d);
				operatorFlag = pageLoadSuccess(d);
				if(!operatorFlag) {
					throw new Exception("page loading time out");
				}
				threadSleepRandomTime(1500L, 2500L);
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
	

	private boolean tryLogin(WebDriver d, JsonReportDTO reportDTO, MaiMaiClawingBO clawingEventBO) throws InterruptedException {
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("a").addClass("primary-button").addId("loginBtn");
		try {
			WebElement loginButton = d.findElement(By.xpath(x.getXpath()));
			loginButton.click();
			threadSleepRandomTime();
		} catch (Exception e) {
			System.out.println("can not find login button");
			return false;
		}
		
		x.start("input").addClass("loginPhoneInput").addType("text");
		try {
			WebElement usernameInput = d.findElement(By.xpath(x.getXpath()));
			usernameInput.click();
			usernameInput.clear();
			usernameInput.sendKeys(clawingEventBO.getUsername());
			threadSleepRandomTime();
		} catch (Exception e) {
			System.out.println("can not find usernameInput");
			return false;
		}
		
		x.start("input").addType("password").addId("login_pw");
		try {
			WebElement passwordInput = d.findElement(By.xpath(x.getXpath()));
			passwordInput.click();
			passwordInput.clear();
			passwordInput.sendKeys(clawingEventBO.getPassword());
		} catch (Exception e) {
			System.out.println("can not find password input");
			return false;
		}
		
		x.start("input").addClass("loginBtn").addType("submit");
		try {
			WebElement loginSubmitButton = d.findElement(By.xpath(x.getXpath()));
			loginSubmitButton.click();
			return true;
		} catch (Exception e) {
			System.out.println("can not find loginSubmitButton");
			return false;
		}
		
	}

	private boolean changeToExploer(WebDriver d) {
		XpathBuilderBO x = new XpathBuilderBO();
		x.start("a").addAttribute("href", "/web/feed_explore");
		
		try {
			WebElement exploreButton = d.findElement(By.xpath(x.getXpath()));
			exploreButton.click();
			return true;
		} catch (Exception e) {
			System.out.println("can not find exeplore button");
			return false;
		}
	}
	
	private boolean clickLikes(WebDriver d) throws InterruptedException {
		XpathBuilderBO x = new XpathBuilderBO();
		try {
			x.start("span").addClass("sc-ckVGcZ edsJEQ sc-kpOJdX bnTSGe");
			String notLikeYetIconXPath = x.getXpath();
			
			
			List<WebElement> notLikeYetIcons = null;
			
			for(int i = 0; i < 10 && notLikeYetIcons == null; i++ ) {
				try {
					notLikeYetIcons = d.findElements(By.xpath(notLikeYetIconXPath));
				} catch (Exception e) {
					skipToPageEnd(d);
					threadSleepRandomTime(2000L, 3000L);
				}
			}
			
			if(notLikeYetIcons == null) {
				System.out.println("find like icon error");
				return false;
			}
			
			
			for(WebElement icon : notLikeYetIcons) {
				try {
					icon.click();
					System.out.println("click one");
					threadSleepRandomTime(100L, 300L);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean pageIsLoading(WebDriver d) {
		XpathBuilderBO x = new XpathBuilderBO();
		
//		加载中 则是此元素
//		<span class="loader-text">加载中...</span>
		
		x.start("span").addClass("loader-text");
		
		try {
			WebElement loadingSpan = d.findElement(By.xpath(x.getXpath()));
			if(loadingSpan.isDisplayed() && jsUtil.isVisibleInViewport(d, loadingSpan)) {
				return true;
			}
			
//			发现页可能会出现加载失败 
//			<div class="cursor-pointer" style="padding: 20px; text-align: center; font-size: 14px; color: rgb(22, 65, 185); height: 40px;">加载失败，点击重试</div>
			
			x.start("div").addClass("cursor-pointer");
			List<WebElement> cursorPointerDivList = d.findElements(By.xpath(x.getXpath()));
			for(WebElement ele : cursorPointerDivList) {
				if("加载失败，点击重试".equals(ele.getText())) {
					ele.click();
					return true;
				}
			}
			
			return false;
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean pageLoadSuccess(WebDriver d) throws InterruptedException {
		boolean flag = !pageIsLoading(d);
		if(flag) {
			return flag;
		}
		
		for(int i = 0; i < 30 && !flag; i++) {
			flag = !pageIsLoading(d);
			Thread.sleep(1000L);
		}
		
		return flag;
	}
}
