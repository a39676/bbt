package demo.dailySign.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import at.pojo.bo.XpathBuilderBO;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.dailySign.pojo.type.DailySignCaseType;
import demo.dailySign.service.QuQiDailySignService;
import demo.selenium.service.impl.SeleniumCommonService;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.result.InsertTestEventResult;
import demo.testCase.pojo.type.TestModuleType;

@Service
public class QuQiDailySignServiceImpl extends SeleniumCommonService implements QuQiDailySignService {

	private String mainUrl = "https://www.quqi.com/";
	
	private TestEvent buildTestEvent() {
		return buildTestEvent(TestModuleType.dailySign, DailySignCaseType.quqi.getId());
	}
	
	@Override
	public InsertTestEventResult insertclawingEvent() {
		TestEvent te = buildTestEvent();
		return testEventService.insertTestEvent(te);
	}
	
	@Override
	public CommonResultBBT clawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		try {
			d.get(mainUrl);
			
			XpathBuilderBO x = new XpathBuilderBO();
			
			x.start("div").addAttribute("class", "pure-menu pure-menu-horizontal")
			.findChild("ul").addAttribute("class", "pure-menu-list")
			.findChild("li").addAttribute("class", "pure-menu-item")
			.findChild("button").addAttribute("class", "pure-button pure-button-primary login-btn");
			
			WebElement loginPageButton = d.findElement(By.xpath(x.getXpath()));
			
			try {
				loginPageButton.click();
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
			}
			
			x.start("form").addAttribute("id", "phone-password-login-form")
			.findChild("div").addAttribute("class", "form-item")
			.findChild("div").addAttribute("class", "form-field")
			.findChild("input").addAttribute("name", "phone");
			
			WebElement phoneInput = d.findElement(By.xpath(x.getXpath()));
			phoneInput.click();
			phoneInput.clear();
			phoneInput.sendKeys("18022379435");
			
			x.start("form").addAttribute("id", "phone-password-login-form")
			.findChild("div").addAttribute("class", "form-item")
			.findChild("div").addAttribute("class", "form-field")
			.findChild("input").addAttribute("name", "password");
			
			WebElement pwdInput = d.findElement(By.xpath(x.getXpath()));
			pwdInput.click();
			pwdInput.clear();
			pwdInput.sendKeys("GJ1621828228");
			
			x.start("button").addAttribute("id", "btn-signup");
			WebElement loginButton = d.findElement(By.xpath(x.getXpath()));
			try {
				loginButton.click();
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
			}
			
			Thread.sleep(800L);

			x.start("a").addAttribute("webix_l_id", "personal_center_menu")
			.findChild("span").addAttribute("class", "personal-arrow-down");
			WebElement personMenuArrowDown = d.findElement(By.xpath(x.getXpath()));
			personMenuArrowDown.click();
			
			Thread.sleep(800L);
			
			x.start("div").addAttribute("view_id", "$submenu1")
			.findChild("div").addAttribute("class", "webix_win_content")
			.findChild("div").addAttribute("class", "webix_win_body")
			.findChild("div").addAttribute("class", "webix_scroll_cont")
			.findChild("a").addAttribute("webix_l_id", "check_in");
			WebElement dailySignButton = d.findElement(By.xpath(x.getXpath()));
			dailySignButton.click();
			
			r.setIsSuccess();
		} catch (Exception e) {
			
		} finally {
			if (d != null) {
				d.quit();
			}
		}
		
		return r;
	}
}
