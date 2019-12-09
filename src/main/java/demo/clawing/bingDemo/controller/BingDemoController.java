package demo.clawing.bingDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import autoTest.testEvent.pojo.constant.BingDemoUrl;
import autoTest.testEvent.pojo.dto.InsertBingDemoTestEventDTO;
import autoTest.testEvent.pojo.result.InsertBingDemoEventResult;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.clawing.bingDemo.service.BingDemoService;

@Controller
@RequestMapping(value = BingDemoUrl.root)
public class BingDemoController {

	@Autowired
	private BingDemoService bingDemoService;
	@Autowired
	private TestEventService testEventService;
	
	@PostMapping(value = BingDemoUrl.insert)
	@ResponseBody
	public InsertBingDemoEventResult insert(@RequestBody InsertBingDemoTestEventDTO dto) {
		return bingDemoService.insert(dto);
	}
	
	@GetMapping(value = BingDemoUrl.run)
	@ResponseBody
	public String run() {
		testEventService.findTestEventAndRun();
		return "done";
	}
	
}
