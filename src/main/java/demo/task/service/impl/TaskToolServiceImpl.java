package demo.task.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.base.user.mapper.UsersMapper;
import demo.base.user.service.UserRegistService;
import demo.task.service.TaskToolService;
import demo.tool.mapper.MailRecordMapper;

@Component
public class TaskToolServiceImpl implements TaskToolService {

	@Autowired
	private UserRegistService userRegistService;
	
	@Autowired
	private UsersMapper usersMapper;
	
	@Autowired
	private MailRecordMapper mailRecordMapper;
	
//	@Scheduled(cron="0 */60 * * * ?")   //每30分钟执行一次  
//	public void sendTomcatOut() {
//		mailService.sendTomcatOut();
//	}
	
//	@Scheduled(cron="0 */60 * * * ?")
//	public void sendTomcatLogFolder() {
//		mailService.sendTomcatLogFolder();
//	}
	
//	@Scheduled(cron="0 */32 * * * ?")
//	public void imageShowReload() {
//		imageService.imageShowReload();
//	}
	
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
	
}
