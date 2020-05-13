package demo.selenium.service.impl;

import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

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

import at.screenshot.pojo.dto.TakeScreenshotSaveDTO;
import at.screenshot.pojo.result.ScreenshotSaveResult;
import at.screenshot.service.ScreenshotService;
import at.webDriver.pojo.constant.WebDriverConstant;
import demo.autoTestBase.captcha.service.CaptchaService;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.service.CommonService;
import demo.selenium.service.SeleniumGlobalOptionService;

@Service
public class AuxiliaryToolServiceImpl extends CommonService {

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
	
	public String captchaHandle(WebDriver d, int startX, int startY, int endX, int endY, TestEvent te, boolean numberAndLetterOnly)
			throws IOException {
		
		TakeScreenshotSaveDTO dto = new TakeScreenshotSaveDTO();
		dto.setDriver(d);
		dto.setStartX(startX);
		dto.setStartY(startY);
		dto.setEndX(endX);
		dto.setEndY(endY);
		ScreenshotSaveResult vcodeImgSaveResult = screenshotService.screenshotSave(dto, globalOptionService.getScreenshotSavingFolder(), null);
		return captchaService.ocr(vcodeImgSaveResult.getSavingPath(), numberAndLetterOnly);
	}
	
	public String captchaHandle(WebDriver d, int startX, int startY, int endX, int endY, TestEvent te)
			throws IOException {
		return captchaHandle(d, startX, startY, endX, endY, te, true);
	}

	public String captchaHandle(WebDriver d, WebElement captchaCodeElement, TestEvent te, boolean numberAndLetterOnly)
			throws IOException {
		
		TakeScreenshotSaveDTO dto = new TakeScreenshotSaveDTO();
		dto.setDriver(d);
		dto.setEle(captchaCodeElement);
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
	
	public boolean loadingCheck(WebDriver d, String xpath) throws InterruptedException {
		return loadingCheck(d, xpath, 1000L, 10);
	}
	
	public boolean loadingCheck(WebDriver d, String xpath, long waitGap, int findCount) throws InterruptedException {
		if(waitGap < 0) {
			waitGap = 1000L;
		}
		if(findCount < 0) {
			findCount = 10;
		}
		WebElement tmpElemet = null;
		for(int i = 0; i < findCount && tmpElemet == null; i++) {
			try {
				tmpElemet = d.findElement(By.xpath(xpath));
			} catch (Exception e) {
			} 
			if(tmpElemet != null) {
				return true;
			}
			Thread.sleep(waitGap);
		}
		return false;
	}
	
	public static void main(String[] args) throws IOException {
//		SeleniumAuxiliaryToolServiceImpl t = new SeleniumAuxiliaryToolServiceImpl();
//		Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36
//		t.byXpathBuilder("a", "class", "value");
	}
}
