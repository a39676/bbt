package demo.scriptCore.localClawing.hades.pojo.dto;

public class HadesUserDTO {

	private String username;
	private String pwd;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "HadesUserDTO [username=" + username + ", pwd=" + pwd + "]";
	}

}
