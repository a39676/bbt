package demo.base.user.pojo.constant;

public class UsersUrlConstant {
	
	public static final String root = "/user";
	public static final String userNameExistCheck = "/userNameExistCheck";
	public static final String userRegist = "/userRegist";
//	public static final String registActivation = "/registActivation"; // 暂时不再主动发送注册验证邮件,改为验证用户发送的邮件. 2018-06-28
	public static final String userInfo = "/userInfo";
	public static final String otherUserInfo = "/otherUserInfo";
//	public static final String resendRegistMail = "/resendRegistMail"; // 2018-06-28 暂停向注册用户发送激活邮件,改由用户发回激活码.
	public static final String modifyRegistMail = "/modifyRegistMail";
	public static final String forgotPasswordOrUsername = "/forgotPasswordOrUsername";
	public static final String forgotPassword = "/forgotPassword";
	public static final String forgotUsername = "/forgotUsername";

	public static final String resetPassword = "/resetPassword";
	public static final String isLogin = "/isLogin";

}
