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
		cryptoCoinComplexService.deleteOldKLineDatas();
	}

	@Scheduled(cron="59 * * * * *") 
	public void checkBigMoveInMinutes() {
		cryptoCoinComplexService.checkBigMoveInMinutes();
	}
	
	@Scheduled(fixedDelay = 1000L * 30)
	public void checkBinanceKLineStreamAliveAndReconnect() {
		cryptoCoinComplexService.checkBinanceKLineStreamAliveAndReconnect();
	}
	
	@Scheduled(fixedDelay = 1000L * 60)
	public void getRecentBigMoveCounter() {
		cryptoCoinComplexService.getRecentBigMoveCounter();
	}
	
	@Scheduled(fixedDelay = 1000L * 60 * 10)
	public void getCryptoCoinOptionFromCthulhu() {
		cryptoCoinComplexService.getCryptoCoinOptionFromCthulhu();
	}
}
