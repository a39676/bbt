package demo.selenium.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.selenium.mapper.TestEventMapper;
import demo.selenium.pojo.dto.ScreenshotSaveDTO;
import demo.selenium.pojo.po.TestEvent;
import demo.selenium.service.ScreenshotService;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.SeleniumService;
import demo.selenium.service.WebDriverService;
import demo.selenium.util.JavaScriptCommonUtil;

@Service
public class SeleniumServiceImpl extends CommonService implements SeleniumService {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private ScreenshotService screenshotService;
	@Autowired
	private SeleniumAuxiliaryToolService auxiliaryToolService;
	@Autowired
	private JavaScriptCommonUtil jsUtil;
	
	@Autowired
	private TestEventMapper testEventMapper;
	
//	@Autowired
//	private VerificationCodeMapper verificationCodeMapper;
	
	@Override
	public void testDemo() {
		TestEvent testEven = testEventMapper.selectByPrimaryKey(1L);
		ChromeOptions chromeOption = new ChromeOptions();
//		chromeOption.addArguments(WebDriverGlobalOption.headLess);
		WebDriver driver = webDriverService.buildChromeWebDriver(chromeOption);
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
			
//		imageButton.click();
			WebElement link = driver.findElement(By.linkText("百度翻译"));
			link.click();
			
			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);
			
//			auxiliaryToolService.fluentWait(driver, b);
//			Actions action = new Actions(driver);
//			action
//			.keyDown(Keys.CONTROL).click(imageButton).keyUp(Keys.CONTROL).build()
//			.perform();
			
			jsUtil.openNewTab(driver);
			
			ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
			System.out.println(tabs);
			driver.switchTo().window(tabs.get(1));
			driver.close();
			driver.switchTo().window(tabs.get(0));
			
			File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			ScreenshotSaveDTO screenshotSaveDto = new ScreenshotSaveDTO();
			screenshotSaveDto.setScreenShotFile(scrFile);
			screenshotSaveDto.setEventId(testEven.getId());
			screenshotSaveDto.setEventName(testEven.getEventName());
			screenshotSaveDto.setFileName("testFile");
			screenshotService.screenshotSave(screenshotSaveDto);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(driver != null) {
				driver.quit();
			}
		}
		
	}
	
	
	
}
