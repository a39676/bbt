package demo.config.customComponent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import demo.baseCommon.service.CommonService;
import demo.selenium.service.SeleniumGlobalOptionService;

@Component
public class OnStartup extends CommonService implements ApplicationListener<ApplicationReadyEvent> {

	@Autowired
	private SeleniumGlobalOptionService globalOptionService;

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
		globalOptionService.getDownloadDir();
		globalOptionService.getScreenshotSavingFolder();

		String os = System.getProperty("os.name");
		log.error("OS name: " + os);
		if (isWindows()) {
			log.error("OS: Windows, will set proxy");
			String proxyHost = "127.0.0.1";
			String proxyPort = "10809";

			System.setProperty("http.proxyHost", proxyHost);
			System.setProperty("http.proxyPort", proxyPort);

			System.setProperty("https.proxyHost", proxyHost);
			System.setProperty("https.proxyPort", proxyPort);

			log.error("Had set proxy");
		} else {
			log.error("Set proxy");
			String proxyHost = "127.0.0.1";
			String proxyPort = "2081";

			System.setProperty("http.proxyHost", proxyHost);
			System.setProperty("http.proxyPort", proxyPort);

			System.setProperty("https.proxyHost", proxyHost);
			System.setProperty("https.proxyPort", proxyPort);

			log.error("Had set proxy");
		}
	}
}
