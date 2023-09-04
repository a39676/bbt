package demo.scriptCore.localClawing.hades.service.impl;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import demo.scriptCore.localClawing.hades.pageObject.LoginPage;
import demo.scriptCore.localClawing.hades.service.HadesCases;
import demo.scriptCore.localClawing.hades.service.HadesCommonService;

@Service
public class HadesCasesImpl extends HadesCommonService implements HadesCases {

	@Autowired
	private LoginPage loginPage;
	
	@Override
	public void login() {
		WebDriver webDriver = null;
		try {
			webDriver = webDriverService.buildChromeWebDriver();
			loginPage.getLoginPage(webDriver);
			loginPage.login(webDriver, hadesOptionFile.getUserList().get(0).getUsername(), hadesOptionFile.getUserList().get(0).getPwd());
			
		} catch (Exception e) {
			JsonReportOfCaseDTO errorReport = buildCaseReportDTO();
			reportService.caseReportAppendContent(errorReport, e.getMessage());
		}
	}
}
