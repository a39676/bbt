package demo.selenium.service.impl;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import constant.FileSuffixNameConstant;
import demo.baseCommon.service.CommonService;
import demo.selenium.mapper.TestEventMapper;
import demo.selenium.pojo.dto.ScreenshotSaveDTO;
import demo.selenium.pojo.po.TestEvent;
import demo.selenium.pojo.result.ScreenshotSaveResult;
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
		TestEvent testEvent = testEventMapper.selectByPrimaryKey(1L);
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
			
			driver.manage().timeouts().implicitlyWait(8, TimeUnit.SECONDS);

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
			
			/*
			 * TODO 
			 * 验证码使用 tess 识别
			 */
			
			takeScreenshot(driver, testEvent);
			takeElementScreenshot(driver, testEvent, By.id("demoId"));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(driver != null) {
				driver.quit();
			}
		}
	}
	
	private ScreenshotSaveResult takeScreenshot(WebDriver driver, TestEvent testEvent, String fileName) {
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		ScreenshotSaveDTO screenshotSaveDto = new ScreenshotSaveDTO();
		screenshotSaveDto.setScreenShotFile(scrFile);
		screenshotSaveDto.setEventId(testEvent.getId());
		screenshotSaveDto.setEventName(testEvent.getEventName());
		if(StringUtils.isBlank(fileName)) {
			screenshotSaveDto.setFileName(testEvent.getEventName());
		} else {
			screenshotSaveDto.setFileName(fileName);
		}
		ScreenshotSaveResult r = screenshotService.screenshotSave(screenshotSaveDto);
		return r;
	}
	
	private ScreenshotSaveResult takeScreenshot(WebDriver driver, TestEvent testEvent) {
		return takeScreenshot(driver, testEvent, null);
	}
	
	private ScreenshotSaveResult takeElementScreenshot(WebDriver driver, TestEvent testEvent, By by, String fileName) throws IOException {
		// Get entire page screenshot
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		BufferedImage  fullImg = ImageIO.read(screenshot);
		
		WebElement recaptcha = driver.findElement(by);
		
		Point point = recaptcha.getLocation();

		// Get width and height of the element
		int eleWidth = recaptcha.getSize().getWidth();
		int eleHeight = recaptcha.getSize().getHeight();

		// Crop the entire page screenshot to get only element screenshot
		BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(),
		    eleWidth, eleHeight);
		ImageIO.write(eleScreenshot, FileSuffixNameConstant.png, screenshot);

		ScreenshotSaveDTO screenshotSaveDto = new ScreenshotSaveDTO();
		screenshotSaveDto.setScreenShotFile(screenshot);
		screenshotSaveDto.setEventId(testEvent.getId());
		screenshotSaveDto.setEventName(testEvent.getEventName());
		if(StringUtils.isBlank(fileName)) {
			screenshotSaveDto.setFileName(testEvent.getEventName());
		} else {
			screenshotSaveDto.setFileName(fileName);
		}
		ScreenshotSaveResult r = screenshotService.screenshotSave(screenshotSaveDto);
		return r;
	}
	
	private ScreenshotSaveResult takeElementScreenshot(WebDriver driver, TestEvent testEvent, By by) throws IOException {
		return takeElementScreenshot(driver, testEvent, by, null);
	}
}
