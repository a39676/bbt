package demo.dfsw.oa.newSoft.web.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.dfsw.oa.newSoft.web.service.MainTestService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.service.JavaScriptService;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;
import demo.testCase.pojo.bo.oa.newSoft.web.TestEventLoginCorrect;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.service.TestEventService;

@Service
public class MainTestServiceImpl extends CommonService implements MainTestService {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
	@Autowired
	private JavaScriptService jsUtil;
	
	@Autowired
	private TestEventService eventService;
	
	@Override
	public void loginCorrect() {
		auxTool.toString();
		jsUtil.toString();
		
		TestEventLoginCorrect eventBO = new TestEventLoginCorrect().build();
		TestEvent testEvent = eventService.runNewTestEvent(eventBO);
		
		WebDriver d = webDriverService.buildIEWebDriver();
		
		try {
			d.get(eventBO.getMainUrl());
			
			ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("input", "type", "text").addCondition("name", "j_username");
			By usernameInputBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement usernameInput = null;
			try {
				usernameInput = d.findElement(usernameInputBy);
			} catch (Exception e) {
				auxTool.takeScreenshot(d, testEvent);
				eventService.endTestEvent(testEvent, false, "无法找到用户名输入框");
				return;
			}
			usernameInput.clear();
			usernameInput.sendKeys(eventBO.getUserName());
			
			
			byXpathConditionBo = ByXpathConditionBO.build("input", "type", "password").addCondition("name", "j_password");
			By pwdInputBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement pwdInput = null;
			try {
				pwdInput = d.findElement(pwdInputBy);
			} catch (Exception e) {
				auxTool.takeScreenshot(d, testEvent);
				eventService.endTestEvent(testEvent, false, "无法找到密码输入框");
				return;
			}
			pwdInput.clear();
			pwdInput.sendKeys(eventBO.getPwd());
			
			byXpathConditionBo = ByXpathConditionBO.build("input", "type", "submit").addCondition("class", "submit");
			By loginButtonBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement loginButton = null;
			try {
				loginButton = d.findElement(loginButtonBy);
			} catch (Exception e) {
				auxTool.takeScreenshot(d, testEvent);
				eventService.endTestEvent(testEvent, false, "无法找到登录按钮");
				return;
			}
			loginButton.click();
			
		} catch (Exception e) {
			log.error(e.toString());
			auxTool.takeScreenshot(d, testEvent);
			eventService.endTestEvent(testEvent, false, null);
		} finally {
			if (d != null) {
				d.quit();
			}
		}
	}
	
}
