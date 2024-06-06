package demo.task.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.autoTestBase.testEvent.mq.producer.HeartBeatProducer;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.base.system.mapper.BaseMapper;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.impl.AutomationTestCommonService;
import demo.task.service.TaskToolService;
import demo.tool.service.ComplexToolService;

@Component
public class TaskToolServiceImpl extends AutomationTestCommonService implements TaskToolService {

	@Autowired
	private SeleniumGlobalOptionService seleniumGlobalOptionService;

	@Autowired
	private ComplexToolService complexToolService;

	@Autowired
	private HeartBeatProducer heartBeatProducer;
//	@Autowired
//	private HeartBeatService heartBeatService;

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

	@Scheduled(fixedRate = 1000L * 27)
	public void sendHeartBeat() {
//		heartBeatService.heartbeat();
		heartBeatProducer.send();
	}

	@Scheduled(fixedRate = 1000L * 60 * 10)
	public void cleanExpiredFailEventCounting() {
		testEventService.cleanExpiredFailEventCounting();
	}

	@Scheduled(fixedDelay = 1000L * 60 * 3)
	public void amIAlive() {
		complexToolService.amIAlive();
	}

	@Scheduled(fixedRate = 1000L * 30)
	public void killChromeWebDriverWhenIdle() {
		if (!isLinux() || testEventService.checkExistsRuningEvent()) {
			return;
		}
		ProcessBuilder processBuilder = new ProcessBuilder();
		processBuilder.command("~/toolSH/killChromeDriver.sh");
		try {

			Process process = processBuilder.start();
			process.waitFor();
//				int exitVal = process.waitFor();
//				if (exitVal != 0) {
//					sendTelegramMsg("Kill chrome driver error");
//				}
		} catch (Exception e) {
			e.printStackTrace();
			sendingMsg("Kill chrome driver error");
		}
	}

}
