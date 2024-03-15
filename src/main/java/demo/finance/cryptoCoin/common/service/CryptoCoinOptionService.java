package demo.finance.cryptoCoin.common.service;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.baseCommon.service.CommonService;
import demo.config.costomComponent.OptionFilePathConfigurer;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class CryptoCoinOptionService extends CommonService {

	private String defaultCurrency = "USDT";
	private String binanceApiKey;
	private String binanceSecretKey;
	private Set<String> subscriptionSet = new HashSet<>();

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.CRYPTO_COIN);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.CRYPTO_COIN);
			CryptoCoinOptionService tmp = new Gson().fromJson(jsonStr, CryptoCoinOptionService.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("crypto coin option loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("crypto coin option loading error: " + e.getLocalizedMessage());
		}
	}

	public String getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public String getBinanceApiKey() {
		return binanceApiKey;
	}

	public void setBinanceApiKey(String binanceApiKey) {
		this.binanceApiKey = binanceApiKey;
	}

	public String getBinanceSecretKey() {
		return binanceSecretKey;
	}

	public void setBinanceSecretKey(String binanceSecretKey) {
		this.binanceSecretKey = binanceSecretKey;
	}

	public Set<String> getSubscriptionSet() {
		return subscriptionSet;
	}

	public void setSubscriptionSet(Set<String> subscriptionSet) {
		this.subscriptionSet = subscriptionSet;
	}

	@Override
	public String toString() {
		return "CryptoCoinOptionService [defaultCurrency=" + defaultCurrency + ", binanceApiKey=" + binanceApiKey
				+ ", binanceSecretKey=" + binanceSecretKey + ", subscriptionSet=" + subscriptionSet + "]";
	}

}
