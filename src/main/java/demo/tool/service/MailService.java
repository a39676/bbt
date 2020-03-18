package demo.tool.service;

import java.util.List;
import java.util.Properties;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.tool.pojo.type.MailType;

public interface MailService {

	void sendMailWithAttachment(String sendTo, String title, String context, List<String> attachmentPathList, Properties properties);

	void sendMailWithAttachment(String sendTo, String title, String context, String attachmentPath,
			Properties properties);

	int updateWasUsed(Integer mailId);

	CommonResult sendSimpleMail(Long userId, String sendTo, String title, String content, String mailKey,
			MailType mailType);
}
