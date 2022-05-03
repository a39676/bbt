package demo.scriptCore.scheduleClawing.pojo.dto;

import java.util.List;

import demo.scriptCore.scheduleClawing.pojo.type.EducationInfoSourceType;

public class EducationInfoSubDTO {

	private EducationInfoSourceType sourceType;

	private String mainUrl;

	private List<EducationInfoUrlDTO> infoUrlList;

	public EducationInfoSourceType getSourceType() {
		return sourceType;
	}

	public void setSourceType(EducationInfoSourceType sourceType) {
		this.sourceType = sourceType;
	}

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public List<EducationInfoUrlDTO> getInfoUrlList() {
		return infoUrlList;
	}

	public void setInfoUrlList(List<EducationInfoUrlDTO> infoUrlList) {
		this.infoUrlList = infoUrlList;
	}

	@Override
	public String toString() {
		return "EducationInfoSubDTO [sourceType=" + sourceType + ", infoUrlList=" + infoUrlList + "]";
	}

}
