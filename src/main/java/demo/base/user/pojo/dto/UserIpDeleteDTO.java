package demo.base.user.pojo.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import demo.baseCommon.pojo.param.CommonControllerParam;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.dateTimeHandle.DateHandler;

public class UserIpDeleteDTO implements CommonControllerParam {

	private Date startDate;
	private Date endDate;
	private String uri;
	private List<String> uriList;

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public List<String> getUriList() {
		return uriList;
	}

	public void setUriList(List<String> uriList) {
		this.uriList = uriList;
	}

	@Override
	public UserIpDeleteDTO fromJson(JSONObject json) {
		UserIpDeleteDTO param = new UserIpDeleteDTO();
		DateHandler dateHandler = new DateHandler();
		if(json.containsKey("startDate")) {
			param.setStartDate(dateHandler.stringToDateUnkonwFormat(json.getString("startDate")));
		}
		if(json.containsKey("endDate")) {
			param.setEndDate(dateHandler.stringToDateUnkonwFormat(json.getString("endDate")));
		}
		if(json.containsKey("uri")) {
			param.setUri(json.getString("uri"));
		}
		if(json.containsKey("uriList")) {
			JSONArray jsonArrayUriList = json.getJSONArray("uriList");
			List<String> uriList = new ArrayList<String>();
			for(int i = 0; i < jsonArrayUriList.size(); i++) {
				uriList.add(String.valueOf(jsonArrayUriList.get(i)));
			}
			param.setUriList(uriList);
		}
		
		return param;
	}

}
