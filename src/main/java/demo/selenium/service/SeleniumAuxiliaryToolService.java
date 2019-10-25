package demo.selenium.service;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import demo.selenium.pojo.result.ScreenshotSaveResult;
import demo.testCase.pojo.po.TestEvent;

public interface SeleniumAuxiliaryToolService {

	/**
	 * 等待, 直至页面出现指定元素
	 * 
	 * @param driver
	 * @param locator
	 * @return
	 */
	WebElement fluentWait(WebDriver driver, By locator);

	String findFileNameFromUrl(String url);

	String findFileNameSuffixFromUrl(String str);

	void tabSwitch(WebDriver driver, Integer tabIndex);

	void dragAndDrop(WebDriver driver, WebElement sourceElement, WebElement targetElement);

	ScreenshotSaveResult takeScreenshot(WebDriver driver, TestEvent testEvent, String fileName);

	ScreenshotSaveResult takeScreenshot(WebDriver driver, TestEvent testEvent);

	ScreenshotSaveResult takeElementScreenshot(WebDriver driver, WebElement ele, TestEvent testEvent, String fileName)
			throws IOException;

	ScreenshotSaveResult takeElementScreenshot(WebDriver driver, WebElement targetElement, TestEvent testEvent) throws IOException;

	ScreenshotSaveResult takeCaptchaElementScreenshot(WebDriver driver, TestEvent testEvent, WebElement ele,
			String fileName) throws IOException;

	String saveImg(WebElement ele, String folderPath) throws IOException;

	String captchaHandle(WebDriver d, WebElement captchaCodeElement, TestEvent te, boolean numberAndLetterOnly)
			throws IOException;

	String captchaHandle(WebDriver d, WebElement captchaCodeElement, TestEvent te) throws IOException;
}
