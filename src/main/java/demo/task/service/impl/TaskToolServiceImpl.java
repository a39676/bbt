package demo.task.service.impl;

import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.user.mapper.UsersMapper;
import demo.base.user.service.UserRegistService;
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
	private UserRegistService userRegistService;
	@Autowired
	private ComplexToolService complexToolService;
	@Autowired
	private MovieInteractionService movieInteractionService;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private MailRecordMapper mailRecordMapper;
	
//	@Scheduled(cron="0 */30 * * * ?")   //每30分钟执行一次
//	@Scheduled(cron="40 49 23 * * *") // 每天23:49:40执行
	
	/** 清理无效的错误登录记录. */
	@Scheduled(cron="0 */63 * * * ?")
	public void cleanAttempts() {
		usersMapper.cleanAttempts(new Date(System.currentTimeMillis() - (1000L * 60 * 60 * 24 * 15)));
	}
	
	/** 清理过期或已读的邮件记录. */
	@Scheduled(cron="0 */63 * * * ?")
	public void cleanMailRecord() {
		mailRecordMapper.cleanMailRecord(null);
	}
	
	/** 查看是否有邮件任务未完成(用户注册后/换邮箱后,未发送激活邮件) */
	@Scheduled(cron="0 */10 * * * ?")
	public void hasMailTask() {
		if(mailRecordMapper.hasMailTask() > 0) {
			userRegistService.handleMails();
		}
	}
	
	@Scheduled(cron="05 03 23 * * *") 
	public void cleanTmpFile() {
		complexToolService.cleanTmpFiles(seleniumGlobalOptionService.getDownloadDir(), null, LocalDateTime.now().minusMonths(1));
	}
	
	@Scheduled(cron="01 03 00 * * *")
	public void movieClickCountingRedisToOrm() {
		movieInteractionService.movieClickCountingRedisToOrm();
	}
}
