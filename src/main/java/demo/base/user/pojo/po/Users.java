package demo.base.user.pojo.po;

public class Users {

	private Long userId;

    private String userName;

    private String pwd;

    private Boolean enabled;
    
    private Boolean accountNonLocked;

    private Boolean accountNonExpired;

    private Boolean credentialsNonExpired;
    
    private String pwdd;
    
    
    @Override
	public String toString() {
		return "Users [userId=" + userId + ", userName=" + userName + ", pwd=" + pwd + ", enabled=" + enabled
				+ ", accountNonLocked=" + accountNonLocked + ", accountNonExpired=" + accountNonExpired
				+ ", credentialsNonExpired=" + credentialsNonExpired + "]";
	}

	public Boolean getCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	public void setCredentialsNonExpired(Boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd == null ? null : pwd.trim();
    }

    public Boolean getEnable() {
        return enabled;
    }

    public void setEnable(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getAccountNonExpired() {
        return accountNonExpired;
    }

    public void setAccountNonExpired(Boolean accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }

    public Boolean getAccountNonLocked() {
        return accountNonLocked;
    }

    public void setAccountNonLocked(Boolean accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    
    public Boolean getEnabled() {
		return enabled;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public String getPwdd() {
		return pwdd;
	}

	public void setPwdd(String pwdd) {
		this.pwdd = pwdd;
	}

}