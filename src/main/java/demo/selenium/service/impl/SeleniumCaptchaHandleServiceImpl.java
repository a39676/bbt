package demo.selenium.service.impl;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.screenshot.pojo.result.ScreenshotSaveResult;
import demo.selenium.service.SeleniumCaptchaHandleService;
import demo.tool.captcha.service.CaptchaService;

@Service
public class SeleniumCaptchaHandleServiceImpl extends AutomationTestCommonService implements SeleniumCaptchaHandleService {
	
	@Autowired
	private CaptchaService captchaService;

	@Override
	public String captchaHandle(WebDriver d, int startX, int startY, int endX, int endY,
			boolean numberAndLetterOnly) throws IOException {

		ScreenshotSaveResult vcodeImgSaveResult = screenshot(d, "captchaHandle", startX, endX, startY, endY);
		return captchaService.ocr(vcodeImgSaveResult.getSavingPath(), numberAndLetterOnly);
	}

	@Override
	public String captchaHandle(WebDriver d, int startX, int startY, int endX, int endY)
			throws IOException {
		return captchaHandle(d, startX, startY, endX, endY, true);
	}

	@Override
	public String captchaHandle(WebDriver d, WebElement captchaCodeElement, boolean numberAndLetterOnly)
			throws IOException {
		ScreenshotSaveResult vcodeImgSaveResult = screenshot(d, "captchaHandle", captchaCodeElement);
		return captchaService.ocr(vcodeImgSaveResult.getSavingPath(), numberAndLetterOnly);
	}

	@Override
	public String captchaHandle(WebDriver d, WebElement captchaCodeElement) throws IOException {
		return captchaHandle(d, captchaCodeElement, true);
	}
}
