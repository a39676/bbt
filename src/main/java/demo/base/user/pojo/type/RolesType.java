package demo.base.user.pojo.type;

public enum RolesType {

	ROLE_ADMIN("ROLE_ADMIN", -4),
	ROLE_DEV("ROLE_DEV", -3),
	ROLE_DBA("ROLE_DBA", -2),
	ROLE_SUPER_ADMIN("ROLE_SUPER_ADMIN", -1),
	
	ROLE_USER("ROLE_USER", 1),
	ROLE_USER_ACTIVE("ROLE_USER_ACTIVE", 2),
	ROLE_POSTER("ROLE_POSTER", 3),
	ROLE_DELAY_POSTER("ROLE_DELAY_POSTER", 4);
	
	private String name;
	private Integer code;
	
	RolesType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}
	public Integer getCode() {
		return code;
	}
	
	public static RolesType getRole(Integer code) {
		for(RolesType role : RolesType.values()) {
			if(role.getCode() == code) {
				return role;
			}
		}
		return null;
	}
	
	public static RolesType getRole(String name) {
		for(RolesType role : RolesType.values()) {
			if(role.getName().equals(name)) {
				return role;
			}
		}
		return null;
	}
}
