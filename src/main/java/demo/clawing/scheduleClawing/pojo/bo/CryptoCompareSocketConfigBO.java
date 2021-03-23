package demo.clawing.scheduleClawing.pojo.bo;

import java.util.List;

public class CryptoCompareSocketConfigBO {

	private String apiKey;
	private String uri;
	private List<String> subs;

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public List<String> getSubs() {
		return subs;
	}

	public void setSubs(List<String> subs) {
		this.subs = subs;
	}

	@Override
	public String toString() {
		return "CryptoCompareSocketConfigBO [apiKey=" + apiKey + ", uri=" + uri + ", subs=" + subs + "]";
	}

}
