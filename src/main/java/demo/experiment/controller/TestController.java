package demo.experiment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.experiment.pojo.constant.TestUrl;
import demo.experiment.service.TestService;
import demo.finance.cryptoCoin.data.service.CryptoCoinComplexService;
import demo.finance.cryptoCoin.data.service.impl.CryptoCoinCacheDataService;
import demo.finance.cryptoCoin.technicalAnalysis.service.CryptoCoinTechnicalAnalysisService;
import demo.scriptCore.scheduleClawing.cnStockMarketData.service.CnStockMarketDataService;

@Controller
@RequestMapping(value = { TestUrl.testRoot })
public class TestController extends CommonController {

	@SuppressWarnings("unused")
	@Autowired
	private TestService testService;
	@Autowired
	private CnStockMarketDataService cnStockMarketDataService;
	@Autowired
	private CryptoCoinCacheDataService cryptoCoinCacheDataService;
	@Autowired
	private CryptoCoinComplexService cryptoCoinComplexService;
	@Autowired
	private CryptoCoinTechnicalAnalysisService cryptoCoinTechnicalAnalysisService;

	@GetMapping(value = "/test")
	@ResponseBody
	public String test() throws Exception {
		return "Done";
	}

	@GetMapping(value = "/t5")
	@ResponseBody
	public String test5() {
		cnStockMarketDataService.collectDatasAndSend();
		return "Done";
	}

	@GetMapping(value = "/t8")
	@ResponseBody
	public String t8() {
		return String.valueOf(cryptoCoinCacheDataService.getBinanceKLineCacheMap());
	}

	@GetMapping(value = "/sendAllDailyDataQuery")
	@ResponseBody
	public String sendAllDailyDataQuery() {
		cryptoCoinComplexService.sendAllDailyDataQuery();
		return "Done";
	}

	@GetMapping(value = "/sendDailyDataQuery")
	@ResponseBody
	public String sendDailyDataQuery(
			@RequestParam(value = "symbol", required = false, defaultValue = "") String symbol) {
		cryptoCoinComplexService.sendDailyDataQuery(symbol);
		return "Done";
	}

	@GetMapping(value = "/filter")
	@ResponseBody
	public List<String> filter() {
		return cryptoCoinTechnicalAnalysisService.filter();
	}

}
