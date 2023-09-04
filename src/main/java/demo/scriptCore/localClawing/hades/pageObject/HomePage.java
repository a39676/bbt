package demo.scriptCore.localClawing.hades.pageObject;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

@Service
public class HomePage {

	// 左侧主菜单
	public String centerBtnXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]";
	// 主页面标签栏
	public String subTagMenuXpath = "/html[1]/body[1]/div[1]/div[1]/div[2]/div[2]/div[1]";
	
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
	
	public void clickSupplierCertifiedManager(WebDriver d) {
		WebElement supplierManagerIcon = d.findElement(By.xpath(supplierManagerIconXpath));
		supplierManagerIcon.click();
		
		WebElement certifiedButton = d.findElement(By.xpath(supplierCertifiedXpath));
		certifiedButton.click();
	}
}
