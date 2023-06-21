package demo.selenium.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.selenium.service.JavaScriptService;

@Service
public class JavaScriptServiceImpl extends CommonService implements JavaScriptService {
	
	public final String openNewTab = "window.open('%s','_blank');";
	
	public final String getHtmlSource = "return document.documentElement.outerHTML;";
	
	public final String windowStop = "window.stop()";
	
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
	
	@Override
	public String getHtmlSource(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		Object htmlSource = jse.executeScript(getHtmlSource);
		return String.valueOf(htmlSource);
	}
	
	@Override
	public void scrollToBottom(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
	@Override
	public void scroll(WebDriver driver, int pix) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript("window.scrollBy(0,"+ pix +")");
	}

	@Override
	public boolean isVisibleInViewport(WebDriver driver, WebElement element) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		String js = """
				var elem = arguments[0],
				  box = elem.getBoundingClientRect(),
				  cx = box.left + box.width / 2,
				  cy = box.top + box.height / 2,
				  e = document.elementFromPoint(cx, cy);
				for (; e; e = e.parentElement) {
				  if (e === elem)
				    return true;
				}
				return false;
				""";
		
		return (Boolean) jse.executeScript(js, element);
	}
	
	@Override
	public void windowStop(WebDriver driver) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		jse.executeScript(windowStop);
	}
	
	@Override
	public Object execute(WebDriver driver, String js) {
		JavascriptExecutor jse = (JavascriptExecutor) driver;
		return jse.executeScript(js);
	}
	
}
