package demo.selenium.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.pojo.po.TestEvent;
import demo.selenium.service.SeForJoke;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;

@Service
public class SeForJokeImpl extends CommonService implements SeForJoke {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
//	@Autowired
//	private JavaScriptCommonUtil jsUtil;
	
	private String jokerName = "冯安";
	private String jokerPhone = "18826485386";
	private Integer jokerAge = 22;
	private String jokerAddress = "广州市越秀区万菱广场2204室";
	
	@Override
	public void lanXiang() {
		TestEvent te = new TestEvent();
		te.setId(1L);
		te.setEventName("lanXiang");
//		ChromeOptions options = new ChromeOptions();
//		FirefoxOptions options = new FirefoxOptions();
//		EdgeOptions options = new EdgeOptions();
//		options.addArguments(WebDriverGlobalOption.headLess);
		WebDriver d = webDriverService.buildChrome76WebDriver();
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
			
			ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("option", "value", "11");
			By xueyuanOptionBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement xueyuanOption = d.findElement(xueyuanOptionBy);
			xueyuanOption.click();
			
			WebElement zhuanyeSelector = d.findElement(By.id("zhuanye"));
			zhuanyeSelector.click();
			byXpathConditionBo = ByXpathConditionBO.build("option", "value", "12");
			By zhuanyeOptionBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement zhuanyeOption = d.findElement(zhuanyeOptionBy);
			zhuanyeOption.click();
			
			byXpathConditionBo = ByXpathConditionBO.build("input", "name", "name").addCondition("type", "text");
			By nameInputBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement nameInput = d.findElement(nameInputBy);
			nameInput.sendKeys(jokerName);
			
			byXpathConditionBo = ByXpathConditionBO.build("input", "type", "radio").addCondition("name", "sex");
			By genderBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement genderMaleRadio = d.findElement(genderBy);
			genderMaleRadio.click();
			
			byXpathConditionBo = ByXpathConditionBO.build("input", "name", "age");
			By ageBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement ageInput = d.findElement(ageBy);
			ageInput.sendKeys(jokerAge.toString());
			
			WebElement phoneInput = d.findElement(By.id("phone"));
			phoneInput.sendKeys(jokerPhone);
			
			byXpathConditionBo = ByXpathConditionBO.build("input", "name", "address");
			By addressBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement addressInput = d.findElement(addressBy);
			addressInput.sendKeys(jokerAddress);
			
			auxTool.takeScreenshot(d, te);
			
			byXpathConditionBo = ByXpathConditionBO.build("input", "type", "submit").addCondition("value", "报名");
			By submitBy = auxTool.byXpathBuilder(byXpathConditionBo);
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
//		ChromeOptions options = new ChromeOptions();
		FirefoxOptions options = new FirefoxOptions();
//		options.addArguments(WebDriverGlobalOption.headLess);
//		WebDriver d = webDriverService.buildChromeWebDriver(options);
		WebDriver d = webDriverService.buildFireFoxWebDriver(options);
		
		try {
			String url = "https://liuxue.xdf.cn/blog/ExpertBlog/";
			d.get(url);
			
			// driver等待
			ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("form", "id", "myform");
			By regFormWrapBy = auxTool.byXpathBuilder(byXpathConditionBo);
			
			WebElement regFormWrap = auxTool.fluentWait(d, regFormWrapBy);
			regFormWrap.click();
			
			byXpathConditionBo = ByXpathConditionBO.build("input", "id", "stu_Name");
			By nameBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement nameInput = d.findElement(nameBy);
			nameInput.sendKeys(jokerName);
			
			WebElement phoneInput = d.findElement(By.id("stu_Phone"));
			phoneInput.sendKeys(jokerPhone);
			
			byXpathConditionBo = ByXpathConditionBO.build("select", "id", "stu_Country");
			By countrySelectBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement countrySelect = d.findElement(countrySelectBy);
			countrySelect.click();
			
			byXpathConditionBo = ByXpathConditionBO.build("option", "value", "亚洲");
			By countryOptionBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement countryOption = d.findElement(countryOptionBy);
			countryOption.click();
			
			byXpathConditionBo = ByXpathConditionBO.build("select", "id", "stu_Degree");
			By degreeSelectBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement degreeSelect = d.findElement(degreeSelectBy);
			degreeSelect.click();
			
			byXpathConditionBo = ByXpathConditionBO.build("option", "value", "本科");
			By degreeOptionBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement degreeOption = d.findElement(degreeOptionBy);
			degreeOption.click();
			
			byXpathConditionBo = ByXpathConditionBO.build("select", "id", "stu_City");
			By stuCitySelectBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement stuCitySelect = d.findElement(stuCitySelectBy);
			stuCitySelect.click();
			
			byXpathConditionBo = ByXpathConditionBO.build("option", "value", "广州");
			By stuCityOptionBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement stuCityOption = d.findElement(stuCityOptionBy);
			stuCityOption.click();
			
			byXpathConditionBo = ByXpathConditionBO.build("input", "type", "button").addCondition("id", "dosubmit");
			By submitBy = auxTool.byXpathBuilder(byXpathConditionBo);
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
