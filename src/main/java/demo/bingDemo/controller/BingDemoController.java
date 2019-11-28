package demo.bingDemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import demo.bingDemo.pojo.dto.BingDemoDTO;
import demo.bingDemo.service.BingDemoService;
import demo.testCase.service.TestEventService;

@Controller
@RequestMapping(value = "/bingDemo")
public class BingDemoController {

	@Autowired
	private BingDemoService bingDemoService;
	@Autowired
	private TestEventService testEventService;
	
	@GetMapping(value = "/insert")
	public ModelAndView insert(@RequestParam(value = "keyword", defaultValue = "testDemo") String keyword) {
		BingDemoDTO dto = new BingDemoDTO();
		dto.setKeyword(keyword);
		return bingDemoService.demo(dto);
	}
	
	@GetMapping(value = "/run")
	@ResponseBody
	public String run() {
		testEventService.findTestEventAndRun();
		return "done";
	}
	
}
