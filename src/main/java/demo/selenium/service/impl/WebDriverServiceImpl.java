package demo.selenium.service.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.webDriver.pojo.constant.ChromeConstant;
import at.webDriver.pojo.constant.FireFoxConstant;
import at.webDriver.pojo.constant.WebDriverConstant;
import demo.baseCommon.service.CommonService;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.WebDriverService;
import toolPack.constant.HtmlMimeType;

@Service
public class WebDriverServiceImpl extends CommonService implements WebDriverService {

	@Autowired
	private SeleniumGlobalOptionService globalOptionService;

	@Override
	public WebDriver buildFireFoxWebDriver(FirefoxOptions options) {
		String envName = systemConstantService.getEnvName();
		String path = globalOptionService.getGeckoPath();
		String driverType = WebDriverConstant.geckoDriver;
		System.setProperty(driverType, path);
		if (options == null) {
			options = new FirefoxOptions();
			if (!"dev".equals(envName) || !isWindows()) {
				options.addArguments(WebDriverConstant.headLess);
			}
//			options.addArguments(WebDriverConstant.headLess);
		}

		if (options.getProfile() == null) {
			FirefoxProfile profile = new FirefoxProfile();
//			File adblockExtension = new File("D:\\home\\u2\\extension\\adblock_plus-3.6.3-an_fx.xpi");
//			profile.addExtension(adblockExtension);
//			profile.setPreference("extensions.adblock_plus.currentVersion", "3.6.3");
//			profile.setPreference("extensions.adblock_plus.allPagesActivation", "on");    
//			profile.setPreference("permissions.default.stylesheet", 2);
//			profile.setPreference("dom.ipc.plugins.enabled.libflashplayer.so", false);
//			profile.setPreference("permissions.default.image", 2);
			profile.setPreference(FireFoxConstant.folderList, 2);
			profile.setPreference(FireFoxConstant.downloadDir, globalOptionService.getDownloadDir());
			profile.setPreference(FireFoxConstant.downloadShowWhenStarting, false);
			StringBuffer sb = new StringBuffer();
			for (HtmlMimeType i : HtmlMimeType.values()) {
				sb.append(i.getCode() + ",");
			}
			profile.setPreference(FireFoxConstant.neverAskSaveToDisk, sb.toString());
//			profile.setPreference(FireFoxConstant.browserDownloadUseDownloadDir, true);
			options.setProfile(profile);
		}

		FirefoxDriver driver = new FirefoxDriver(options);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		if ("dev".equals(envName)) {
//			Point p = new Point(0, 0);
//			driver.manage().window().setPosition(p);
//			Dimension targetSize = new Dimension(1024, 800);
//			driver.manage().window().setSize(targetSize);
			driver.manage().window().maximize();
		}
		return driver;
	}

	@Override
	public WebDriver buildFireFoxWebDriverMobileEmulation() {
		return buildFireFoxWebDriverMobileEmulation(null);
	}

	@Override
	public WebDriver buildFireFoxWebDriverMobileEmulation(FirefoxOptions options) {
		String envName = systemConstantService.getEnvName();
		String path = globalOptionService.getGeckoPath();
		String driverType = WebDriverConstant.geckoDriver;
		System.setProperty(driverType, path);
		if (options == null) {
			options = new FirefoxOptions();
			if (!"dev".equals(envName) || !isWindows()) {
				options.addArguments(WebDriverConstant.headLess);
			}
		}

		if (options.getProfile() == null) {
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference(FireFoxConstant.folderList, 2);
			profile.setPreference(FireFoxConstant.downloadDir, globalOptionService.getDownloadDir());
			profile.setPreference(FireFoxConstant.downloadShowWhenStarting, false);
			StringBuffer sb = new StringBuffer();
			for (HtmlMimeType i : HtmlMimeType.values()) {
				sb.append(i.getCode() + ",");
			}
			profile.setPreference(FireFoxConstant.neverAskSaveToDisk, sb.toString());
			options.setProfile(profile);
		}

		FirefoxDriver driver = new FirefoxDriver(options);
		driver.manage().timeouts().pageLoadTimeout(20, TimeUnit.SECONDS);
		if ("dev".equals(envName)) {
			Point p = new Point(0, 0);
			driver.manage().window().setPosition(p);
			Dimension targetSize = new Dimension(360, 1200);
			driver.manage().window().setSize(targetSize);
		}
		return driver;
	}

	@Override
	public WebDriver buildFireFoxWebDriver() {
		return buildFireFoxWebDriver(null);
	}

	@Override
	public WebDriver buildEdgeWebDriver(EdgeOptions options) {
		String path = globalOptionService.getEdgePath();
		String driverType = WebDriverConstant.edgeDriver;
		System.setProperty(driverType, path);
		if (options == null) {
			options = new EdgeOptions();
		}
		WebDriver driver = new EdgeDriver(options);
		return driver;
	}

	@Override
	public WebDriver buildEdgeWebDriver() {
		return buildEdgeWebDriver(null);
	}

	@Override
	public WebDriver buildChromeWebDriverMobileEmulation() {
		log.debug("build chrome driver with default options");
		return buildChromeWebDriverMobileEmulation(null);
	}

	@Override
	public WebDriver buildChromeWebDriverMobileEmulation(ChromeOptions options) {
		log.debug("building chrome driver");
		String path = globalOptionService.getChromePath();
		log.debug("chrome driver path: " + path);
		String driverType = WebDriverConstant.chromeDriver;
		System.setProperty(driverType, path);
		WebDriver driver = null;

		String envName = systemConstantService.getEnvName();
		log.debug("envName: " + envName);
		if (options == null) {
			options = new ChromeOptions();
		}

		if (!"dev".equals(envName)) {
			options.addArguments(WebDriverConstant.headLess);
		}

		Map<String, String> mobileEmulation = new HashMap<>();
		mobileEmulation.put("deviceName", "Nexus 5");
		options.setExperimentalOption("mobileEmulation", mobileEmulation);
		log.debug("before build chrome driver");
		driver = new ChromeDriver(options);
		log.debug("after build chrome driver");
		return driver;

	}

	@Override
	public WebDriver buildChromeWebDriver(ChromeOptions options) {
		log.debug("building chrome driver");
		String path = globalOptionService.getChromePath();
		log.debug("chrome driver path: " + path);
		String driverType = WebDriverConstant.chromeDriver;
		System.setProperty(driverType, path);
		WebDriver driver = null;

		String envName = systemConstantService.getEnvName();
		log.debug("envName: " + envName);
		if (options == null) {
			options = new ChromeOptions();
		}

		if (!"dev".equals(envName)) {
			options.addArguments(WebDriverConstant.headLess);
		}

		log.debug("before build chrome driver");
		driver = new ChromeDriver(options);
		log.debug("after build chrome driver");
		return driver;
	}


	@Override
	public WebDriver buildChromeWebDriver() {
		return buildChromeWebDriver(null);
	}

	@Override
	public WebDriver buildChrome45WebDriver(ChromeOptions options) {
		String envName = systemConstantService.getEnvName();
		String path = globalOptionService.getChrome45Path();
		String driverType = WebDriverConstant.chromeDriver;
		System.setProperty(driverType, path);
		WebDriver driver = null;
		if (options == null) {
			options = new ChromeOptions();
			if (!"dev".equals(envName) || !isWindows()) {
				options.addArguments(WebDriverConstant.headLess);
			}
			options.setExperimentalOption(ChromeConstant.downloadDir, globalOptionService.getDownloadDir())
					.setExperimentalOption(ChromeConstant.promptForDownload, false)
					.setExperimentalOption(ChromeConstant.directoryUpgrade, true)
					.setExperimentalOption(ChromeConstant.safebrowsingEnabled, true);
		}

		driver = new ChromeDriver(options);
		return driver;
	}

	@Override
	public WebDriver buildChrome45WebDriver() {
		return buildChrome45WebDriver(null);
	}

	@SuppressWarnings("deprecation")
	@Override
	public WebDriver buildIEWebDriver() {
		String path = globalOptionService.getIePath();
		String driverType = WebDriverConstant.ieDriver;
		System.setProperty(driverType, path);
		DesiredCapabilities ieCapabilities = DesiredCapabilities.internetExplorer();
		ieCapabilities.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
		WebDriver driver = new InternetExplorerDriver(ieCapabilities);

		return driver;
	}

	@Override
	public WebDriver buildOperaWebDriver() {
		String path = globalOptionService.getOperaPath();
		String driverType = WebDriverConstant.operaDriver;
		System.setProperty(driverType, path);
		WebDriver driver = new OperaDriver();
		return driver;
	}
}
