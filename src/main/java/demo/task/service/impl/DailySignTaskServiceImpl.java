package demo.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.clawing.dailySign.service.WuYiJobDailySignService;

@Component
public class DailySignTaskServiceImpl extends SeleniumTaskCommonServiceImpl {
	
	@Autowired
	private WuYiJobDailySignService wuyiService;
	
	@Scheduled(fixedRate = 1000L * 60 * 5)
	public void insertWuYiSign() {
		if(!"dev".equals(constantService.getValByName("envName"))) {
			wuyiService.insertDailySignEvent();
		}
	}
}
