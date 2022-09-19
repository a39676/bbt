package demo.scriptCore.scheduleClawing.currencyExchangeRate.service.impl;

import java.math.BigDecimal;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.common.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.common.pojo.type.AutomationTestFlowResultType;
import autoTest.testEvent.scheduleClawing.currencyExchangeRate.pojo.dto.CurrencyExchangeRateCollectDTO;
import autoTest.testEvent.scheduleClawing.currencyExchangeRate.pojo.dto.CurrencyExchangeRatePairDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import auxiliaryCommon.pojo.type.CurrencyType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.scheduleClawing.currencyExchangeRate.mq.sender.CurrencyExchangeRateDailyDataAckProducer;
import demo.scriptCore.scheduleClawing.currencyExchangeRate.service.CurrencyExchangeRateService;
import finance.currencyExchangeRate.pojo.dto.CurrencyExchageRateDataDTO;
import finance.currencyExchangeRate.pojo.result.CurrencyExchageRateCollectResult;

@Service
public class CurrencyExchangeRateServiceImpl extends AutomationTestCommonService
		implements CurrencyExchangeRateService {
	
	@Autowired
	private CurrencyExchangeRateDailyDataAckProducer currencyExchangeRateDailyDataAckProducer;

	@Override
	public TestEventBO getDailyData(TestEventBO tbo) {
		ScheduleClawingType flowType = ScheduleClawingType.CURRENCY_EXCHANGE_RAGE;
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
		sendAutomationTestResult(tbo);

		return tbo;
	}

	private AutomationTestCaseResult collectFromOanda(TestEventBO tbo, WebDriver webDriver)
			throws Exception {
		String casename = "collectFromOanda";
		AutomationTestCaseResult r = initCaseResult(casename);
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(casename);

		reportService.caseReportAppendContent(caseReport, "Before collect from Oanda");

		CurrencyExchangeRateCollectDTO paramDTO = buildTestEventParamFromJsonCustomization(tbo.getParamStr(),
				CurrencyExchangeRateCollectDTO.class);
		if (paramDTO == null) {
			reportService.caseReportAppendContent(caseReport, "Exchange rate parameter reading error");
			throw new Exception();
		}

		String mainUrl = paramDTO.getMainUrl();

		try {
			webDriver.get(mainUrl);
			reportService.caseReportAppendContent(caseReport, "Get: " + mainUrl);
		} catch (TimeoutException e) {
			jsUtil.windowStop(webDriver);
			reportService.caseReportAppendContent(caseReport, "HTTP timeout");
		}

		CurrencyExchageRateDataDTO dataDTO = null;
		CurrencyExchageRateCollectResult collectResult = new CurrencyExchageRateCollectResult();
		collectResult.setIsDailyQuery(paramDTO.getIsDailyQuery());
		CurrencyType fromCurrency = null;
		CurrencyType toCurrency = null;
		for(CurrencyExchangeRatePairDTO currencyPairDTO : paramDTO.getPairList()) {
			fromCurrency = CurrencyType.getType(currencyPairDTO.getCurrencyFromCode());
			toCurrency = CurrencyType.getType(currencyPairDTO.getCurrencyToCode());
			dataDTO = collectSubData(webDriver, caseReport, fromCurrency, toCurrency);
			if(dataDTO != null) {
				collectResult.addData(dataDTO);
			}
		}
		
		currencyExchangeRateDailyDataAckProducer.sendCurrencyExchangeRateData(collectResult);
		r.setResultType(AutomationTestFlowResultType.PASS);
		tbo.getCaseResultList().add(r);
		tbo.getReport().getCaseReportList().add(caseReport);
		return r;
	}

	private CurrencyExchageRateDataDTO collectSubData(WebDriver d, JsonReportOfCaseDTO caseReport,
			CurrencyType currencyFrom, CurrencyType currencyTo) throws InterruptedException {
		CurrencyExchageRateDataDTO dto = new CurrencyExchageRateDataDTO();

		String swapIconXpathStr = "//body/div[@id='scroll-wrap']/main[1]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/button[1]/span[1]/*[1]";

		if (!auxTool.loadingCheck(d, swapIconXpathStr)) {
			reportService.caseReportAppendContent(caseReport, "Page loading timeout");
			return null;
		}

		String currencyNameInputOfFromXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/input[1]";
		String currencyNameInputOfToXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/input[1]";

		WebElement currencyNameInputOfFrom = d.findElement(By.xpath(currencyNameInputOfFromXpathStr));
		currencyNameInputOfFrom.click();
		for(int i = 0; i < 10; i++) {
			currencyNameInputOfFrom.sendKeys(Keys.BACK_SPACE);
		}
		currencyNameInputOfFrom.sendKeys(currencyFrom.getName());
		threadSleepRandomTime(100L, 200L);
		currencyNameInputOfFrom.sendKeys(Keys.DOWN);
		threadSleepRandomTime(100L, 200L);
		currencyNameInputOfFrom.sendKeys(Keys.RETURN);

		WebElement currencyNameInputOfTo = d.findElement(By.xpath(currencyNameInputOfToXpathStr));
		currencyNameInputOfTo.click();
		for(int i = 0; i < 10; i++) {
			currencyNameInputOfFrom.sendKeys(Keys.BACK_SPACE);
		}
		currencyNameInputOfTo.sendKeys(currencyTo.getName());
		threadSleepRandomTime(100L, 200L);
		currencyNameInputOfTo.sendKeys(Keys.DOWN);
		threadSleepRandomTime(100L, 200L);
		currencyNameInputOfTo.sendKeys(Keys.RETURN);

		if (!auxTool.loadingCheck(d, swapIconXpathStr)) {
			reportService.caseReportAppendContent(caseReport, "Page loading timeout");
			return null;
		}

		dto.setCurrencyCodeFrom(currencyFrom.getCode());
		dto.setCurrencyCodeTo(currencyTo.getCode());
		
		String valueOfCurrencyToXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[3]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[3]/table[1]/tbody[1]/tr[1]/td[2]/span[2]";
		WebElement eleValueOfCurrencyTo = d.findElement(By.xpath(valueOfCurrencyToXpathStr));
		try {
			BigDecimal valueOfCurrencyTo = new BigDecimal(eleValueOfCurrencyTo.getText());
			dto.setCurrencyAmountTo(valueOfCurrencyTo);
			dto.setCurrencyAmountFrom(BigDecimal.ONE);
		} catch (Exception e) {
			reportService.caseReportAppendContent(caseReport, "Catch error value of \"To currency\": " + eleValueOfCurrencyTo.getText());
			return null;
		}
		
		String valueOfYesterdayBuyLowXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[1]/td[2]";
		WebElement eleValueOfYesterBuyLow = d.findElement(By.xpath(valueOfYesterdayBuyLowXpathStr));
		try {
			BigDecimal yesterdayBuyLow = new BigDecimal(eleValueOfYesterBuyLow.getText());
			dto.setYesterdayBuyLow(yesterdayBuyLow);
		} catch (Exception e) {
			reportService.caseReportAppendContent(caseReport, "Catch error value of \"Buy low yesterday\": " + eleValueOfYesterBuyLow.getText());
			return null;
		}
		
		String valueOfYesterdayBuyHighXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[3]/td[2]";
		WebElement eleValueOfYesterBuyHigh = d.findElement(By.xpath(valueOfYesterdayBuyHighXpathStr));
		try {
			BigDecimal yesterdayBuyHigh = new BigDecimal(eleValueOfYesterBuyHigh.getText());
			dto.setYesterdayBuyHigh(yesterdayBuyHigh);
		} catch (Exception e) {
			reportService.caseReportAppendContent(caseReport, "Catch error value of \"Buy high yesterday\": " + eleValueOfYesterBuyHigh.getText());
			return null;
		}
		
		String valueOfYesterdaySellLowXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[3]/td[2]";
		WebElement eleValueOfYesterSellLow = d.findElement(By.xpath(valueOfYesterdaySellLowXpathStr));
		try {
			BigDecimal yesterdaySellLow = new BigDecimal(eleValueOfYesterSellLow.getText());
			dto.setYesterdaySellLow(yesterdaySellLow);
		} catch (Exception e) {
			reportService.caseReportAppendContent(caseReport, "Catch error value of \"Sell low yesterday\": " + eleValueOfYesterSellLow.getText());
			return null;
		}
		
		String valueOfYesterdaySellHighXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[3]/td[3]";
		WebElement eleValueOfYesterSellHigh = d.findElement(By.xpath(valueOfYesterdaySellHighXpathStr));
		try {
			BigDecimal yesterdaySellHigh = new BigDecimal(eleValueOfYesterSellHigh.getText());
			dto.setYesterdaySellHigh(yesterdaySellHigh);
		} catch (Exception e) {
			reportService.caseReportAppendContent(caseReport, "Catch error value of \"Sell high of yesterday \": " + eleValueOfYesterSellHigh.getText());
			return null;
		}
		
		String valueOfYesterdayBuyAvgXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[2]/td[2]";
		WebElement eleValueOfYesterdayBuyAvg = d.findElement(By.xpath(valueOfYesterdayBuyAvgXpathStr));
		try {
			BigDecimal yesterdayBuyAvg = new BigDecimal(eleValueOfYesterdayBuyAvg.getText());
			dto.setYesterdayBuyAvg(yesterdayBuyAvg);
		} catch (Exception e) {
			reportService.caseReportAppendContent(caseReport, "Catch error value of \"Buy avg of yesterday\": " + eleValueOfYesterdayBuyAvg.getText());
			return null;
		}
		
		String valueOfYesterdaySellAvgXpathStr = "/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/div[1]/div[4]/div[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/table[1]/tbody[1]/tr[2]/td[3]";
		WebElement eleValueOfYesterdaySellAvg = d.findElement(By.xpath(valueOfYesterdaySellAvgXpathStr));
		try {
			BigDecimal yesterdaySellAvg = new BigDecimal(eleValueOfYesterdaySellAvg.getText());
			dto.setYesterdaySellAvg(yesterdaySellAvg);
		} catch (Exception e) {
			reportService.caseReportAppendContent(caseReport, "Catch error value of \"Sel avg of yesterday\": " + eleValueOfYesterdaySellAvg.getText());
			return null;
		}
		
		return dto;
	}
}
