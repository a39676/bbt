package demo.testing.controller;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.controller.CommonController;
import demo.clawing.badJoke.sms.pojo.dto.BadJokeSMSDTO;
import demo.clawing.badJoke.sms.service.BadJokeSMSService;
import demo.clawing.clawingStudent.service.ClawingStudentService;
import demo.selenium.service.WebDriverService;
import demo.testing.pojo.constant.TestUrl;

@Controller
@RequestMapping(value = { TestUrl.testRoot2 })
public class TestController2 extends CommonController {

	@Autowired
	private ClawingStudentService ss;
	
	@Autowired
	private BadJokeSMSService badJoke;
	
	@Autowired
	private WebDriverService webDriverService;
	
	
	@GetMapping(value = "/ss")
	public void ss() {
		ss.claw();
	}
	
	@GetMapping(value = "/badJoke")
	public void badJokd() {
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		
		TestEvent te = new TestEvent();
		te.setEventName("badjoke");
		te.setCaseId(2L);
		te.setId(3L);
		BadJokeSMSDTO dto = new BadJokeSMSDTO();
		dto.setMobileNum("18826485386");
		try {
			badJoke.toString();
//			badJoke._91wenwen(d, te, dto);
//			badJoke.zhiWang(d, te, dto);
//			badJoke.mafengwo(d, te, dto);
//			badJoke.zhipin(d, te, dto);
//			badJoke.jumpw(d, te, dto);
//			badJoke.nike(d, te, dto);
//			badJoke.chunQiu(d, te, dto);
//			badJoke.flyme(d, te, dto);
//			badJoke.zjzwfw(d, te, dto);
//			badJoke.hnair(d, te, dto);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(d != null) {
//				d.quit();
			}
		}
	}
	
}
