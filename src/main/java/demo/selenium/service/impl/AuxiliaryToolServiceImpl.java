package demo.selenium.service.impl;

import java.time.Duration;
import java.util.Random;
import java.util.function.Function;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.stereotype.Service;

import at.webDriver.pojo.constant.WebDriverConstant;
import demo.baseCommon.service.CommonService;

@Service
public class AuxiliaryToolServiceImpl extends CommonService {

	public WebElement fluentWait(WebDriver driver, final By by) {
		Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
				.withTimeout(Duration.ofSeconds(WebDriverConstant.pageWaitingTimeoutSecond))
				.pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);

		WebElement foo = wait.until(new Function<WebDriver, WebElement>() {
			public WebElement apply(WebDriver driver) {
				return driver.findElement(by);
			}
		});

		return foo;
	};

	public void swipeCaptchaHadle(WebDriver d, WebElement swipeButton, WebElement chuteElement) {
		Random r = new Random();
		Integer dragPix = chuteElement.getSize().getWidth();
		Integer subDragPix = dragPix / 10;
		Actions builder = new Actions(d);
		Action dragAndDrop = builder.clickAndHold(swipeButton).moveByOffset(subDragPix, r.nextInt(50) + 1)
				.pause(r.nextInt(220)).moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220))
				.moveByOffset(subDragPix, r.nextInt(50) + 1).pause(r.nextInt(220)).release().build();
		dragAndDrop.perform();
	}

	public boolean loadingCheck(WebDriver d, String xpath) throws InterruptedException {
		return loadingCheck(d, xpath, 1000L, 10);
	}

	public boolean loadingCheck(WebDriver d, String xpath, long waitGap, int findCount) throws InterruptedException {
		if (waitGap < 0) {
			waitGap = 1000L;
		}
		if (findCount < 0) {
			findCount = 10;
		}
		WebElement tmpElemet = null;
		for (int i = 0; i < findCount && tmpElemet == null; i++) {
			try {
				tmpElemet = d.findElement(By.xpath(xpath));
			} catch (Exception e) {
			}
			if (tmpElemet != null) {
				return true;
			}
			Thread.sleep(waitGap);
		}
		return false;
	}

}
