package demo.selenium.service.impl;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.screenshot.pojo.result.ScreenshotSaveResult;
import demo.autoTestBase.captcha.service.CaptchaService;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.selenium.service.SeleniumCaptchaHandleService;

@Service
public class SeleniumCaptchaHandleServiceImpl extends SeleniumCommonService implements SeleniumCaptchaHandleService {
	
	@Autowired
	private CaptchaService captchaService;

	@Override
	public String captchaHandle(WebDriver d, int startX, int startY, int endX, int endY, TestEvent te,
			boolean numberAndLetterOnly) throws IOException {

		ScreenshotSaveResult vcodeImgSaveResult = screenshot(d, "captchaHandle", startX, endX, startY, endY);
		return captchaService.ocr(vcodeImgSaveResult.getSavingPath(), numberAndLetterOnly);
	}

	@Override
	public String captchaHandle(WebDriver d, int startX, int startY, int endX, int endY, TestEvent te)
			throws IOException {
		return captchaHandle(d, startX, startY, endX, endY, te, true);
	}

	@Override
	public String captchaHandle(WebDriver d, WebElement captchaCodeElement, TestEvent te, boolean numberAndLetterOnly)
			throws IOException {
		ScreenshotSaveResult vcodeImgSaveResult = screenshot(d, "captchaHandle", captchaCodeElement);
		return captchaService.ocr(vcodeImgSaveResult.getSavingPath(), numberAndLetterOnly);
	}

	@Override
	public String captchaHandle(WebDriver d, WebElement captchaCodeElement, TestEvent te) throws IOException {
		return captchaHandle(d, captchaCodeElement, te, true);
	}
}
