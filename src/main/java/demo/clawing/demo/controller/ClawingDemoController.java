package demo.clawing.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.autoTestBase.testEvent.service.TestEventService;
import demo.clawing.demo.pojo.constant.ClawingUrl;

@Controller
@RequestMapping(value = ClawingUrl.root)
public class ClawingDemoController {

	@Autowired
	private TestEventService testEventService;
	
	@GetMapping(value = ClawingUrl.run)
	@ResponseBody
	public String run() {
		testEventService.findTestEventAndRun();
		return "done";
	}
	
}
