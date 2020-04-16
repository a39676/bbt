package demo.clawing.scheduleClawing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.clawing.scheduleClawing.service.WuYiJobRefreshService;

@Controller
@RequestMapping(value = "/51job")
public class WuyiJobController {

	@Autowired
	private WuYiJobRefreshService wuyiService;
	
	@GetMapping(value = "/watchMeList")
	public ModelAndView insertWuYiSign() {
		return wuyiService.watchMeList();
	}
	
}
