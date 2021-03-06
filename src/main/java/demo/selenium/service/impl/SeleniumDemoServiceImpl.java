package demo.selenium.service.impl;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.tool.WebATToolService;
import demo.autoTestBase.testEvent.pojo.bo.TestEventDemoBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.selenium.pojo.result.TestEventResult;
import demo.selenium.service.JavaScriptService;
import demo.selenium.service.SeleniumService;
import demo.selenium.service.WebDriverService;

@Service
public class SeleniumDemoServiceImpl extends SeleniumCommonService implements SeleniumService {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private AuxiliaryToolServiceImpl auxiliaryToolService;
	@Autowired
	private WebATToolService webATToolService;
	@Autowired
	private JavaScriptService jsUtil;
	@Autowired
	private TestEventService eventService;
	
	@Override
	public TestEventResult testDemo() {
		TestEventResult r = new TestEventResult();
		
		TestEventDemoBO eventBO = new TestEventDemoBO().build();
		TestEvent testEvent = eventService.runNewTestEvent(eventBO);

		WebDriver driver = webDriverService.buildFireFoxWebDriver();
		try {
			String url = eventBO.getMainUrl();
			driver.get(url);
			
			// driver等待  (如果页面已经加载完成, 即进入下一步逻辑)
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			// 指定页面元素搜索条件
			By keywordBy = By.id("kw");
			
			// 等待, 直至页面出现指定元素
			auxiliaryToolService.fluentWait(driver, keywordBy);
			
//			检出页面上的搜索框
			WebElement searchBox = driver.findElement(By.id("kw"));
			searchBox.sendKeys(eventBO.getKeyWord());
			searchBox.submit();
			
			WebElement imageButton = driver.findElement(By.partialLinkText("图片"));
			By b = By.partialLinkText("图片");
			
			WebElement uploadEle = driver.findElement(By.id("uploadButton"));
			uploadEle.clear();
			uploadEle.sendKeys("/path/to/target/file");

			WebElement recaptchaEle = driver.findElement(By.id("demoId"));
			String src = recaptchaEle.getAttribute("src");
			System.out.println(src);
			
			auxiliaryToolService.fluentWait(driver, b);
			Actions clickWithCtrl = new Actions(driver);
			clickWithCtrl
			.keyDown(Keys.CONTROL).click(imageButton).keyUp(Keys.CONTROL).build()
			.perform();
			
			jsUtil.openNewTab(driver);
			
			webATToolService.tabSwitch(driver, 0);
			
//			auxiliaryToolService.dragAndDrop(driver, sourceElement, targetElement);
			
			Select s = new Select(driver.findElement(By.id("demo")));
			s.getOptions();
			
			WebElement radioElement = driver.findElement(By.id("demoRadioId"));
			radioElement.click();
			
			screenshot(driver, "demo");
			String ocrResult = captchaHandleService.captchaHandle(driver, recaptchaEle, testEvent);
			System.out.println(ocrResult);
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
			
		} finally {
			if(driver != null) {
				driver.quit();
			}
			
		}
		return r;
	}
	
}
