package demo.selenium.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;

public interface WebDriverService {

	WebDriver buildFireFoxWebDriver(FirefoxOptions options);

	WebDriver buildFireFoxWebDriver();

	WebDriver buildEdgeWebDriver(EdgeOptions options);

	WebDriver buildEdgeWebDriver();

	WebDriver buildChromeWebDriver(ChromeOptions options);

	WebDriver buildChromeWebDriver();

	WebDriver buildIEWebDriver();

	WebDriver buildChrome45WebDriver(ChromeOptions options);

	WebDriver buildChrome45WebDriver();

	WebDriver buildOperaWebDriver();

}
