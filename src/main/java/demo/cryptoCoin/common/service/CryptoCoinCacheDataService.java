package demo.cryptoCoin.common.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import finance.cryptoCoin.binance.pojo.dto.DepthCompleteDTO;

@Scope("singleton")
@Service
public class CryptoCoinCacheDataService extends CommonService {

	private Map<String, DepthCompleteDTO> binanceDepthMap = Collections.synchronizedMap(new HashMap<>());

	public Map<String, DepthCompleteDTO> getBinanceDepthMap() {
		return binanceDepthMap;
	}

	public void setBinanceDepthMap(Map<String, DepthCompleteDTO> binanceDepthMap) {
		this.binanceDepthMap = binanceDepthMap;
	}

}
