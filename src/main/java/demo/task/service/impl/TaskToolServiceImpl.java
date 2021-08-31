package demo.task.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
	private BaseMapper baseMapper;

//	@Scheduled(cron="0 */30 * * * ?")   //每30分钟执行一次
//	@Scheduled(cron="40 49 23 * * *") // 每天23:49:40执行
//	@Scheduled(fixedRate = 1000) // 上次任务结束后 1000 毫秒后再执行

	/**
	 * 2021-06-17 keep database connection alive after update JDK and update MySQL
	 * and SpringBoot database connection will lose automation
	 */
	@Scheduled(cron = "*/31 * * * * ?")
	public void keepDatabaseConnectionAlive() {
		baseMapper.keepDatabaseAlive();
	}

	@Scheduled(cron = "05 03 23 * * *")
	public void cleanTmpFile() {
		complexToolService.cleanTmpFiles(seleniumGlobalOptionService.getDownloadDir(), null,
				LocalDateTime.now().minusMonths(1));
	}

}
