package demo.config.costomComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import demo.baseCommon.service.CommonService;
import demo.finance.cryptoCoin.data.binance.BinanceWSClient2;
import demo.selenium.service.SeleniumGlobalOptionService;
import finance.common.pojo.type.IntervalType;

@Component
public class OnStartup extends CommonService implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private SeleniumGlobalOptionService globalOptionService;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		globalOptionService.getDownloadDir();
		globalOptionService.getScreenshotSavingFolder();

		if (isWindows()) {
			log.error("OS: Windows, will set proxy");
			String proxyHost = "127.0.0.1";
			String proxyPort = "10809";

			System.setProperty("http.proxyHost", proxyHost);
			System.setProperty("http.proxyPort", proxyPort);

			System.setProperty("https.proxyHost", proxyHost);
			System.setProperty("https.proxyPort", proxyPort);

			log.error("Had set proxy");
		}
	}
	
	// TODO for test
	@Autowired
	private BinanceWSClient2 binanceWSClient;
	
	public void forTest() {
		binanceWSClient.addNewKLineSubcript("BTCUSDT", IntervalType.MINUTE_1.getName());
		binanceWSClient.addNewKLineSubcript("ETHUSDT", IntervalType.MINUTE_1.getName());
		binanceWSClient.addNewKLineSubcript("DOGEUSDT", IntervalType.MINUTE_1.getName());
	}

}
