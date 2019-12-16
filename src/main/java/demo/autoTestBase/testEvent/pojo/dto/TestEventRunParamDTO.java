package demo.autoTestBase.testEvent.pojo.dto;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;

public class TestEventRunParamDTO {

	private TestEvent te;
	private String screenshotPath;
	private String reportOutputFolderPath;
	private String parameterPath;

	public TestEvent getTe() {
		return te;
	}

	public void setTe(TestEvent te) {
		this.te = te;
	}

	public String getScreenshotPath() {
		return screenshotPath;
	}

	public void setScreenshotPath(String screenshotPath) {
		this.screenshotPath = screenshotPath;
	}

	public String getReportOutputFolderPath() {
		return reportOutputFolderPath;
	}

	public void setReportOutputFolderPath(String reportOutputFolderPath) {
		this.reportOutputFolderPath = reportOutputFolderPath;
	}

	public String getParameterPath() {
		return parameterPath;
	}

	public void setParameterPath(String parameterPath) {
		this.parameterPath = parameterPath;
	}

	@Override
	public String toString() {
		return "TestEventRunParamDTO [te=" + te + ", screenshotPath=" + screenshotPath + ", reportOutputFolderPath="
				+ reportOutputFolderPath + ", parameterPath=" + parameterPath + "]";
	}

}
