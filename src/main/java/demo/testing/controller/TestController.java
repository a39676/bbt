package demo.testing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@GetMapping(value = "/monsterCollecting")
	public void monsterCollecting() {
		twCollectService.monsterCollecting();
	}

	@GetMapping(value = "/skillCollecting")
	public void skillCollecting() {
		twCollectService.skillCollecting();
	}

	@GetMapping(value = "/equipmentCollecting")
	public void equipmentCollecting() {
		twCollectService.equipmentCollecting();
	}

	@GetMapping(value = "/equipmentCollecting2")
	public void equipmentCollecting2(@RequestParam(value = "sub") String sub,
			@RequestParam(value = "prefix") String prefix) {
		twCollectService.equipmentCollecting2(sub, prefix);
	}
}
