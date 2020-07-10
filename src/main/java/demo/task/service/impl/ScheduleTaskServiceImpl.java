package demo.task.service.impl;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.clawing.scheduleClawing.service.PreciousMetalsPriceService;
import demo.clawing.scheduleClawing.service.WuYiJobRefreshService;

@Component
public class ScheduleTaskServiceImpl extends SeleniumTaskCommonServiceImpl {

	@Autowired
	private WuYiJobRefreshService wuyiService;

	@Autowired
	private PreciousMetalsPriceService preciousMetalsPriceService;

//	@Scheduled(fixedRate = 1000L * 60 * 5)
	@Scheduled(cron = "0 */5 * * * ?")
	public void insertWuYiSign() {
		if (!"dev".equals(constantService.getValByName("envName"))) {
			LocalDateTime now = LocalDateTime.now();
			if (now.getHour() >= 8 && now.getHour() <= 22) {
				wuyiService.insertClawingEvent();
			} else {
				if (ThreadLocalRandom.current().nextInt(1, 4) <= 1) {
					wuyiService.insertClawingEvent();
				}
			}
		}
	}

	@Scheduled(cron = "*/30 * * * * ?")
	public void insertMetalsPriceClaw() {
		if (!"dev".equals(constantService.getValByName("envName"))
				&& isPreciousMetalsTransTime()) {
			preciousMetalsPriceService.insertClawingEvent();
		}
	}
	
	private boolean isPreciousMetalsTransTime() {
		LocalDateTime beiJingNow = LocalDateTime.now();
		LocalDateTime washtonNow = beiJingNow.minusHours(12);
		int dayOfWeek = beiJingNow.getDayOfWeek().getValue();
		
		/*
		 * 2020-07-06
		 * 理论上美国黄金交易时间为: 
		 * (北京时间) 周一06:60 ~ 周六 03:30
		 * 冬令时期间 延后半小时
		 */
		boolean isUSWinterTime = localDateTimeHandler.isUSWinterTime(washtonNow.toLocalDate());
		if(isUSWinterTime) {
			beiJingNow = beiJingNow.minusMinutes(30);
		}
		
		if (dayOfWeek == 1) {
			return (beiJingNow.getHour() >= 6 && beiJingNow.getMinute() >= 30);
		} else if (dayOfWeek > 1 && dayOfWeek < 6) {
			return true;
		} else if (dayOfWeek == 6) {
			return (beiJingNow.getHour() <= 3 && beiJingNow.getMinute() <= 30);
		} else if (dayOfWeek > 6) {
			return false;
		}

		return false;
	}

//	@Autowired
//	private MaiMaiScheduleClawingServiceImpl maiMaiLocalClawingServiceImpl;
//	@Scheduled(cron="0 */21 * * * ?")
//	public void insertMaiMai() {
//		if(!"dev".equals(constantService.getValByName("envName"))) {
//			maiMaiLocalClawingServiceImpl.insertClawingEvent();
//		}
//	}
}
