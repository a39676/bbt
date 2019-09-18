package demo.selenium.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import constant.FileSuffixNameConstant;
import demo.baseCommon.service.CommonService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.pojo.bo.ByXpathConditionSubBO;
import demo.selenium.pojo.constant.RegexConstant;
import demo.selenium.pojo.constant.WebDriverConstant;
import demo.selenium.pojo.dto.ScreenshotSaveDTO;
import demo.selenium.pojo.result.ScreenshotSaveResult;
import demo.selenium.service.ScreenshotService;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.testCase.pojo.po.TestEvent;

@Service
public class SeleniumAuxiliaryToolServiceImpl extends CommonService implements SeleniumAuxiliaryToolService {

	@Autowired
	private ScreenshotService screenshotService;

	@Override
	public WebElement fluentWait(WebDriver driver, final By by) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(WebDriverConstant.pageWaitingTimeoutSecond, TimeUnit.SECONDS)
				.pollingEvery(5, TimeUnit.SECONDS).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(by);
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
		// TODO
		/*
		 * 待完善指定标签关闭
		 */
		driver.close();
	}

	@Override
	public void dragAndDrop(WebDriver driver, WebElement sourceElement, WebElement targetElement) {
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(sourceElement).moveToElement(targetElement).release(targetElement)
				.build();
		dragAndDrop.perform();
	}

	@Override
	public ScreenshotSaveResult takeScreenshot(WebDriver driver, TestEvent testEvent, String fileName) {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		ScreenshotSaveDTO screenshotSaveDto = new ScreenshotSaveDTO();
		screenshotSaveDto.setScreenShotFile(scrFile);
		screenshotSaveDto.setEventId(testEvent.getId());
		screenshotSaveDto.setEventName(testEvent.getEventName());
		if (StringUtils.isBlank(fileName)) {
			screenshotSaveDto.setFileName(testEvent.getEventName());
		} else {
			screenshotSaveDto.setFileName(fileName);
		}
		ScreenshotSaveResult r = screenshotService.screenshotSave(screenshotSaveDto);
		return r;
	}

	@Override
	public ScreenshotSaveResult takeScreenshot(WebDriver driver, TestEvent testEvent) {
		return takeScreenshot(driver, testEvent, null);
	}

	@Override
	public ScreenshotSaveResult takeElementScreenshot(WebDriver driver, TestEvent testEvent, By by, String fileName)
			throws IOException {
		// Get entire page screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(screenshot);

		WebElement recaptcha = driver.findElement(by);

		Point point = recaptcha.getLocation();

		// Get width and height of the element
		int eleWidth = recaptcha.getSize().getWidth();
		int eleHeight = recaptcha.getSize().getHeight();

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, FileSuffixNameConstant.png, screenshot);

		ScreenshotSaveDTO screenshotSaveDto = new ScreenshotSaveDTO();
		screenshotSaveDto.setScreenShotFile(screenshot);
		screenshotSaveDto.setEventId(testEvent.getId());
		screenshotSaveDto.setEventName(testEvent.getEventName());
		if (StringUtils.isBlank(fileName)) {
			screenshotSaveDto.setFileName(testEvent.getEventName());
		} else {
			screenshotSaveDto.setFileName(fileName);
		}
		ScreenshotSaveResult r = screenshotService.screenshotSave(screenshotSaveDto);
		return r;
	}
	
	@Override
	public ScreenshotSaveResult takeElementScreenshot(WebDriver driver, TestEvent testEvent, WebElement ele, String fileName)
			throws IOException {
		// Get entire page screenshot
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		BufferedImage fullImg = ImageIO.read(screenshot);

		Point point = ele.getLocation();

		// Get width and height of the element
		int eleWidth = ele.getSize().getWidth();
		int eleHeight = ele.getSize().getHeight();

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot = fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, FileSuffixNameConstant.png, screenshot);

		ScreenshotSaveDTO screenshotSaveDto = new ScreenshotSaveDTO();
		screenshotSaveDto.setScreenShotFile(screenshot);
		screenshotSaveDto.setEventId(testEvent.getId());
		screenshotSaveDto.setEventName(testEvent.getEventName());
		if (StringUtils.isBlank(fileName)) {
			screenshotSaveDto.setFileName(testEvent.getEventName());
		} else {
			screenshotSaveDto.setFileName(fileName);
		}
		ScreenshotSaveResult r = screenshotService.screenshotSave(screenshotSaveDto);
		return r;
	}

	@Override
	public ScreenshotSaveResult takeElementScreenshot(WebDriver driver, TestEvent testEvent, By by) throws IOException {
		return takeElementScreenshot(driver, testEvent, by, null);
	}

	@Override
	public By byXpathBuilder(ByXpathConditionBO bo) {
		StringBuffer s = new StringBuffer("//" + bo.getTagName() + "[");
		if (bo.getKvList() == null || bo.getKvList().size() < 1) {
			s.append("]");
			return By.xpath(s.toString());
		}
		ByXpathConditionSubBO b = null;
		for (int i = 0; i < bo.getKvList().size(); i++) {
			b = bo.getKvList().get(i);
			s.append("(@" + b.getKey() + "='" + b.getValue() + "')");
			if (i < bo.getKvList().size() - 1) {
				s.append(" and ");
			}
		}
		s.append("]");
		return By.xpath(s.toString());
	}

	@Override
	public String saveImg(WebElement ele, String folderPath) throws IOException {
		if (ele == null) {
			return null;
		}
		String src = ele.getAttribute("src");
		if (StringUtils.isBlank(src)) {
			return null;
		}

		URL imageURL = new URL(src);
		BufferedImage saveImage = ImageIO.read(imageURL);
		String fileName = findFileNameFromUrl(src);
		String suffix = findFileNameSuffixFromUrl(src);

		String filePath = folderPath + File.separator + fileName;
		ImageIO.write(saveImage, suffix, new File(filePath));

		return filePath;
	}

	public static void main(String[] args) throws IOException {
//		SeleniumAuxiliaryToolServiceImpl t = new SeleniumAuxiliaryToolServiceImpl();
//		Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36
	}
}
