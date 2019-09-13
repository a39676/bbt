package demo.selenium.service;

import org.openqa.selenium.WebDriver;

public interface JavaScriptService {

	void openNewTab(WebDriver d, String url);

	void openNewTab(WebDriver driver);

	String getHtmlSource(WebDriver driver);

}
