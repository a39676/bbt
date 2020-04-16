package demo.clawing.scheduleClawing.pojo.bo;

public class WuYiJobClawingBO extends DailySignAccountBO {

	private String resumeDetailUrl;
	private String jobintentUrl;
	private String refreshCV;
	private Integer refreshStep = 4;

	public String getResumeDetailUrl() {
		return resumeDetailUrl;
	}

	public void setResumeDetailUrl(String resumeDetailUrl) {
		this.resumeDetailUrl = resumeDetailUrl;
	}

	public String getJobintentUrl() {
		return jobintentUrl;
	}

	public void setJobintentUrl(String jobintentUrl) {
		this.jobintentUrl = jobintentUrl;
	}

	public String getRefreshCV() {
		return refreshCV;
	}

	public void setRefreshCV(String refreshCV) {
		this.refreshCV = refreshCV;
	}

	public Integer getRefreshStep() {
		return refreshStep;
	}

	public void setRefreshStep(Integer refreshStep) {
		this.refreshStep = refreshStep;
	}

	@Override
	public String toString() {
		return "WuYiJobClawingBO [resumeDetailUrl=" + resumeDetailUrl + ", jobintentUrl=" + jobintentUrl
				+ ", refreshCV=" + refreshCV + ", refreshStep=" + refreshStep + ", getUsername()=" + getUsername()
				+ ", getPwd()=" + getPwd() + ", getMainUrl()=" + getMainUrl() + ", getSignType()=" + getSignType()
				+ ", getRemark()=" + getRemark() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}

}
