package demo.selenium.service.impl;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.springframework.stereotype.Service;

import demo.selenium.pojo.constant.ChromeConstant;
import demo.selenium.pojo.constant.FireFoxConstant;
import demo.selenium.pojo.constant.WebDriverGlobalOption;
import demo.selenium.pojo.type.BrowserConfigType;
import demo.selenium.service.WebDriverService;

@Service
public class WebDriverServiceImpl implements WebDriverService {

	@Override
	public WebDriver buildFireFoxWebDriver(FirefoxOptions options) {
		BrowserConfigType browserType = BrowserConfigType.gecko;
		System.setProperty(browserType.getDriver(), browserType.getPath());
		if (options == null) {
			options = new FirefoxOptions();
			options.addArguments(WebDriverGlobalOption.headLess);
		}

		if (options.getProfile() == null) {
			FirefoxProfile profile = new FirefoxProfile();
			profile.setPreference(FireFoxConstant.downloadDir, WebDriverGlobalOption.downloadDir);
			profile.setPreference(FireFoxConstant.folderList, 2);
			profile.setPreference(FireFoxConstant.downloadShowWhenStarting, false);
			profile.setPreference(FireFoxConstant.neverAskSaveToDisk, "text/csv");
			options.setProfile(profile);
		}

		WebDriver driver = new FirefoxDriver(options);

		return driver;
	}

	@Override
	public WebDriver buildFireFoxWebDriver() {
		return buildFireFoxWebDriver(null);
	}

	@Override
	public WebDriver buildEdgeWebDriver(EdgeOptions options) {
		BrowserConfigType browserType = BrowserConfigType.edge;
		System.setProperty(browserType.getDriver(), browserType.getPath());
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
	public WebDriver buildChromeWebDriver(ChromeOptions options) {
		BrowserConfigType browserType = BrowserConfigType.chrome;
		System.setProperty(browserType.getDriver(), browserType.getPath());
		WebDriver driver = null;
		if (options == null) {
			options = new ChromeOptions();
			options.addArguments(WebDriverGlobalOption.headLess);
			options.setExperimentalOption(ChromeConstant.downloadDir, WebDriverGlobalOption.downloadDir);
			options.setExperimentalOption(ChromeConstant.promptForDownload, false);
			options.setExperimentalOption(ChromeConstant.directoryUpgrade, true);
			options.setExperimentalOption(ChromeConstant.safebrowsingEnabled, true);
		}

		driver = new ChromeDriver(options);
		return driver;
	}

	@Override
	public WebDriver buildChromeWebDriver() {
		return buildChromeWebDriver(null);
	}
}
