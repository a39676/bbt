package demo.neobux.service.impl;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.captcha.service.CaptchaService;
import demo.neobux.service.NeobuxOptionService;
import demo.neobux.service.NeobuxService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.pojo.po.TestEvent;
import demo.selenium.pojo.result.ScreenshotSaveResult;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;

@Service
public class NeobuxServiceImpl extends CommonService implements NeobuxService {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
	@Autowired
	private CaptchaService captchaService;
	@Autowired
	private NeobuxOptionService optionService;
//	@Autowired
//	private SeleniumGlobalOptionService globalOptionService;

	private String mainUrl = "https://www.neobux.com";
	
	private static TestEvent t = new TestEvent();
	
	static {
		t.setEventName("neobux");
		t.setId(2L);
	}
	
	@Override
	public void test1() {
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		String mainWindow = d.getWindowHandle();
		
		System.out.println(mainWindow);
		
		try {
			d.get(mainUrl);
			auxTool.takeScreenshot(d, t);
			
			int loginCountdown = 10;
			while(findLoginButton(d) != null && loginCountdown >= 0 ) {
				login(d);
				loginCountdown--;
			}
			if(loginCountdown < 0) {
				auxTool.takeScreenshot(d, t);
				log.debug("login failed to many");
				return;
			}
			
			if(!clickViewADList(d)) {
				auxTool.takeScreenshot(d, t);
				log.debug("can not find ad list button");
				return;
			}
			
			List<WebElement> adList = findADList(d);
			if(adList == null) {
				auxTool.takeScreenshot(d, t);
				log.debug("can not find ad list not click");
				return;
			} else {
				auxTool.takeScreenshot(d, t);
				log.debug("find ad list not click");
			}
			
			auxTool.takeScreenshot(d, t);
			
		} catch (Exception e) {
			e.printStackTrace();
			auxTool.takeScreenshot(d, t);
		} finally {
			if (d != null) {
				d.quit();
			}
		}
		
	}
	
	private WebElement findLoginButton(WebDriver d) {
		List<WebElement> spans = d.findElements(By.tagName("span"));
		WebElement loginButton = null;
		WebElement tmpEle = null;
		for(int i = 0; i < spans.size() && loginButton == null; i++) {
			tmpEle = spans.get(i);
			if(loginButton == null && "login".equalsIgnoreCase(tmpEle.getText())) {
				loginButton = tmpEle;
			}
		}
		return loginButton;
	}
	
	private boolean login(WebDriver d) {
		List<WebElement> spans = d.findElements(By.tagName("span"));
		WebElement loginButton = findLoginButton(d);
		if(loginButton == null) {
			auxTool.takeScreenshot(d, t);
			return false;
		}
		
		loginButton.click();
		
		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("input", "id", "Kf1");
		By usernameInputBy = auxTool.byXpathBuilder(byXpathConditionBo);
		WebElement usernameInput = auxTool.fluentWait(d, usernameInputBy);
		usernameInput.sendKeys(optionService.getNeobuxUsername());
		
		byXpathConditionBo = ByXpathConditionBO.build("input", "id", "Kf2");
		By pwdInputBy = auxTool.byXpathBuilder(byXpathConditionBo);
		WebElement pwdInput = auxTool.fluentWait(d, pwdInputBy);
		pwdInput.sendKeys(optionService.getNeobuxPwd());
		
		byXpathConditionBo = ByXpathConditionBO.build("input", "id", "Kf4");
		By secondPwdInputBy = auxTool.byXpathBuilder(byXpathConditionBo);
		WebElement secondPwdInput = auxTool.fluentWait(d, secondPwdInputBy);
		secondPwdInput.sendKeys(optionService.getNeobuxPwd());
		
		spans = d.findElements(By.tagName("span"));
		WebElement sendButton = null;
		WebElement tmpEle = null;
		for(int i = 0; i < spans.size() && sendButton == null; i++) {
			tmpEle = spans.get(i);
			if(sendButton == null && "send".equalsIgnoreCase(tmpEle.getText())) {
				sendButton = tmpEle;
			}
		}
		if(sendButton == null) {
			auxTool.takeScreenshot(d, t);
			return false;
		}
		
		tryFillVcode(d);
		
		sendButton.click();
		return true;
	}
	
	private WebElement tryFindVcodeImg(WebDriver d) {
		List<WebElement> imgs = d.findElements(By.tagName("img"));
		WebElement img = null;
		Integer width = 0;
		Integer height = 0;
		for(int i = 0; i < imgs.size() && img == null; i++) {
			try {
				width = Integer.parseInt(imgs.get(i).getAttribute("width"));
				height = Integer.parseInt(imgs.get(i).getAttribute("height"));
				
				if(width.equals(91) && height.equals(24)) {
					img = imgs.get(i);
				}
			} catch (Exception e) {
				
			}
		}
		return img;
	}
	
	private String tryReadVcode(WebDriver d, WebElement ele) {
		ScreenshotSaveResult r;
		try {
			r = auxTool.takeElementScreenshot(d, t, ele, String.valueOf(snowFlake.getNextId()));
		} catch (IOException e) {
			log.info(e.getMessage());
			return null;
		}
		if(!r.isSuccess()) {
			return null;
		}
		String ocrResult = captchaService.ocr(r.getSavingPath(), true);
		log.info(ocrResult);
		return ocrResult;
	}

	private void tryFillVcode(WebDriver d) {
		WebElement vcodeInputElement = null;
		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("input", "id", "Kf3");
		By vcodeInputBy = auxTool.byXpathBuilder(byXpathConditionBo);
		try {
			vcodeInputElement = d.findElement(vcodeInputBy);
			if(vcodeInputElement != null) {
				vcodeInputElement.clear();
				WebElement vocodeEle = tryFindVcodeImg(d);
				String vcode = tryReadVcode(d, vocodeEle);
				vcodeInputElement.sendKeys(vcode);
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
	
	private boolean clickViewADList(WebDriver d) {
		List<WebElement> spans = d.findElements(By.tagName("span"));
		for(int i = 0; i < spans.size(); i++) {
			if("View Advertisements".equalsIgnoreCase(spans.get(i).getText())) {
				spans.get(i).click();
				return true;
			}
		}
		return false;
	}

	private List<WebElement> findADList(WebDriver d) {
		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("i", "class", "ic-star-1");
		By adNotClickedBy = auxTool.byXpathBuilder(byXpathConditionBo);
		List<WebElement> adList = null;
		try {
			adList = d.findElements(adNotClickedBy);
			for(WebElement i : adList) {
				System.out.println(i.getText());
			}
		} catch (Exception e) {
			log.info(e.getMessage());
		}
		return adList;
	}
}
