package demo.clawing.demo.service.impl;

import java.io.File;

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
import demo.clawing.demo.service.BingDemoService;
import demo.selenium.service.impl.SeleniumCommonService;
import image.pojo.result.ImageSavingResult;

@Service
public class BingDemoServiceImpl extends SeleniumCommonService implements BingDemoService {
	
	private String eventName = "bing_search_demo";
	
	private String getReportOutputPath() {
		return globalOptionService.getReportOutputFolder() + File.separator + eventName;
	}
	
	@Override
	public CommonResultBBT clawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		JsonReportDTO reportDTO = new JsonReportDTO();
		
		String reportOutputFolderPath = getReportOutputPath();
		
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());

		WebDriver d = null;

		String mainUrl = "https://cn.bing.com/?FORM=BEHPTB";
		
		try {
			d = webDriverService.buildChromeWebDriver();
			try {
				d.get(mainUrl);
				reportService.appendContent(reportDTO, "打开: " + mainUrl);
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				reportService.appendContent(reportDTO, "访问超时");
			}
			
			XpathBuilderBO x = new XpathBuilderBO();
			
			ScreenshotSaveResult screenSaveResult = screenshot(d, te.getEventName());
			ImageSavingResult uploadImgResult = saveImgToCX(screenSaveResult.getSavingPath(), screenSaveResult.getFileName());
			reportService.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
			x.start("input").addAttribute("id", "sb_form_q");
			WebElement keywordInput = d.findElement(By.xpath(x.getXpath()));
			keywordInput.click();
			keywordInput.clear();
			keywordInput.sendKeys(te.getRemark());
			
			if(StringUtils.isBlank(te.getRemark())) {
				reportService.appendContent(reportDTO, "输入空白关键词");
			} else {
				reportService.appendContent(reportDTO, "输入关键词: " + te.getRemark());
			}
			
			screenSaveResult = screenshot(d, te.getEventName());
			uploadImgResult = saveImgToCX(screenSaveResult.getSavingPath(), screenSaveResult.getFileName());
			reportService.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
			x.start("div").addAttribute("id", "sb_go_par");
			WebElement searchButton = d.findElement(By.xpath(x.getXpath()));
			searchButton.click();
			
			reportService.appendContent(reportDTO, "点击搜索");
			
			screenSaveResult = screenshot(d, te.getEventName());
			uploadImgResult = saveImgToCX(screenSaveResult.getSavingPath(), screenSaveResult.getFileName());
			reportService.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
			reportService.appendContent(reportDTO, "完成");
			
			r.setIsSuccess();
		} catch (Exception e) {
			reportService.appendContent(reportDTO, e.getMessage());
			
		} finally {
			r.setMessage(reportDTO.getContent());
			tryQuitWebDriver(d, reportDTO);
			String reportOutputPath = reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json";
			if(reportService.outputReport(reportDTO, reportOutputPath)) {
				updateTestEventReportPath(te, reportOutputPath);
			}
		}
		
		return r;
	}
	
	public void searchInHomepage(TestEvent te) {
		
	}

}
