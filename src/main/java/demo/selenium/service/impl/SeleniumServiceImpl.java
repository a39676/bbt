package demo.selenium.service.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.config.costom_component.Tess;
import demo.selenium.mapper.TestEventMapper;
import demo.selenium.pojo.po.TestEvent;
import demo.selenium.pojo.result.ScreenshotSaveResult;
import demo.selenium.pojo.result.TestEventResult;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.SeleniumService;
import demo.selenium.service.WebDriverService;
import demo.selenium.util.JavaScriptCommonUtil;

@Service
public class SeleniumServiceImpl extends CommonService implements SeleniumService {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxiliaryToolService;
	@Autowired
	private JavaScriptCommonUtil jsUtil;
	@Autowired
	private Tess tess;
	@Autowired
	private TestEventMapper testEventMapper;
	
	@Override
	public TestEventResult testDemo() {
		TestEventResult r = new TestEventResult();
		
		TestEvent testEvent = testEventMapper.selectByPrimaryKey(1L);
		ChromeOptions chromeOption = new ChromeOptions();
//		chromeOption.addArguments(WebDriverGlobalOption.headLess);
		WebDriver driver = webDriverService.buildChrome76WebDriver(chromeOption);
		try {
			String url = "http://www.baidu.com";
			driver.get(url);
			
			// driver等待
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			WebElement searchBox = driver.findElement(By.id("kw"));
			searchBox.sendKeys("test");
			searchBox.submit();
			
			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
			
			WebElement imageButton = driver.findElement(By.partialLinkText("图片"));
			By b = By.partialLinkText("图片");
			
			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
			
			WebElement uploadEle = driver.findElement(By.id("uploadButton"));
			uploadEle.clear();
			uploadEle.sendKeys("/path/to/target/file");

			WebElement recaptcha = driver.findElement(By.id("demoId"));
			String src = recaptcha.getAttribute("src");
			System.out.println(src);
			
			auxiliaryToolService.fluentWait(driver, b);
			Actions clickWithCtrl = new Actions(driver);
			clickWithCtrl
			.keyDown(Keys.CONTROL).click(imageButton).keyUp(Keys.CONTROL).build()
			.perform();
			
			jsUtil.openNewTab(driver);
			
			auxiliaryToolService.tabSwitch(driver, 0);
			
//			auxiliaryToolService.dragAndDrop(driver, sourceElement, targetElement);
			
			Select s = new Select(driver.findElement(By.id("demo")));
			s.getOptions();
			
			WebElement radioElement = driver.findElement(By.id("demoRadioId"));
			radioElement.click();
			
			auxiliaryToolService.takeScreenshot(driver, testEvent);
			ScreenshotSaveResult elementImageSaveResult = auxiliaryToolService.takeElementScreenshot(driver, testEvent, By.id("demoId"));
			String ocrResult = null;
			if(elementImageSaveResult.isSuccess() && StringUtils.isNotBlank(elementImageSaveResult.getSavingPath())) {
				 ocrResult = tess.ocr(elementImageSaveResult.getSavingPath());
			}
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
