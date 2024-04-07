package demo.finance.cryptoCoin.data.service;

public interface CryptoCoinComplexService {

	void deleteOld1MinKLineDatas();

	void checkBigMoveInMinutes();

	void checkBinanceKLineStreamAliveAndReconnect();

	void getRecentBigMoveCounterBySymbol();

	void getCryptoCoinOptionFromCthulhu();

}
