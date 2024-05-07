package demo.finance.cryptoCoin.common.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

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
	private List<String> symbolList = new ArrayList<>();
	private Integer dailyDataQueryInOneTime = 5;

	public String getDefaultCurrency() {
		return defaultCurrency;
	}

	public void setDefaultCurrency(String defaultCurrency) {
		this.defaultCurrency = defaultCurrency;
	}

	public List<String> getSymbolList() {
		return symbolList;
	}

	public void setSymbolList(List<String> symbolList) {
		this.symbolList = symbolList;
	}

	public Integer getDailyDataQueryInOneTime() {
		return dailyDataQueryInOneTime;
	}

	public void setDailyDataQueryInOneTime(Integer dailyDataQueryInOneTime) {
		this.dailyDataQueryInOneTime = dailyDataQueryInOneTime;
	}

	@Override
	public String toString() {
		return "CryptoCoinOptionService [defaultCurrency=" + defaultCurrency + ", symbolList=" + symbolList
				+ ", dailyDataQueryInOneTime=" + dailyDataQueryInOneTime + "]";
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
