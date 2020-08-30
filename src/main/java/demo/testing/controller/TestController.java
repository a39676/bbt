package demo.testing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.baseCommon.controller.CommonController;
import demo.clawing.tw.service.TwCollectService;
import demo.testing.pojo.constant.TestUrl;
import demo.testing.service.TestService;

@Controller
@RequestMapping(value = { TestUrl.testRoot })
public class TestController extends CommonController {
	
	@SuppressWarnings("unused")
	@Autowired
	private TestService testService;
	
	@Autowired
	private TwCollectService twCollectService;
	
	@GetMapping(value = "/twCollectTest")
	public void twCollectTest() {
		twCollectService.monsterCollecting();
	}
}
