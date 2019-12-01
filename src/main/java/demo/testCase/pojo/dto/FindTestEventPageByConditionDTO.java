package demo.testCase.pojo.dto;

import java.time.LocalDateTime;

public class FindTestEventPageByConditionDTO {

	private Long id;
	private Long moduleId;
	private String eventName;
	private String reportPath;
	private LocalDateTime startTime;
	private Long limit;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		this.eventName = eventName;
	}

	public String getReportPath() {
		return reportPath;
	}

	public void setReportPath(String reportPath) {
		this.reportPath = reportPath;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public Long getLimit() {
		return limit;
	}

	public void setLimit(Long limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "FindTestEventPageByConditionDTO [id=" + id + ", moduleId=" + moduleId + ", eventName=" + eventName
				+ ", reportPath=" + reportPath + ", startTime=" + startTime + ", limit=" + limit + "]";
	}

}
