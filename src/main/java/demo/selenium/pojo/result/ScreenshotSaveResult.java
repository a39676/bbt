package demo.selenium.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class ScreenshotSaveResult extends CommonResult {

	private String cloudId;
	private String url;
	private String savingPath;
	private Long screenShotId;

	public Long getScreenShotId() {
		return screenShotId;
	}

	public void setScreenShotId(Long screenShotId) {
		this.screenShotId = screenShotId;
	}

	public String getCloudId() {
		return cloudId;
	}

	public void setCloudId(String cloudId) {
		this.cloudId = cloudId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSavingPath() {
		return savingPath;
	}

	public void setSavingPath(String savingPath) {
		this.savingPath = savingPath;
	}

	@Override
	public String toString() {
		return "SavingScreenShotResult [cloudId=" + cloudId + ", url=" + url + ", savingPath=" + savingPath
				+ ", screenShotId=" + screenShotId + "]";
	}

}
