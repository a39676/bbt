package demo.baseCommon.pojo.type;

public enum ResultType {
	
	// controller层可以返回给外部的结果.
	success	("success", "0"),
	resetPassword("已成功重置密码", "0"),
	createArticleLongSuccess("已成功发送,可能稍后就会出现...", "0"),
	evaluationVoteSuccess("已评~", "0"),
	setArticleHotSuccess("文章已置顶", "0"),
	complaintReciveSuccess("投诉已接收,我们将尽快处理,感谢您的反馈!", "0"),
	articleCommentDeleteSuccess("评论已删除", "0"),
	articleCommentPassSuccess("评论已通过", "0"),
	
	fail ("fail", "-1"),
	nullParam ("参数为空", "-2"),
	errorParam ("参数异常", "-3"),
	serviceError("内部异常", "-4"),
	notLoginUser("请登录后操作~", "-5"),
	loginFail("账户或密码错,请重新登录", "-6"),
//	logicError("逻辑错误限制", "-7"),
//	communicationError("网络通讯异常", "-8"),
	
	// service层返回给 controller层,内部logger记录用的结果,视情况是否返回给外部.
	differentPassword("两次输入密码不同,请重新输入", "-3-1"),
	invalidPassword("请设定8~16位的密码", "-3-2"),
	wrongOldPassword("原密码错误", "-3-3"),
	articleTooLong("内容过长", "-3-4"),
	articleTooShort("内容过短", "-3-5"),
	channelUUIDError("发现了一个奇怪的异常,请刷新页面后重试..", "-3-6"),
	articleWasPass("文章已通过", "-3-7"),
	articleWasReject("文章已拒绝", "-3-8"),
	articleWasDelete("文章已删除", "-3-9"),
	
	mailPropertiesError ("邮件配置参数异常", "-4-1"),
	mailBaseOptionError ("邮件基础参数异常", "-4-2"),
	errorWhenArticleSave ("文章保存时异常", "-4-7"),
	errorWhenArticleLoad("文章加载时异常", "-4-8"),
	imageStoreInsertError("图片转存处理异常", "-4-9"),
	imageStoreInsertError_2("图片转存处理异常_2", "-4-10"),
	imageStoreInsertError_3("图片转存处理异常_3", "-4-11"),
	imageStoreInsertError_4("图片转存处理异常_4", "-4-12"),
	
	articleChannelPostLimit("当前频道每日发送次数已达上限", "-7-1"),
	mailExists ("邮箱已注册", "-7-2"),
	linkExpired ("链接已过期", "-7-3"),
	mailNotRegist ("邮箱未注册", "-7-4"),
	mailNotActivation ("邮箱未激活", "-7-5"),
	notYourArticle ("删除过程异常", "-7-6"),
	hadEvaluationVoted ("人海中已留下你的足迹...", "-7-7"),
	justComment("刚刚才发过言...要不坐下先喝杯咖啡...", "-7-8"),
	
	communicationError("网络通讯异常", "-8-1"),
	;
	
	
	private String resultName;
	private String resultCode;
	
	ResultType(String name, String code) {
		this.resultName = name;
		this.resultCode = code;
	}

	public String getName() {
		return this.resultName;
	}
	
	public String getCode() {
		return this.resultCode;
	}
}
