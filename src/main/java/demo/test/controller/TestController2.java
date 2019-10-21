package demo.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.baseCommon.controller.CommonController;
import demo.clawing.service.ClawingStudentService;
import demo.test.pojo.constant.TestUrl;

@Controller
@RequestMapping(value = { TestUrl.testRoot2 })
public class TestController2 extends CommonController {


	@Autowired
	private ClawingStudentService ss;
	
	@GetMapping(value = "/ss")
	public void ss() {
		ss.claw();
	}
}
