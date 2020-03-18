package demo.tool.service.impl;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import auxiliaryCommon.pojo.result.CommonResult;
import auxiliaryCommon.pojo.type.BaseResultType;
import demo.base.system.pojo.bo.SystemConstantStore;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.baseCommon.pojo.type.ResultType;
import demo.baseCommon.service.CommonService;
import demo.tool.mapper.MailRecordMapper;
import demo.tool.pojo.type.MailType;
import demo.tool.service.MailService;
import mail.service.MailToolService;
import toolPack.emailHandle.mailService.send.SendEmail;


@Service
public class MailServiceImpl extends CommonService implements MailService {

	@Autowired
	private MailToolService mailToolService;
	
	@Autowired
	private MailRecordMapper mailRecordMapper;
	
	private boolean isMailReady() {
		if(redisTemplate.hasKey(SystemConstantStore.adminMailName) && redisTemplate.hasKey(SystemConstantStore.adminMailPwd)) {
			return true;
		} else {
			constantService.getValsByName(Arrays.asList(SystemConstantStore.adminMailName, SystemConstantStore.adminMailPwd));
			if(redisTemplate.hasKey(SystemConstantStore.adminMailName) && redisTemplate.hasKey(SystemConstantStore.adminMailPwd)) {
				return true;
			} else {
				return false;
			}
		}
	}
	
	@Override
	public CommonResult sendSimpleMail(Long userId, String sendTo, String title, String content, String mailKey, MailType mailType) {
		CommonResult result = new CommonResult();
		if(userId == null || mailType == null || mailType.getCode() == null) {
			result.failWithMessage(BaseResultType.nullParam.getName());
			return result;
		}
		if(!isMailReady()) {
			result.failWithMessage(ResultType.mailBaseOptionError.getName());
			return result;
		}
		
		Properties properties = mailToolService.buildSinaSmtpSslProperties();

		SendEmail sm = new SendEmail();
		sm.sendMail(
				constantService.getValByName(SystemConstantStore.adminMailName), 
				constantService.getValByName(SystemConstantStore.adminMailPwd), 
				Arrays.asList(sendTo),
				null,
				Arrays.asList(constantService.getValByName(SystemConstantStore.adminMailName)),
				title, 
				content, 
				null,
				properties
				);
		

		result.successWithMessage(mailKey);
		return result;
	}

	@Override
	public void sendMailWithAttachment(String sendTo, String title, String content, List<String> attachmentPathList,
			Properties properties) {
		if(!isMailReady()) {
			return;
		}
		SendEmail sm = new SendEmail();
		sm.sendMail(
				constantService.getValByName(SystemConstantStore.adminMailName), 
				constantService.getValByName(SystemConstantStore.adminMailPwd), 
				Arrays.asList(sendTo),
				null,
				Arrays.asList(constantService.getValByName(SystemConstantStore.adminMailName)),
				title, 
				content, 
				attachmentPathList,
				properties
				);
	}

	@Override
	public void sendMailWithAttachment(String sendTo, String title, String content, String attachmentPath,
			Properties properties) {
		if(!isMailReady()) {
			return;
		}
		SendEmail sm = new SendEmail();
		sm.sendMail(
				constantService.getValByName(SystemConstantStore.adminMailName), 
				constantService.getValByName(SystemConstantStore.adminMailPwd), 
				Arrays.asList(sendTo),
				null,
				Arrays.asList(constantService.getValByName(SystemConstantStore.adminMailName)),
				title, 
				content, 
				Arrays.asList(attachmentPath),
				properties
				);
	}

	@Override
	public int updateWasUsed(Integer mailId) {
		if(mailId == null) {
			return 0;
		}
		return mailRecordMapper.updateWasUsed(mailId);
	}
	
	@Override
	public CommonResultBBT sandFailTaskReport(Long userId, List<Long> failTastIdList, String email) {
		CommonResultBBT result = new CommonResultBBT();
		if(!isMailReady()) {
			result.failWithMessage(ResultType.mailBaseOptionError.getName());
			return result;
		}
		
		LocalDateTime now = LocalDateTime.now();
		String nowStr = localDateTimeHandler.dateToStr(now);
		if(failTastIdList == null || failTastIdList.size() < 1) {
			sendSimpleMail(userId, email, ("截至: " + nowStr + " 最近2天无失败任务"), ("截至: " + nowStr + " 最近2天无失败任务"), null, MailType.sandFailTaskReport);
		} else {
			/*
			 * 2020-02-04
			 * 未知原因, 邮件无法寄出
			 * 大概率是新浪邮箱不允许发送邮件内容带有过多链接的邮件
			 */
//			String targetHost = constantService.getValByName(SystemConstantStore.hostNameSeek);
//			StringBuffer sb = new StringBuffer();
//			for(Long testEventId : failTastIdList) {
//				sb.append(targetHost + AutoTestUrl.root + AutoTestInteractionUrl.findReportByTestEventId + "?testEventId=" + testEventId + "\n");
//			}
//			sendSimpleMail(userId, email, ("截至: " + nowStr + " 最近2天的失败任务报告"), sb.toString(), null, MailType.sandFailTaskReport);
			sendSimpleMail(userId, email, ("截至: " + nowStr + " 最近2天有" + failTastIdList.size() + "个失败任务"), ("截至: " + nowStr + " 最近2天有" + failTastIdList.size() + "个失败任务"), null, MailType.sandFailTaskReport);
		}
		
		result.setIsSuccess();
		return result;
	}

}
