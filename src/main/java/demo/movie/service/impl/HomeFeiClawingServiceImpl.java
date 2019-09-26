package demo.movie.service.impl;

import java.util.List;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.movie.service.HomeFeiClawingService;
import demo.movie.service.MovieClawingOptionService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;
import demo.testCase.pojo.po.TestEvent;

@Service
public class HomeFeiClawingServiceImpl extends MovieClawingCommonService implements HomeFeiClawingService {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
	@Autowired
	private MovieClawingOptionService optionService;
	
	private String mainUrl = "http://bbs.homefei.me";
	private String newMovie = mainUrl + "/thread-htm-fid-108.html";
	
	@Override
	public void clawing() {
		TestEvent te = getTestEvent();
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		
		
		try {
			d.get(mainUrl);
			
			String mainWindowHandler = d.getWindowHandle();
			
			login(d);
			Thread.sleep(2500L);
			dailyCheckIn(d, mainWindowHandler);
			
			d.get(newMovie);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			auxTool.takeScreenshot(d, te);
		} finally {
			if(d != null) {
				d.quit();
			}
		}
	}
	
	private void login(WebDriver d) {
		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("input", "id", "nav_pwuser");
		By usernameInputBy = auxTool.byXpathBuilder(byXpathConditionBo);
		
		byXpathConditionBo = ByXpathConditionBO.build("input", "id", "showpwd");
		By pwdInputBy = auxTool.byXpathBuilder(byXpathConditionBo);
		
		byXpathConditionBo = ByXpathConditionBO.build("button", "type", "submit").addCondition("name", "head_login");
		By loginButtonBy = auxTool.byXpathBuilder(byXpathConditionBo);
		
		try {
			WebElement usernameInput = d.findElement(usernameInputBy);
			WebElement pwdInput = d.findElement(pwdInputBy);
			WebElement loginButton = d.findElement(loginButtonBy);
			
			usernameInput.clear();
			usernameInput.sendKeys(optionService.getHomeFeiUsername());
			pwdInput.clear();
			pwdInput.sendKeys(optionService.getHomeFeiPwd());
			loginButton.click();
		} catch (Exception e) {
			log.error(e.getMessage());
			auxTool.takeScreenshot(d, getTestEvent());
		}
		
	}
	
	private void dailyCheckIn(WebDriver d, String mainWindowHandler) {
		try {
			List<WebElement> bList = d.findElements(By.tagName("b"));
			WebElement tmpEle = null;
			WebElement checkInInterfaceButton = null;
			for(int i = 0; i < bList.size() && checkInInterfaceButton == null; i++) {
				tmpEle = bList.get(i);
				if("签到领奖".equals(tmpEle.getText())) {
					checkInInterfaceButton = tmpEle;
				}
			}
			if(checkInInterfaceButton == null) {
				log.debug("homeFei can not found check in button");
				auxTool.takeScreenshot(d, getTestEvent());
				return;
			}
			
			checkInInterfaceButton.click();
			
			Thread.sleep(2200L);
			
			Set<String> windows = d.getWindowHandles();
			for(String w : windows) {
				if(!w.equals(mainWindowHandler)) {
					d.switchTo().window(w);
				}
			}
			
			ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("div", "id", "punch");
			By checkInButtonBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement checkInButton = d.findElement(checkInButtonBy);
			checkInButton.click();
			d.close();
			d.switchTo().window(mainWindowHandler);
			
		} catch (Exception e) {
			log.debug("homeFei daily check in fail {} " + e.getMessage());
			auxTool.takeScreenshot(d, getTestEvent());
		} finally {
			Set<String> windows = d.getWindowHandles();
			for(String w : windows) {
				if(!w.equals(mainWindowHandler)) {
					d.switchTo().window(w);
					d.close();
				}
			}
			d.switchTo().window(mainWindowHandler);
		}
		
	}
	
	private TestEvent getTestEvent() {
		TestEvent te = new TestEvent();
		te.setId(6L);
		te.setEventName("homeFei");
		return te;
	}
}
