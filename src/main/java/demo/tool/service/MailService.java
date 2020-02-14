package demo.tool.service;

import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.search.SearchTerm;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.base.user.pojo.bo.UserMailAndMailKeyBO;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.tool.pojo.MailRecord;
import demo.tool.pojo.type.MailType;

public interface MailService {

	void sendMailWithAttachment(String sendTo, String title, String context, List<String> attachmentPathList, Properties properties);

	void sendMailWithAttachment(String sendTo, String title, String context, String attachmentPath,
			Properties properties);

//	CommonResult sendRegistMail(Long userId, String email, String nickName); // 暂时不再主动发送注册验证邮件,改为验证用户发送的邮件. 2018-06-28

	public MailRecord findMailByMailKeyMailType(String mailKey, MailType mailType);

	int updateWasUsed(Integer mailId);

	CommonResultBBT sendForgotPasswordMail(Long userId, String email, String hostName);
	
	CommonResultBBT sendForgotUsernameMail(String userName, String email, String hostName);

	MailRecord findRegistActivationUnusedByUserId(Long userId);

//	CommonResult resendRegistMail(Long userId, String sendTo, String nickName, String mailKey, String hostName); // 暂时不再主动发送注册验证邮件,改为验证用户发送的邮件. 2018-06-28
	
	void sendErrorMail(String errorMessage);

	Message[] searchInbox(SearchTerm st);

	String insertNewRegistMailKey(Long userId);

	SearchTerm singleSearchTerm(String targetSendFrom, String targetContent, Date startDate);

	SearchTerm searchByTargetContents(List<UserMailAndMailKeyBO> userMailAndMailKeyBOList);

	CommonResultBBT sandFailTaskReport(Long userId, List<Long> failTastIdList, String email);

	CommonResult sendSimpleMail(Long userId, String sendTo, String title, String content, String mailKey,
			MailType mailType);


}
