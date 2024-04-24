package demo.scriptCore.scheduleClawing.cnStockMarketData.service.impl;

import java.io.File;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import demo.baseCommon.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Component
public class CnStockMarkctOptionComponent extends CommonService {

	private List<String> watchingCode;

	public List<String> getWatchingCode() {
		return watchingCode;
	}

	public void setWatchingCode(List<String> watchingCode) {
		this.watchingCode = watchingCode;
	}

	@Override
	public String toString() {
		return "CnStockMarkctOptionComponent [watchingCode=" + watchingCode + "]";
	}

	@PostConstruct
	public void refreshOption() {
		File optionFile = new File(OptionFilePathConfigurer.CN_STOCK_MARKET);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.CN_STOCK_MARKET);
			CnStockMarkctOptionComponent tmp = new Gson().fromJson(jsonStr, CnStockMarkctOptionComponent.class);
			BeanUtils.copyProperties(tmp, this);
			log.error("CN stock market option loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("CN stock market option loading error: " + e.getLocalizedMessage());
		}
	}

}
