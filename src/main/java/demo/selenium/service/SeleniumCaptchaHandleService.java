package demo.selenium.service;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;

public interface SeleniumCaptchaHandleService {

	String captchaHandle(WebDriver d, int startX, int startY, int endX, int endY, TestEvent te,
			boolean numberAndLetterOnly) throws IOException;

	String captchaHandle(WebDriver d, int startX, int startY, int endX, int endY, TestEvent te) throws IOException;

	String captchaHandle(WebDriver d, WebElement captchaCodeElement, TestEvent te, boolean numberAndLetterOnly)
			throws IOException;

	String captchaHandle(WebDriver d, WebElement captchaCodeElement, TestEvent te) throws IOException;

}
