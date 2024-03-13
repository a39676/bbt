package demo.cryptoCoin.data.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.cryptoCoin.data.service.CryptoCoinComplexService;
import finance.common.pojo.type.IntervalType;
import finance.cryptoCoin.binance.pojo.dto.KLineKeyBO;
import finance.cryptoCoin.pojo.bo.CryptoCoinPriceCommonDataBO;

@Service
public class CryptoCoinComplexServiceImpl extends CommonService implements CryptoCoinComplexService {

	@Autowired
	private CryptoCoinCacheDataService cacheDataServcie;

	@Override
	public void deleteOldKLineDatas() {
		int oneMinDataListMaxSize = 30;
		Map<KLineKeyBO, List<CryptoCoinPriceCommonDataBO>> map = cacheDataServcie.getBinanceKLineCacheMap();
		for (KLineKeyBO key : map.keySet()) {
			if (IntervalType.MINUTE_1.name().equals(key.getInterval())) {
				List<CryptoCoinPriceCommonDataBO> list = map.get(key);
				while (list != null && list.size() > oneMinDataListMaxSize) {
					list.remove(0);
				}
			}
		}
	}
}
