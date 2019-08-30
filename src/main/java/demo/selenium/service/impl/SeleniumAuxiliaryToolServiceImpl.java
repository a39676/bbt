package demo.selenium.service.impl;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.selenium.pojo.constant.RegexConstant;
import demo.selenium.pojo.constant.WebDriverConstant;
import demo.selenium.service.SeleniumAuxiliaryToolService;

@Service
public class SeleniumAuxiliaryToolServiceImpl extends CommonService implements SeleniumAuxiliaryToolService {

	public void saveImageFromWebElement(WebElement webElement, String fileName) {
		/*
		 * TODO testing
		 */
//		String src = webElement.getAttribute("src");
//		\.\w{3,4}($|\?)

//		String suffix = 
//		ImageIO.write(im, formatName, output);
	}

	@Override
	public WebElement fluentWait(WebDriver driver, final By locator) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(WebDriverConstant.pageWaitingTimeoutSecond, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(locator);
			}
		});

		return foo;
	};

	@Override
	public String findFileNameFromUrl(String url) {
		Pattern fileNamePattern = Pattern.compile(RegexConstant.fileNameRegex);
		Matcher fileNameMatcher = fileNamePattern.matcher(url);

		if (fileNameMatcher.find()) {
			return fileNameMatcher.group(0);
		} else {
			return null;
		}
	}

	@Override
	public String findFileNameSuffixFromUrl(String str) {
		Pattern fileNameSuffixPattern = Pattern.compile(RegexConstant.fileNameSuffixRegex);
		Matcher fileNameSuffixMatcher = fileNameSuffixPattern.matcher(str);

		if (fileNameSuffixMatcher.find()) {
			if (fileNameSuffixMatcher.groupCount() > 0) {
				String s = fileNameSuffixMatcher.group(0);
				return s.substring(0, s.length());
			}
		}
		return null;
	}

	@Override
	public void tabSwitch(WebDriver driver, Integer tabIndex) {
		if (tabIndex == null || tabIndex < 0) {
			tabIndex = 0;
		}

		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());

		if (tabIndex > tabs.size() - 1) {
			tabIndex = tabs.size() - 1;
		}

		driver.switchTo().window(tabs.get(tabIndex));
	}

	public void closeTab(WebDriver driver, Integer closeTabIndex) {
//		TODO
		/*
		 * 待完善指定标签关闭
		 */
		driver.close();
	}

	@Override
	public void dragAndDrop(WebDriver driver, WebElement sourceElement, WebElement targetElement) {
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(sourceElement)
				.moveToElement(targetElement)
				.release(targetElement)
				.build();
		dragAndDrop.perform();
	}
	
}
