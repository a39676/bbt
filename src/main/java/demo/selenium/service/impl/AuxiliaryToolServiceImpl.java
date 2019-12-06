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

import at.pojo.constant.WebDriverConstant;
import at.pojo.dto.TakeScreenshotSaveDTO;
import at.pojo.result.ScreenshotSaveResult;
import at.service.ScreenshotService;
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
	
	public static void main(String[] args) throws IOException {
//		SeleniumAuxiliaryToolServiceImpl t = new SeleniumAuxiliaryToolServiceImpl();
//		Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36
//		t.byXpathBuilder("a", "class", "value");
	}
}
