package demo.selenium.service.impl;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
import demo.selenium.pojo.testCases.TestCaseDemo;
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
	public TestEventResult testDemo(TestCaseDemo testCase) {
		TestEventResult r = new TestEventResult();
		
		Long newEventId = snowFlake.getNextId();
		TestEvent testEvent = new TestEvent();
		testEvent.setCaseId(TestCaseDemo.id);
		testEvent.setId(newEventId);
		testEventMapper.insertSelective(testEvent);

		WebDriver driver = webDriverService.buildFireFoxWebDriver();
		try {
			String url = TestCaseDemo.mainUrl;
			driver.get(url);
			
			// driver等待  (如果页面已经加载完成, 即进入下一步逻辑)
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			
			// 指定页面元素搜索条件
			By keywordBy = By.id("kw");
			
			// 等待, 直至页面出现指定元素
			auxiliaryToolService.fluentWait(driver, keywordBy);
			
//			检出页面上的搜索框
			WebElement searchBox = driver.findElement(By.id("kw"));
			searchBox.sendKeys(TestCaseDemo.searchKeyWord);
			searchBox.submit();
			
			WebElement imageButton = driver.findElement(By.partialLinkText("图片"));
			By b = By.partialLinkText("图片");
			
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
