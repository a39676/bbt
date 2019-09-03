package demo.selenium.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.service.SeForJoke;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;

@Service
public class SeForJokeImpl extends CommonService implements SeForJoke {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxiliaryToolService;
//	@Autowired
//	private JavaScriptCommonUtil jsUtil;
	
	private String jokerName = "冯安";
	
	@Override
	public void lanxiang() {
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
			
			WebElement xueyuanSelectorEle = auxiliaryToolService.fluentWait(d, xueyuanSelectorBy);
			xueyuanSelectorEle.click();
			
			ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("option", "value", "11");
			By xueyuanOptionBy = auxiliaryToolService.byXpathBuilder(byXpathConditionBo);
			WebElement xueyuanOption = d.findElement(xueyuanOptionBy);
			xueyuanOption.click();
			
			WebElement zhuanyeSelector = d.findElement(By.id("zhuanye"));
			zhuanyeSelector.click();
			byXpathConditionBo = ByXpathConditionBO.build("option", "value", "12");
			By zhuanyeOptionBy = auxiliaryToolService.byXpathBuilder(byXpathConditionBo);
			WebElement zhuanyeOption = d.findElement(zhuanyeOptionBy);
			zhuanyeOption.click();
			
			byXpathConditionBo = ByXpathConditionBO.build("input", "name", "name").addCondition("type", "text");
			By nameInputBy = auxiliaryToolService.byXpathBuilder(byXpathConditionBo);
			WebElement nameInput = d.findElement(nameInputBy);
			nameInput.sendKeys(jokerName);
			
			byXpathConditionBo = ByXpathConditionBO.build("input", "type", "radio").addCondition("name", "sex");
			By genderBy = auxiliaryToolService.byXpathBuilder(byXpathConditionBo);
			WebElement genderMaleRadio = d.findElement(genderBy);
			genderMaleRadio.click();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(d != null) {
				d.quit();
			}
		}
	}
	
}
