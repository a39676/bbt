package demo.testing.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.selenium.service.OldDataDeleteService;
import demo.testing.pojo.constant.TestUrl;
import demo.testing.service.TestService;

@Controller
@RequestMapping(value = { TestUrl.testRoot })
public class TestController extends CommonController {

	@SuppressWarnings("unused")
	@Autowired
	private TestService testService;

	@Autowired
	private OldDataDeleteService oldDataDeleteService;
	
	@GetMapping(value = "/deleteOldReport")
	@ResponseBody
	public String deleteOldReport() {
		try {
			oldDataDeleteService.deleteOldReport();
			return "done";
		} catch (IOException e) {
			return e.getMessage();
		}
	}
}
