package demo.autoTestBase.testEvent.pojo.bo;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import autoTest.report.pojo.dto.JsonReportOfFlowDTO;
import autoTest.testEvent.common.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.common.pojo.type.AutomationTestFlowResultType;
import autoTest.testModule.pojo.type.TestModuleType;

public class TestEventBO {
	
	private Long eventId;
	private TestModuleType moduleType;
	private Long flowId;
	private String flowName;

	private LocalDateTime appointment;

	private JsonReportOfFlowDTO report;
	private List<AutomationTestCaseResult> caseResultList = new ArrayList<>();
	private LocalDateTime screenshotImageValidTime;
	private String paramStr;
	private LocalDateTime startTime;
	private LocalDateTime endTime;

	private String remark;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public TestModuleType getModuleType() {
		return moduleType;
	}

	public void setModuleType(TestModuleType moduleType) {
		this.moduleType = moduleType;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public LocalDateTime getAppointment() {
		return appointment;
	}

	public void setAppointment(LocalDateTime appointment) {
		this.appointment = appointment;
	}

	public JsonReportOfFlowDTO getReport() {
		return report;
	}

	public void setReport(JsonReportOfFlowDTO report) {
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
		if (caseResultList == null || caseResultList.isEmpty()) {
			return false;
		}
		for (AutomationTestCaseResult subResult : caseResultList) {
			if (!AutomationTestFlowResultType.PASS.equals(subResult.getResultType())) {
				return false;
			}
		}
		return true;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "TestEventBO [eventId=" + eventId + ", moduleType=" + moduleType + ", flowId=" + flowId + ", flowName="
				+ flowName + ", appointment=" + appointment + ", report=" + report + ", caseResultList="
				+ caseResultList + ", screenshotImageValidTime=" + screenshotImageValidTime + ", paramStr=" + paramStr
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", remark=" + remark + "]";
	}

}
