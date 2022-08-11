package demo.selenium.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface JavaScriptService {

	void openNewTab(WebDriver d, String url);

	void openNewTab(WebDriver driver);

	String getHtmlSource(WebDriver driver);

	void scrollToBottom(WebDriver driver);

	void windowStop(WebDriver driver);

	void execute(WebDriver driver, String js);

	boolean isVisibleInViewport(WebDriver driver, WebElement element);

	/**
	 * if pix > 0 scroll down, else scroll up
	 * @param driver
	 * @param pix 
	 */
	void scroll(WebDriver driver, int pix);

}
