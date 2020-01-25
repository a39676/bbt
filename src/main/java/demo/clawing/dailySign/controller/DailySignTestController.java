package demo.clawing.dailySign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.clawing.dailySign.pojo.constant.DailySignUrl;
import demo.clawing.dailySign.service.CdBaoDailySignService;
import demo.clawing.dailySign.service.LiePinDailySignService;
import demo.clawing.dailySign.service.WuYiJobDailySignService;

@Controller
@RequestMapping(value = DailySignUrl.root)
public class DailySignTestController {

	@Autowired
	private WuYiJobDailySignService wuyiService;
	@Autowired
	private LiePinDailySignService leiPinService;
	@Autowired
	private CdBaoDailySignService cdBaoService;
	
	@GetMapping(value = DailySignUrl.insertWuYiSign)
	public void insertWuYiSign() {
		wuyiService.insertDailySignEvent();
	}
	
	@GetMapping(value = DailySignUrl.insertLiePinSign)
	public void insertLiePinSign() {
		leiPinService.insertDailySignEvent();
	}
	
	@GetMapping(value = DailySignUrl.insertCDBaoSign)
	public void insertCDBaoSign() {
		cdBaoService.insertDailySignEvent();
	}
}
