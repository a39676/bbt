package demo.selenium.util;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class JavaScriptCommonUtil {
	
	public final String openNewTab = "window.open('%s','_blank');";
	
	public void openNewTab(WebDriver d, String url) {
		JavascriptExecutor jse = (JavascriptExecutor) d;
		if(StringUtils.isEmpty(url)) {
			url = "";
		}
		String script = String.format(openNewTab, url);
		jse.executeScript(script);
	}
	
	public void openNewTab(WebDriver driver) {
		openNewTab(driver, "");
	}
	
}
