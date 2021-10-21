package demo.scriptCore.demo.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.pojo.type.AutomationTestFlowResultType;
import autoTest.testEvent.searchingDemo.pojo.dto.BingSearchInHomePageDTO;
import autoTest.testEvent.searchingDemo.pojo.type.BingDemoSearchFlowType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.demo.pojo.pe.HomePage;
import demo.scriptCore.demo.pojo.pe.ResultPage;
import demo.scriptCore.demo.service.BingDemoService;

@Service
public class BingDemoServiceImpl extends BingDemoCommonService implements BingDemoService {

	@Override
	public TestEventBO searchInHomepage(TestEventBO tbo) {
		BingDemoSearchFlowType flowType = BingDemoSearchFlowType.SEARCH_IN_HOMEPAGE;
		tbo.setFlowId(flowType.getId());
		tbo.setFlowName(flowType.getFlowName());

		try {
			keywordSearchInHomepage(tbo);
			checkResult(tbo);

		} catch (Exception e) {
			JsonReportOfCaseDTO errorReport = buildCaseReportDTO();
			reportService.caseReportAppendContent(errorReport, e.getMessage());
			tbo.getReport().getCaseReportList().add(errorReport);
		}
		tryQuitWebDriver(tbo.getWebDriver());
		sendAutomationTestResult(tbo);

		return tbo;
	}

	private AutomationTestCaseResult keywordSearchInHomepage(TestEventBO tbo) {
		String casename = "searchInHomepage";
		AutomationTestCaseResult r = initCaseResult(casename);
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(casename);

		reportService.caseReportAppendContent(caseReport, "准备进行搜索");
		WebDriver d = tbo.getWebDriver();

		BingSearchInHomePageDTO dto = auxTool.buildParamDTO(tbo, BingSearchInHomePageDTO.class);
		if (dto == null) {
			reportService.caseReportAppendContent(caseReport, "读取参数异常");
			return r;
		}

		String mainUrl = "https://cn.bing.com/?FORM=BEHPTB";

		try {
			d.get(mainUrl);
			reportService.caseReportAppendContent(caseReport, "打开: " + mainUrl);
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
			reportService.caseReportAppendContent(caseReport, "访问超时");
		}

		addScreenshotToReport(d, caseReport);

		WebElement keywordInput = d.findElement(By.xpath(HomePage.keywordInput));
		keywordInput.click();
		keywordInput.clear();
		keywordInput.sendKeys(dto.getSearchKeyword());

		if (StringUtils.isBlank(dto.getSearchKeyword())) {
			reportService.caseReportAppendContent(caseReport, "输入空白关键词");
		} else {
			reportService.caseReportAppendContent(caseReport, "输入关键词: " + dto.getSearchKeyword());
		}

		addScreenshotToReport(d, caseReport);

		WebElement searchButton = d.findElement(By.xpath(HomePage.searchButton));
		searchButton.click();

		reportService.caseReportAppendContent(caseReport, "点击搜索");

		addScreenshotToReport(d, caseReport);

		reportService.caseReportAppendContent(caseReport, "完成搜索");
		r.setResultType(AutomationTestFlowResultType.PASS);
		tbo.getCaseResultList().add(r);
		tbo.getReport().getCaseReportList().add(caseReport);
		return r;
	}

	private AutomationTestCaseResult checkResult(TestEventBO tbo) {
		String casename = "checkResult";
		AutomationTestCaseResult r = initCaseResult(casename);
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(casename);
		try {
			reportService.caseReportAppendContent(caseReport, "准备检查搜索结果");

			WebDriver d = tbo.getWebDriver();

			BingSearchInHomePageDTO dto = auxTool.buildParamDTO(tbo, BingSearchInHomePageDTO.class);



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
