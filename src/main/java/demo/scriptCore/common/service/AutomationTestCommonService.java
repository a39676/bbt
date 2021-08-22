package demo.scriptCore.common.service;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import at.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.pojo.dto.AutomationTestResultDTO;
import autoTest.testEvent.pojo.result.AutomationTestCaseResult;
import demo.autoTestBase.testEvent.mq.producer.AutomationTestResultProducer;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import toolPack.dateTimeHandle.DateTimeUtilCommon;
import toolPack.ioHandle.FileUtilCustom;

public abstract class AutomationTestCommonService extends SeleniumCommonService {

	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	protected AutomationTestResultProducer automationTestResultProducer;

	protected Path savingTestEventDynamicParam(BuildTestEventBO bo, String paramStr) {
		LocalDateTime now = LocalDateTime.now();
		String nowStr = localDateTimeHandler.dateToStr(now, DateTimeUtilCommon.dateTimeFormatNoSymbol);
		File mainFolder = new File(MAIN_FOLDER_PATH + File.separator + "automationTestDynamicParam" + File.separator
				+ bo.getTestModuleType().getModuleName() + File.separator + bo.getFlowName() + File.separator + nowStr);
		if (!mainFolder.exists() || !mainFolder.isDirectory()) {
			mainFolder.mkdirs();
		}
		File newParamFile = new File(
				mainFolder.getAbsolutePath() + File.separator + bo.getEventId().toString() + ".json");
		try {
			ioUtil.byteToFile(paramStr.getBytes(StandardCharsets.UTF_8), newParamFile.getAbsolutePath());
		} catch (Exception e) {
			log.error(bo.getFlowName() + ", save param file error: " + e.getLocalizedMessage());
		}

		return newParamFile.toPath();
	}

	protected AutomationTestCaseResult buildCaseResult(Object flowType) {
		AutomationTestCaseResult r = new AutomationTestCaseResult();
		Class<? extends Object> flowTypeClass = flowType.getClass();
		try {
			Method getFlowNameMethod = flowTypeClass.getMethod("getFlowName");
			r.setCaseName((String) getFlowNameMethod.invoke(flowType));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e1) {
			e1.printStackTrace();
			log.error("build AutomationTestFlowResult error, can NOT find getFlowName method: " + e1.getLocalizedMessage());
			r.setCaseName(null);
		}

		try {
			Method getIdMethod = flowTypeClass.getMethod("getId");
			r.setCaseId((Long) getIdMethod.invoke(flowType));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e1) {
			e1.printStackTrace();
			log.error("build AutomationTestFlowResult error, can NOT find getId method: " + e1.getLocalizedMessage());
			r.setCaseId(null);
		}
		
		return r;
	}

	protected JsonReportOfCaseDTO buildCaseReportDTO(Object flowType) {

		JsonReportOfCaseDTO report = new JsonReportOfCaseDTO();
		report.setReportElementList(new ArrayList<>());
		
		Class<? extends Object> flowTypeClass = flowType.getClass();
		try {
			Method getFlowNameMethod = flowTypeClass.getMethod("getFlowName");
			
			report.setCaseTypeName((String) getFlowNameMethod.invoke(flowType));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e1) {
			e1.printStackTrace();
			log.error("build AutomationTestFlowResult error, can NOT find getFlowName method: " + e1.getLocalizedMessage());
			report.setCaseTypeName(null);
		}

		try {
			Method getIdMethod = flowTypeClass.getMethod("getId");
			report.setCaseTypeID((Long) getIdMethod.invoke(flowType));
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException e1) {
			e1.printStackTrace();
			log.error("build AutomationTestFlowResult error, can NOT find getId method: " + e1.getLocalizedMessage());
			report.setCaseTypeID(null);
		}
		
		return report;
	}
	
	protected JsonReportOfCaseDTO buildCaseReportDTO() {
		JsonReportOfCaseDTO report = new JsonReportOfCaseDTO();
		report.setReportElementList(new ArrayList<>());
		report.setCaseTypeName("running error");
		report.setCaseTypeID(-1L);
		return report;
	}

	protected AutomationTestResultDTO buildAutomationTestResultDTO(TestEventBO bo) {
		AutomationTestResultDTO dto = new AutomationTestResultDTO();
		
		dto.setStartTime(bo.getStartTime());
		dto.setEndTime(bo.getEndTime());
		dto.setTestEventId(bo.getEvent().getId());
		dto.setReport(bo.getReport());
		dto.setCaseResultList(bo.getCaseResultList());
		dto.setRemark(bo.getEvent().getRemark());
		return dto;
	}
	
	protected void sendAutomationTestResult(TestEventBO bo) {
		AutomationTestResultDTO dto = buildAutomationTestResultDTO(bo);
		automationTestResultProducer.send(dto);
	}
}
