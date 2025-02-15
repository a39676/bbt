package demo.scriptCore.localClawing.complex.subway.pojo.dto;

public class SubwayStudyTaskPreinitDTO {

	private String kngId;
	private String courseId;
	private String targetCode;
	private String targetId;
	private String customFunctionCode;
	private SubwayStudyStudyParamDTO studyParam;
	private SubwayStudyTargetParamDTO targetParam;

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

	public String getTargetCode() {
		return targetCode;
	}

	public void setTargetCode(String targetCode) {
		this.targetCode = targetCode;
	}

	public String getTargetId() {
		return targetId;
	}

	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}

	public String getCustomFunctionCode() {
		return customFunctionCode;
	}

	public void setCustomFunctionCode(String customFunctionCode) {
		this.customFunctionCode = customFunctionCode;
	}

	public SubwayStudyStudyParamDTO getStudyParam() {
		return studyParam;
	}

	public void setStudyParam(SubwayStudyStudyParamDTO studyParam) {
		this.studyParam = studyParam;
	}

	public SubwayStudyTargetParamDTO getTargetParam() {
		return targetParam;
	}

	public void setTargetParam(SubwayStudyTargetParamDTO targetParam) {
		this.targetParam = targetParam;
	}

	@Override
	public String toString() {
		return "SubwayStudyTaskPreinitDTO [kngId=" + kngId + ", courseId=" + courseId + ", targetCode=" + targetCode
				+ ", targetId=" + targetId + ", customFunctionCode=" + customFunctionCode + ", studyParam=" + studyParam
				+ ", targetParam=" + targetParam + "]";
	}

}
