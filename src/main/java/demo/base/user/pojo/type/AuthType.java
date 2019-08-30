package demo.base.user.pojo.type;

/**
 * 角色枚举
 * 部分基础角色
 * @author Daven
 */
public enum AuthType {

	SUPER_ADMIN("SUPER_ADMIN", -1L),
	ADMIN("ADMIN", -2L),
	USER("USER", 1L),
	USER_ACTIVE("USER_ACTIVE", 2L),
	POSTER("POSTER", 3L),
	DELAY_POSTER("DELAY_POSTER", 4L),
	;
	
	private String name;
	private Long code;
	
	AuthType(String roleName, Long roleId) {
		this.name = roleName;
		this.code = roleId;
	}
	
	public String getName() {
		return name;
	}
	public Long getCode() {
		return code;
	}
	
	public static AuthType getRole(Long id) {
		for(AuthType role : AuthType.values()) {
			if(role.getCode() == id) {
				return role;
			}
		}
		return null;
	}
	
	public static AuthType getRole(String roleName) {
		for(AuthType role : AuthType.values()) {
			if(role.getName().equals(roleName)) {
				return role;
			}
		}
		return null;
	}
}
