package demo.selenium.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.pojo.po.TestEvent;
import demo.selenium.service.SeComplexOA;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;

@Service
public class SeComplexOAImpl extends CommonService implements SeComplexOA {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
//	@Autowired
//	private JavaScriptCommonUtil jsUtil;

	private String pwd = "dfsw@2019";
	private String mhUrl = "http://128.23.67.253:8888/frame_mh";

	@Override
	public void tmpTest() {
//		ChromeOptions options = new ChromeOptions();
//		FirefoxOptions options = new FirefoxOptions();
//		EdgeOptions options = new EdgeOptions();
//		options.addArguments(WebDriverGlobalOption.headLess);
//		WebDriver d = webDriverService.buildFireFoxWebDriver(options);
//		WebDriver d = webDriverService.buildEdgeWebDriver(options);
//		WebDriver d = webDriverService.buildChrome76WebDriver(options);
		WebDriver d = webDriverService.buildIEWebDriver();

		try {
			menHuLogin(d);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (d != null) {
				d.quit();
			}
		}
	}

	private void menHuLogin(WebDriver d) {
		TestEvent te = new TestEvent();
		te.setId(1L);
		te.setEventName("menHuLogin");

		d.get(mhUrl);

		WebElement userNameInput = d.findElement(By.id("username"));
		userNameInput.clear();
		userNameInput.sendKeys("adminSystem");

		WebElement pwdInput = d.findElement(By.id("password"));
		pwdInput.sendKeys(pwd);

		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("input", "class", "login_btn")
				.addCondition("type", "submit").addCondition("value", "登 录");
		By loginButtonBy = auxTool.byXpathBuilder(byXpathConditionBo);
		WebElement loginButton = d.findElement(loginButtonBy);
		loginButton.click();
		
		System.out.println("done");
	}

}
