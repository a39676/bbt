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
	
	@Override
	public void lanxiang() {
		TestEvent te = new TestEvent();
		te.setId(1L);
		te.setEventName("lx");
//		ChromeOptions options = new ChromeOptions();
		FirefoxOptions options = new FirefoxOptions();
//		options.addArguments(WebDriverGlobalOption.headLess);
//		WebDriver d = webDriverService.buildChromeWebDriver(options);
		WebDriver d = webDriverService.buildFireFoxWebDriver(options);
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
			ageInput.sendKeys("22");
			
			WebElement phoneInput = d.findElement(By.id("phone"));
			phoneInput.sendKeys(jokerPhone);
			
			byXpathConditionBo = ByXpathConditionBO.build("input", "name", "address");
			By addressBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement addressInput = d.findElement(addressBy);
			addressInput.sendKeys("广州市越秀区万菱广场2204室");
			
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
	
}
