package demo.scriptCore.localClawing.hades.service.impl;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import demo.scriptCore.localClawing.hades.pageObject.HomePage;
import demo.scriptCore.localClawing.hades.pageObject.LoginPage;
import demo.scriptCore.localClawing.hades.service.HadesCases;
import demo.scriptCore.localClawing.hades.service.HadesCommonService;

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
			homePage.contentLoadingCheck(webDriver);
			System.out.println(homePage.getActiveTagName(webDriver));
			
			homePage.clickSupplierCertifiedManager(webDriver);
			homePage.contentLoadingCheck(webDriver);
			System.out.println(homePage.getActiveTagName(webDriver));
			
			homePage.tagSwitch(webDriver, "供应商信息管理");
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
