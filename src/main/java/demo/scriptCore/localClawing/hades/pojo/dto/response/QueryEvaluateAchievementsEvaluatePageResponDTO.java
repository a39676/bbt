package demo.scriptCore.localClawing.hades.pojo.dto.response;

import net.sf.json.JSONObject;

public class QueryEvaluateAchievementsEvaluatePageResponDTO extends HadeResponseCommonDTO {

	private JSONObject data;

	public JSONObject getData() {
		return data;
	}

	public void setData(JSONObject data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "QueryEvaluateAchievementsEvaluatePageResponDTO [data=" + data + ", getCode()=" + getCode()
				+ ", getSuccess()=" + getSuccess() + ", getMessage()=" + getMessage() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
