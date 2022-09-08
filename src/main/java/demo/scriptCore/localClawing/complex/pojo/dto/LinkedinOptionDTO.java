package demo.scriptCore.localClawing.complex.pojo.dto;

public class LinkedinOptionDTO {

	private String mainUrl;
	private String loginUrl;
	private String username;
	private String pwd;
	private String buildRelationshipButtonText;
	private String appliedRelationshipButtonText;
	private Double applyRate;

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public String getLoginUrl() {
		return loginUrl;
	}

	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}

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

	public String getBuildRelationshipButtonText() {
		return buildRelationshipButtonText;
	}

	public void setBuildRelationshipButtonText(String buildRelationshipButtonText) {
		this.buildRelationshipButtonText = buildRelationshipButtonText;
	}

	public String getAppliedRelationshipButtonText() {
		return appliedRelationshipButtonText;
	}

	public void setAppliedRelationshipButtonText(String appliedRelationshipButtonText) {
		this.appliedRelationshipButtonText = appliedRelationshipButtonText;
	}

	public Double getApplyRate() {
		return applyRate;
	}

	public void setApplyRate(Double applyRate) {
		this.applyRate = applyRate;
	}

	@Override
	public String toString() {
		return "LinkedinOptionDTO [mainUrl=" + mainUrl + ", loginUrl=" + loginUrl + ", username=" + username + ", pwd="
				+ pwd + ", buildRelationshipButtonText=" + buildRelationshipButtonText
				+ ", appliedRelationshipButtonText=" + appliedRelationshipButtonText + ", applyRate=" + applyRate + "]";
	}

}
