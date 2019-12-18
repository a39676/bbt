package demo.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.clawing.dailySign.service.QuQiDailySignService;
import demo.clawing.dailySign.service.WuYiJobDailySignService;

@Component
public class DailySignTaskServiceImpl extends SeleniumTaskCommonServiceImpl {
	
	@Autowired
	private QuQiDailySignService quQiDailySignService;
	@Autowired
	private WuYiJobDailySignService wuyiService;
	
	@Scheduled(cron="03 02 01 * * *")
	public void insertQuQiDailySign() {
		quQiDailySignService.insertDailySignEvent();
	}
	
	@Scheduled(fixedRate = 1000L * 60 * 35)
	public void insertWuYiSign() {
		wuyiService.insertDailySignEvent();
	}
}
