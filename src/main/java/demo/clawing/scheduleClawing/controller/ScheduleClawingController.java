package demo.clawing.scheduleClawing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.clawing.scheduleClawing.pojo.constant.ScheduleClawingUrl;
import demo.clawing.scheduleClawing.service.CdBaoDailySignService;
import demo.clawing.scheduleClawing.service.LiePinDailySignService;
import demo.clawing.scheduleClawing.service.WuYiJobRefreshService;
import demo.clawing.scheduleClawing.service.impl.MaiMaiScheduleClawingServiceImpl;

@Controller
@RequestMapping(value = ScheduleClawingUrl.root)
public class ScheduleClawingController {

	@Autowired
	private WuYiJobRefreshService wuyiService;
	@Autowired
	private LiePinDailySignService leiPinService;
	@Autowired
	private CdBaoDailySignService cdBaoService;
	@Autowired
	private MaiMaiScheduleClawingServiceImpl maiMaiLocalClawingServiceImpl;
	
	@GetMapping(value = ScheduleClawingUrl.insertWuYiSign)
	public void insertWuYiSign() {
		wuyiService.insertClawingEvent();
	}
	
	@GetMapping(value = ScheduleClawingUrl.insertLiePinSign)
	public void insertLiePinSign() {
		leiPinService.insertDailySignEvent();
	}
	
	@GetMapping(value = ScheduleClawingUrl.insertCDBaoSign)
	public void insertCDBaoSign() {
		cdBaoService.insertDailySignEvent();
	}
	
	@GetMapping(value = ScheduleClawingUrl.insertMaiMai)
	public void insertMaiMaiLocalClawing() {
		maiMaiLocalClawingServiceImpl.insertClawingEvent();
	}
}
