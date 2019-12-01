package demo.bingDemo.service.impl;

import java.io.File;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import at.pojo.bo.XpathBuilderBO;
import at.pojo.dto.JsonReportDTO;
import at.pojo.dto.TakeScreenshotSaveDTO;
import at.pojo.result.ScreenshotSaveResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.bingDemo.pojo.dto.BingDemoDTO;
import demo.bingDemo.pojo.type.BingDemoCaseType;
import demo.bingDemo.service.BingDemoService;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.impl.SeleniumCommonService;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.result.InsertTestEventResult;
import demo.testCase.pojo.type.TestModuleType;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service
public class BingDemoServiceImpl extends SeleniumCommonService implements BingDemoService {
	
	private String eventName = "bingDemo";
	
	@Autowired
	private SeleniumGlobalOptionService globalOptionService;

	private TestEvent buildTestEvent() {
		BingDemoCaseType t = BingDemoCaseType.bingDemo;
		return buildTestEvent(TestModuleType.ATDemo, t.getId(), t.getEventName());
	}
	
	private String getScreenshotSaveingPath() {
		return globalOptionService.getScreenshotSavingFolder() + File.separator + eventName;
	}
	
	private String getReportOutputPath() {
		return globalOptionService.getReportOutputFolder() + File.separator + eventName;
	}
	
	@Override
	public InsertTestEventResult insertclawingEvent(String keyword) {
		TestEvent te = buildTestEvent();
		te.setRemark(keyword);
		return testEventService.insertTestEvent(te);
	}
	
	@Override
	public CommonResultBBT clawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		JsonReportDTO dto = new JsonReportDTO();
		
		String screenshotPath = getScreenshotSaveingPath();
		String reportOutputFolderPath = getReportOutputPath();
		
		dto.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		WebDriver d = webDriverService.buildFireFoxWebDriver();

		String bingUrl = "https://cn.bing.com/?FORM=BEHPTB";
		
		try {
			d.get(bingUrl);
			
			jsonReporter.appendContent(dto, "打开: " + bingUrl);
			
			XpathBuilderBO x = new XpathBuilderBO();
			
			TakeScreenshotSaveDTO screenshotDTO = new TakeScreenshotSaveDTO();
			screenshotDTO.setDriver(d);
			ScreenshotSaveResult screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath, null);
			
			UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(dto, uploadImgResult.getImgUrl());
			
			x.start("input").addAttribute("id", "sb_form_q");
			WebElement keywordInput = d.findElement(By.xpath(x.getXpath()));
			keywordInput.click();
			keywordInput.clear();
			keywordInput.sendKeys(te.getRemark());
			
			jsonReporter.appendContent(dto, "输入关键词: " + te.getRemark());
			
			screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath, null);
			uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(dto, uploadImgResult.getImgUrl());
			
			x.start("div").addAttribute("id", "sb_go_par");
			WebElement searchButton = d.findElement(By.xpath(x.getXpath()));
			searchButton.click();
			
			jsonReporter.appendContent(dto, "点击搜索");
			
			screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath, null);
			uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(dto, uploadImgResult.getImgUrl());
			
			jsonReporter.appendContent(dto, "完成");
			
			r.setIsSuccess();
		} catch (Exception e) {
			jsonReporter.appendContent(dto, e.getMessage());
			
		} finally {
			r.setMessage(dto.getContent());
			if (d != null) {
				d.quit();
			}
			String reportOutputPath = dto.getOutputReportPath() + File.separatorChar + dto.getReportFileName() + ".json";
			if(jsonReporter.outputReport(dto, reportOutputPath)) {
				updateTestEventReportPath(te, reportOutputPath);
			}
		}
		
		return r;
	}

	@Override
	public ModelAndView demo(BingDemoDTO dto) {
		InsertTestEventResult r = insertclawingEvent(dto.getKeyword());
		int waitingEventCount = testEventService.countWaitingEvent();
		Long eventId = r.getNewTestEventId();
		
		/*
		 * TODO set view name
		 */
		ModelAndView v = new ModelAndView();
		v.addObject("waitingEventCount", waitingEventCount);
		v.addObject("eventId", eventId);
		if(r.isSuccess()) {
			v.addObject("message", "本次任务已经成功排入队列, 输入参数为: " + dto.getKeyword() + ", 前面还有 " + waitingEventCount + " 个任务在等待执行");
		} else {
			v.addObject("message", "本次任务未成功排入队列, 请联系 davenchan12546@gmail.com");
		}
		
		return v;
	}
	
}
