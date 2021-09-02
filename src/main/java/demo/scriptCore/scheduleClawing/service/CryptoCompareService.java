package demo.scriptCore.scheduleClawing.service;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.scheduleClawing.pojo.result.CryptoCoinDailyDataResult;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;

public interface CryptoCompareService {

	/**
	 * 
	 * 每次调用, 仅获取一个币种法币对的数据
	 * @param te
	 * @param reportDTO
	 * @return
	 */
	CryptoCoinDailyDataResult cryptoCoinDailyDataAPI(TestEventBO tbo, CryptoCoinDailyDataQueryDTO paramDTO);

}
