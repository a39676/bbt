package demo.fakeFTP.pojo.param.controllerParam;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONObject;

public class GetFilePathDetailParam implements CommonControllerParam {

	private String filePath;

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Override
	public String toString() {
		return "GetFilePathDetailParam [filePath=" + filePath + "]";
	}

	@Override
	public GetFilePathDetailParam fromJson(JSONObject json) {
		GetFilePathDetailParam param = new GetFilePathDetailParam();
		if (json.containsKey("filePath")) {
			param.setFilePath(json.getString("filePath"));
		}
		return param;
	}

}
