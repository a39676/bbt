package demo.scriptCore.scheduleClawing.service.impl;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.scheduleClawing.pojo.dto.EducationInfoSubDTO;
import demo.scriptCore.scheduleClawing.pojo.dto.EducationInfoSummaryDTO;
import demo.scriptCore.scheduleClawing.pojo.dto.EducationInfoUrlDTO;
import demo.scriptCore.scheduleClawing.pojo.type.EducationInfoSourceType;
import demo.scriptCore.scheduleClawing.service.EducationInfoCollectionService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class EducationInfoCollectionServiceImpl extends AutomationTestCommonService
		implements EducationInfoCollectionService {

	private static final String PARAM_PATH_STR = "d:/home/u2/bbt/optionFile/tmp/educationInfoOption.json";

	@Override
	public TestEventBO clawing(TestEventBO tbo) {
		CommonResult r = new CommonResult();

		ScheduleClawingType caseType = ScheduleClawingType.EDUCATION_INFO;
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(caseType.getFlowName());
		
		WebDriver webDriver = null;

		try {
			FileUtilCustom ioUtil = new FileUtilCustom();
			String content = ioUtil.getStringFromFile(PARAM_PATH_STR);

			EducationInfoSummaryDTO mainDTO = new Gson().fromJson(content, EducationInfoSummaryDTO.class);

			if (mainDTO == null) {
				reportService.caseReportAppendContent(caseReport, "参数文件读取异常");
				throw new Exception();
			}
			
			webDriver = webDriverService.buildChromeWebDriver();

			threadSleepRandomTime();

			reportService.caseReportAppendContent(caseReport, "完成登录");

			r.setIsSuccess();

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			tryQuitWebDriver(webDriver);
			sendAutomationTestResult(tbo);
		}

		return tbo;
	}

	@Override
	public TestEventBO receiveAndBuildTestEventBO(AutomationTestInsertEventDTO dto) {
		TestEventBO bo = buildTestEventBOPreHandle(dto);

		TestModuleType modultType = TestModuleType.getType(dto.getTestModuleType());
		bo.setModuleType(modultType);
		ScheduleClawingType caseType = ScheduleClawingType.getType(dto.getFlowType());
		bo.setFlowName(caseType.getFlowName());
		bo.setFlowId(caseType.getId());
		bo.setEventId(dto.getTestEventId());
		bo.setAppointment(dto.getAppointment());
		bo.setParamStr(dto.getParamStr());

		return bo;
	}

	public void runInfoCollectorPrefix(EducationInfoSubDTO dto, WebDriver webDriver) {
//		TODO
		if (EducationInfoSourceType.GZEDUCMS_CN.equals(dto.getSourceType())) {

		} else if (EducationInfoSourceType.HAIZHU_GOV_CN.equals(dto.getSourceType())) {

		}
	}

	public void runInfoCollector(EducationInfoSubDTO dto, WebDriver d) {
		d.get(dto.getMainUrl());
		
//		尝试关闭悬浮窗
		try {
			WebElement floEleCloseSpan = d.findElement(By.xpath("//span[@id='ClickRemoveFlo']"));
			floEleCloseSpan.click();
		} catch (Exception e) {
		}
		
		/*
		 * class = c1-bline
		 * //body/div[1]/div[3]/div[1]/div[1]/div[2]/ul[1]/li[1]/div[1]/div[1]/div[1]/a[1]
		 */
		
		List<EducationInfoUrlDTO> infoUrlList = dto.getInfoUrlList();
		EducationInfoUrlDTO lastInfoUrl = infoUrlList.get(infoUrlList.size() - 1);
		
		String infosXpath = xPathBuilder.start("div").addClass("c1-bline").toString();
		List<WebElement> infoDivList = d.findElements(By.xpath(infosXpath));
		for(int i = 1; i < infoDivList.size() + 1; i++) {
			/*
			 * TODO 逐一对比
			 */
		}
	}
}
