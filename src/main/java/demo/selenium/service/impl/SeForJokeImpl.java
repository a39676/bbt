package demo.selenium.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.screenshot.pojo.dto.TakeScreenshotSaveDTO;
import at.screenshot.service.ScreenshotService;
import at.xpath.pojo.bo.XpathBuilderBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.service.CommonService;
import demo.selenium.service.SeForJoke;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.WebDriverService;

@Service
public class SeForJokeImpl extends CommonService implements SeForJoke {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private AuxiliaryToolServiceImpl auxTool;
//	@Autowired
//	private JavaScriptCommonUtil jsUtil;
	@Autowired
	private ScreenshotService screenshotService;
	@Autowired
	private SeleniumGlobalOptionService globalOptionService;
	
	private String jokerName = "冯安";
	private String jokerPhone = "18826485386";
	private Integer jokerAge = 22;
	private String jokerAddress = "广州市越秀区万菱广场2204室";
	
	@Override
	public void test() {
		TestEvent te = new TestEvent();
		te.setId(1L);
		te.setEventName("test");
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		
		try {
			String url = "http://www.baidu.com";
			d.get(url);
			
			JavaScriptServiceImpl j = new JavaScriptServiceImpl();
			
			j.getHtmlSource(d);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(d != null) {
				d.quit();
			}
		}
	}
	
	@Override
	public void lanXiang() {
		TestEvent te = new TestEvent();
		te.setId(1L);
		te.setEventName("lanXiang");
//		ChromeOptions options = new ChromeOptions();
//		FirefoxOptions options = new FirefoxOptions();
//		EdgeOptions options = new EdgeOptions();
//		options.addArguments(WebDriverGlobalOption.headLess);
		WebDriver d = webDriverService.buildChromeWebDriver();
//		WebDriver d = webDriverService.buildFireFoxWebDriver(options);
//		WebDriver d = webDriverService.buildEdgeWebDriver(options);
//		WebDriver d = webDriverService.buildIEWebDriver();
		
		try {
			String url = "http://www.lxjx.cn/index.php?a=Index&c=Index&m=baoming";
			d.get(url);
			
			// driver等待
//		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			By xueyuanSelectorBy = By.id("xueyuan");
			
			WebElement xueyuanSelectorEle = auxTool.fluentWait(d, xueyuanSelectorBy);
			xueyuanSelectorEle.click();
			
			XpathBuilderBO xpathBuilder = new XpathBuilderBO();
			xpathBuilder.start("option").addAttribute("value", "11");
			By xueyuanOptionBy = By.xpath(xpathBuilder.getXpath());
			WebElement xueyuanOption = d.findElement(xueyuanOptionBy);
			xueyuanOption.click();
			
			WebElement zhuanyeSelector = d.findElement(By.id("zhuanye"));
			zhuanyeSelector.click();
			xpathBuilder.start("option").addAttribute("value", "12");
			By zhuanyeOptionBy = By.xpath(xpathBuilder.getXpath());
			WebElement zhuanyeOption = d.findElement(zhuanyeOptionBy);
			zhuanyeOption.click();
			
			xpathBuilder.start("input").addAttribute("name", "name").addAttribute("type", "text");
			By nameInputBy = By.xpath(xpathBuilder.getXpath());
			WebElement nameInput = d.findElement(nameInputBy);
			nameInput.sendKeys(jokerName);
			
			xpathBuilder.start("input").addAttribute("type", "radio").addAttribute("name", "sex");
			By genderBy = By.xpath(xpathBuilder.getXpath());
			WebElement genderMaleRadio = d.findElement(genderBy);
			genderMaleRadio.click();
			
			xpathBuilder.start("input").addAttribute("name", "age");
			By ageBy = By.xpath(xpathBuilder.getXpath());
			WebElement ageInput = d.findElement(ageBy);
			ageInput.sendKeys(jokerAge.toString());
			
			WebElement phoneInput = d.findElement(By.id("phone"));
			phoneInput.sendKeys(jokerPhone);
			
			xpathBuilder.start("input").addAttribute("name", "address");
			By addressBy = By.xpath(xpathBuilder.getXpath());
			WebElement addressInput = d.findElement(addressBy);
			addressInput.sendKeys(jokerAddress);
			
			TakeScreenshotSaveDTO dto = new TakeScreenshotSaveDTO();
			dto.setDriver(d);
			screenshotService.screenshotSave(dto, globalOptionService.getScreenshotSavingFolder(), te.getEventName());
			
			xpathBuilder.start("input").addAttribute("type", "submit").addAttribute("value", "报名");
			By submitBy = By.xpath(xpathBuilder.getXpath());
			WebElement submitButton = d.findElement(submitBy);
			submitButton.click();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(d != null) {
				d.quit();
			}
		}
	}
	
	@Override
	public void liuXue() {
		TestEvent te = new TestEvent();
		te.setId(1L);
		te.setEventName("liuXue");
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		XpathBuilderBO xpathBuilder = new XpathBuilderBO();
		
		try {
			String url = "https://liuxue.xdf.cn/blog/ExpertBlog/";
			d.get(url);
			
			// driver等待
			By regFormWrapBy = By.xpath(xpathBuilder.getXpath());
			
			WebElement regFormWrap = auxTool.fluentWait(d, regFormWrapBy);
			regFormWrap.click();
			
			
			xpathBuilder.start("input").addAttribute("id", "stu_Name");
			By nameBy = By.xpath(xpathBuilder.getXpath());
			WebElement nameInput = d.findElement(nameBy);
			nameInput.sendKeys(jokerName);
			
			WebElement phoneInput = d.findElement(By.id("stu_Phone"));
			phoneInput.sendKeys(jokerPhone);
			
			
			xpathBuilder.start("select").addAttribute("id", "stu_Country");
			By countrySelectBy = By.xpath(xpathBuilder.getXpath());
			WebElement countrySelect = d.findElement(countrySelectBy);
			countrySelect.click();
			
			
			xpathBuilder.start("option").addAttribute("value", "亚洲");
			By countryOptionBy = By.xpath(xpathBuilder.getXpath());
			WebElement countryOption = d.findElement(countryOptionBy);
			countryOption.click();
			
			
			xpathBuilder.start("select").addAttribute("id", "stu_Degree");
			By degreeSelectBy = By.xpath(xpathBuilder.getXpath());
			WebElement degreeSelect = d.findElement(degreeSelectBy);
			degreeSelect.click();
			
			
			xpathBuilder.start("option").addAttribute("value", "本科");
			By degreeOptionBy = By.xpath(xpathBuilder.getXpath());
			WebElement degreeOption = d.findElement(degreeOptionBy);
			degreeOption.click();
			
			
			xpathBuilder.start("select").addAttribute("id", "stu_City");
			By stuCitySelectBy = By.xpath(xpathBuilder.getXpath());
			WebElement stuCitySelect = d.findElement(stuCitySelectBy);
			stuCitySelect.click();
			
			
			xpathBuilder.start("option").addAttribute("value", "广州");
			By stuCityOptionBy = By.xpath(xpathBuilder.getXpath());
			WebElement stuCityOption = d.findElement(stuCityOptionBy);
			stuCityOption.click();
			
			
			xpathBuilder.start("input").addAttribute("type", "button").addAttribute("id", "dosubmit");
			By submitBy = By.xpath(xpathBuilder.getXpath());
			WebElement submitButton = d.findElement(submitBy);
			submitButton.click();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(d != null) {
				d.quit();
			}
		}
	}
	
}
