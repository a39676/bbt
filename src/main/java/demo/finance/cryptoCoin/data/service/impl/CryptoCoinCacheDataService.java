package demo.finance.cryptoCoin.data.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import finance.cryptoCoin.binance.spot.pojo.dto.CryptoCoinBinanceSpotDepthCompleteDTO;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;
import finance.cryptoCoin.pojo.bo.KLineKeyBO;

@Scope("singleton")
@Service
public class CryptoCoinCacheDataService extends CommonService {

	private Map<String, CryptoCoinBinanceSpotDepthCompleteDTO> binanceDepthMap = Collections.synchronizedMap(new HashMap<>());
	private Map<KLineKeyBO, List<CryptoCoinPriceCommonDataBO>> binanceKLineCacheMap = Collections
			.synchronizedMap(new HashMap<>());

	public Map<String, CryptoCoinBinanceSpotDepthCompleteDTO> getBinanceDepthMap() {
		return binanceDepthMap;
	}

	public void setBinanceDepthMap(Map<String, CryptoCoinBinanceSpotDepthCompleteDTO> binanceDepthMap) {
		this.binanceDepthMap = binanceDepthMap;
	}

	public Map<KLineKeyBO, List<CryptoCoinPriceCommonDataBO>> getBinanceKLineCacheMap() {
		return binanceKLineCacheMap;
	}

	public void setBinanceKLineCacheMap(Map<KLineKeyBO, List<CryptoCoinPriceCommonDataBO>> binanceKLineCacheMap) {
		this.binanceKLineCacheMap = binanceKLineCacheMap;
	}

}
