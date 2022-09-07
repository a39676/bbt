package demo.scriptCore.scheduleClawing.cryptoCoin.service;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.scheduleClawing.cryptoCoin.pojo.result.CryptoCoinDailyDataResult;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;

public interface BinanceService {

	CryptoCoinDailyDataResult cryptoCoinDailyDataAPI(TestEventBO tbo, CryptoCoinDailyDataQueryDTO paramDTO);

}
