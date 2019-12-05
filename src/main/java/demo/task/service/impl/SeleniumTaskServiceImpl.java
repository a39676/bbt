package demo.task.service.impl;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SeleniumTaskServiceImpl extends SeleniumTaskCommonServiceImpl {
	
	@Scheduled(fixedRate = 1000L * 60 * 5)
	public void findTestEventAndRun() {
		testEventService.findTestEventAndRun();
	}
	
}
