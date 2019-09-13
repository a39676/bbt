package demo.selenium;

import java.awt.AWTException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import demo.selenium.service.impl.JavaScriptServiceImpl;
import demo.selenium.service.impl.WebDriverServiceImpl;


public class Selenium {

	public static void testSelenium(WebDriver driver) throws InterruptedException, IOException, AWTException {
		
		driver.get("http://www.baidu.com");
		Thread.sleep(1300L);
		
		WebElement searchBox = driver.findElement(By.id("kw"));
		searchBox.sendKeys("test");
		searchBox.submit();
		
		Thread.sleep(1300L);
		WebElement imageButton = driver.findElement(By.partialLinkText("图片"));
//		imageButton.click();
		WebElement link = driver.findElement(By.linkText("百度翻译"));
		link.click();
		Actions action = new Actions(driver);
		action
		.keyDown(Keys.CONTROL).click(imageButton).keyUp(Keys.CONTROL).build()
	    .perform();
		
		JavaScriptServiceImpl jsUtil = new JavaScriptServiceImpl();
		jsUtil.openNewTab(driver);
		
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		System.out.println(tabs);
	    driver.switchTo().window(tabs.get(1));
	    driver.close();
	    driver.switchTo().window(tabs.get(0));
		
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("d:/auxiliary/tmp/screenshot.png"));
		
		
	}
	
	
	public static void main(String[] args) throws InterruptedException, IOException, AWTException {
		WebDriverServiceImpl deiverBuilder = new WebDriverServiceImpl();
		WebDriver driver = null;
		try {
			driver = deiverBuilder.buildEdgeWebDriver();
			testSelenium(driver);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(driver != null) {
				driver.quit();
			}
		}
		System.exit(0);
	}
}
