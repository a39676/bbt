package demo.finance.cryptoCoin.data.service;

import demo.finance.cryptoCoin.data.pojo.po.CryptoCoinMaxVolume;

public interface CryptoCoinComplexService {

	void sendDailyDataQueryInSteps();

	void sendDailyDataQuery(String symbol);

	void checkMaxVolume();

	CryptoCoinMaxVolume refreshMaxVolumeRecord(Long coinTypeId);

}
