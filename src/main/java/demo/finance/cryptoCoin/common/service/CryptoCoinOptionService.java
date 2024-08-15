package demo.finance.cryptoCoin.common.service;

import java.io.File;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import finance.cryptoCoin.pojo.type.CurrencyTypeForCryptoCoin;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class CryptoCoinOptionService extends CommonService {

	private String defaultCurrency = CurrencyTypeForCryptoCoin.USDT.getName();
	private Integer dailyDataQueryInOneTime = 5;
	private String ccmHost;

	public String getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public Integer getDailyDataQueryInOneTime() {
		return dailyDataQueryInOneTime;
	}

	public void setDailyDataQueryInOneTime(Integer dailyDataQueryInOneTime) {
		this.dailyDataQueryInOneTime = dailyDataQueryInOneTime;
	}

	public String getCcmHost() {
		return ccmHost;
	}

	public void setCcmHost(String ccmHost) {
		this.ccmHost = ccmHost;
	}

	@Override
	public String toString() {
		return "CryptoCoinOptionService [defaultCurrency=" + defaultCurrency + ", dailyDataQueryInOneTime="
				+ dailyDataQueryInOneTime + ", ccmHost=" + ccmHost + "]";
	}

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

	private void refreshOption(String jsonStr) {
		try {
			CryptoCoinOptionService tmp = buildObjFromJsonCustomization(jsonStr, this.getClass());
			BeanUtils.copyProperties(tmp, this);
			log.error("crypto coin option loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("crypto coin option loading error: " + e.getLocalizedMessage());
		}
	}

}
