package demo.task.service.impl;

import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import demo.clawing.scheduleClawing.service.CryptoCoinPriceService;
import demo.clawing.scheduleClawing.service.WuYiJobRefreshService;
import demo.clawing.scheduleClawing.service.component.webSocket.BinanceWSClient;
import demo.clawing.scheduleClawing.service.component.webSocket.CryptoCompareWSClient;

@Component
public class ScheduleTaskServiceImpl extends SeleniumTaskCommonServiceImpl {

	@Autowired
	private WuYiJobRefreshService wuyiService;
//	@Autowired
//	private PreciousMetalsPriceService preciousMetalsPriceService;
	@Autowired
	private CryptoCoinPriceService cryptoCoinPriceService;
	@Autowired
	private CryptoCompareWSClient cryptoCompareWSClient;
	@Autowired
	private BinanceWSClient binanceWSClient;

//	@Scheduled(fixedRate = 1000L * 60 * 5)
	@Scheduled(cron = "0 */5 * * * ?")
	public void insertWuYiRefresh() {
		if (!"dev".equals(constantService.getValByName("envName"))) {
			LocalDateTime now = LocalDateTime.now();
			if (now.getHour() >= 8 && now.getHour() <= 22) {
				wuyiService.insertClawingEvent();
			} else {
				if (ThreadLocalRandom.current().nextInt(1, 4) <= 1) {
					wuyiService.insertClawingEvent();
				}
			}
		}
	}

//	@Scheduled(cron = "*/60 * * * * ?")
//	public void insertCryptoCoinNewPriceCollect() {
//		if (!"dev".equals(constantService.getValByName("envName"))) {
//			cryptoCoinPriceService.insertNewCryptoCoinPriceEvent();
//		}
//	}

	@Scheduled(cron = "0 */5 * * * ?")
	public void insertCryptoCoinMinuteDataCollect() {
		if (!"dev".equals(constantService.getValByName("envName"))) {
			cryptoCoinPriceService.insertCryptoCoinMinuteDataCollectEvent();
		}
	}

//	@Scheduled(cron = "1 3 0 * * *") // 每天00:03:01执行
//	public void insertCryptoCoinDailyDataCollect() {
//		if (!"dev".equals(constantService.getValByName("envName"))) {
//			cryptoCoinPriceService.insertCryptoCoinDailyDataCollectEvent();
//		}
//	}

	@Scheduled(cron = "*/31 * * * * ?")
	public void checkCryptoCompareWebSocket() {
		if (!"dev".equals(constantService.getValByName("envName"))) {
			if (!cryptoCompareWSClient.getSocketLiveFlag()) {
				log.error("crypto compare web socket disconnected");
				cryptoCompareWSClient.startWebSocket();
			}
		}
	}
	
	@Scheduled(cron = "*/10 * * * * ?")
	public void checkBinanceWebSocket() {
		if (!"dev".equals(constantService.getValByName("envName"))) {
			if (!binanceWSClient.getSocketLiveFlag()) {
				log.error("binance web socket disconnected");
				binanceWSClient.startWebSocket();
			}
		}
	}
	
	@Scheduled(cron="0 0 0 * * *")
	@Scheduled(cron="0 0 8 * * *")
	@Scheduled(cron="0 0 16 * * *")
	public void syncCryptoCoinWebSocket() {
		if (!"dev".equals(constantService.getValByName("envName"))) {
			if (!binanceWSClient.getSocketLiveFlag()) {
				cryptoCompareWSClient.syncSubscription();
				binanceWSClient.wsDestory();
			}
		}
	}

//	@Scheduled(cron = "*/30 * * * * ?")
//	public void insertMetalsPriceClaw() {
//		/*
//		 * TODO
//		 * 2020-07-16
//		 * 大概率损失 每周 开盘 & 收盘价, 
//		 * 交给cx 通过api 加入任务?
//		 * 需要设法捕捉
//		 */
//		if (!"dev".equals(constantService.getValByName("envName"))
//				&& isPreciousMetalsTransactionTime()) {
//			preciousMetalsPriceService.insertClawingEvent();
//		}
//	}
//	
//	/*
//	 * TODO
//	 * 2020-07-16
//	 * 需要解耦
//	 * 以下"时间" 统一为北京时间
//	 * 
//	 * 1. 输入时间, 判断是否交易时间
//	 * 2. 交易时间, 
//	 */
//	private boolean isPreciousMetalsTransactionTime() {
//		LocalDateTime beiJingNow = LocalDateTime.now();
//		LocalDateTime washtonNow = beiJingNow.minusHours(12);
//		int dayOfWeek = beiJingNow.getDayOfWeek().getValue();
//		
//		/*
//		 * 2020-07-06
//		 * 理论上美国黄金交易时间为: 
//		 * (北京时间) 周一06:60 ~ 周六 03:30
//		 * 冬令时期间 延后半小时
//		 */
//		boolean isUSWinterTime = localDateTimeHandler.isUSWinterTime(washtonNow.toLocalDate());
//		if(isUSWinterTime) {
//			beiJingNow = beiJingNow.minusMinutes(30);
//		}
//		
//		if (dayOfWeek == 1) {
//			return (beiJingNow.getHour() >= 6 && beiJingNow.getMinute() >= 30);
//		} else if (dayOfWeek > 1 && dayOfWeek < 6) {
//			return true;
//		} else if (dayOfWeek == 6) {
//			return (beiJingNow.getHour() <= 3 && beiJingNow.getMinute() <= 30);
//		} else if (dayOfWeek > 6) {
//			return false;
//		}
//
//		return false;
//	}

//	@Autowired
//	private MaiMaiScheduleClawingServiceImpl maiMaiLocalClawingServiceImpl;
//	@Scheduled(cron="0 */21 * * * ?")
//	public void insertMaiMai() {
//		if(!"dev".equals(constantService.getValByName("envName"))) {
//			maiMaiLocalClawingServiceImpl.insertClawingEvent();
//		}
//	}
}
