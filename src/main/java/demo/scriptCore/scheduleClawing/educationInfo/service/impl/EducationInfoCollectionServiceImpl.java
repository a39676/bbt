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
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.config.customComponent.OptionFilePathConfigurer;
import demo.scriptCore.scheduleClawing.common.pojo.dto.CollectUrlHistoryDTO;
import demo.scriptCore.scheduleClawing.educationInfo.pojo.dto.EducationInfoOptionDTO;
import demo.scriptCore.scheduleClawing.educationInfo.pojo.type.EducationInfoSourceType;
import demo.scriptCore.scheduleClawing.educationInfo.service.EducationInfoCollectionService;
import demo.selenium.service.impl.AutomationTestCommonService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import toolPack.httpHandel.HttpUtil;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class EducationInfoCollectionServiceImpl extends AutomationTestCommonService
		implements EducationInfoCollectionService {

	@Override
	public TestEventBO clawing(TestEventBO tbo) {
		log.error("Start education info collection clawing");
		sendingMsg("In education info collection");
		CommonResult r = new CommonResult();
		
		log.error("delete old urls");
		deleteOldUrls();

		ScheduleClawingType caseType = ScheduleClawingType.EDUCATION_INFO;
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(caseType.getFlowName());

		WebDriver webDriver = null;

		try {
			FileUtilCustom ioUtil = new FileUtilCustom();
			String content = ioUtil.getStringFromFile(OptionFilePathConfigurer.EDUCATION_INFO);

			log.error("load education info option");
			EducationInfoOptionDTO dto = buildObjFromJsonCustomization(content, EducationInfoOptionDTO.class);
			if (dto == null) {
				log.error("education info DTO error, content: " + content);
				reportService.caseReportAppendContent(caseReport, "参数文件结构异常");
				throw new Exception();
			}

			List<CollectUrlHistoryDTO> newUrlList = null;
			EducationInfoSourceType sourceType = null;

			try {
				sourceType = EducationInfoSourceType.HAIZHU_GOV_CN;
				newUrlList = runHaizhuGovInfoCollector(dto.getSourceUrl().get(sourceType.getName()),
						dto.getUrlHistory().get(sourceType.getName()));
				if (!newUrlList.isEmpty()) {
					log.error("found new url, from runHaizhuGovInfoCollector.");
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
					log.error("found new url, from runGzJyjInfoCollector.");
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
					log.error("found new url, from runGZZK_1Collector.");
					if (!dto.getUrlHistory().containsKey(sourceType.getName())) {
						dto.getUrlHistory().put(sourceType.getName(), new ArrayList<>());
					}
					dto.getUrlHistory().get(sourceType.getName()).addAll(newUrlList);
				}
			} catch (Exception e) {
			}

			try {
				webDriver = webDriverService.buildChromeWebDriver();
				sourceType = EducationInfoSourceType.GZEDUCMS_CN;
				newUrlList = runGzEduCmsInfoCollector(webDriver, dto.getSourceUrl().get(sourceType.getName()),
						dto.getUrlHistory().get(sourceType.getName()));
				if (!newUrlList.isEmpty()) {
					log.error("found new url, from runGzEduCmsInfoCollector.");
					if (!dto.getUrlHistory().containsKey(sourceType.getName())) {
						dto.getUrlHistory().put(sourceType.getName(), new ArrayList<>());
					}
					dto.getUrlHistory().get(sourceType.getName()).addAll(newUrlList);
				}
			} catch (Exception e) {
			}

			Gson gson = new GsonBuilder().registerTypeAdapter(LocalDateTime.class, localDateTimeAdapter).setPrettyPrinting().create();
			String jsonString = gson.toJson(dto);
			ioUtil.byteToFile(jsonString.toString().getBytes(StandardCharsets.UTF_8), OptionFilePathConfigurer.EDUCATION_INFO);

			r.setIsSuccess();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if(!tryQuitWebDriver(webDriver)) {
			sendingMsg("Web driver quit failed, " + caseType.getFlowName());
		}
		sendAutomationTestResult(tbo);

		return tbo;
	}

	private List<CollectUrlHistoryDTO> runHaizhuGovInfoCollector(String mainUrl, List<CollectUrlHistoryDTO> urlHistoryList) {
		log.error("start runHaizhuGovInfoCollector");
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
					sendingMsg("New url: " + url + " , title: " + title);
				}
			}
		} catch (Exception e) {
		}

		return newInfoUrlList;
	}

	private List<CollectUrlHistoryDTO> runGzJyjInfoCollector(String mainUrl, List<CollectUrlHistoryDTO> urlHistoryList) {
		log.error("start runGzJyjInfoCollector");
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
					sendingMsg("New url: " + url + " , title: " + title);
				}
			}
		} catch (Exception e) {
		}

		return newInfoUrlList;

	}

	private List<CollectUrlHistoryDTO> runGzEduCmsInfoCollector(WebDriver d, String mainUrl,
			List<CollectUrlHistoryDTO> urlHistoryList) {
		log.error("start runGzEduCmsInfoCollector");
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
					sendingMsg("New url: " + urlStr + " , title: " + title);
				}
			} catch (Exception e) {
			}
		}

		return newInfoUrlList;
	}
	
	private List<CollectUrlHistoryDTO> runGZZK_1Collector(String mainUrl, List<CollectUrlHistoryDTO> urlHistoryList) {
		log.error("start runGZZK_1Collector");
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
					sendingMsg("New url: " + url + " , title: " + title);
				}
			}
		} catch (Exception e) {
		}

		return newInfoUrlList;

	}
	
	@Override
	public void deleteOldUrls() {
		FileUtilCustom ioUtil = new FileUtilCustom();
		EducationInfoOptionDTO dto = null;
		
		int maxSize = 30;
		int overloadCounting = 0;
		
		try {
			String content = ioUtil.getStringFromFile(OptionFilePathConfigurer.EDUCATION_INFO);
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
		ioUtil.byteToFile(jsonString.toString().getBytes(StandardCharsets.UTF_8), OptionFilePathConfigurer.EDUCATION_INFO);
	}
}
