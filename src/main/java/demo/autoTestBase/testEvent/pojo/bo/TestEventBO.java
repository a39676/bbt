package demo.autoTestBase.testEvent.pojo.bo;

import java.time.LocalDateTime;
import java.util.List;

import org.openqa.selenium.WebDriver;

import at.report.pojo.dto.JsonReportOfEventDTO;
import autoTest.testEvent.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.pojo.type.AutomationTestFlowResultType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;

public class TestEventBO {

	private TestEvent event;
	private WebDriver webDriver;
	private JsonReportOfEventDTO report;
	private List<AutomationTestCaseResult> caseResultList;
	private LocalDateTime screenshotImageValidTime;
	private String paramStr;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

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

	public JsonReportOfEventDTO getReport() {
		return report;
	}

	public void setReport(JsonReportOfEventDTO report) {
		this.report = report;
	}

	public List<AutomationTestCaseResult> getCaseResultList() {
		return caseResultList;
	}

	public void setCaseResultList(List<AutomationTestCaseResult> caseResultList) {
		this.caseResultList = caseResultList;
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

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}
	
	public boolean isPass() {
		if(caseResultList == null || caseResultList.isEmpty()) {
			return false;
		}
		for(AutomationTestCaseResult subResult : caseResultList) {
			if(!AutomationTestFlowResultType.PASS.equals(subResult.getResultType())) {
				return false;
			}
		}
		return true;
	}

	@Override
	public String toString() {
		return "TestEventBO [event=" + event + ", webDriver=" + webDriver + ", report=" + report + ", flowResultList="
				+ caseResultList + ", screenshotImageValidTime=" + screenshotImageValidTime + ", paramStr=" + paramStr
				+ ", startTime=" + startTime + ", endTime=" + endTime + "]";
	}

}
