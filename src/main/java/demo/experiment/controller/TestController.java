package demo.experiment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.experiment.pojo.constant.TestUrl;
import demo.experiment.service.TestService;
import demo.finance.cryptoCoin.data.binance.BinanceDataWSClient;
import demo.finance.cryptoCoin.data.service.CryptoCoinComplexService;
import demo.finance.cryptoCoin.data.service.impl.CryptoCoinCacheDataService;
import demo.scriptCore.scheduleClawing.cnStockMarketData.service.CnStockMarketDataService;
import demo.scriptCore.scheduleClawing.currencyExchangeRate.service.CurrencyExchangeRateService;
import demo.task.service.impl.AutomationTaskServiceImpl;
import finance.common.pojo.type.IntervalType;

@Controller
@RequestMapping(value = { TestUrl.testRoot })
public class TestController extends CommonController {

	@Autowired
	private TestService testService;

	@GetMapping(value = "/test")
	@ResponseBody
	public String test() throws Exception {
		return testService.testing("something", "other");
	}

	@Autowired
	private AutomationTaskServiceImpl automationTaskServiceImpl;

	@GetMapping(value = "/test2")
	@ResponseBody
	public String test2() {
		automationTaskServiceImpl.sendNormalDataTask();
		return "Done";
	}

	@GetMapping(value = "/test3")
	@ResponseBody
	public String test3(@RequestParam("msg") String msg) {
		testService.sendMsg(msg);
		return "Done";
	}

	@Autowired
	private CurrencyExchangeRateService currencyExchangeRateService;

	@GetMapping(value = "/test4")
	@ResponseBody
	public String test4() {
		currencyExchangeRateService.sendDailyDataQuery();
		return "Done";
	}

	@Autowired
	private CnStockMarketDataService cnStockMarketDataService;

	@GetMapping(value = "/t5")
	@ResponseBody
	public String test5() {
		cnStockMarketDataService.collectDatasAndSend();
		return "Done";
	}

	@Autowired
	private BinanceDataWSClient binanceWSClient;

	@GetMapping(value = "/t6")
	@ResponseBody
	public String test6(@RequestParam(value = "symbol") String symbol) {
		binanceWSClient.addNewKLineSubcript(symbol, "1m");
		return "Done";
	}

	@GetMapping(value = "/t6_")
	@ResponseBody
	public String test6_() {
		binanceWSClient.addNewKLineSubcript("BTCUSDT", IntervalType.MINUTE_1.getName());
		binanceWSClient.addNewKLineSubcript("ETHUSDT", IntervalType.MINUTE_1.getName());
		binanceWSClient.addNewKLineSubcript("DOGEUSDT", IntervalType.MINUTE_1.getName());
		return "Done";
	}

	@GetMapping(value = "/t7")
	@ResponseBody
	public String test7(@RequestParam(value = "symbol") String symbol) {
		binanceWSClient.closeKLineConnection(symbol, "1m");
		return "Done";
	}

	@GetMapping(value = "/killAll")
	@ResponseBody
	public String killAll() {
		binanceWSClient.killStream();
		return "Done";
	}

	@Autowired
	private CryptoCoinCacheDataService cryptoCoinCacheDataService;

	@GetMapping(value = "/t8")
	@ResponseBody
	public String t8() {
		return String.valueOf(cryptoCoinCacheDataService.getBinanceKLineCacheMap());
	}

	@Autowired
	private CryptoCoinComplexService cryptoCoinComplexService;

	@GetMapping(value = "/t9")
	@ResponseBody
	public String t9() {
		cryptoCoinComplexService.getRecentBigMoveCounter();
		return "Done";
	}

	@GetMapping(value = "/t10")
	@ResponseBody
	public String t1() {
		cryptoCoinComplexService.getCryptoCoinOptionFromCthulhu();
		return "Done";
	}

	@Autowired
	protected RedisTemplate<String, Object> redisTemplate;

	@GetMapping(value = "/t11")
	@ResponseBody
	public String t11(@RequestParam(value = "pattern") String pattern) {
		return String.valueOf(redisTemplate.keys(pattern));
	}
}
