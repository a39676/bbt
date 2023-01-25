package demo.task.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.autoTestBase.testEvent.mq.producer.HeartBeatProducer;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.base.system.mapper.BaseMapper;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.task.service.TaskToolService;
import demo.tool.service.ComplexToolService;

@Component
public class TaskToolServiceImpl implements TaskToolService {

	@Autowired
	private SeleniumGlobalOptionService seleniumGlobalOptionService;

	@Autowired
	private ComplexToolService complexToolService;

	@Autowired
	private HeartBeatProducer heartBeatProducer;

	@Autowired
	private BaseMapper baseMapper;
	
	@Autowired
	protected TestEventService testEventService;

	/**
	 * 2021-06-17 keep database connection alive after update JDK and update MySQL
	 * and SpringBoot database connection will lose automation
	 */
	@Scheduled(fixedDelay = 1000L * 20)
	public void keepDatabaseConnectionAlive() {
		baseMapper.keepDatabaseAlive();
	}

	@Scheduled(cron = "05 03 23 * * *")
	public void cleanTmpFile() {
		complexToolService.cleanTmpFiles(seleniumGlobalOptionService.getDownloadDir(), null,
				LocalDateTime.now().minusMonths(1));
	}
	
	@Scheduled(cron="0 */3 * ? * *")
	public void sendHeartBeat() {
		heartBeatProducer.send();
	}
	
	@Scheduled(fixedRate = 1000L * 60 * 10)
	public void cleanExpiredFailEventCounting() {
		testEventService.cleanExpiredFailEventCounting();
	}
	
	

}
