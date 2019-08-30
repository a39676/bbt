package demo.base.user.pojo.type;

/**
 * 角色类型
 * 系统角色, 机构角色
 * @author Daven
 */
public enum AuthTypeType {

	SYS_AUTH("SYS_AUTH", -1),
	
	ORG_AUTH("ORG_AUTH", 1);
	
	private String name;
	private Integer code;
	
	AuthTypeType(String roleName, Integer roleId) {
		this.name = roleName;
		this.code = roleId;
	}
	
	public String getName() {
		return name;
	}
	public Integer getCode() {
		return code;
	}
	
	public static AuthTypeType getRole(Integer id) {
		for(AuthTypeType role : AuthTypeType.values()) {
			if(role.getCode() == id) {
				return role;
			}
		}
		return null;
	}
	
	public static AuthTypeType getRole(String roleName) {
		for(AuthTypeType role : AuthTypeType.values()) {
			if(role.getName().equals(roleName)) {
				return role;
			}
		}
		return null;
	}
}
