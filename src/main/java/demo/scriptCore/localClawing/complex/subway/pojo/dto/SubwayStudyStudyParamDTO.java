package demo.scriptCore.localClawing.complex.subway.pojo.dto;

public class SubwayStudyStudyParamDTO {

	private String originOrgId;
	private Integer previewType;

	public String getOriginOrgId() {
		return originOrgId;
	}

	public void setOriginOrgId(String originOrgId) {
		this.originOrgId = originOrgId;
	}

	public Integer getPreviewType() {
		return previewType;
	}

	public void setPreviewType(Integer previewType) {
		this.previewType = previewType;
	}

	@Override
	public String toString() {
		return "SubwayStudyStudyParamDTO [originOrgId=" + originOrgId + ", previewType=" + previewType + "]";
	}

}
