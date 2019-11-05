package demo.test.controller;

import javax.servlet.http.HttpServletRequest;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.badJoke.sms.pojo.dto.BadJokeSMSDTO;
import demo.badJoke.sms.service.BadJokeSMSService;
import demo.baseCommon.controller.CommonController;
import demo.clawing.service.ClawingStudentService;
import demo.movieInteraction.service.MovieInteractionService;
import demo.selenium.service.WebDriverService;
import demo.test.pojo.constant.TestUrl;
import demo.testCase.pojo.po.TestEvent;
import movie.pojo.dto.FindMovieDetailDTO;

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
//			badJoke.sdo(d, te, dto);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(d != null) {
//				d.quit();
			}
		}
	}
	
	
	@Autowired
	private MovieInteractionService ms;
	
	@GetMapping(value = "/findMovieDetail")
	@ResponseBody
	public Long findMovieDetail(HttpServletRequest request) {
		Long movieId = 639859682541965312L;
		FindMovieDetailDTO dto = new FindMovieDetailDTO();
		dto.setMovieId(movieId);
		ms.findMovieDetail(request, dto);
		
		return ms.findMovieClickCount(movieId);
	}
	
	@GetMapping(value = "/movieClickCountingRedisToOrm")
	@ResponseBody
	public void movieClickCountingRedisToOrm(HttpServletRequest request) {
		ms.movieClickCountingRedisToOrm();
	}
}
