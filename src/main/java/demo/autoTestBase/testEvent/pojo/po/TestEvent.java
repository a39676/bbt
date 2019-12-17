package demo.autoTestBase.testEvent.pojo.po;

import java.time.LocalDateTime;

public class TestEvent {
    private Long id;

	private Long projectId;

	private Long caseId;

	private Long processId;

	private Long moduleId;

	private String eventName;

	private String remark;

	private String parameterFilePath;

	private String reportPath;

	private LocalDateTime createTime;

	private LocalDateTime appointment;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private Boolean isPass;

	private Boolean isDelete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName == null ? null : eventName.trim();
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark == null ? null : remark.trim();
	}

	public String getParameterFilePath() {
		return parameterFilePath;
	}

	public void setParameterFilePath(String parameterFilePath) {
		this.parameterFilePath = parameterFilePath == null ? null : parameterFilePath.trim();
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath == null ? null : reportPath.trim();
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getAppointment() {
		return appointment;
	}

	public void setAppointment(LocalDateTime appointment) {
		this.appointment = appointment;
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

	public Boolean getIsPass() {
		return isPass;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}