package demo.task.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.autoTestBase.testEvent.service.TestEventService;
import demo.interaction.movieInteraction.service.MovieInteractionService;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.task.service.TaskToolService;
import demo.tool.mapper.MailRecordMapper;
import demo.tool.service.ComplexToolService;

@Component
public class TaskToolServiceImpl implements TaskToolService {

	@Autowired
	private SeleniumGlobalOptionService seleniumGlobalOptionService;
	
	@Autowired
	private ComplexToolService complexToolService;
	@Autowired
	private MovieInteractionService movieInteractionService;
	@Autowired
	private TestEventService testEventService;
	
	@Autowired
	private MailRecordMapper mailRecordMapper;
	
//	@Scheduled(cron="0 */30 * * * ?")   //每30分钟执行一次
//	@Scheduled(cron="40 49 23 * * *") // 每天23:49:40执行
//	@Scheduled(fixedRate = 1000) // 上次任务结束后 1000 毫秒后再执行
	
	
	/** 清理过期或已读的邮件记录. */
	@Scheduled(cron="0 */63 * * * ?")
	public void cleanMailRecord() {
		mailRecordMapper.cleanMailRecord(null);
	}
	
	@Scheduled(cron="05 03 23 * * *") 
	public void cleanTmpFile() {
		complexToolService.cleanTmpFiles(seleniumGlobalOptionService.getDownloadDir(), null, LocalDateTime.now().minusMonths(1));
	}
	
	@Scheduled(cron="01 03 00 * * *")
	public void movieClickCountingRedisToOrm() {
		movieInteractionService.movieClickCountingRedisToOrm();
	}
	
	/** 将最近2天运行失败的定时任务报告发送到指定邮箱 */
	@Scheduled(cron="02 03 04 * * *")
	public void sendFailReports() {
		testEventService.sendFailReports();
	}
	
	
}
