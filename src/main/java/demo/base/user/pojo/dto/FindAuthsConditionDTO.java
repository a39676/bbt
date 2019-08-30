package demo.base.user.pojo.dto;

public class FindAuthsConditionDTO {

	private Integer authType;
	private String authName;
	private Long roleId;
	private String roleName;
	private boolean authIsDelete = false;
	private boolean authRoleIsDelete = false;
	private boolean roleIsDelete = false;

	public boolean isAuthRoleIsDelete() {
		return authRoleIsDelete;
	}

	public void setAuthRoleIsDelete(boolean authRoleIsDelete) {
		this.authRoleIsDelete = authRoleIsDelete;
	}

	public boolean isAuthIsDelete() {
		return authIsDelete;
	}

	public void setAuthIsDelete(boolean authIsDelete) {
		this.authIsDelete = authIsDelete;
	}

	public boolean isRoleIsDelete() {
		return roleIsDelete;
	}

	public void setRoleIsDelete(boolean roleIsDelete) {
		this.roleIsDelete = roleIsDelete;
	}

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	@Override
	public String toString() {
		return "FindAuthsConditionDTO [authType=" + authType + ", authName=" + authName + ", roleId=" + roleId
				+ ", roleName=" + roleName + ", authIsDelete=" + authIsDelete + ", authRoleIsDelete=" + authRoleIsDelete
				+ ", roleIsDelete=" + roleIsDelete + "]";
	}

}
