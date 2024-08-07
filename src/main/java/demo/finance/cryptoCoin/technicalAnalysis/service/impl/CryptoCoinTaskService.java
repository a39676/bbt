package demo.finance.cryptoCoin.technicalAnalysis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.finance.cryptoCoin.data.service.CryptoCoinComplexService;

@Component
public class CryptoCoinTaskService {

	@Autowired
	private CryptoCoinComplexService cryptoCoinComplexService;

//	@Scheduled(cron = "*/12 0-59 03-04 * * *")
	@Scheduled(cron = "12 20 2 * * *")
	public void sendDailyDataQueryInSteps() {
		cryptoCoinComplexService.sendDailyDataQuerys();
	}

}
