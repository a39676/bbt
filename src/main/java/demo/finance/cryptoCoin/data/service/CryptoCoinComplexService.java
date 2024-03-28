package demo.finance.cryptoCoin.data.service;

public interface CryptoCoinComplexService {

	void deleteOldKLineDatas();

	void checkBigMoveInMinutes();

	void checkBinanceKLineStreamAliveAndReconnect();

	void getRecentBigMoveCounterBySymbol();

	void getCryptoCoinOptionFromCthulhu();

}
