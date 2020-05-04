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
				wuyiService.insertClawingEvent();
			} else {
				if(ThreadLocalRandom.current().nextInt(1, 4) <= 1) {
					wuyiService.insertClawingEvent();
				}
			}
		}
	}
	
	/*
	 * 未知原因导致 脉脉 任务偶发性异常
	 * 不能正常退出 WebDriver 导致 vps 瘫痪
	 * 待修复
	 */
//	@Autowired
//	private MaiMaiScheduleClawingServiceImpl maiMaiLocalClawingServiceImpl;
//	
//	@Scheduled(cron="0 */20 * * * ?")
//	public void insertMaiMai() {
//		if(!"dev".equals(constantService.getValByName("envName"))) {
//			maiMaiLocalClawingServiceImpl.insertClawingEvent();
//		}
//	}
}
