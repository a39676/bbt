package demo.finance.cryptoCoin.common.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.baseCommon.service.CommonService;
import demo.config.costomComponent.OptionFilePathConfigurer;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class CryptoCoinOptionService extends CommonService {

	private String defaultCurrency = "USDT";
	private String binanceApiKey;
	private String binanceSecretKey;
	private List<String> binanceKLineSubscriptionSymbolSet = new ArrayList<>();
	private Double bigMoveIn1min = 1D;
	private Double bigMoveIn5min = 3D;
	private Double bigMoveIn10min = 5D;

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.CRYPTO_COIN);
		if (!optionFile.exists()) {
			return;
		}
		FileUtilCustom fileUtil = new FileUtilCustom();
		String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.CRYPTO_COIN);
		refreshOption(jsonStr);
	}

	public void refreshOption(String jsonStr) {
		try {
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

	public List<String> getBinanceKLineSubscriptionSymbolSet() {
		return binanceKLineSubscriptionSymbolSet;
	}

	public void setBinanceKLineSubscriptionSymbolSet(List<String> binanceKLineSubscriptionSymbolSet) {
		this.binanceKLineSubscriptionSymbolSet = binanceKLineSubscriptionSymbolSet;
	}

	public Double getBigMoveIn1min() {
		return bigMoveIn1min;
	}

	public void setBigMoveIn1min(Double bigMoveIn1min) {
		this.bigMoveIn1min = bigMoveIn1min;
	}

	public Double getBigMoveIn5min() {
		return bigMoveIn5min;
	}

	public void setBigMoveIn5min(Double bigMoveIn5min) {
		this.bigMoveIn5min = bigMoveIn5min;
	}

	public Double getBigMoveIn10min() {
		return bigMoveIn10min;
	}

	public void setBigMoveIn10min(Double bigMoveIn10min) {
		this.bigMoveIn10min = bigMoveIn10min;
	}

	@Override
	public String toString() {
		return "CryptoCoinOptionService [defaultCurrency=" + defaultCurrency + ", binanceApiKey=" + binanceApiKey
				+ ", binanceSecretKey=" + binanceSecretKey + ", binanceKLineSubscriptionSymbolSet="
				+ binanceKLineSubscriptionSymbolSet + ", bigMoveIn1min=" + bigMoveIn1min + ", bigMoveIn5min="
				+ bigMoveIn5min + ", bigMoveIn10min=" + bigMoveIn10min + "]";
	}

}
