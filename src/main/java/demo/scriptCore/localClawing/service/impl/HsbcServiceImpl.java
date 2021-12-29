package demo.scriptCore.localClawing.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.localClawing.service.HsbcService;

@Service
public class HsbcServiceImpl extends AutomationTestCommonService implements HsbcService {

	String mainUrl = "https://www.hkg2vl0830-cn.p2g.netd2.hsbc.com.hk/PublicContent/wechat/wechat_library/VTM/prd-branch/index.html#/";
	
	@Override
	public void weixinPreReg() {
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		d.get(mainUrl);
		
		welcomePage(d);
		
//		xPathBuilder.start().addId("landing_accept_input");
		
	}
	
	public void welcomePage(WebDriver d) {
		WebElement accept1 = d.findElement(By.id("landing_accept_input"));
		accept1.click();
		WebElement accept2 = d.findElement(By.id("landing_accept_callback"));
		accept2.click();
		WebElement start = d.findElement(By.id("landing_start"));
		start.click();
	}
}
