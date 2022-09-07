package demo.scriptCore.scheduleClawing.educationInfo.service.impl;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.scheduleClawing.common.pojo.dto.CollectUrlHistoryDTO;
import demo.scriptCore.scheduleClawing.educationInfo.pojo.dto.EducationInfoOptionDTO;
import demo.scriptCore.scheduleClawing.educationInfo.pojo.type.EducationInfoSourceType;
import demo.scriptCore.scheduleClawing.educationInfo.service.EducationInfoCollectionService;
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
		
		deleteOldUrls();

		ScheduleClawingType caseType = ScheduleClawingType.EDUCATION_INFO;
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(caseType.getFlowName());

		WebDriver webDriver = null;

		try {
			FileUtilCustom ioUtil = new FileUtilCustom();
			String content = ioUtil.getStringFromFile(PARAM_PATH_STR);

			EducationInfoOptionDTO dto = buildObjFromJsonCustomization(content, EducationInfoOptionDTO.class);
			if (dto == null) {
				reportService.caseReportAppendContent(caseReport, "参数文件结构异常");
				throw new Exception();
			}

			webDriver = webDriverService.buildChromeWebDriver();

			List<CollectUrlHistoryDTO> newUrlList = null;
			EducationInfoSourceType sourceType = null;

			try {
				sourceType = EducationInfoSourceType.HAIZHU_GOV_CN;
				newUrlList = runHaizhuGovInfoCollector(dto.getSourceUrl().get(sourceType.getName()),
						dto.getUrlHistory().get(sourceType.getName()));
				if (!newUrlList.isEmpty()) {
					if (!dto.getUrlHistory().containsKey(sourceType.getName())) {
						dto.getUrlHistory().put(sourceType.getName(), new ArrayList<>());
					}
					dto.getUrlHistory().get(sourceType.getName()).addAll(newUrlList);
				}
			} catch (Exception e) {
			}

			try {
				sourceType = EducationInfoSourceType.JYJ_GZ;
				newUrlList = runGzJyjInfoCollector(dto.getSourceUrl().get(sourceType.getName()),
						dto.getUrlHistory().get(sourceType.getName()));
				if (!newUrlList.isEmpty()) {
					if (!dto.getUrlHistory().containsKey(sourceType.getName())) {
						dto.getUrlHistory().put(sourceType.getName(), new ArrayList<>());
					}
					dto.getUrlHistory().get(sourceType.getName()).addAll(newUrlList);
				}
			} catch (Exception e) {
			}
			
			try {
				sourceType = EducationInfoSourceType.GZZK_1;
				newUrlList = runGZZK_1Collector(dto.getSourceUrl().get(sourceType.getName()),
						dto.getUrlHistory().get(sourceType.getName()));
				if (!newUrlList.isEmpty()) {
					if (!dto.getUrlHistory().containsKey(sourceType.getName())) {
						dto.getUrlHistory().put(sourceType.getName(), new ArrayList<>());
					}
					dto.getUrlHistory().get(sourceType.getName()).addAll(newUrlList);
				}
			} catch (Exception e) {
			}

			try {
				sourceType = EducationInfoSourceType.GZEDUCMS_CN;
				newUrlList = runGzEduCmsInfoCollector(webDriver, dto.getSourceUrl().get(sourceType.getName()),
						dto.getUrlHistory().get(sourceType.getName()));
				if (!newUrlList.isEmpty()) {
					if (!dto.getUrlHistory().containsKey(sourceType.getName())) {
						dto.getUrlHistory().put(sourceType.getName(), new ArrayList<>());
					}
					dto.getUrlHistory().get(sourceType.getName()).addAll(newUrlList);
				}
			} catch (Exception e) {
			}

			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).setPrettyPrinting().create();
			String jsonString = gson.toJson(dto);
			ioUtil.byteToFile(jsonString.toString().getBytes(StandardCharsets.UTF_8), PARAM_PATH_STR);

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

	private List<CollectUrlHistoryDTO> runHaizhuGovInfoCollector(String mainUrl, List<CollectUrlHistoryDTO> urlHistoryList) {
//		text 参数可配置搜索关键字
		HttpUtil h = new HttpUtil();

		List<CollectUrlHistoryDTO> newInfoUrlList = new ArrayList<>();
		Set<String> urlStrHistorySet = new HashSet<>();
		if(urlHistoryList != null) {
			for (CollectUrlHistoryDTO urlDTO : urlHistoryList) {
				urlStrHistorySet.add(urlDTO.getUrl());
			}
		}

		try {
			String content = h.sendGet(mainUrl);
			content = content.replaceAll("startMark", "");
			content = content.substring(1, content.length() - 1);
			JSONObject json = JSONObject.fromObject(content);
			JSONArray resultList = json.getJSONArray("results");
			CollectUrlHistoryDTO tmpDTO = null;
			for (int i = 0; i < resultList.size(); i++) {
				JSONObject j = resultList.getJSONObject(i);
				String title = j.getString("title");
				String url = j.getString("url");
				if (!urlStrHistorySet.contains(url)) {
					tmpDTO = new CollectUrlHistoryDTO();
					tmpDTO.setRecrodDate(LocalDateTime.now());
					tmpDTO.setUrl(url);
					newInfoUrlList.add(tmpDTO);
					sendMsg("New url: " + url + " , title: " + title);
				}
			}
		} catch (Exception e) {
		}

		return newInfoUrlList;
	}

	private List<CollectUrlHistoryDTO> runGzJyjInfoCollector(String mainUrl, List<CollectUrlHistoryDTO> urlHistoryList) {
		HttpUtil h = new HttpUtil();
		List<CollectUrlHistoryDTO> newInfoUrlList = new ArrayList<>();

		Set<String> urlStrHistorySet = new HashSet<>();
		if(urlHistoryList != null) {
			for (CollectUrlHistoryDTO urlDTO : urlHistoryList) {
				urlStrHistorySet.add(urlDTO.getUrl());
			}
		}
		
		try {
			String content = h.sendGet(mainUrl);

			Element doc = Jsoup.parse(content);
			Elements newsListDiv = doc.select("div.news_list");
			Elements targetUl = newsListDiv.select("ul");
			Elements targetAlinkList = targetUl.select("a[href]");
			CollectUrlHistoryDTO tmpDTO = null;
			for (Element eleA : targetAlinkList) {
				String title = eleA.attr("title");
				String url = eleA.attr("href");
				if (!urlStrHistorySet.contains(url)) {
					tmpDTO = new CollectUrlHistoryDTO();
					tmpDTO.setRecrodDate(LocalDateTime.now());
					tmpDTO.setUrl(url);
					newInfoUrlList.add(tmpDTO);
					sendMsg("New url: " + url + " , title: " + title);
				}
			}
		} catch (Exception e) {
		}

		return newInfoUrlList;

	}

	private List<CollectUrlHistoryDTO> runGzEduCmsInfoCollector(WebDriver d, String mainUrl,
			List<CollectUrlHistoryDTO> urlHistoryList) {
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

		List<CollectUrlHistoryDTO> newInfoUrlList = new ArrayList<>();

//		尝试获取所有通知的列表
		String infosXpath = xPathBuilder.start().addClass("c1-bline").getXpath();
		List<WebElement> infoDivList = d.findElements(By.xpath(infosXpath));

		String normalXpath = "//body/div[1]/div[3]/div[1]/div[1]/div[2]/ul[1]/li[1]/div[1]/div[_]/div[1]/a[1]";
//		                      //body/div[1]/div[3]/div[1]/div[1]/div[2]/ul[1]/li[1]/div[1]/div[1]/div[1]/a[1]

		try {
			threadSleepRandomTime();
		} catch (Exception e) {
		}

		Set<String> urlStrHistorySet = new HashSet<>();
		if(urlHistoryList != null) {
			for (CollectUrlHistoryDTO urlDTO : urlHistoryList) {
				urlStrHistorySet.add(urlDTO.getUrl());
			}
		}

//		逐个 url 匹配
		CollectUrlHistoryDTO tmpDTO = null;
		for (int i = 1; i < infoDivList.size() + 1; i++) {
			try {
				String tmpPath = normalXpath.replaceAll("_", String.valueOf(i));
				WebElement urlA = d.findElement(By.xpath(tmpPath));
				String urlStr = urlA.getAttribute("href");
				String title = urlA.getAttribute("title");
				urlStr = mainUrl + urlStr;
				if (!urlStrHistorySet.contains(urlStr)) {
					tmpDTO = new CollectUrlHistoryDTO();
					tmpDTO.setRecrodDate(LocalDateTime.now());
					tmpDTO.setUrl(urlStr);
					newInfoUrlList.add(tmpDTO);
					sendMsg("New url: " + urlStr + " , title: " + title);
				}
			} catch (Exception e) {
			}
		}

		return newInfoUrlList;
	}
	
	private List<CollectUrlHistoryDTO> runGZZK_1Collector(String mainUrl, List<CollectUrlHistoryDTO> urlHistoryList) {
		HttpUtil h = new HttpUtil();
		List<CollectUrlHistoryDTO> newInfoUrlList = new ArrayList<>();

		Set<String> urlStrHistorySet = new HashSet<>();
		if(urlHistoryList != null) {
			for (CollectUrlHistoryDTO urlDTO : urlHistoryList) {
				urlStrHistorySet.add(urlDTO.getUrl());
			}
		}
		
		try {
			String content = h.sendGet(mainUrl);

			Element doc = Jsoup.parse(content);
			Elements targetUL = doc.select("ul.clearfix");
			Elements targetAlinkList = targetUL.select("a[href]");
			CollectUrlHistoryDTO tmpDTO = null;
			for (Element eleA : targetAlinkList) {
				String title = eleA.attr("title");
				String url = eleA.attr("href");
				if (!urlStrHistorySet.contains(url)) {
					tmpDTO = new CollectUrlHistoryDTO();
					tmpDTO.setRecrodDate(LocalDateTime.now());
					tmpDTO.setUrl(url);
					newInfoUrlList.add(tmpDTO);
					sendMsg("New url: " + url + " , title: " + title);
				}
			}
		} catch (Exception e) {
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

	@Override
	public void deleteOldUrls() {
		FileUtilCustom ioUtil = new FileUtilCustom();
		EducationInfoOptionDTO dto = null;
		
		int maxSize = 30;
		int overloadCounting = 0;
		
		try {
			String content = ioUtil.getStringFromFile(PARAM_PATH_STR);
			dto = buildObjFromJsonCustomization(content, EducationInfoOptionDTO.class);
		} catch (Exception e) {
			log.error("Read EducationInfoOptionDTO record error");
			return;
		}

		Map<String, List<CollectUrlHistoryDTO>> urlHistoryMap = dto.getUrlHistory();
		
		for(Entry<String, List<CollectUrlHistoryDTO>> entrySet : urlHistoryMap.entrySet()) {
			List<CollectUrlHistoryDTO> tmpList = entrySet.getValue();
			if(tmpList.size() <= maxSize) {
				continue;
			}
			
			Collections.sort(tmpList);
			
			overloadCounting = tmpList.size() - maxSize;
			
			tmpList = tmpList.subList(overloadCounting, tmpList.size());
			
			entrySet.setValue(tmpList);
		}
		
		Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).setPrettyPrinting().create();
		String jsonString = gson.toJson(dto);
		ioUtil.byteToFile(jsonString.toString().getBytes(StandardCharsets.UTF_8), PARAM_PATH_STR);
	}
}
