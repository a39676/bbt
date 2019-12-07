package demo.clawing.bingDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import autoTest.testEvent.pojo.constant.BingDemoUrl;
import autoTest.testEvent.pojo.result.InsertBingDemoEventResult;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.clawing.bingDemo.pojo.dto.BingDemoDTO;
import demo.clawing.bingDemo.service.BingDemoService;

@Controller
@RequestMapping(value = BingDemoUrl.root)
public class BingDemoController {

	@Autowired
	private BingDemoService bingDemoService;
	@Autowired
	private TestEventService testEventService;
	
	@GetMapping(value = BingDemoUrl.insert)
	@ResponseBody
	public InsertBingDemoEventResult insert(@RequestParam(value = "keyword", defaultValue = "testDemo") String keyword) {
		BingDemoDTO dto = new BingDemoDTO();
		dto.setKeyword(keyword);
		return bingDemoService.demo(dto);
	}
	
	@GetMapping(value = BingDemoUrl.run)
	@ResponseBody
	public String run() {
		testEventService.findTestEventAndRun();
		return "done";
	}
	
}
