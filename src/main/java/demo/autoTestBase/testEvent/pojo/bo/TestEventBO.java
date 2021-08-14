package demo.autoTestBase.testEvent.pojo.bo;

import java.time.LocalDateTime;

import org.openqa.selenium.WebDriver;

import at.report.pojo.dto.JsonReportDTO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;

public class TestEventBO {

	private TestEvent event;
	private WebDriver webDriver;
	private JsonReportDTO report;
	private String reportOutputFolderPath;
	private LocalDateTime screenshotImageValidTime;
	private String paramStr;

	public TestEvent getEvent() {
		return event;
	}

	public void setEvent(TestEvent event) {
		this.event = event;
	}

	public WebDriver getWebDriver() {
		return webDriver;
	}

	public void setWebDriver(WebDriver webDriver) {
		this.webDriver = webDriver;
	}

	public JsonReportDTO getReport() {
		return report;
	}

	public void setReport(JsonReportDTO report) {
		this.report = report;
	}

	public String getReportOutputFolderPath() {
		return reportOutputFolderPath;
	}

	public void setReportOutputPath(String reportOutputFolderPath) {
		this.reportOutputFolderPath = reportOutputFolderPath;
	}

	public LocalDateTime getScreenshotImageValidTime() {
		return screenshotImageValidTime;
	}

	public void setScreenshotImageValidTime(LocalDateTime screenshotImageValidTime) {
		this.screenshotImageValidTime = screenshotImageValidTime;
	}

	public String getParamStr() {
		return paramStr;
	}

	public void setParamStr(String paramStr) {
		this.paramStr = paramStr;
	}

	@Override
	public String toString() {
		return "TestEventBO [event=" + event + ", webDriver=" + webDriver + ", report=" + report
				+ ", reportOutputFolderPath=" + reportOutputFolderPath + ", screenshotImageValidTime="
				+ screenshotImageValidTime + ", paramStr=" + paramStr + "]";
	}

}
