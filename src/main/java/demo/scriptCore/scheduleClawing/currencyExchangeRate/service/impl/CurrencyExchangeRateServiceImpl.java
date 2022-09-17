package demo.scriptCore.scheduleClawing.currencyExchangeRate.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.common.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.common.pojo.type.AutomationTestFlowResultType;
import autoTest.testEvent.scheduleClawing.currencyExchangeRate.pojo.dto.CurrencyExchangeRateCollectDTO;
import autoTest.testEvent.scheduleClawing.currencyExchangeRate.pojo.type.CurrencyExchangeRateFlowType;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.scheduleClawing.currencyExchangeRate.pojo.dto.CurrencyExchageRateDataDTO;
import demo.scriptCore.scheduleClawing.currencyExchangeRate.service.CurrencyExchangeRateService;

@Service
public class CurrencyExchangeRateServiceImpl extends AutomationTestCommonService
		implements CurrencyExchangeRateService {

	@Override
	public TestEventBO getDailyData(TestEventBO tbo) {
		CurrencyExchangeRateFlowType flowType = CurrencyExchangeRateFlowType.DAILY_DATA;
		tbo.setFlowId(flowType.getId());
		tbo.setFlowName(flowType.getFlowName());
		WebDriver webDriver = null;

		try {
			webDriver = webDriverService.buildChromeWebDriver();
			collectFromOanda(tbo, webDriver);

		} catch (Exception e) {
			JsonReportOfCaseDTO errorReport = buildCaseReportDTO();
			reportService.caseReportAppendContent(errorReport, e.getMessage());
			tbo.getReport().getCaseReportList().add(errorReport);
		}
		tryQuitWebDriver(webDriver);
//		TODO
//		sendAutomationTestResult(tbo);

		return tbo;
	}

	private AutomationTestCaseResult collectFromOanda(TestEventBO tbo, WebDriver webDriver)
			throws InterruptedException {
		String casename = "collect";
		AutomationTestCaseResult r = initCaseResult(casename);
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(casename);

		reportService.caseReportAppendContent(caseReport, "Before collect from Oanda");

		CurrencyExchangeRateCollectDTO dto = buildTestEventParamFromJsonCustomization(tbo.getParamStr(),
				CurrencyExchangeRateCollectDTO.class);
		if (dto == null) {
			reportService.caseReportAppendContent(caseReport, "Exchange rate parameter reading error");
			return r;
		}

		String mainUrl = dto.getMainUrl();

		try {
			webDriver.get(mainUrl);
			reportService.caseReportAppendContent(caseReport, "Get: " + mainUrl);
		} catch (TimeoutException e) {
			jsUtil.windowStop(webDriver);
			reportService.caseReportAppendContent(caseReport, "HTTP timeout");
		}

		collectSubData(webDriver, CurrencyType.USD, CurrencyType.CNY);
		collectSubData(webDriver, CurrencyType.EUR, CurrencyType.CNY);
		
//		TODO
		r.setResultType(AutomationTestFlowResultType.PASS);
		tbo.getCaseResultList().add(r);
		tbo.getReport().getCaseReportList().add(caseReport);
		return r;
	}

	private CurrencyExchageRateDataDTO collectSubData(WebDriver d, CurrencyType currencyFrom,
			CurrencyType currencyTo) throws InterruptedException {
		CurrencyExchageRateDataDTO dto = new CurrencyExchageRateDataDTO();

		String swapIconXpathStr = "//body/div[@id='scroll-wrap']/main[1]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/button[1]/span[1]/*[1]";

		String currencyNameOfFromXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]";
		String currencyNameOfToXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]";

		String currencyNameInputOfFromXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/input[1]";
		String currencyNameInputOfToXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/input[1]";

		String valueOfCurrencyToXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[3]/table[1]/tbody[1]/tr[1]/td[2]/span[2]";

		if (!auxTool.loadingCheck(d, swapIconXpathStr)) {
			return null;
		}
		
		WebElement currencyNameInputOfFrom = d.findElement(By.xpath(currencyNameInputOfFromXpathStr));
		currencyNameInputOfFrom.click();
		currencyNameInputOfFrom.clear();
		currencyNameInputOfFrom.sendKeys(currencyFrom.getName());
		currencyNameInputOfFrom.sendKeys(Keys.DOWN);
		currencyNameInputOfFrom.sendKeys(Keys.RETURN);
//		TODO
		
		return dto;
	}
}
