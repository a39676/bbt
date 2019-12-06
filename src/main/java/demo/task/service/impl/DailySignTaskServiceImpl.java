package demo.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.clawing.dailySign.service.QuQiDailySignService;

@Component
public class DailySignTaskServiceImpl extends SeleniumTaskCommonServiceImpl {
	
	@Autowired
	private QuQiDailySignService quQiDailySignService;
	
	@Scheduled(cron="03 02 01 * * *") 
	public void insertQuQiDailySign() {
		quQiDailySignService.insertclawingEvent();
	}
	
}
