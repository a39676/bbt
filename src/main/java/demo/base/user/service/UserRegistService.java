package demo.base.user.service;

import demo.base.user.pojo.dto.UserRegistDTO;
import demo.base.user.pojo.vo.__baseSuperAdminRegistVO;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface UserRegistService {

	CommonResultBBT newUserRegist(UserRegistDTO param, String ip);

	CommonResultBBT modifyRegistEmail(Long userId, String email);

	CommonResultBBT sendForgotPasswordMail(String email, String hostName);

	CommonResultBBT sendForgotUsernameMail(String email, String hostName);

	CommonResultBBT resetPasswordByMailKey(String mailKey, String newPassword, String newPasswordRepeat);

	CommonResultBBT resetPasswordByLoginUser(Long userId, String oldPassword, String newPassword,
			String newPasswordRepeat);

	boolean isUserExists(String userName);

	CommonResultBBT registActivation(String mailKey, String activeEMail);

	void handleMails();

	__baseSuperAdminRegistVO __baseSuperAdminRegist();

//	CommonResultBBT resendRegistMail(Long userId); //2018-06-28 暂停向注册用户发送激活邮件,改由用户发回激活码.
}
