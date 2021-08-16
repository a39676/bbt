package demo.scriptCore.demo.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.xpath.pojo.bo.XpathBuilderBO;
import autoTest.testEvent.searchingDemo.pojo.dto.ATBingDemoDTO;
import autoTest.testEvent.searchingDemo.pojo.dto.BingSearchInHomePageDTO;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.scriptCore.demo.pojo.type.testEvent.SearchingDemoEventType;
import demo.scriptCore.demo.service.BingDemoService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class BingDemoServiceImpl extends SeleniumCommonService implements BingDemoService {

	@Autowired
	private FileUtilCustom ioUtil;
	
	private TestEvent buildBingSearchDemo(ATBingDemoDTO dto) {
		BuildTestEventBO bo = new BuildTestEventBO();

		TestModuleType testModuleType = TestModuleType.ATDemo;
		SearchingDemoEventType testCastType = SearchingDemoEventType.bingDemo;
		String eventName = "bing_search_demo";
		
		String paramterFolderPath = getParameterSaveingPath(eventName);
		
		if(dto.getBingSearchInHomePageDTO() == null) {
			File paramterFile = new File(paramterFolderPath + File.separator + "default.json");
			if (!paramterFile.exists()) {
				return null;
			}
			bo.setParameterFilePath(paramterFile.getAbsolutePath());
		} else {
			File paramterFile = new File(paramterFolderPath + File.separator + snowFlake.getNextId() + ".json");
			JSONObject json = JSONObject.fromObject(dto);
			ioUtil.byteToFile(json.toString().getBytes(StandardCharsets.UTF_8), paramterFile.getAbsolutePath());
			bo.setParameterFilePath(paramterFile.getAbsolutePath());
		}

		bo.setTestModuleType(testModuleType);
		bo.setCaseId(testCastType.getId());
		bo.setEventName(eventName);
		
		return buildTestEvent(bo);
	}

	@Override
	public InsertTestEventResult insertSearchDemoEvent(ATBingDemoDTO dto) {
		TestEvent te = buildBingSearchDemo(dto);
		return testEventService.insertTestEvent(te);
	}
	
	@Override
	public InsertTestEventResult insertSearchDemoEvent() {
		return insertSearchDemoEvent(null);
	}
	
	@Override
	public CommonResult testing(TestEvent te) {
		CommonResult r = new CommonResult();
		
		TestEventBO tbo = auxTool.beforeRunning(te);
		
		try {
			r = searchInHomepage(tbo);
			r = checkResult(tbo);
		
		} catch (Exception e) {
			reportService.appendContent(tbo.getReport(), e.getMessage());

		} finally {
			tryQuitWebDriver(tbo.getWebDriver(), tbo.getReport());
			saveReport(tbo);
		}

		return r;
	}

	private CommonResult searchInHomepage(TestEventBO tbo) {
		CommonResult r = new CommonResult();
		reportService.appendContent(tbo.getReport(), "准备进行搜索");
		WebDriver d = tbo.getWebDriver();

		BingSearchInHomePageDTO dto = auxTool.buildParamDTO(tbo, BingSearchInHomePageDTO.class);
		if (dto == null) {
			reportService.appendContent(tbo.getReport(), "读取参数异常");
			return r;
		}

		addScreenshotToReport(d, tbo);

		String mainUrl = "https://cn.bing.com/?FORM=BEHPTB";

		try {
			d.get(mainUrl);
			reportService.appendContent(tbo.getReport(), "打开: " + mainUrl);
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
			reportService.appendContent(tbo.getReport(), "访问超时");
		}

		XpathBuilderBO x = new XpathBuilderBO();

		addScreenshotToReport(d, tbo);

		x.start("input").addAttribute("id", "sb_form_q");
		WebElement keywordInput = d.findElement(By.xpath(x.getXpath()));
		keywordInput.click();
		keywordInput.clear();
		keywordInput.sendKeys(dto.getSearchKeyword());

		if (StringUtils.isBlank(dto.getSearchKeyword())) {
			reportService.appendContent(tbo.getReport(), "输入空白关键词");
		} else {
			reportService.appendContent(tbo.getReport(), "输入关键词: " + dto.getSearchKeyword());
		}

		addScreenshotToReport(d, tbo);

		x.start("div").addAttribute("id", "sb_go_par");
		WebElement searchButton = d.findElement(By.xpath(x.getXpath()));
		searchButton.click();

		reportService.appendContent(tbo.getReport(), "点击搜索");

		addScreenshotToReport(d, tbo);

		reportService.appendContent(tbo.getReport(), "完成搜索");
		r.setIsSuccess();
		return r;
	}
	
	private CommonResult checkResult(TestEventBO tbo) {
		CommonResult r = new CommonResult();
		reportService.appendContent(tbo.getReport(), "准备检查搜索结果");

		WebDriver d = tbo.getWebDriver();
		
		BingSearchInHomePageDTO dto = auxTool.buildParamDTO(tbo, BingSearchInHomePageDTO.class);
		
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("ol").addId("b_results");
		
		WebElement resultListOL = null;
		try {
			resultListOL = d.findElement(By.xpath(x.getXpath()));
		} catch (Exception e) {
			reportService.appendContent(tbo.getReport(), "无法定位搜索结果");
			return r;
		}
		
		if(!resultListOL.getText().contains(dto.getSearchKeyword())) {
			reportService.appendContent(tbo.getReport(), "搜索结果未包含目标关键字");
			return r;
		}
		
		x.findChild("li");
		List<WebElement> resultListLi = d.findElements(By.xpath(x.getXpath()));
		
		for(int i = 0; i < resultListLi.size(); i++) {
			if(resultListLi.get(i).getText().contains(dto.getSearchKeyword())) {
				reportService.appendContent(tbo.getReport(), "第" + (i + 1) + "位包含目标关键字");
				r.setIsSuccess();
			}
		}
		
		return r;
	}

}
