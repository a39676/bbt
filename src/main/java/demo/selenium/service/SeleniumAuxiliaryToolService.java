package demo.selenium.service;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface SeleniumAuxiliaryToolService {

	/**
	 * 等待, 直至页面出现指定元素
	 * @param driver
	 * @param locator
	 * @return
	 */
	WebElement fluentWait(WebDriver driver, By locator);

	String findFileNameFromUrl(String url);

	String findFileNameSuffixFromUrl(String str);

}
