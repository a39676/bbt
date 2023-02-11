package demo.scriptCore.bingDemo.servcie.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.common.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.common.pojo.type.AutomationTestFlowResultType;
import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.dto.BingSearchInHomePageDTO;
import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.type.BingDemoSearchFlowType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.bingDemo.pojo.pe.HomePage;
import demo.scriptCore.bingDemo.pojo.pe.ResultPage;
import demo.scriptCore.bingDemo.servcie.BingDemoService;

@Service
public class BingDemoServiceImpl extends BingDemoCommonService implements BingDemoService {

	@Override
	public TestEventBO searchInHomepage(TestEventBO tbo) {
		BingDemoSearchFlowType flowType = BingDemoSearchFlowType.SEARCH_IN_HOMEPAGE;
		tbo.setFlowId(flowType.getId());
		tbo.setFlowName(flowType.getFlowName());

		WebDriver webDriver = null;
		
		try {
			webDriver = webDriverService.buildChromeWebDriver();
			keywordSearchInHomepage(tbo, webDriver);
			checkResult(tbo, webDriver);

		} catch (Exception e) {
			JsonReportOfCaseDTO errorReport = buildCaseReportDTO();
			reportService.caseReportAppendContent(errorReport, e.getMessage());
			tbo.getReport().getCaseReportList().add(errorReport);
		}
		if(!tryQuitWebDriver(webDriver)) {
			sendTelegramMsg("Web driver quit failed, " + flowType.getFlowName());
		}
		sendAutomationTestResult(tbo);

		return tbo;
	}

	private AutomationTestCaseResult keywordSearchInHomepage(TestEventBO tbo, WebDriver webDriver) {
		String casename = "searchInHomepage";
		AutomationTestCaseResult r = initCaseResult(casename);
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(casename);

		reportService.caseReportAppendContent(caseReport, "准备进行搜索");

		BingSearchInHomePageDTO dto = buildTestEventParamFromJsonCustomization(tbo.getParamStr(), BingSearchInHomePageDTO.class);
		if (dto == null) {
			reportService.caseReportAppendContent(caseReport, "读取参数异常");
			return r;
		}

		String mainUrl = "https://cn.bing.com/?FORM=BEHPTB";

		try {
			webDriver.get(mainUrl);
			reportService.caseReportAppendContent(caseReport, "打开: " + mainUrl);
		} catch (TimeoutException e) {
			jsUtil.windowStop(webDriver);
			reportService.caseReportAppendContent(caseReport, "访问超时");
		}

		addScreenshotToReport(webDriver, caseReport);

		WebElement keywordInput = webDriver.findElement(By.xpath(HomePage.keywordInput));
		keywordInput.click();
		keywordInput.clear();
		keywordInput.sendKeys(dto.getSearchKeyword());

		if (StringUtils.isBlank(dto.getSearchKeyword())) {
			reportService.caseReportAppendContent(caseReport, "输入空白关键词");
		} else {
			reportService.caseReportAppendContent(caseReport, "输入关键词: " + dto.getSearchKeyword());
		}

		addScreenshotToReport(webDriver, caseReport);

		WebElement searchButton = webDriver.findElement(By.xpath(HomePage.searchButton));
		searchButton.click();

		reportService.caseReportAppendContent(caseReport, "点击搜索");

		addScreenshotToReport(webDriver, caseReport);

		reportService.caseReportAppendContent(caseReport, "完成搜索");
		r.setResultType(AutomationTestFlowResultType.PASS);
		tbo.getCaseResultList().add(r);
		tbo.getReport().getCaseReportList().add(caseReport);
		return r;
	}

	private AutomationTestCaseResult checkResult(TestEventBO tbo, WebDriver webDriver) {
		String casename = "checkResult";
		AutomationTestCaseResult r = initCaseResult(casename);
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(casename);
		try {
			reportService.caseReportAppendContent(caseReport, "准备检查搜索结果");

			WebDriver d = webDriver;

			BingSearchInHomePageDTO dto = buildTestEventParamFromJsonCustomization(tbo.getParamStr(), BingSearchInHomePageDTO.class);



			WebElement resultListOL = null;
			try {
				resultListOL = d.findElement(By.xpath(ResultPage.resultListOL));
			} catch (Exception e) {
				reportService.caseReportAppendContent(caseReport, "无法定位搜索结果");
				throw new Exception();
			}

			if (!resultListOL.getText().contains(dto.getSearchKeyword())) {
				reportService.caseReportAppendContent(caseReport, "搜索结果未包含目标关键字");
			} else {
				List<WebElement> resultListLi = d.findElements(By.xpath(ResultPage.resultListLi));

				for (int i = 0; i < resultListLi.size(); i++) {
					if (resultListLi.get(i).getText().contains(dto.getSearchKeyword())) {
						reportService.caseReportAppendContent(caseReport, "第" + (i + 1) + "位包含目标关键字");
						r.setResultType(AutomationTestFlowResultType.PASS);
					}
				}
			}
		} catch (Exception e) {
//			e.printStackTrace();
		}
		
		tbo.getCaseResultList().add(r);
		tbo.getReport().getCaseReportList().add(caseReport);
		return r;

	}

}
