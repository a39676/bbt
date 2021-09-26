package demo.scriptCore.common.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.report.pojo.dto.JsonReportOfFlowDTO;
import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.pojo.dto.AutomationTestResultDTO;
import autoTest.testEvent.pojo.result.AutomationTestCaseResult;
import demo.autoTestBase.testEvent.mq.producer.AutomationTestResultProducer;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;

public abstract class AutomationTestCommonService extends SeleniumCommonService {

	@Autowired
	protected AutomationTestResultProducer automationTestResultProducer;

	protected TestEventBO buildTestEventBOPreHandle(AutomationTestInsertEventDTO dto) {
		return buildTestEventBOPreHandle(dto, true);
	}
	
	protected TestEventBO buildTestEventBOPreHandle(AutomationTestInsertEventDTO dto, Boolean needWebDriver) {
		TestEventBO tbo = new TestEventBO();
		tbo.setStartTime(LocalDateTime.now());
		
		JsonReportOfFlowDTO reportDTO = new JsonReportOfFlowDTO();
		tbo.setReport(reportDTO);

		if(needWebDriver != null && needWebDriver) {
			try {
				tbo.setWebDriver(webDriverService.buildChromeWebDriver());
			} catch (Exception e) {
				log.error("automation test build web driver error");
				return null;
			}
		}

		return tbo;
	}

	protected AutomationTestCaseResult buildCaseResult(String casename) {
		AutomationTestCaseResult r = new AutomationTestCaseResult();
		r.setCaseName(casename);
		return r;
	}

	protected JsonReportOfCaseDTO buildCaseReportDTO(String casename) {

		JsonReportOfCaseDTO report = new JsonReportOfCaseDTO();
		report.setReportElementList(new ArrayList<>());
		report.setCaseTypeName(casename);

		return report;
	}
	
	protected JsonReportOfCaseDTO buildCaseReportDTO() {
		JsonReportOfCaseDTO report = new JsonReportOfCaseDTO();
		report.setReportElementList(new ArrayList<>());
		report.setCaseTypeName("running error");
		return report;
	}

	protected AutomationTestResultDTO buildAutomationTestResultDTO(TestEventBO bo) {
		AutomationTestResultDTO dto = new AutomationTestResultDTO();
		
		dto.setStartTime(bo.getStartTime());
		dto.setEndTime(bo.getEndTime());
		dto.setTestEventId(bo.getEventId());
		dto.setReport(bo.getReport());
		dto.setCaseResultList(bo.getCaseResultList());
		dto.setRemark(bo.getRemark());
		return dto;
	}
	
	protected void sendAutomationTestResult(TestEventBO bo) {
		AutomationTestResultDTO dto = buildAutomationTestResultDTO(bo);
		automationTestResultProducer.send(dto);
	}
}
