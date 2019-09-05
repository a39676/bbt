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

	WebDriver buildChrome76WebDriver(ChromeOptions options);

	WebDriver buildChrome76WebDriver();

	WebDriver buildIEWebDriver();

	WebDriver buildChrome45WebDriver(ChromeOptions options);

	WebDriver buildChrome45WebDriver();

}
