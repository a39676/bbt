package demo.test.controller;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.baseCommon.controller.CommonController;
import demo.movie.pojo.result.DoubanSubClawingResult;
import demo.movie.service.DoubanClawingService;
import demo.selenium.service.WebDriverService;
import demo.test.pojo.constant.TestUrl;
import demo.testCase.pojo.po.TestEvent;

@Controller
@RequestMapping(value = { TestUrl.testRoot2 })
public class TestController2 extends CommonController {

	@Autowired
	private WebDriverService webDriverService;
	
	@Autowired
	private DoubanClawingService ds;
	
	@GetMapping(value = "/ds")
	public void ds() {
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		TestEvent te = new TestEvent();
		te.setCaseId(0L);
		te.setId(0L);
		DoubanSubClawingResult r = ds.clawing(d, "最后的兵团", te);
		System.out.println(r);
	}
	
}
