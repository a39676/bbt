package demo.scriptCore.scheduleClawing.pojo.dto;

import java.util.List;

public class EducationInfoSummaryDTO {

	private List<EducationInfoSubDTO> infoList;

	public List<EducationInfoSubDTO> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<EducationInfoSubDTO> infoList) {
		this.infoList = infoList;
	}

	@Override
	public String toString() {
		return "EducationInfoSummaryDTO [infoList=" + infoList + "]";
	}

}
