package demo.experiment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.experiment.pojo.constant.TestUrl;
import demo.experiment.service.TestService;

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
}