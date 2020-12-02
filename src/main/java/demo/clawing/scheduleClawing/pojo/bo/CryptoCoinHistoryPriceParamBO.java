package demo.clawing.scheduleClawing.pojo.bo;

import java.util.List;

public class CryptoCoinHistoryPriceParamBO {

	private List<String> coinType;
	private List<String> currency;
	private Integer limit;
	private String apiKey;

	public List<String> getCoinType() {
		return coinType;
	}

	public void setCoinType(List<String> coinType) {
		this.coinType = coinType;
	}

	public List<String> getCurrency() {
		return currency;
	}

	public void setCurrency(List<String> currency) {
		this.currency = currency;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String toString() {
		return "CryptoCoinHistoryPriceParamBO [coinType=" + coinType + ", currency=" + currency + ", limit=" + limit
				+ ", apiKey=" + apiKey + "]";
	}

}
