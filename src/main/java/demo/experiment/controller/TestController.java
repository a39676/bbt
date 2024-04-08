package demo.experiment.controller;

import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.experiment.pojo.constant.TestUrl;
import demo.experiment.service.TestService;
import demo.finance.cryptoCoin.common.service.CryptoCoinOptionService;
import demo.finance.cryptoCoin.data.binance.BinanceDataApiUnit;
import demo.finance.cryptoCoin.data.binance.BinanceDataWSClient;
import demo.finance.cryptoCoin.data.service.CryptoCoinComplexService;
import demo.finance.cryptoCoin.data.service.impl.CryptoCoinCacheDataService;
import demo.scriptCore.scheduleClawing.cnStockMarketData.service.CnStockMarketDataService;
import finance.common.pojo.type.IntervalType;
import finance.cryptoCoin.binance.pojo.dto.KLineKeyBO;

@Controller
@RequestMapping(value = { TestUrl.testRoot })
public class TestController extends CommonController {

	@Autowired
	private TestService testService;
	@Autowired
	private CnStockMarketDataService cnStockMarketDataService;
	@Autowired
	private BinanceDataWSClient binanceWSClient;
	@Autowired
	private CryptoCoinCacheDataService cryptoCoinCacheDataService;
	@Autowired
	private CryptoCoinOptionService optionService;
	@Autowired
	private CryptoCoinComplexService cryptoCoinComplexService;
	@Autowired
	private BinanceDataApiUnit binanceDataApiUnit;

	@GetMapping(value = "/test")
	@ResponseBody
	public String test() throws Exception {
		return testService.testing("something", "other");
	}

	@GetMapping(value = "/t5")
	@ResponseBody
	public String test5() {
		cnStockMarketDataService.collectDatasAndSend();
		return "Done";
	}

	@GetMapping(value = "/t6")
	@ResponseBody
	public String test6(@RequestParam(value = "symbol", defaultValue = "", required = false) String symbol) {
		if (StringUtils.isBlank(symbol)) {
			binanceWSClient.addNewKLineSubcript("BTCUSDT", IntervalType.MINUTE_1.getName());
			binanceWSClient.addNewKLineSubcript("ETHUSDT", IntervalType.MINUTE_1.getName());
			binanceWSClient.addNewKLineSubcript("DOGEUSDT", IntervalType.MINUTE_1.getName());
		} else {
			binanceWSClient.addNewKLineSubcript(symbol, "1m");
		}
		return "Done";
	}

	@GetMapping(value = "/t8")
	@ResponseBody
	public String t8() {
		return String.valueOf(cryptoCoinCacheDataService.getBinanceKLineCacheMap());
	}

	@GetMapping(value = "/t9")
	@ResponseBody
	public String t9() {
		cryptoCoinComplexService.getRecentBigMoveCounterBySymbol();
		return "Done";
	}

	@GetMapping(value = "/t13")
	@ResponseBody
	public String t13() {
		cryptoCoinComplexService.getCryptoCoinOptionFromCthulhu();
		return String.valueOf(optionService);
	}

	@GetMapping(value = "/t14")
	@ResponseBody
	public String t14() {
		KLineKeyBO key = new KLineKeyBO();
		key.setSymbol("BTCUSDT");
		key.setInterval(IntervalType.MINUTE_1.getName());
		cryptoCoinCacheDataService.getBinanceKLineCacheMap().put(key, new ArrayList<>());
		cryptoCoinComplexService.checkBigMoveInHours();
		return "Done";
	}

	@GetMapping(value = "/t15")
	@ResponseBody
	public String t15(@RequestParam(value = "symbol", defaultValue = "", required = false) String symbol) {
		return String.valueOf(binanceDataApiUnit.getKLineHourData(symbol));
	}

}
