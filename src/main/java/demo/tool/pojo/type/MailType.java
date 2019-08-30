package demo.tool.pojo.type;

public enum MailType {
	/** 注册激活 */
	registActivation("registActivation", 1),
	/** 重置密码 */
	forgotPassword("forgotPassword", 2),
	/** 忘记用户名 */
	forgotUsername("forgotUsername", 3),
	;

	private String name;
	private Integer code;

	private MailType(String mailType, Integer mailCode) {
		this.name = mailType;
		this.code = mailCode;
	}

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}
	
	public static MailType getType(String typeName) {
		for(MailType t : MailType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static MailType getType(Integer typeCode) {
		for(MailType t : MailType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
