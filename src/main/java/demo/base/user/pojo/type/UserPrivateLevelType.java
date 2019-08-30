package demo.base.user.pojo.type;

/** 用户隐私级别. */
public enum UserPrivateLevelType {
	
	p1("仅自己可见", 1),
	p2("仅好友可见", 2),
	p3("全部可见", 3),
	;
	
	private String level;
	private Integer code;
	
	UserPrivateLevelType(String level, Integer code) {
		this.level = level;
		this.code = code;
	}
	

	public String getLevel() {
		return level;
	}

	public Integer getCode() {
		return code;
	}
	

	public static UserPrivateLevelType getType(String level) {
		for(UserPrivateLevelType t : UserPrivateLevelType.values()) {
			if(t.getLevel().equals(level)) {
				return t;
			}
		}
		
		return null;
	}
	
	public static UserPrivateLevelType getType(Integer code) {
		if(code == null) {
			return null;
		}
		for(UserPrivateLevelType t : UserPrivateLevelType.values()) {
			if(t.getCode().equals(code)) {
				return t;
			}
		}
		return null;
	}
}
