package demo.selenium.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.selenium.service.JavaScriptService;

@Service
public class JavaScriptServiceImpl extends CommonService implements JavaScriptService {
	
	public final String openNewTab = "window.open('%s','_blank');";
	
	public final String getHtmlSource = "return document.documentElement.outerHTML;";
	
	@Override
	public void openNewTab(WebDriver d, String url) {
		JavascriptExecutor jse = (JavascriptExecutor) d;
		if(StringUtils.isEmpty(url)) {
			url = "";
		}
		String script = String.format(openNewTab, url);
		jse.executeScript(script);
	}
	
	@Override
	public void openNewTab(WebDriver driver) {
		openNewTab(driver, "");
	}
	
	public void getHtmlSource(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
//		TODO
		Object htmlSource = jse.executeScript(getHtmlSource);
		System.out.println(htmlSource);
	}
}
