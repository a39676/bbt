package demo.task.service.impl;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.clawing.scheduleClawing.service.WuYiJobRefreshService;

@Component
public class DailySignTaskServiceImpl extends SeleniumTaskCommonServiceImpl {
	
	@Autowired
	private WuYiJobRefreshService wuyiService;
	
//	@Scheduled(fixedRate = 1000L * 60 * 5)
	@Scheduled(cron="0 */5 * * * ?")
	public void insertWuYiSign() {
		if(!"dev".equals(constantService.getValByName("envName"))) {
			LocalDateTime now = LocalDateTime.now();
			if(now.getHour() >= 8 && now.getHour() <= 22) {
				wuyiService.insertDailySignEvent();
			} else {
				if(ThreadLocalRandom.current().nextInt(1, 4) <= 1) {
					wuyiService.insertDailySignEvent();
				}
			}
		}
	}
}
