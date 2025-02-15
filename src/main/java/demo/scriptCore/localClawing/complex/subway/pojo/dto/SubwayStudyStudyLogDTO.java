package demo.scriptCore.localClawing.complex.subway.pojo.dto;

import net.sf.json.JSONObject;

public class SubwayStudyStudyLogDTO {
	private String logTypeCode; // ": "003",
	private String uuId; // ": "21f76db2-ebff-4465-b131-bb19e121a423",
	private String pageUrl; // "https://gzmtr.yunxuetang.cn/o2o/#/playinfo?projectid=1818900058971518977&taskId=1818900267997321218",
	private JSONObject requestHeaderJson; // ": { "source": 501 },
	private String courseId; // ": "",
	private String kngId; // ": "472338b2-3a82-4ffb-b50e-457020b9c038",
	private String ext1; // ": "开始定时器",
	private String ext2; // ": "",
	private String ext3; // ": 1,
	private String ext4; // ": "",
	private String ext5; // ": "8/4/2024, 9:53:02 PM"
	private String requestMethod; // "post"
	private JSONObject requestJson;
	private JSONObject responseJson;
	private String requestUrl; // "study/submit/second"

	public String getLogTypeCode() {
		return logTypeCode;
	}

	public void setLogTypeCode(String logTypeCode) {
		this.logTypeCode = logTypeCode;
	}

	public String getUuId() {
		return uuId;
	}

	public void setUuId(String uuId) {
		this.uuId = uuId;
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}

	public JSONObject getRequestHeaderJson() {
		return requestHeaderJson;
	}

	public void setRequestHeaderJson(JSONObject requestHeaderJson) {
		this.requestHeaderJson = requestHeaderJson;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

	public String getKngId() {
		return kngId;
	}

	public void setKngId(String kngId) {
		this.kngId = kngId;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getExt2() {
		return ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	public String getExt3() {
		return ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	public String getExt4() {
		return ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}

	public String getExt5() {
		return ext5;
	}

	public void setExt5(String ext5) {
		this.ext5 = ext5;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public JSONObject getRequestJson() {
		return requestJson;
	}

	public void setRequestJson(JSONObject requestJson) {
		this.requestJson = requestJson;
	}

	public JSONObject getResponseJson() {
		return responseJson;
	}

	public void setResponseJson(JSONObject responseJson) {
		this.responseJson = responseJson;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	@Override
	public String toString() {
		return "SubwayStudyStudyLogDTO [logTypeCode=" + logTypeCode + ", uuId=" + uuId + ", pageUrl=" + pageUrl
				+ ", requestHeaderJson=" + requestHeaderJson + ", courseId=" + courseId + ", kngId=" + kngId + ", ext1="
				+ ext1 + ", ext2=" + ext2 + ", ext3=" + ext3 + ", ext4=" + ext4 + ", ext5=" + ext5 + ", requestMethod="
				+ requestMethod + ", requestJson=" + requestJson + ", responseJson=" + responseJson + ", requestUrl="
				+ requestUrl + "]";
	}

}
