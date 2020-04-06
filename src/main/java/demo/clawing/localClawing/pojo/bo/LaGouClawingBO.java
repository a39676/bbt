package demo.clawing.localClawing.pojo.bo;

public class LaGouClawingBO {

	private String username;
	private String password;
	private String mainUrl;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	@Override
	public String toString() {
		return "LaGouClawingBO [username=" + username + ", password=" + password + ", mainUrl=" + mainUrl + "]";
	}

}
