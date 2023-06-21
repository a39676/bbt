package demo.scriptCore.scheduleClawing.complex.pojo.dto;

public class UnderWayCoursewareDTO {

	private String coursewareId;
	private Integer seconds;

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

	@Override
	public String toString() {
		return "UnderWayCoursewareDTO [coursewareId=" + coursewareId + ", seconds=" + seconds + "]";
	}

}
