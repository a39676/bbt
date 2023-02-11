package demo.scriptCore.common.service;

import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import at.tool.WebDriverATToolService;
import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.common.pojo.dto.AutomationTestResultDTO;
import autoTest.testEvent.common.pojo.result.AutomationTestCaseResult;
import demo.autoTestBase.testEvent.mq.producer.AutomationTestResultProducer;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import demo.tool.mq.producer.TelegramCalendarNoticeMessageAckProducer;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.dto.TelegramBotNoticeMessageDTO;

public abstract class AutomationTestCommonService extends SeleniumCommonService {

	@Autowired
	protected WebDriverATToolService webATToolService;
	@Autowired
	protected AutomationTestResultProducer automationTestResultProducer;
	@Autowired
	private TelegramCalendarNoticeMessageAckProducer telegramMessageAckProducer;

	protected AutomationTestCaseResult initCaseResult(String casename) {
		AutomationTestCaseResult r = new AutomationTestCaseResult();
		r.setCaseName(casename);
		return r;
	}

	protected JsonReportOfCaseDTO initCaseReportDTO(String casename) {

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
		bo.setEndTime(LocalDateTime.now());
		AutomationTestResultDTO dto = buildAutomationTestResultDTO(bo);
		automationTestResultProducer.send(dto);
	}
	
	protected void sendTelegramMsg(String msg) {
		TelegramBotNoticeMessageDTO dto = new TelegramBotNoticeMessageDTO();
		dto.setId(TelegramStaticChatID.MY_ID);
		dto.setBotName(TelegramBotType.CX_CALENDAR_NOTICE_BOT.getName());
		dto.setMsg(msg);
		telegramMessageAckProducer.send(dto);
	}
}
