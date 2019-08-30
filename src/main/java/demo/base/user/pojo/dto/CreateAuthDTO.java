package demo.base.user.pojo.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import demo.base.user.pojo.type.AuthTypeType;

public class CreateAuthDTO {

	@NotNull
	@Size(min = 2, max = 30)
	private String authName;
	
	/** {@link AuthTypeType}} */
	private Integer authType = AuthTypeType.ORG_AUTH.getCode();

	public String getAuthName() {
		return authName;
	}

	public void setAuthName(String authName) {
		this.authName = authName;
	}

	public Integer getAuthType() {
		return authType;
	}

	public void setAuthType(Integer authType) {
		this.authType = authType;
	}

	@Override
	public String toString() {
		return "CreateAuthDTO [authName=" + authName + ", authType=" + authType + "]";
	}

}
