package demo.scriptCore.scheduleClawing.service.impl;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.scheduleClawing.pojo.dto.EducationInfoOptionDTO;
import demo.scriptCore.scheduleClawing.pojo.type.EducationInfoSourceType;
import demo.scriptCore.scheduleClawing.service.EducationInfoCollectionService;
import demo.tool.mq.producer.TelegramCalendarNoticeMessageAckProducer;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import telegram.pojo.constant.TelegramBotType;
import telegram.pojo.constant.TelegramStaticChatID;
import telegram.pojo.dto.TelegramMessageDTO;
import toolPack.httpHandel.HttpUtil;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class EducationInfoCollectionServiceImpl extends AutomationTestCommonService
		implements EducationInfoCollectionService {

	private static final String PARAM_PATH_STR = "/home/u2/bbt/optionFile/automationTest/educationInfoOption.json";

	@Autowired
	private TelegramCalendarNoticeMessageAckProducer telegramMessageAckProducer;

	@Override
	public TestEventBO clawing(TestEventBO tbo) {
		CommonResult r = new CommonResult();

		ScheduleClawingType caseType = ScheduleClawingType.EDUCATION_INFO;
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(caseType.getFlowName());

		WebDriver webDriver = null;

		try {
			FileUtilCustom ioUtil = new FileUtilCustom();
			String content = ioUtil.getStringFromFile(PARAM_PATH_STR);

			EducationInfoOptionDTO dto = new Gson().fromJson(content, EducationInfoOptionDTO.class);
			if (dto == null) {
				reportService.caseReportAppendContent(caseReport, "参数文件结构异常");
				throw new Exception();
			}

			webDriver = webDriverService.buildChromeWebDriver();
			
			List<String> newUrlList = runHaizhuGovInfoCollector(
					dto.getSourceUrl().get(EducationInfoSourceType.HAIZHU_GOV_CN.getName()), dto.getUrlHistory());
			if (!newUrlList.isEmpty()) {
				dto.getUrlHistory().addAll(newUrlList);
			}

			newUrlList = runGzEduCmsInfoCollector(webDriver,
					dto.getSourceUrl().get(EducationInfoSourceType.GZEDUCMS_CN.getName()), dto.getUrlHistory());
			if (!newUrlList.isEmpty()) {
				dto.getUrlHistory().addAll(newUrlList);
			}

			JSONObject json = JSONObject.fromObject(dto);
			ioUtil.byteToFile(json.toString().getBytes(StandardCharsets.UTF_8), PARAM_PATH_STR);

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

	private List<String> runHaizhuGovInfoCollector(String mainUrl, List<String> urlHistoryList) {
//		text 参数可配置搜索关键字
		HttpUtil h = new HttpUtil();

		List<String> newInfoUrlList = new ArrayList<>();

		try {
			String content = h.sendGet(mainUrl);
			content = content.replaceAll("startMark", "");
			content = content.substring(1, content.length() - 1);
			JSONObject json = JSONObject.fromObject(content);
			JSONArray resultList = json.getJSONArray("results");
			for (int i = 0; i < resultList.size(); i++) {
				JSONObject j = resultList.getJSONObject(i);
				String title = j.getString("title");
				String url = j.getString("url");
				if (!urlHistoryList.contains(url)) {
					newInfoUrlList.add(url);
					sendMsg("New url: " + url + " , title: " + title);
				}
			}
		} catch (Exception e) {
		}

		return newInfoUrlList;
	}
	
	private List<String> runGzEduCmsInfoCollector(WebDriver d, String mainUrl, List<String> urlHistoryList) {
		d.get(mainUrl);

		try {
			threadSleepRandomTime();
		} catch (Exception e) {
		}
		
//		尝试关闭悬浮窗
		try {
			WebElement floEleCloseSpan = d.findElement(By.xpath("//span[@id='ClickRemoveFlo']"));
			floEleCloseSpan.click();
		} catch (Exception e) {
		}

		List<String> newInfoUrlList = new ArrayList<>();

//		尝试获取所有通知的列表
		String infosXpath = xPathBuilder.start().addClass("c1-bline").getXpath();
		List<WebElement> infoDivList = d.findElements(By.xpath(infosXpath));

		String normalXpath = "//body/div[1]/div[3]/div[1]/div[1]/div[2]/ul[1]/li[1]/div[1]/div[_]/div[1]/a[1]";
//		                      //body/div[1]/div[3]/div[1]/div[1]/div[2]/ul[1]/li[1]/div[1]/div[1]/div[1]/a[1]

		try {
			threadSleepRandomTime();
		} catch (Exception e) {
		}
		
//		逐个 url 匹配
		for (int i = 1; i < infoDivList.size() + 1; i++) {
			try {
				String tmpPath = normalXpath.replaceAll("_", String.valueOf(i));
				WebElement urlA = d.findElement(By.xpath(tmpPath));
				String urlStr = urlA.getAttribute("href");
				String title = urlA.getAttribute("title");
				urlStr = mainUrl + urlStr;
				if (!urlHistoryList.contains(urlStr)) {
					newInfoUrlList.add(urlStr);
					sendMsg("New url: " + urlStr + " , title: " + title);
				}
			} catch (Exception e) {
			}
		}

		return newInfoUrlList;
	}

	private void sendMsg(String msg) {
		TelegramMessageDTO dto = new TelegramMessageDTO();
		dto.setId(TelegramStaticChatID.MY_ID);
		dto.setBotName(TelegramBotType.CX_CALENDAR_NOTICE_BOT.getName());
		dto.setMsg(msg);
		telegramMessageAckProducer.send(dto);
	}

}
