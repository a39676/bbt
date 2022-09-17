package demo.experiment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import autoTest.testEvent.scheduleClawing.currencyExchangeRate.pojo.dto.CurrencyExchangeRateCollectDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.baseCommon.controller.CommonController;
import demo.experiment.pojo.constant.TestUrl;
import demo.experiment.service.TestService;
import demo.scriptCore.scheduleClawing.currencyExchangeRate.service.CurrencyExchangeRateService;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = { TestUrl.testRoot })
public class TestController extends CommonController {

//	@SuppressWarnings("unused")
	@Autowired
	private TestService testService;

	@GetMapping(value = "/test")
	@ResponseBody
	public String test() throws Exception {
		return testService.testing("something");
	}
	
	@Autowired
	private CurrencyExchangeRateService currencyExchangeRateService;
	
	@GetMapping(value = "/t1")
	@ResponseBody
	public TestEventBO getDailyData() throws Exception {
		TestEventBO tbo = new TestEventBO();
		CurrencyExchangeRateCollectDTO dto = new CurrencyExchangeRateCollectDTO();
		dto.setMainUrl("https://www.oanda.com/currency-converter/zh/?from=JPY&to=CNY&amount=1");
		JSONObject dtoJson = JSONObject.fromObject(dto);
		JSONObject paramJson = new JSONObject();
		paramJson.put("CurrencyExchangeRateCollectDTO", dtoJson);
		tbo.setParamStr(paramJson.toString());
		return currencyExchangeRateService.getDailyData(tbo);
	}

//	@Autowired
//	private TmpTaskService tmpTaskService;
//	
//	@GetMapping(value = "/t1")
//	public void t1() {
//		tmpTaskService.t1();
//	}
	
//	@Autowired
//	private V2exJobInfoCollectionService s;
//	
//	@GetMapping(value = "/t2")
//	@ResponseBody
//	public String t2() {
//		TestEventBO tbo = new TestEventBO();
//		s.clawing(tbo);
//		return ":";
//	}
}
