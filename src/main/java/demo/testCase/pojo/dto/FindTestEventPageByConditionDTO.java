package demo.testCase.pojo.dto;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;

public class FindTestEventPageByConditionDTO {

	private Long id;
	private Long moduleId;
	private String eventName;
	private String reportPath;
	@ApiModelProperty("最早创建时间")
	private LocalDateTime createStartTime;
	@ApiModelProperty("最迟创建时间")
	private LocalDateTime createEndTime;
	@ApiModelProperty("最早运行时间")
	private LocalDateTime runTimeStartTime;
	@ApiModelProperty("最迟运行时间")
	private LocalDateTime runTimeEndTime;
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

	public LocalDateTime getCreateStartTime() {
		return createStartTime;
	}

	public void setCreateStartTime(LocalDateTime createStartTime) {
		this.createStartTime = createStartTime;
	}

	public LocalDateTime getCreateEndTime() {
		return createEndTime;
	}

	public void setCreateEndTime(LocalDateTime createEndTime) {
		this.createEndTime = createEndTime;
	}

	public LocalDateTime getRunTimeStartTime() {
		return runTimeStartTime;
	}

	public void setRunTimeStartTime(LocalDateTime runTimeStartTime) {
		this.runTimeStartTime = runTimeStartTime;
	}

	public LocalDateTime getRunTimeEndTime() {
		return runTimeEndTime;
	}

	public void setRunTimeEndTime(LocalDateTime runTimeEndTime) {
		this.runTimeEndTime = runTimeEndTime;
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
				+ ", reportPath=" + reportPath + ", createStartTime=" + createStartTime + ", createEndTime="
				+ createEndTime + ", runTimeStartTime=" + runTimeStartTime + ", runTimeEndTime=" + runTimeEndTime
				+ ", limit=" + limit + "]";
	}

}
