package demo.selenium.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.Set;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;

import at.report.pojo.dto.JsonReportDTO;
import at.report.service.ATJsonReportService;
import at.screenshot.service.ScreenshotService;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.baseCommon.service.CommonService;
import demo.interaction.image.service.ImageInteractionService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.WebDriverService;
import image.pojo.dto.ImageSavingTransDTO;
import image.pojo.dto.UploadImageToCloudinaryDTO;
import image.pojo.result.ImageSavingResult;
import image.pojo.result.UploadImageToCloudinaryResult;

public abstract class SeleniumCommonService extends CommonService {

	@Autowired
	protected ATJsonReportService jsonReporter;
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
	
	protected TestEvent buildTestEvent(BuildTestEventBO bo) {
		if(bo.getTestModuleType() == null || bo.getCaseId() == null) {
			return null;
		}
		TestEvent te = new TestEvent();
		te.setProcessId(bo.getProcessId());
		te.setCaseId(bo.getCaseId());
		te.setModuleId(bo.getTestModuleType().getId());
		te.setId(snowFlake.getNextId());
		te.setEventName(bo.getEventName());
		te.setParameterFilePath(bo.getParameterFilePath());
		return te;
	}
	
	protected UploadImageToCloudinaryResult uploadImgToCloudinary(String imgFilePath) {
		UploadImageToCloudinaryDTO uploadImgDTO = new UploadImageToCloudinaryDTO();
		uploadImgDTO.setFilePath(imgFilePath);
		UploadImageToCloudinaryResult uploadImgResult = imageInteractionService.uploadImageToCloudinary(uploadImgDTO);
		return uploadImgResult;
	}
	
	protected ImageSavingResult saveImgToCX(String imgFilePath, String imgFileName, LocalDateTime validTime) {
		ImageSavingTransDTO dto = new ImageSavingTransDTO();
		dto.setImgName(imgFileName);
		dto.setImgPath(imgFilePath);
		dto.setValidTime(validTime);
		
		ImageSavingResult r = imageInteractionService.saveImgToCX(dto);
		return r;
	}
	
	protected int updateTestEventReportPath(TestEvent te, String reportPath) {
		return testEventService.updateTestEventReportPath(te, reportPath);
	}

	protected boolean tryQuitWebDriver(WebDriver d, JsonReportDTO reportDTO) {
		if(d != null) {
			try {
				d.quit();
				return true;
			} catch (Exception e2) {
				if(reportDTO != null) {
					jsonReporter.appendContent(reportDTO, e2.getMessage());
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
	
	protected String getReportOutputPath(String eventName) {
		String path = globalOptionService.getReportOutputFolder() + File.separator + eventName;
		globalOptionService.checkFolderExists(path);
		return path;
	}
	
	protected String getParameterSaveingPath(String eventName) {
		String path = globalOptionService.getParameterSavingFolder() + File.separator + eventName;
		globalOptionService.checkFolderExists(path);
		return path;
	}
	
	protected void threadSleepRandomTime(Long minMS, Long maxMS) throws InterruptedException {
		Double i = ((Math.random() * (maxMS - minMS)) + minMS);
		Thread.sleep(i.longValue());
	}
	
	protected void threadSleepRandomTime() throws InterruptedException {
		threadSleepRandomTime(3000L, 3000L);
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
		
		if(windowKeep == null) {
			windowKeep = d.getWindowHandle();
		} else if (!windowHandles.contains(windowKeep)) {
			return false;
		}
		
		for(String tmpWindowHandle : windowHandles) {
			if(!tmpWindowHandle.equals(windowKeep)) {
				d.switchTo().window(tmpWindowHandle);
				d.close();
			}
		}
		d.switchTo().window(windowKeep);
		return true;
	}
}
