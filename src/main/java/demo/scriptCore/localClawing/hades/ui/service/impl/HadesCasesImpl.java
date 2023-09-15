package demo.scriptCore.localClawing.hades.ui.service.impl;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import demo.scriptCore.localClawing.hades.common.service.HadesCommonService;
import demo.scriptCore.localClawing.hades.ui.pageObject.HomePage;
import demo.scriptCore.localClawing.hades.ui.pageObject.LoginPage;
import demo.scriptCore.localClawing.hades.ui.service.HadesCases;

@Service
public class HadesCasesImpl extends HadesCommonService implements HadesCases {

	@Autowired
	private LoginPage loginPage;
	@Autowired
	private HomePage homePage;

	@Override
	public void login() {
		WebDriver webDriver = null;
		try {
			webDriver = webDriverService.buildChromeWebDriver();
			loginPage.getLoginPage(webDriver);
			loginPage.login(webDriver, hadesOptionFile.getUserList().get(0).getUsername(),
					hadesOptionFile.getUserList().get(0).getPwd());

		} catch (Exception e) {
			JsonReportOfCaseDTO errorReport = buildCaseReportDTO();
			reportService.caseReportAppendContent(errorReport, e.getMessage());
		}

		if (!tryQuitWebDriver(webDriver)) {
			sendTelegramMsg("Web driver quit failed");
		}
	}

	@Override
	public void test() {
		WebDriver webDriver = null;
		try {
			webDriver = webDriverService.buildChromeWebDriver();
			loginPage.getLoginPage(webDriver);
			loginPage.login(webDriver, hadesOptionFile.getUserList().get(0).getUsername(),
					hadesOptionFile.getUserList().get(0).getPwd());
			homePage.loadingCheck(webDriver);
			
			homePage.clickSupplierInfoManager(webDriver);
			System.out.println("click info manager");
			homePage.contentLoadingCheck(webDriver);
			System.out.println(homePage.getActiveTagName(webDriver));
			System.out.println("tag switch 供应商信息管理");
			homePage.tagSwitch(webDriver, "供应商信息管理");
			System.out.println(homePage.getActiveTagName(webDriver));
			
			System.out.println("click supplier certified manager");
			homePage.clickSupplierCertifiedManager(webDriver);
			homePage.contentLoadingCheck(webDriver);
			System.out.println(homePage.getActiveTagName(webDriver));
			
			System.out.println("tag switch 供应商信息管理");
			homePage.tagSwitch(webDriver, "供应商信息管理");
			homePage.contentLoadingCheck(webDriver);
			System.out.println(homePage.getActiveTagName(webDriver));
			
			System.out.println("content tag switch 关联联系人列表");
			System.out.println(homePage.contentTagSwitch(webDriver, "关联联系人列表"));
			homePage.contentLoadingCheck(webDriver);
			System.out.println(homePage.getActiveTagName(webDriver));
			
			System.out.println("content tag switch 供应商代注册");
			System.out.println(homePage.contentTagSwitch(webDriver, "供应商代注册"));
			homePage.contentLoadingCheck(webDriver);
			System.out.println(homePage.getActiveTagName(webDriver));
			

		} catch (Exception e) {
			JsonReportOfCaseDTO errorReport = buildCaseReportDTO();
			reportService.caseReportAppendContent(errorReport, e.getMessage());
		}

		if (!tryQuitWebDriver(webDriver)) {
			sendTelegramMsg("Web driver quit failed");
		}
	}
}
