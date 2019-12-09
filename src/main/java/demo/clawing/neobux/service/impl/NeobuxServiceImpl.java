package demo.clawing.neobux.service.impl;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.pojo.bo.XpathBuilderBO;
import at.pojo.dto.TakeScreenshotSaveDTO;
import at.service.ScreenshotService;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.clawing.neobux.service.NeobuxOptionService;
import demo.clawing.neobux.service.NeobuxService;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.WebDriverService;
import demo.selenium.service.impl.AuxiliaryToolServiceImpl;
import demo.selenium.service.impl.SeleniumCommonService;

@Service
public class NeobuxServiceImpl extends SeleniumCommonService implements NeobuxService {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private AuxiliaryToolServiceImpl auxTool;
	@Autowired
	private NeobuxOptionService optionService;
	@Autowired
	private SeleniumGlobalOptionService globalOptionService;
	@Autowired
	private ScreenshotService screenshotService;

	private String mainUrl = "https://www.neobux.com";
	
	private static TestEvent t = new TestEvent();
	
	static {
		t.setEventName("neobux");
		t.setId(2L);
	}
	
	@Override
	public void test1() {
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		
		String scrSavePath = globalOptionService.getScreenshotSavingFolder();

		String mainWindow = d.getWindowHandle();
		
		System.out.println(mainWindow);
		
		TakeScreenshotSaveDTO dto = new TakeScreenshotSaveDTO();
		dto.setDriver(d);
		try {
			d.get(mainUrl);
			screenshotService.screenshotSave(dto, scrSavePath, null);
			
			int loginCountdown = 10;
			while(findLoginButton(d) != null && loginCountdown >= 0 ) {
				login(d);
				loginCountdown--;
			}
			if(loginCountdown < 0) {
				screenshotService.screenshotSave(dto, scrSavePath, null);
				log.debug("login failed to many");
				return;
			}
			
			if(!clickViewADList(d)) {
				screenshotService.screenshotSave(dto, scrSavePath, null);
				log.debug("can not find ad list button");
				return;
			}
			
			List<WebElement> adList = findADList(d);
			if(adList == null) {
				screenshotService.screenshotSave(dto, scrSavePath, null);
				log.debug("can not find ad list not click");
				return;
			} else {
				screenshotService.screenshotSave(dto, scrSavePath, null);
				log.debug("find ad list not click");
			}
			
			screenshotService.screenshotSave(dto, scrSavePath, null);
			
		} catch (Exception e) {
			e.printStackTrace();
			screenshotService.screenshotSave(dto, scrSavePath, null);
		} finally {
			tryQuitWebDriver(d);
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
		String scrSavePath = globalOptionService.getScreenshotSavingFolder();
		XpathBuilderBO xpathBuilder = new XpathBuilderBO();
		List<WebElement> spans = d.findElements(By.tagName("span"));
		WebElement loginButton = findLoginButton(d);
		TakeScreenshotSaveDTO dto = new TakeScreenshotSaveDTO();
		dto.setDriver(d);
		if(loginButton == null) {
			screenshotService.screenshotSave(dto, scrSavePath, null);
			return false;
		}
		
		loginButton.click();
		
		xpathBuilder.start("input").addAttribute("id", "Kf1");
		By usernameInputBy = By.xpath(xpathBuilder.getXpath());
		WebElement usernameInput = auxTool.fluentWait(d, usernameInputBy);
		usernameInput.sendKeys(optionService.getNeobuxUsername());
		
		xpathBuilder.start("input").addAttribute("id", "Kf2");
		By pwdInputBy = By.xpath(xpathBuilder.getXpath());
		WebElement pwdInput = auxTool.fluentWait(d, pwdInputBy);
		pwdInput.sendKeys(optionService.getNeobuxPwd());
		
		xpathBuilder.start("input").addAttribute("id", "Kf4");
		By secondPwdInputBy = By.xpath(xpathBuilder.getXpath());
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
			screenshotService.screenshotSave(dto, scrSavePath, null);
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
	
	private String tryReadVcode(WebDriver d, WebElement ele) throws IOException {
		
		String captchaCode = auxTool.captchaHandle(d, ele, t);
		return captchaCode;
	}

	private void tryFillVcode(WebDriver d) {
		WebElement vcodeInputElement = null;
		XpathBuilderBO xpathBuilder = new XpathBuilderBO();
		xpathBuilder.start("input").addAttribute("id", "Kf3");
		By vcodeInputBy = By.xpath(xpathBuilder.getXpath());
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
		XpathBuilderBO xpathBuilder = new XpathBuilderBO();
		xpathBuilder.start("i").addAttribute("class", "ic-star-1");
		By adNotClickedBy = By.xpath(xpathBuilder.getXpath());
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
