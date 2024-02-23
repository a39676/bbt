package demo.experiment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.experiment.pojo.constant.TestUrl;
import demo.experiment.service.TestService;
import demo.scriptCore.scheduleClawing.cnStockMarketData.service.CnStockMarketDataService;
import demo.scriptCore.scheduleClawing.currencyExchangeRate.service.CurrencyExchangeRateService;
import demo.task.service.impl.AutomationTaskServiceImpl;

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
}
