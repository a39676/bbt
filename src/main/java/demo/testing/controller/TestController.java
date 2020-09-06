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
	
	@GetMapping(value = "/equipmentCollecting3")
	public void equipmentCollecting3(@RequestParam(value = "url") String url, @RequestParam(value = "flag", required = false) String flag) {
		boolean itemNameFirst = false;
		if("1".equals(flag)) {
			itemNameFirst = true;
		}
		twCollectService.equipmentCollecting3(url, itemNameFirst);
	}
	
	@GetMapping(value = "/equipmentCollectingImgOnly")
	public void equipmentCollectingImgOnly(@RequestParam(value = "url") String url) {
		twCollectService.equipmentCollectingImgOnly(url);
	}
	
	@GetMapping(value = "/itemCollectHandle")
	public void itemCollectHandle(@RequestParam(value = "url") String url, @RequestParam(value = "flag", required = false) String flag) {
		boolean togetherTD = false;
		if("1".equals(flag)) {
			togetherTD = true;
		}
		twCollectService.itemCollecting(url, togetherTD);
	}
}
