package demo.scriptCore.localClawing.hades.pageObject;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Service;

@Service
public class HomePage {

	// 左侧主菜单
	public String centerBtnXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]";

	// 主页面标签栏
	public String subTagMenuXpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[1]";
	// 标签切换按钮
	public String subTagSwitchIconXpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[2]/div[1]/span[1]/i[1]";
	// 标签切换(左侧下拉标签格式)
	public String subTagDropListPatternXpath = "/html[1]/body[1]/ul[1]/li[%d]";
	// 标签切换(左侧下拉标签列表, 所有)
	public String subTagDropListXpath = "/html[1]/body[1]/ul[1]/li";
	// 标签列表(横, 所有)
	public String subTagListXpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/ul[1]/li";

	// 内容标签列表(内容窗口内标签warper)
	public String contentTagListWarperXpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]";
	// 内容标签(内容窗口内标签, 所有)
	public String contentTagXpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]/ul[1]/li/div[1]";

	// 左侧主菜单-供应商管理
	public String supplierManagerIconXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]";
	public String supplierManagerTextXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[2]/div[1]";
	// 左侧主菜单-供应商管理-供应商认证
	public String supplierCertifiedXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]";
	// 左侧主菜单-供应商管理-供应商信息管理
	public String supplierInfoManagerXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]";
	// 左侧主菜单-供应商管理-供应商准入管理
	public String supplierAccessManagerXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[3]/div[1]";
	// 左侧主菜单-供应商管理-供应商授信管理
	public String supplierCreditManagerXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[4]/div[1]";
	// 左侧主菜单-供应商管理-供应商绩效管理
	public String supplierPerformanceManagerXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[5]/div[1]";
	// 左侧主菜单-供应商管理-供应商黑名单管理
	public String supplierBlackListManagerXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[6]/div[1]";
	// 左侧主菜单-供应商管理-供应商淘汰管理
	public String supplierEliminationXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[7]/div[1]";

	public boolean loadingCheck(WebDriver d) {
		try {
			WebDriverWait wait = new WebDriverWait(d, Duration.ofSeconds(30));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(subTagMenuXpath)));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean contentLoadingCheck(WebDriver d) {
		try {
			JavascriptExecutor js = (JavascriptExecutor) d;
			return js.executeScript("return document.readyState").equals("complete");
		} catch (Exception e) {
			return false;
		}
	}

	public void clickSupplierManager(WebDriver d) {
		WebElement supplierManagerIcon = d.findElement(By.xpath(supplierManagerIconXpath));
		supplierManagerIcon.click();
	}

	public void clickSupplierCertifiedManager(WebDriver d) {
		clickSupplierManager(d);

		WebElement certifiedButton = d.findElement(By.xpath(supplierCertifiedXpath));
		certifiedButton.click();
	}

	public void clickSupplierInfoManager(WebDriver d) {
		clickSupplierManager(d);

		WebElement certifiedButton = d.findElement(By.xpath(supplierInfoManagerXpath));
		certifiedButton.click();
	}

	public void clickSupplierPerformanceManager(WebDriver d) {
		clickSupplierManager(d);

		WebElement certifiedButton = d.findElement(By.xpath(supplierPerformanceManagerXpath));
		certifiedButton.click();
	}

	public boolean tagSwitch(WebDriver d, String tagName) {
		boolean switchFlag = false;
		WebElement tagSwitchIcon = d.findElement(By.xpath(subTagSwitchIconXpath));
		tagSwitchIcon.click();
		List<WebElement> tagList = d.findElements(By.xpath(subTagDropListXpath));
		for (int i = 0; i < tagList.size() && !switchFlag; i++) {
			WebElement tag = tagList.get(i);
			if (tagName.equals(tag.getText())) {
				tag.click();
				for (int j = 0; j < 3; j++) {
					contentLoadingCheck(d);
					switchFlag = tagName.equals(getActiveTagName(d));
				}
			}
		}

		return switchFlag;
	}

	public boolean contentTagSwitch(WebDriver d, String tagName) {
		boolean switchFlag = false;
		List<WebElement> tagList = d.findElements(By.xpath(contentTagXpath));
		for (int i = 0; i < tagList.size() && !switchFlag; i++) {
			WebElement tag = tagList.get(i);
			if (tagName.equals(tag.getText())) {
				tag.click();
				switchFlag = true;
			}
		}

		return switchFlag;
	}

	public String getActiveTagName(WebDriver d) {
		List<WebElement> tagList = d.findElements(By.xpath(subTagListXpath));
		String activeClassName = "li-active";
		for (int i = 0; i < tagList.size(); i++) {
			WebElement tag = tagList.get(i);
			String cssClasses = tag.getAttribute("class");
			if (cssClasses.contains(activeClassName)) {
				return tag.getText();
			}
		}

		return null;
	}
}
