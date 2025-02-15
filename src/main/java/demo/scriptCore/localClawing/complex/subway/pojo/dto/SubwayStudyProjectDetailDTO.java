package demo.scriptCore.localClawing.complex.subway.pojo.dto;

import java.util.List;

public class SubwayStudyProjectDetailDTO {

	private List<SubwayStudyProjectDataDTO> datas;

	public List<SubwayStudyProjectDataDTO> getDatas() {
		return datas;
	}

	public void setDatas(List<SubwayStudyProjectDataDTO> datas) {
		this.datas = datas;
	}

	@Override
	public String toString() {
		return "SubwayStudyProjectDetailDTO [datas=" + datas + "]";
	}

}
