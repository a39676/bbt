package demo.scriptCore.scheduleClawing.service;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.scheduleClawing.pojo.result.CryptoCoinDailyDataResult;
import finance.cryptoCoin.pojo.dto.CryptoCoinDailyDataQueryDTO;

public interface BinanceService {

	CryptoCoinDailyDataResult cryptoCoinDailyDataAPI(TestEventBO tbo, CryptoCoinDailyDataQueryDTO paramDTO);

}
