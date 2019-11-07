package demo.selenium.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.pojo.constant.WebDriverConstant;
import at.pojo.dto.TakeScreenshotSaveDTO;
import at.pojo.result.ScreenshotSaveResult;
import at.service.ScreenshotService;
import demo.baseCommon.service.CommonService;
import demo.captcha.service.CaptchaService;
import demo.selenium.pojo.constant.RegexConstant;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.testCase.pojo.po.TestEvent;

@Service
public class WebATToolServiceImpl extends CommonService {

	@Autowired
	private CaptchaService captchaService;
	@Autowired
	private ScreenshotService screenshotService;
	@Autowired
	private SeleniumGlobalOptionService globalOptionService;

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

	public String findFileNameFromUrl(String url) {
		Pattern fileNamePattern = Pattern.compile(RegexConstant.fileNameRegex);
		Matcher fileNameMatcher = fileNamePattern.matcher(url);

		if (fileNameMatcher.find()) {
			return fileNameMatcher.group(0);
		} else {
			return null;
		}
	}
	
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
	
	public void dragAndDrop(WebDriver driver, WebElement sourceElement, WebElement targetElement) {
		Actions builder = new Actions(driver);
		Action dragAndDrop = builder.clickAndHold(sourceElement).moveToElement(targetElement).release(targetElement)
				.build();
		dragAndDrop.perform();
	}
	
	
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
	
	public String captchaHandle(WebDriver d, WebElement captchaCodeElement, TestEvent te, boolean numberAndLetterOnly)
			throws IOException {
		
		TakeScreenshotSaveDTO dto = new TakeScreenshotSaveDTO();
		dto.setDriver(d);
		ScreenshotSaveResult vcodeImgSaveResult = screenshotService.screenshotSave(dto, globalOptionService.getScreenshotSavingFolder(), null);
		return captchaService.ocr(vcodeImgSaveResult.getSavingPath(), numberAndLetterOnly);
	}
	
	public String captchaHandle(WebDriver d, WebElement captchaCodeElement, TestEvent te) throws IOException {
		return captchaHandle(d, captchaCodeElement, te, true);
	}
	
	public void swipeCaptchaHadle(WebDriver d, WebElement swipeButton, WebElement chuteElement) { 
		Random r = new Random();
		Integer dragPix = chuteElement.getSize().getWidth();
		Integer subDragPix = dragPix / 10;
		Actions builder = new Actions(d);
		Action dragAndDrop = builder.clickAndHold(swipeButton)
				.moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220))
				.release()
				.build();
		dragAndDrop.perform();
		
	}
	
	public static void main(String[] args) throws IOException {
//		SeleniumAuxiliaryToolServiceImpl t = new SeleniumAuxiliaryToolServiceImpl();
//		Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36
//		t.byXpathBuilder("a", "class", "value");
	}
}
