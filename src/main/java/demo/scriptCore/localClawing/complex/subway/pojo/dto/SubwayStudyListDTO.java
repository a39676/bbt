package demo.scriptCore.localClawing.complex.subway.pojo.dto;

import java.util.List;

import net.sf.json.JSONObject;

public class SubwayStudyListDTO {

	private List<SubwayStudyProjectDTO> datas;
	private JSONObject paging;

	public List<SubwayStudyProjectDTO> getDatas() {
		return datas;
	}

	public void setDatas(List<SubwayStudyProjectDTO> datas) {
		this.datas = datas;
	}

	public JSONObject getPaging() {
		return paging;
	}

	public void setPaging(JSONObject paging) {
		this.paging = paging;
	}

	@Override
	public String toString() {
		return "SubwayStudyListDTO [datas=" + datas + ", paging=" + paging + "]";
	}

}
