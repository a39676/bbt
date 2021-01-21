package demo.clawing.scheduleClawing.pojo.bo;

import java.util.List;

public class CryptoCompareSocketConfigBO {

	private String apiKey;
	private String uri;
	private List<String> targetCoins;
	private List<String> targetCurrency;

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

	public List<String> getTargetCoins() {
		return targetCoins;
	}

	public void setTargetCoins(List<String> targetCoins) {
		this.targetCoins = targetCoins;
	}

	public List<String> getTargetCurrency() {
		return targetCurrency;
	}

	public void setTargetCurrency(List<String> targetCurrency) {
		this.targetCurrency = targetCurrency;
	}

	@Override
	public String toString() {
		return "CryptoCompareSocketConfigBO [apiKey=" + apiKey + ", uri=" + uri + ", targetCoins=" + targetCoins
				+ ", targetCurrency=" + targetCurrency + "]";
	}

}
