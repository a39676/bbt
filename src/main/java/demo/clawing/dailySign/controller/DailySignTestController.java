package demo.clawing.dailySign.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.clawing.dailySign.pojo.constant.DailySignUrl;
import demo.clawing.dailySign.service.QuQiDailySignService;
import demo.clawing.dailySign.service.WuYiJobDailySignService;

@Controller
@RequestMapping(value = DailySignUrl.root)
public class DailySignTestController {

	@Autowired
	private QuQiDailySignService quQiservice;
	@Autowired
	private WuYiJobDailySignService wuyiService;
	
	@GetMapping(value = DailySignUrl.insertQuQiSign)
	public void insertQuQiSign() {
		quQiservice.insertclawingEvent();
	}
	
	@GetMapping(value = DailySignUrl.insertWuYiSign)
	public void insertWuYiSign() {
		wuyiService.insertclawingEvent();
	}
}
