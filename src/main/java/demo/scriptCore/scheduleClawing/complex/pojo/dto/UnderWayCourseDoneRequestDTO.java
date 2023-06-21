package demo.scriptCore.scheduleClawing.complex.pojo.dto;

import java.time.LocalDateTime;

public class UnderWayCourseDoneRequestDTO {

	private String urlStr;
	private String trainProjectId;
	private String coursewareId;
	private Integer seconds;
	private LocalDateTime startTime;

	public String getUrlStr() {
		return urlStr;
	}

	public void setUrlStr(String urlStr) {
		this.urlStr = urlStr;
	}

	public String getTrainProjectId() {
		return trainProjectId;
	}

	public void setTrainProjectId(String trainProjectId) {
		this.trainProjectId = trainProjectId;
	}

	public String getCoursewareId() {
		return coursewareId;
	}

	public void setCoursewareId(String coursewareId) {
		this.coursewareId = coursewareId;
	}

	public Integer getSeconds() {
		return seconds;
	}

	public void setSeconds(Integer seconds) {
		this.seconds = seconds;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	@Override
	public String toString() {
		return "UnderWayCourseDoneRequestDTO [urlStr=" + urlStr + ", trainProjectId=" + trainProjectId
				+ ", coursewareId=" + coursewareId + ", seconds=" + seconds + ", startTime=" + startTime + "]";
	}

}
