package demo.clawing.demo.service.impl;

import java.io.File;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import at.report.pojo.dto.JsonReportDTO;
import at.screenshot.pojo.result.ScreenshotSaveResult;
import at.xpath.pojo.bo.XpathBuilderBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.demo.service.BaiduDemoService;
import demo.selenium.service.impl.SeleniumCommonService;
import image.pojo.result.ImageSavingResult;
import selenium.pojo.constant.SeleniumConstant;

@Service
public class BaiduDemoServiceImpl extends SeleniumCommonService implements BaiduDemoService {
	
	private String eventName = "baidu_search_demo";
	
	private String getReportOutputPath() {
		return globalOptionService.getReportOutputFolder() + File.separator + eventName;
	}
	
	@Override
	public CommonResultBBT clawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		JsonReportDTO reportDTO = new JsonReportDTO();
		
		String reportOutputFolderPath = getReportOutputPath();
		
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		WebDriver d = webDriverService.buildFireFoxWebDriver();

		String mainUrl = "https://www.baidu.com/";
		
		
		try {
			try {
				d.get(mainUrl);
				jsonReporter.appendContent(reportDTO, "打开: " + mainUrl);
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "访问超时");
			}
			
			XpathBuilderBO x = new XpathBuilderBO();
			
			ScreenshotSaveResult screenSaveResult = screenshot(d, te.getEventName());
			LocalDateTime screenshotImageValidTime = LocalDateTime.now().plusMonths(SeleniumConstant.maxHistoryMonth);
			
//			UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			ImageSavingResult uploadImgResult = saveImgToCX(screenSaveResult.getSavingPath(), screenSaveResult.getFileName(), screenshotImageValidTime);
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
			x.start("input").addAttribute("id", "kw");
			WebElement keywordInput = d.findElement(By.xpath(x.getXpath()));
			keywordInput.click();
			keywordInput.clear();
			keywordInput.sendKeys(te.getRemark());
			
			if(StringUtils.isBlank(te.getRemark())) {
				jsonReporter.appendContent(reportDTO, "输入空白关键词");
			} else {
				jsonReporter.appendContent(reportDTO, "输入关键词: " + te.getRemark());
			}
			
			screenSaveResult = screenshot(d, te.getEventName());
			uploadImgResult = saveImgToCX(screenSaveResult.getSavingPath(), screenSaveResult.getFileName(), screenshotImageValidTime);
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
			x.start("input").addAttribute("id", "su");
			WebElement searchButton = d.findElement(By.xpath(x.getXpath()));
			searchButton.click();
			
			jsonReporter.appendContent(reportDTO, "点击搜索");
			
			screenSaveResult = screenshot(d, te.getEventName());
			uploadImgResult = saveImgToCX(screenSaveResult.getSavingPath(), screenSaveResult.getFileName(), screenshotImageValidTime);
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
			Thread.sleep(1500L);
			
			/*
			 * TODO
			 */
//			x.start("a").addAttribute("class", "OP_LOG_LINK");
//			List<WebElement> orgAList = d.findElements(By.xpath(x.getXpath()));
//			jsonReporter.appendContent(reportDTO, "找到: " + orgAList.size() + "个官网标记");
			
			jsonReporter.appendContent(reportDTO, "完成");
			
			r.setIsSuccess();
		} catch (Exception e) {
			jsonReporter.appendContent(reportDTO, e.getMessage());
			
		} finally {
			r.setMessage(reportDTO.getContent());
			tryQuitWebDriver(d, reportDTO);
			String reportOutputPath = reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json";
			if(jsonReporter.outputReport(reportDTO, reportOutputPath)) {
				updateTestEventReportPath(te, reportOutputPath);
			}
		}
		
		return r;
	}

}
