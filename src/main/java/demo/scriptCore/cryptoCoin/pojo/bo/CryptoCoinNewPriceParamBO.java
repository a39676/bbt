package demo.scriptCore.cryptoCoin.pojo.bo;

import java.util.List;

public class CryptoCoinNewPriceParamBO {

	private List<String> coinType;
	private List<String> currency;

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

	@Override
	public String toString() {
		return "CryptoCoinNewPriceParamBO [coinType=" + coinType + ", currency=" + currency + "]";
	}

}
