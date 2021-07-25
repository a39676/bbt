package demo.autoTestBase.testEvent.pojo.bo;

import java.time.LocalDateTime;

import org.openqa.selenium.WebDriver;

import at.report.pojo.dto.JsonReportDTO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;

public class TestEventBO {

	private TestEvent event;
	private JsonReportDTO report;
	private WebDriver d;
	private String reportOutputFolderPath;
	private LocalDateTime screenshotImageValidTime;
	

	@Override
	public String toString() {
		return "TestEventBO [event=" + event + ", report=" + report + "]";
	}

	public TestEvent getEvent() {
		return event;
	}

	public void setEvent(TestEvent event) {
		this.event = event;
	}

	public JsonReportDTO getReport() {
		return report;
	}

	public void setReport(JsonReportDTO report) {
		this.report = report;
	}

}
