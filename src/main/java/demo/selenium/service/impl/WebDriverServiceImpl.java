package demo.selenium.service.impl;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.Map;

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
import org.openqa.selenium.remote.DesiredCapabilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import constant.HtmlMimeType;
import demo.baseCommon.service.CommonService;
import demo.selenium.pojo.constant.ChromeConstant;
import demo.selenium.pojo.constant.FireFoxConstant;
import demo.selenium.pojo.constant.WebDriverConstant;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.WebDriverService;

@Service
public class WebDriverServiceImpl extends CommonService implements WebDriverService {

	@Autowired
	private SeleniumGlobalOptionService globalOptionService;

	@Override
	public WebDriver buildFireFoxWebDriver(FirefoxOptions options) {
		String path = globalOptionService.getGeckoPath();
		String driverType = WebDriverConstant.geckoDriver;
		System.setProperty(driverType, path);
		if (options == null) {
			options = new FirefoxOptions();
			if (!"dev".equals(envName) || !isWindows()) {
				options.addArguments(WebDriverConstant.headLess);
			}
			options.addArguments(WebDriverConstant.headLess);
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
//			profile.setPreference(FireFoxConstant.browserDownloadUseDownloadDir, true);
			options.setProfile(profile);
		}

		FirefoxDriver driver = new FirefoxDriver(options);
		if ("dev".equals(envName) ) {
			Point p = new Point(0, 0);
			driver.manage().window().setPosition(p);
			Dimension targetSize = new Dimension(1440, 900);
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
	public WebDriver buildChrome76WebDriver(ChromeOptions options) {
		String path = globalOptionService.getChrome76Path();
		String driverType = WebDriverConstant.chromeDriver;
		System.setProperty(driverType, path);
		WebDriver driver = null;
		/*
		 * 2019-09-08
		 * I have no idea why 
		 * can NOT set any experimental option with this code
		 * every experimental option call "unrecognized chrome option" error
		if (options == null) {
			options = new ChromeOptions();
			if (!"dev".equals(envName)) {
				options.addArguments(WebDriverConstant.headLess);
			}
			options.setExperimentalOption(ChromeConstant.downloadDir, globalOptionService.getDownloadDir());
			options.setExperimentalOption(ChromeConstant.promptForDownload, false);
			options.setExperimentalOption(ChromeConstant.directoryUpgrade, true);
			options.setExperimentalOption(ChromeConstant.safebrowsingEnabled, true);
		}
		 */

		driver = buildChromeDriverBugFix(options);
		return driver;
	}

	@SuppressWarnings("deprecation")
	private ChromeDriver buildChromeDriverBugFix(ChromeOptions options) {
		if(options == null) {
			options = new ChromeOptions();
		}
		DesiredCapabilities capabilities = DesiredCapabilities.chrome();
		Map<String, String> prefs = new Hashtable<>();
		prefs.put(ChromeConstant.downloadDir, globalOptionService.getDownloadDir());
		prefs.put(ChromeConstant.promptForDownload, "false");
		prefs.put(ChromeConstant.directoryUpgrade, "true");
		prefs.put(ChromeConstant.safebrowsingEnabled, "false");
		prefs.put("profile.default_content_settings.popups", "0");
		String[] switches = { "--start-maximized", "--ignore-certificate-errors" };
		capabilities.setJavascriptEnabled(true);
		capabilities.setCapability("chrome.prefs", prefs);
		capabilities.setCapability("chrome.switches", Arrays.asList(switches));
		if (!"dev".equals(envName) || !isWindows()) {
			options.addArguments(WebDriverConstant.headLess);
		}
		capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		
		return new ChromeDriver(capabilities);
	}

	@Override
	public WebDriver buildChrome76WebDriver() {
		return buildChrome76WebDriver(null);
	}

	@Override
	public WebDriver buildChrome45WebDriver(ChromeOptions options) {
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

}
