package demo.mobile.pojo.bo;

import net.sf.json.JSONObject;

public class MobileDataBO {

	private Integer midNum;
	private JSONObject json;
	private String province;
	private String city;
	private Integer company;
	private Integer areaId;

	@Override
	public String toString() {
		return "MobileDataBO [midNum=" + midNum + ", province=" + province + ", city=" + city
				+ ", company=" + company + ", areaId=" + areaId + "]";
	}

	public Integer getAreaId() {
		return areaId;
	}

	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}

	public Integer getMidNum() {
		return midNum;
	}

	public void setMidNum(Integer midNum) {
		this.midNum = midNum;
	}

	public JSONObject getJson() {
		return json;
	}

	public void setJson(JSONObject json) {
		this.json = json;
	}

	public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getCompany() {
		return company;
	}

	public void setCompany(Integer company) {
		this.company = company;
	}
}
