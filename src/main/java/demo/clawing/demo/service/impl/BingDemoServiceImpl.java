package demo.clawing.demo.service.impl;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import at.pojo.bo.XpathBuilderBO;
import at.pojo.dto.JsonReportDTO;
import at.pojo.dto.TakeScreenshotSaveDTO;
import at.pojo.result.ScreenshotSaveResult;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.demo.service.BingDemoService;
import demo.selenium.service.impl.SeleniumCommonService;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service
public class BingDemoServiceImpl extends SeleniumCommonService implements BingDemoService {
	
	private String eventName = "bing search demo";
	
	private String getScreenshotSaveingPath() {
		return globalOptionService.getScreenshotSavingFolder() + File.separator + eventName;
	}
	
	private String getReportOutputPath() {
		return globalOptionService.getReportOutputFolder() + File.separator + eventName;
	}
	
	@Override
	public CommonResultBBT clawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		JsonReportDTO reportDTO = new JsonReportDTO();
		
		String screenshotPath = getScreenshotSaveingPath();
		String reportOutputFolderPath = getReportOutputPath();
		
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		WebDriver d = null;

		String mainUrl = "https://cn.bing.com/?FORM=BEHPTB";
		
		try {
			d = webDriverService.buildFireFoxWebDriver();
			try {
				d.get(mainUrl);
				jsonReporter.appendContent(reportDTO, "打开: " + mainUrl);
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "访问超时");
			}
			
			XpathBuilderBO x = new XpathBuilderBO();
			
			TakeScreenshotSaveDTO screenshotDTO = new TakeScreenshotSaveDTO();
			screenshotDTO.setDriver(d);
			ScreenshotSaveResult screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath, null);
			
			UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
			x.start("input").addAttribute("id", "sb_form_q");
			WebElement keywordInput = d.findElement(By.xpath(x.getXpath()));
			keywordInput.click();
			keywordInput.clear();
			keywordInput.sendKeys(te.getRemark());
			
			if(StringUtils.isBlank(te.getRemark())) {
				jsonReporter.appendContent(reportDTO, "输入空白关键词");
			} else {
				jsonReporter.appendContent(reportDTO, "输入关键词: " + te.getRemark());
			}
			
			screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath, null);
			uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
			x.start("div").addAttribute("id", "sb_go_par");
			WebElement searchButton = d.findElement(By.xpath(x.getXpath()));
			searchButton.click();
			
			jsonReporter.appendContent(reportDTO, "点击搜索");
			
			screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath, null);
			uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
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
