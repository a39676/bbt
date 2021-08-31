package demo.selenium.service;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public interface SeleniumCaptchaHandleService {

	String captchaHandle(WebDriver d, int startX, int startY, int endX, int endY, boolean numberAndLetterOnly)
			throws IOException;

	String captchaHandle(WebDriver d, int startX, int startY, int endX, int endY) throws IOException;

	String captchaHandle(WebDriver d, WebElement captchaCodeElement, boolean numberAndLetterOnly) throws IOException;

	String captchaHandle(WebDriver d, WebElement captchaCodeElement) throws IOException;

}
