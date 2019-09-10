package demo.selenium.service;

import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.pojo.po.TestEvent;
import demo.selenium.pojo.result.ScreenshotSaveResult;

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

	void tabSwitch(WebDriver driver, Integer tabIndex);

	void dragAndDrop(WebDriver driver, WebElement sourceElement, WebElement targetElement);

	ScreenshotSaveResult takeScreenshot(WebDriver driver, TestEvent testEvent, String fileName);

	ScreenshotSaveResult takeScreenshot(WebDriver driver, TestEvent testEvent);

	ScreenshotSaveResult takeElementScreenshot(WebDriver driver, TestEvent testEvent, By by, String fileName)
			throws IOException;

	ScreenshotSaveResult takeElementScreenshot(WebDriver driver, TestEvent testEvent, By by) throws IOException;

	By byXpathBuilder(ByXpathConditionBO bo);

	String saveImg(WebElement ele, String folderPath) throws IOException;

}
