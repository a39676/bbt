package demo.clawing.dailySign.pojo.bo;

public class DailySignAccountBO {

	private String userName;

	private String pwd;

	private String mainUrl;

	private Long signType;

	private String remark;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public Long getSignType() {
		return signType;
	}

	public void setSignType(Long signType) {
		this.signType = signType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}