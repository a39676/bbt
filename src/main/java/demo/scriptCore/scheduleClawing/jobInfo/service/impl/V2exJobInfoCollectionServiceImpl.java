package demo.scriptCore.scheduleClawing.jobInfo.service.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
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
		sendTelegramMsg("Start v2ex collecting");
		CommonResult r = new CommonResult();

		deleteOldUrls(PARAM_PATH_STR);

		ScheduleClawingType caseType = ScheduleClawingType.V2EX_JOB_INFO;
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(caseType.getFlowName());

		try {
			FileUtilCustom ioUtil = new FileUtilCustom();
			String content = ioUtil.getStringFromFile(PARAM_PATH_STR);

			V2exJobInfoOptionDTO dto = buildObjFromJsonCustomization(content, V2exJobInfoOptionDTO.class);
			if (dto == null) {
				reportService.caseReportAppendContent(caseReport, "参数文件结构异常");
				throw new Exception();
			}

			List<CollectUrlHistoryDTO> newUrlList = null;

			try {
				for (int pageNum = 1; pageNum <= dto.getMaxPageSize(); pageNum++) {
					newUrlList = runCollectorWithJavaUrl(dto, pageNum);
					if (!newUrlList.isEmpty()) {
						if (dto.getUrlHistory() == null) {
							dto.setUrlHistory(new ArrayList<>());
						}
						dto.getUrlHistory().addAll(newUrlList);
					}
				}
			} catch (Exception e) {
				sendTelegramMsg("Hit error when collect data" + e.getLocalizedMessage());
				reportService.caseReportAppendContent(caseReport,
						"V2ex data collector error: " + e.getLocalizedMessage());
				tbo.getReport().getCaseReportList().add(caseReport);
			}

			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter)
					.setPrettyPrinting().create();
			String jsonString = gson.toJson(dto);
			ioUtil.byteToFile(jsonString.toString().getBytes(StandardCharsets.UTF_8), PARAM_PATH_STR);

			r.setIsSuccess();

		} catch (Exception e) {
			sendTelegramMsg("Hit error when collect data" + e.getLocalizedMessage());
			reportService.caseReportAppendContent(caseReport, "V2ex data collector error: " + e.getLocalizedMessage());
			tbo.getReport().getCaseReportList().add(caseReport);
		}

//		if (!tryQuitWebDriver(webDriver)) {
//			sendTelegramMsg("Web driver quit failed, " + caseType.getFlowName());
//		}
		sendAutomationTestResult(tbo);

		return tbo;
	}

	private List<CollectUrlHistoryDTO> runCollectorWithJavaUrl(V2exJobInfoOptionDTO dto, int pageNum) {

		String mainUrlStr = "https://v2ex.com/";
		String urlStr = mainUrlStr + "/go/jobs" + "?p=" + pageNum;

		URL url = null;
		try {
			url = new URI(urlStr).toURL();
		} catch (MalformedURLException | URISyntaxException e) {
		}

		StringBuffer htmlStrBuffer = new StringBuffer();
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
			for (String line; (line = reader.readLine()) != null;) {
				htmlStrBuffer.append(line);
			}
		} catch (Exception e) {
		}

		String htmlStr = htmlStrBuffer.toString();

		Set<String> urlStrHistorySet = new HashSet<>();
		if (dto.getUrlHistory() != null) {
			for (CollectUrlHistoryDTO urlDTO : dto.getUrlHistory()) {
				urlStrHistorySet.add(urlDTO.getUrl());
			}
		}

		Element doc = Jsoup.parse(htmlStr);
		Elements topicList = doc.select("a.topic-link");

//		逐个 url 匹配
		CollectUrlHistoryDTO tmpDTO = null;
		String tmpUrl = null;
		boolean containKeyword = false;
		List<CollectUrlHistoryDTO> newInfoUrlList = new ArrayList<>();

		for (Element t : topicList) {
			for (int i = 0; !containKeyword && i < dto.getKeywords().size(); i++) {
				containKeyword = t.text() != null && t.text().toLowerCase().contains(dto.getKeywords().get(i));
			}

			if (!containKeyword) {
				continue;
			}

			tmpUrl = t.attr("href");
			if (StringUtils.isNotBlank(tmpUrl) && tmpUrl.contains("#")) {
				tmpUrl = tmpUrl.substring(0, tmpUrl.indexOf("#"));
				if (!urlStrHistorySet.contains(tmpUrl)) {
					tmpDTO = new CollectUrlHistoryDTO();
					tmpDTO.setRecrodDate(LocalDateTime.now());
					tmpDTO.setUrl(tmpUrl);
					newInfoUrlList.add(tmpDTO);
					sendTelegramMsg("New url: " + mainUrlStr + tmpUrl + " , title: " + t.text());
				}
			}

			containKeyword = false;
		}

		return newInfoUrlList;
	}

	@SuppressWarnings("unused")
	private List<CollectUrlHistoryDTO> runCollectorInWebDriver(WebDriver d, V2exJobInfoOptionDTO dto, int pageNum) {
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
