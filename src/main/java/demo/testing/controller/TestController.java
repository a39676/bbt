package demo.testing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.baseCommon.controller.CommonController;
import demo.clawing.medicine.service.ClawingSinaMedicineService;
import demo.clawing.neobux.service.NeobuxService;
import demo.selenium.service.SeForJoke;
import demo.selenium.service.SeleniumService;
import demo.testing.pojo.constant.TestUrl;
import demo.testing.service.TestService;

@Controller
@RequestMapping(value = { TestUrl.testRoot })
public class TestController extends CommonController {
	
	@SuppressWarnings("unused")
	@Autowired
	private TestService testService;
	@Autowired
	private SeleniumService seleniumService;
	
	@Autowired
	private SeForJoke seForJoke;
	@Autowired
	private ClawingSinaMedicineService medicineService;
	@Autowired
	private NeobuxService nbService;
	
	
	@GetMapping(value = "/seleniumTest")
	public void seleniumTest() { 
		seleniumService.testDemo();
	}
	
	@GetMapping(value = "/logTest")
	public void logTest() {
		String str = "strrrrrr";
		log.info("something {}", str);
	}
	
	@GetMapping(value = "sjlanxiang")
	public void sjlanxiang() {
		seForJoke.lanXiang();
	}
	
	@GetMapping(value = "sjliuxue")
	public void sjLiuXue() {
		seForJoke.liuXue();
	}

	@GetMapping(value = "sjtest")
	public void sjTest() {
		seForJoke.test();
	}
	
	@GetMapping(value = "mtest")
	public void mTest() {
		medicineService.medicineTest();
	}
	
	@GetMapping(value = "nbtest")
	public void noebuxTest() {
		nbService.test1();
	}
	
}
