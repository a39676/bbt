package demo.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.clawing.lottery.service.LotterySixService;

@Component
public class LotteryTaskServiceImpl extends SeleniumTaskCommonServiceImpl {
	
	@Autowired
	private LotterySixService lotteryService;
	
	@Scheduled(cron="39 22 01 * * *") 
	public void insertHomeFeiEvent1() {
		lotteryService.insertTaskEvent();
	}
	
}
