package demo.scriptCore.scheduleClawing.jobInfo.service.impl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.scheduleClawing.common.pojo.dto.CollectUrlHistoryDTO;
import demo.scriptCore.scheduleClawing.jobInfo.pojo.dto.V2exJobInfoOptionDTO;
import demo.scriptCore.scheduleClawing.jobInfo.service.JobInfoCollectionCommonService;
import demo.scriptCore.scheduleClawing.jobInfo.service.V2exJobInfoCollectionService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class V2exJobInfoCollectionServiceImpl extends JobInfoCollectionCommonService
		implements V2exJobInfoCollectionService {

	private static final String PARAM_PATH_STR = MAIN_FOLDER_PATH + "/optionFile/automationTest/v2exJobInfoOption.json";

	@Override
	public TestEventBO clawing(TestEventBO tbo) {
		CommonResult r = new CommonResult();

		deleteOldUrls(PARAM_PATH_STR);

		ScheduleClawingType caseType = ScheduleClawingType.V2EX_JOB_INFO;
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(caseType.getFlowName());

		WebDriver webDriver = null;

		try {
			FileUtilCustom ioUtil = new FileUtilCustom();
			String content = ioUtil.getStringFromFile(PARAM_PATH_STR);

			V2exJobInfoOptionDTO dto = buildObjFromJsonCustomization(content, V2exJobInfoOptionDTO.class);
			if (dto == null) {
				reportService.caseReportAppendContent(caseReport, "参数文件结构异常");
				throw new Exception();
			}

			webDriver = webDriverService.buildChromeWebDriver();

			List<CollectUrlHistoryDTO> newUrlList = null;

			try {
				for (int pageNum = 1; pageNum <= dto.getMaxPageSize(); pageNum++) {
					newUrlList = runCollector(webDriver, dto, pageNum);
					if (!newUrlList.isEmpty()) {
						if (dto.getUrlHistory() == null) {
							dto.setUrlHistory(new ArrayList<>());
						}
						dto.getUrlHistory().addAll(newUrlList);
					}
				}
			} catch (Exception e) {
			}

			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter)
					.setPrettyPrinting().create();
			String jsonString = gson.toJson(dto);
			ioUtil.byteToFile(jsonString.toString().getBytes(StandardCharsets.UTF_8), PARAM_PATH_STR);

			r.setIsSuccess();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if(!tryQuitWebDriver(webDriver)) {
			sendTelegramMsg("Web driver quit failed, " + caseType.getFlowName());
		}
		sendAutomationTestResult(tbo);

		return tbo;
	}

	private List<CollectUrlHistoryDTO> runCollector(WebDriver d, V2exJobInfoOptionDTO dto, int pageNum) {
		d.get(dto.getMainUrl() + "?p=" + pageNum);

		try {
			threadSleepRandomTime();
		} catch (Exception e) {
		}

		Set<String> urlStrHistorySet = new HashSet<>();
		if (dto.getUrlHistory() != null) {
			for (CollectUrlHistoryDTO urlDTO : dto.getUrlHistory()) {
				urlStrHistorySet.add(urlDTO.getUrl());
			}
		}

		List<CollectUrlHistoryDTO> newInfoUrlList = new ArrayList<>();

//		逐个 url 匹配
		CollectUrlHistoryDTO tmpDTO = null;
		String normalXpath = "/html[1]/body[1]/div[2]/div[1]/div[3]/div[3]/div[2]/div/table[1]/tbody[1]/tr[1]/td[3]/span[1]/a[1]";
		List<WebElement> aList = d.findElements(By.xpath(normalXpath));
		String tmpUrl = null;
		boolean containKeyword = false;
		for (WebElement ele : aList) {
			for (int i = 0; !containKeyword && i < dto.getKeywords().size(); i++) {
				containKeyword = ele.getText() != null
						&& ele.getText().toLowerCase().contains(dto.getKeywords().get(i));
			}

			if (!containKeyword) {
				continue;
			}

			tmpUrl = ele.getAttribute("href");
			if (StringUtils.isNotBlank(tmpUrl) && tmpUrl.contains("#")) {
				tmpUrl = tmpUrl.substring(0, tmpUrl.indexOf("#"));
				if (!urlStrHistorySet.contains(tmpUrl)) {
					tmpDTO = new CollectUrlHistoryDTO();
					tmpDTO.setRecrodDate(LocalDateTime.now());
					tmpDTO.setUrl(tmpUrl);
					newInfoUrlList.add(tmpDTO);
					sendTelegramMsg("New url: " + tmpUrl + " , title: " + ele.getText());
				}
			}

			containKeyword = false;
		}

		return newInfoUrlList;
	}

}
