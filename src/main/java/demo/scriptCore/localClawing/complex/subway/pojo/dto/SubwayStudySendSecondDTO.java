package demo.scriptCore.localClawing.complex.subway.pojo.dto;

public class SubwayStudySendSecondDTO {

	private Integer acqSecond = 20; // ": 20,
	private Integer actualSecond = 20; // ": 20,
	private Integer speed = 1; // ": 1,
	private String kngId; // ": "e7908b8f-b501-475a-90c5-855011d8c2f8",
	private String courseId; // ": "",
	private Integer viewLoc = 0; // ": 0,
	private String targetId; // ": "1818908417816729602",
	private String targetCode; // ": "o2o",
	private String originOrgId; // ": "d5bcbf8c-e595-40ff-9c0c-f39a5d02f1bc",
	private SubwayStudyTargetParamDTO targetParam;

	public Integer getAcqSecond() {
		return acqSecond;
	}

	public void setAcqSecond(Integer acqSecond) {
		this.acqSecond = acqSecond;
	}

	public Integer getActualSecond() {
		return actualSecond;
	}

	public void setActualSecond(Integer actualSecond) {
		this.actualSecond = actualSecond;
	}

	public Integer getSpeed() {
		return speed;
	}

	public void setSpeed(Integer speed) {
		this.speed = speed;
	}

	public String getKngId() {
		return kngId;
	}

	public void setKngId(String kngId) {
		this.kngId = kngId;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public Integer getViewLoc() {
		return viewLoc;
	}

	public void setViewLoc(Integer viewLoc) {
		this.viewLoc = viewLoc;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getTargetCode() {
		return targetCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}

	public String getOriginOrgId() {
		return originOrgId;
	}

	public void setOriginOrgId(String originOrgId) {
		this.originOrgId = originOrgId;
	}

	public SubwayStudyTargetParamDTO getTargetParam() {
		return targetParam;
	}

	public void setTargetParam(SubwayStudyTargetParamDTO targetParam) {
		this.targetParam = targetParam;
	}

	@Override
	public String toString() {
		return "SubwayStudySendSecondDTO [acqSecond=" + acqSecond + ", actualSecond=" + actualSecond + ", speed="
				+ speed + ", kngId=" + kngId + ", courseId=" + courseId + ", viewLoc=" + viewLoc + ", targetId="
				+ targetId + ", targetCode=" + targetCode + ", originOrgId=" + originOrgId + ", targetParam="
				+ targetParam + "]";
	}

}
