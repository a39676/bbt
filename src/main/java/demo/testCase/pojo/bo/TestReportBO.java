package demo.testCase.pojo.bo;

import java.time.LocalDateTime;

public class TestReportBO {

	private Long id;

	private Long projectId;

	private Long caseId;

	private Long moduleId;

	private String eventName;

	private String reportPath;

	private LocalDateTime createTime;

	private LocalDateTime startTime;

	private LocalDateTime endTime;

	private Boolean isPass;

	public void setId(Long id) {
		this.id = id;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public void setIsPass(Boolean isPass) {
		this.isPass = isPass;
	}

	public Long getId() {
		return id;
	}

	public Long getProjectId() {
		return projectId;
	}

	public Long getCaseId() {
		return caseId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public String getEventName() {
		return eventName;
	}

	public String getReportPath() {
		return reportPath;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public Boolean getIsPass() {
		return isPass;
	}

	@Override
	public String toString() {
		return "TestReportBO [id=" + id + ", projectId=" + projectId + ", caseId=" + caseId + ", moduleId=" + moduleId
				+ ", eventName=" + eventName + ", reportPath=" + reportPath + ", createTime=" + createTime
				+ ", startTime=" + startTime + ", endTime=" + endTime + ", isPass=" + isPass + ", getId()=" + getId()
				+ ", getProjectId()=" + getProjectId() + ", getCaseId()=" + getCaseId() + ", getModuleId()="
				+ getModuleId() + ", getEventName()=" + getEventName() + ", getReportPath()=" + getReportPath()
				+ ", getCreateTime()=" + getCreateTime() + ", getStartTime()=" + getStartTime() + ", getEndTime()="
				+ getEndTime() + ", getIsPass()=" + getIsPass() + ", getClass()=" + getClass() + ", hashCode()="
				+ hashCode() + ", toString()=" + super.toString() + "]";
	}

}
