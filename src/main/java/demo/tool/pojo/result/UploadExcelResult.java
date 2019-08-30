package demo.tool.pojo.result;

import demo.baseCommon.pojo.result.CommonResult;

public class UploadExcelResult extends CommonResult {

	private String pk;

	private String url;

	private String uri;

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	@Override
	public String toString() {
		return "UploadExcelResult [pk=" + pk + ", url=" + url + ", uri=" + uri + "]";
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPk() {
		return pk;
	}

	public void setPk(String pk) {
		this.pk = pk;
	}

}
