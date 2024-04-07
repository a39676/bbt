package demo.finance.cryptoCoin.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.finance.cryptoCoin.data.service.CryptoCoinComplexService;

@Component
public class CryptoTaskService {

	@Autowired
	private CryptoCoinComplexService cryptoCoinComplexService;

	@Scheduled(fixedDelay = 1000L * 30)
	public void deleteOldData() {
		cryptoCoinComplexService.deleteOld1MinKLineDatas();
	}

	@Scheduled(cron = "* */10 * * * *")
	public void checkBigMoveInMinutes() {
		cryptoCoinComplexService.checkBigMoveInMinutes();
	}

	@Scheduled(cron = "* */5 * * * *")
	public void checkBigMoveInHours() {
		cryptoCoinComplexService.checkBigMoveInHours();
	}

	@Scheduled(fixedDelay = 1000L * 30)
	public void checkBinanceKLineStreamAliveAndReconnect() {
		cryptoCoinComplexService.checkBinanceKLineStreamAliveAndReconnect();
	}

	@Scheduled(fixedDelay = 1000L * 60)
	public void getRecentBigMoveCounter() {
		cryptoCoinComplexService.getRecentBigMoveCounterBySymbol();
	}

	@Scheduled(fixedDelay = 1000L * 60 * 10)
	public void getCryptoCoinOptionFromCthulhu() {
		cryptoCoinComplexService.getCryptoCoinOptionFromCthulhu();
	}
}
