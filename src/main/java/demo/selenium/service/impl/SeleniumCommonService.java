package demo.selenium.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;

import at.report.pojo.dto.JsonReportDTO;
import at.report.service.ATJsonReportService;
import at.screenshot.pojo.dto.TakeScreenshotSaveDTO;
import at.screenshot.pojo.result.ScreenshotSaveResult;
import at.screenshot.service.ScreenshotService;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.constant.TestEventOptionConstant;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.baseCommon.service.CommonService;
import demo.interaction.image.service.ImageInteractionService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.SeleniumCaptchaHandleService;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.WebDriverService;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.ImageSavingResult;
import image.pojo.result.UploadImageToCloudinaryResult;
import net.sf.json.JSONObject;
import selenium.pojo.constant.SeleniumConstant;

public abstract class SeleniumCommonService extends CommonService {

	@Autowired
	protected ATJsonReportService reportService;
	@Autowired
	protected ImageInteractionService imageInteractionService;
	@Autowired
	protected TestEventService testEventService;
	@Autowired
	protected WebDriverService webDriverService;
	@Autowired
	protected AuxiliaryToolServiceImpl auxTool;
	@Autowired
	protected ScreenshotService screenshotService;
	@Autowired
	protected JavaScriptServiceImpl jsUtil;
	@Autowired
	protected SeleniumGlobalOptionService globalOptionService;
	@Autowired
	protected SeleniumCaptchaHandleService captchaHandleService;
	
	protected TestEvent buildTestEvent(BuildTestEventBO bo) {
		if (bo.getTestModuleType() == null || bo.getFlowId() == null) {
			return null;
		}
		TestEvent te = new TestEvent();
		te.setFlowId(bo.getFlowId());
		te.setModuleId(bo.getTestModuleType().getId());
		if(bo.getEventId() != null) {
			te.setId(bo.getEventId());
		} else {
			te.setId(snowFlake.getNextId());
		}
		te.setEventName(bo.getFlowName());
		te.setParameterFilePath(bo.getParameterFilePath());
		return te;
	}

	protected JSONObject tryFindParam(Long testEventId) {
		String paramStr = redisConnectService.getValByName(TestEventOptionConstant.TEST_EVENT_REDIS_PARAM_KEY_PREFIX + "_" + testEventId);
		if(StringUtils.isNotBlank(paramStr)) {
			try {
				return JSONObject.fromObject(paramStr);
			} catch (Exception e) {
				log.error("find error param: " + paramStr);
			}
		}
		return null;
	}
	
	protected UploadImageToCloudinaryResult uploadImgToCloudinary(String imgFilePath) {
		UploadImageToCloudinaryDTO uploadImgDTO = new UploadImageToCloudinaryDTO();
		uploadImgDTO.setFilePath(imgFilePath);
		UploadImageToCloudinaryResult uploadImgResult = imageInteractionService.uploadImageToCloudinary(uploadImgDTO);
		return uploadImgResult;
	}

	protected ImageSavingResult saveImgToCX(String imgFilePath, String imgFileName) {
		return saveImgToCX(imgFilePath, imgFileName, null);
	}

	protected ImageSavingResult saveImgToCX(String imgFilePath, String imgFileName, LocalDateTime validTime) {
		ImageSavingTransDTO dto = new ImageSavingTransDTO();
		dto.setImgName(imgFileName);
		dto.setImgPath(imgFilePath);
		if (validTime == null) {
			dto.setValidTime(LocalDateTime.now().plusMonths(SeleniumConstant.maxHistoryMonth));
		} else {
			dto.setValidTime(validTime);
		}

		ImageSavingResult r = imageInteractionService.saveImgToCX(dto);
		return r;
	}

	protected void saveReport(TestEventBO tbo) {
		if (reportService.outputReport(tbo.getReport(), tbo.getReportOutputFolderPath())) {
			updateTestEventReportPath(tbo.getEvent(), tbo.getReportOutputFolderPath());
		}
	}
	
	protected int updateTestEventReportPath(TestEvent te, String reportPath) {
		return testEventService.updateTestEventReportPath(te, reportPath);
	}

	protected boolean tryQuitWebDriver(WebDriver d, JsonReportDTO reportDTO) {
		if (d != null) {
			try {
				d.quit();
				return true;
			} catch (Exception e2) {
				if (reportDTO != null) {
					reportService.appendContent(reportDTO, e2.getMessage());
				}
				return false;
			}
		} else {
			return true;
		}
	}

	protected boolean tryQuitWebDriver(WebDriver d) {
		return tryQuitWebDriver(d, null);
	}

	protected String getScreenshotSaveingPath(String eventName) {
		String path = globalOptionService.getScreenshotSavingFolder() + File.separator + eventName;
		globalOptionService.checkFolderExists(path);
		return path;
	}

	protected TakeScreenshotSaveDTO buildScreenshotDTO(WebDriver d, String testEventName) {
		String screenshotPath = getScreenshotSaveingPath(testEventName);
		TakeScreenshotSaveDTO dto = new TakeScreenshotSaveDTO();
		dto.setDriver(d);
		dto.setTargetFolderPath(screenshotPath);
		return dto;
	}

	protected TakeScreenshotSaveDTO buildScreenshotDTO(WebDriver d, String testEventName, WebElement ele) {
		String screenshotPath = getScreenshotSaveingPath(testEventName);
		TakeScreenshotSaveDTO dto = new TakeScreenshotSaveDTO();
		dto.setDriver(d);
		dto.setTargetFolderPath(screenshotPath);
		dto.setEle(ele);
		return dto;
	}

	protected TakeScreenshotSaveDTO buildScreenshotDTO(WebDriver d, String testEventName, int startX, int endX, int startY, int endY) {
		String screenshotPath = getScreenshotSaveingPath(testEventName);
		TakeScreenshotSaveDTO dto = new TakeScreenshotSaveDTO();
		dto.setDriver(d);
		dto.setTargetFolderPath(screenshotPath);
		dto.setStartX(startX);
		dto.setEndX(endX);
		dto.setStartY(startY);
		dto.setEndY(endY);
		return dto;
	}

	protected ScreenshotSaveResult screenshot(WebDriver d, String testEventName) {
		TakeScreenshotSaveDTO dto = buildScreenshotDTO(d, testEventName);
		return screenshotService.screenshot(dto);
	}
	
	protected ScreenshotSaveResult screenshot(WebDriver d, String testEventName, WebElement ele) {
		TakeScreenshotSaveDTO dto = buildScreenshotDTO(d, testEventName, ele);
		return screenshotService.screenshot(dto);
	}
	
	protected ScreenshotSaveResult screenshot(WebDriver d, String testEventName, int startX, int endX, int startY, int endY) {
		TakeScreenshotSaveDTO dto = buildScreenshotDTO(d, testEventName, startX, endX, startY, endY);
		return screenshotService.screenshot(dto);
	}

	protected String getReportOutputFolderPath(String eventName) {
		if(StringUtils.isBlank(eventName)) {
			eventName = "unknow";
		}
		String path = globalOptionService.getReportOutputFolder() + File.separator + eventName;
		globalOptionService.checkFolderExists(path);
		return path;
	}

	protected String getParameterSaveingPath(String eventName) {
		String path = globalOptionService.getParameterSavingFolder() + File.separator + eventName;
		globalOptionService.checkFolderExists(path);
		return path;
	}
	
	protected void addScreenshotToReport(WebDriver d, TestEventBO tbo) {
		ScreenshotSaveResult screenSaveResult = screenshot(d, tbo.getEvent().getEventName());
		ImageSavingResult uploadImgResult = saveImgToCX(screenSaveResult.getSavingPath(),
				screenSaveResult.getFileName());
		reportService.appendImage(tbo.getReport(), uploadImgResult.getImgUrl());
	}

	protected void threadSleepRandomTime(Long minMS, Long maxMS) throws InterruptedException {
		Double i = ((Math.random() * (maxMS - minMS)) + minMS);
		Thread.sleep(i.longValue());
	}

	protected void threadSleepRandomTime() throws InterruptedException {
		threadSleepRandomTime(1000L, 3000L);
	}
	
	protected void threadSleepRandomTimeLong() throws InterruptedException {
		threadSleepRandomTime(5000L, 8000L);
	}

	protected void skipToPageEnd(WebDriver d) {
		Actions action = new Actions(d);
		action.sendKeys(Keys.END).build().perform();
	}

	protected void backToPageStart(WebDriver d) {
		Actions action = new Actions(d);
		action.sendKeys(Keys.HOME).build().perform();
	}

	protected boolean closeOtherWindow(WebDriver d, String windowKeep) {
		Set<String> windowHandles = d.getWindowHandles();

		if (windowKeep == null) {
			windowKeep = d.getWindowHandle();
		} else if (!windowHandles.contains(windowKeep)) {
			return false;
		}

		for (String tmpWindowHandle : windowHandles) {
			if (!tmpWindowHandle.equals(windowKeep)) {
				d.switchTo().window(tmpWindowHandle);
				d.close();
			}
		}
		d.switchTo().window(windowKeep);
		return true;
	}
}
