package demo.selenium.service.impl;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.beans.factory.annotation.Autowired;

import at.screenshot.pojo.constant.ScreenshotConstant;
import at.screenshot.pojo.dto.TakeScreenshotSaveDTO;
import at.screenshot.pojo.result.ScreenshotSaveResult;
import at.screenshot.service.ScreenshotService;
import at.tool.WebDriverATToolService;
import at.webDriver.pojo.constant.WebDriverConstant;
import at.xpath.pojo.bo.XpathBuilderBO;
import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.report.service.ATJsonReportService;
import autoTest.testEvent.common.pojo.dto.AutomationTestResultDTO;
import autoTest.testEvent.common.pojo.result.AutomationTestCaseResult;
import demo.autoTestBase.testEvent.mq.producer.AutomationTestResultProducer;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.base.system.service.impl.RedisHashConnectService;
import demo.base.system.service.impl.RedisOriginalConnectService;
import demo.base.system.service.impl.SystemOptionService;
import demo.baseCommon.service.CommonService;
import demo.interaction.image.service.ImageInteractionService;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.WebDriverService;
import demo.tool.mq.producer.TelegramMessageAckProducer;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.ImageSavingResult;
import image.pojo.result.UploadImageToCloudinaryResult;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.dto.TelegramBotNoticeMessageDTO;
import telegram.pojo.type.TelegramBotType;
import toolPack.constant.FileSuffixNameConstant;
import toolPack.dateTimeHandle.DateTimeUtilCommon;
import toolPack.ioHandle.FileUtilCustom;

public abstract class AutomationTestCommonService extends CommonService {

	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	protected XpathBuilderBO xPathBuilder;
	@Autowired
	protected ATJsonReportService reportService;
	@Autowired
	protected ImageInteractionService imageInteractionService;
	@Autowired
	protected WebDriverService webDriverService;
	@Autowired
	protected ScreenshotService screenshotService;
	@Autowired
	protected JavaScriptServiceImpl jsUtil;
	@Autowired
	protected SeleniumGlobalOptionService globalOptionService;
	@Autowired
	protected RedisOriginalConnectService redisOriginalConnectService;
	@Autowired
	protected RedisHashConnectService redisHashConnectService;
	@Autowired
	protected SystemOptionService systemOptionService;
	@Autowired
	protected WebDriverATToolService webATToolService;
	@Autowired
	protected AutomationTestResultProducer automationTestResultProducer;
	@Autowired
	private TelegramMessageAckProducer telegramMessageAckProducer;
	
	protected void sendingMsg(String msg) {
		log.error("Sending telegram message: " + msg);
		TelegramBotNoticeMessageDTO dto = new TelegramBotNoticeMessageDTO();
		dto.setId(TelegramStaticChatID.MY_ID);
		dto.setBotName(TelegramBotType.BBT_MESSAGE.getName());
		dto.setMsg(msg);
		telegramMessageAckProducer.send(dto);
//		reminderMessageService.sendReminder(msg);
	}
	
	
	protected UploadImageToCloudinaryResult uploadImgToCloudinary(String imgFilePath) {
		UploadImageToCloudinaryDTO uploadImgDTO = new UploadImageToCloudinaryDTO();
		uploadImgDTO.setFilePath(imgFilePath);
		UploadImageToCloudinaryResult uploadImgResult = imageInteractionService.uploadImageToCloudinary(uploadImgDTO);
		return uploadImgResult;
	}

	protected ImageSavingResult saveImgToCX(String imgBase64Str, String imgFileName) {
		return saveImgToCX(imgBase64Str, imgFileName, null);
	}

	protected ImageSavingResult saveImgToCX(String imgBase64Str, String imgFileName, LocalDateTime validTime) {
		ImageSavingTransDTO dto = new ImageSavingTransDTO();
		dto.setImgName(imgFileName);
		dto.setImgBase64Str(imgBase64Str);
		dto.setValidTime(validTime);

		ImageSavingResult r = imageInteractionService.saveImgToCX(dto);
		return r;
	}

	protected boolean tryQuitWebDriver(WebDriver d) {
		if (d != null) {
			Set<String> windowHadles = d.getWindowHandles();
			for (String windowHandle : windowHadles) {
				d.switchTo().window(windowHandle);
				d.close();
			}
			
			try {
				d.quit();
				return true;
			} catch (Exception e) {
				log.error("web deiver quit error: " + e.getMessage());
				return false;
			}
		} else {
			return true;
		}
	}

	protected String getScreenshotSaveingPath(String eventName) {
		String path = globalOptionService.getScreenshotSavingFolder() + File.separator + eventName;
		ioUtil.checkFolderExists(path);
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

	protected TakeScreenshotSaveDTO buildScreenshotDTO(WebDriver d, String testEventName, int startX, int endX,
			int startY, int endY) {
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

	protected ScreenshotSaveResult screenshot(WebDriver d, String testFlowName) {
		TakeScreenshotSaveDTO dto = buildScreenshotDTO(d, testFlowName);
		return screenshotService.takeScreenshotAndSaveAtLocal(dto);
	}

	protected ScreenshotSaveResult screenshot(WebDriver d, String testEventName, WebElement ele) {
		TakeScreenshotSaveDTO dto = buildScreenshotDTO(d, testEventName, ele);
		return screenshotService.takeScreenshotAndSaveAtLocal(dto);
	}

	protected ScreenshotSaveResult screenshot(WebDriver d, String testEventName, int startX, int endX, int startY,
			int endY) {
		TakeScreenshotSaveDTO dto = buildScreenshotDTO(d, testEventName, startX, endX, startY, endY);
		return screenshotService.takeScreenshotAndSaveAtLocal(dto);
	}

	protected String takeScreenshotToBase64(WebDriver d, String testFlowName) {
		TakeScreenshotSaveDTO dto = buildScreenshotDTO(d, testFlowName);
		return screenshotService.takeScreenshotToBase64(dto);
	}

	protected void addScreenshotToReport(WebDriver d, JsonReportOfCaseDTO flowReportDTO) {
		// TODO 图片传输需重构, 非本地保存, 直接将图片依 base64 形式传送到 cx
		String screenSaveResult = takeScreenshotToBase64(d, flowReportDTO.getCaseTypeName());
		String targetFileName = String.format(ScreenshotConstant.screenShotFilenameFormat,
				flowReportDTO.getCaseTypeName(),
				localDateTimeHandler.dateToStr(LocalDateTime.now(), DateTimeUtilCommon.dateTimeFormatNoSymbol),
				FileSuffixNameConstant.PNG);
		ImageSavingResult uploadImgResult = saveImgToCX(screenSaveResult, targetFileName);
		reportService.caseReportAppendImage(flowReportDTO, uploadImgResult.getImgUrl());
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

	protected AutomationTestCaseResult initCaseResult(String casename) {
		AutomationTestCaseResult r = new AutomationTestCaseResult();
		r.setCaseName(casename);
		return r;
	}

	protected JsonReportOfCaseDTO initCaseReportDTO(String casename) {

		JsonReportOfCaseDTO report = new JsonReportOfCaseDTO();
		report.setReportElementList(new ArrayList<>());
		report.setCaseTypeName(casename);

		return report;
	}

	protected JsonReportOfCaseDTO buildCaseReportDTO() {
		JsonReportOfCaseDTO report = new JsonReportOfCaseDTO();
		report.setReportElementList(new ArrayList<>());
		report.setCaseTypeName("running error");
		return report;
	}

	protected AutomationTestResultDTO buildAutomationTestResultDTO(TestEventBO bo) {
		AutomationTestResultDTO dto = new AutomationTestResultDTO();

		dto.setStartTime(bo.getStartTime());
		dto.setEndTime(bo.getEndTime());
		dto.setTestEventId(bo.getEventId());
		dto.setReport(bo.getReport());
		dto.setCaseResultList(bo.getCaseResultList());
		dto.setRemark(bo.getRemark());
		return dto;
	}

	protected void sendAutomationTestResult(TestEventBO bo) {
		bo.setEndTime(LocalDateTime.now());
		AutomationTestResultDTO dto = buildAutomationTestResultDTO(bo);
		automationTestResultProducer.send(dto);
	}

	protected WebElement fluentWait(WebDriver driver, final By by) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(WebDriverConstant.pageWaitingTimeoutSecond))
				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(by);
			}
		});

		return foo;
	};

	protected void swipeCaptchaHadle(WebDriver d, WebElement swipeButton, WebElement chuteElement) {
		Random r = new Random();
		Integer dragPix = chuteElement.getSize().getWidth();
		Integer subDragPix = dragPix / 10;
		Actions builder = new Actions(d);
		Action dragAndDrop = builder.clickAndHold(swipeButton).moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220)).moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220)).release().build();
		dragAndDrop.perform();
	}

	protected boolean loadingCheck(WebDriver d, String xpath) throws InterruptedException {
		return loadingCheck(d, xpath, 1000L, 10);
	}

	protected boolean loadingCheck(WebDriver d, String xpath, long waitGap, int findCount) throws InterruptedException {
		if (waitGap < 0) {
			waitGap = 1000L;
		}
		if (findCount < 0) {
			findCount = 10;
		}
		WebElement tmpElemet = null;
		for (int i = 0; i < findCount && tmpElemet == null; i++) {
			try {
				tmpElemet = d.findElement(By.xpath(xpath));
			} catch (Exception e) {
			}
			if (tmpElemet != null) {
				return true;
			}
			Thread.sleep(waitGap);
		}
		return false;
	}

	protected void selectorRandomSelect(WebDriver d, String selectorXpath, Integer min, Integer max) {
		try {
			WebElement selectorElement = d.findElement(By.xpath(selectorXpath));
			Select selector = new Select(selectorElement);
			List<WebElement> optionList = selector.getOptions();
			if (max == null || max > optionList.size()) {
				max = optionList.size();
			}
			if (min == null || min < 0) {
				min = 0;
			}
			Double randomIndex = ((Math.random() * (max - min)) + min);
			selector.selectByIndex(randomIndex.intValue());
		} catch (Exception e) {

		}
	}

	protected void selectorSelectByKeyword(WebDriver d, String selectorXpath, String keyword) {
		try {
			WebElement selectorElement = d.findElement(By.xpath(selectorXpath));
			Select selector = new Select(selectorElement);
			List<WebElement> optionList = selector.getOptions();
			WebElement option = null;
			String value = null;
			String text = null;
			for (int i = 0; i < optionList.size(); i++) {
				option = optionList.get(i);
				value = option.getAttribute("value");
				text = option.getText();
				if ((value != null && value.contains(keyword)) || (text != null && text.contains(keyword))) {
					selector.selectByIndex(i);
					return;
				}
			}
		} catch (Exception e) {

		}
	}
}
