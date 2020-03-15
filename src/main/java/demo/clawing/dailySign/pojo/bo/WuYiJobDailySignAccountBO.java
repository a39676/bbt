package demo.clawing.dailySign.pojo.bo;

public class WuYiJobDailySignAccountBO extends DailySignAccountBO {

	private String resumeDetailUrl;
	private String jobintentUrl;
	private String refreshCV;

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

	@Override
	public String toString() {
		return "WuYiJobDailySignAccountBO [resumeDetailUrl=" + resumeDetailUrl + ", jobintentUrl=" + jobintentUrl
				+ ", refreshCV=" + refreshCV + "]";
	}

}
