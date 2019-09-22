package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.config.costom_component.SnowFlake;
import demo.movie.service.DyttClawingService;
import demo.test.pojo.constant.TestUrl;

@Controller
@RequestMapping(value = { TestUrl.testRoot2 })
public class TestController2 extends CommonController {

//	@Autowired
//	private TestService testService;
//	@Autowired
//	private TestCaseService caseService;
	@Autowired
	private DyttClawingService dytt;
	

	@Autowired
	private SnowFlake snowFlake;

	@GetMapping(value = "/snowFlake")
	@ResponseBody
	public String snowFlake() {
		return String.valueOf(snowFlake.getNextId());
	}
	
	@GetMapping(value = "/dytt")
	@ResponseBody
	public String dytt() {
		dytt.clawing();
		return "done";
	}
	
	@GetMapping(value = "/insertCommonCase")
	@ResponseBody
	public String insertCommonCase() {
		return null;
	}
	
}
