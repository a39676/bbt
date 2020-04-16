package demo.clawing.localClawing.service.impl;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import at.report.pojo.dto.JsonReportDTO;
import at.screenshot.pojo.dto.TakeScreenshotSaveDTO;
import at.screenshot.pojo.result.ScreenshotSaveResult;
import at.xpath.pojo.bo.XpathBuilderBO;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.common.service.JobClawingCommonService;
import demo.clawing.localClawing.pojo.bo.LaGouClawingBO;
import demo.clawing.localClawing.pojo.type.LocalClawingCaseType;
import demo.clawing.localClawing.service.LaGouLocalClawingService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service
public class LaGouLocalClawingServiceImpl extends JobClawingCommonService implements LaGouLocalClawingService {

	private String eventName = "laGouLocalClawing";
	private String userDataFileName = "laGouSign.json";
	
	private TestModuleType testModuleType = TestModuleType.localClawing;
	private LocalClawingCaseType testCastType = LocalClawingCaseType.laGou;
	
	private int varifyButtonCount = 0;
	
	private TestEvent buildLocalClawingEvent() {
		String paramterFolderPath = getParameterSaveingPath(eventName);
		File paramterFile = new File(paramterFolderPath + File.separator + userDataFileName);
		if (!paramterFile.exists()) {
//			TODO
			return null;
		}
		
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(testModuleType);
		bo.setCaseId(testCastType.getId());
		bo.setEventName(testCastType.getEventName());
		bo.setParameterFilePath(paramterFile.getAbsolutePath());
		return buildTestEvent(bo);
	}
	
	@Override
	public InsertTestEventResult insertLocalClawingEvent() {
		TestEvent te = buildLocalClawingEvent();
		return testEventService.insertTestEvent(te);
	}

	@Override
	public CommonResultBBT localClawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		
		JsonReportDTO reportDTO = new JsonReportDTO();
		WebDriver d = null;
		
		String screenshotPath = getScreenshotSaveingPath(eventName);
		String reportOutputFolderPath = getReportOutputPath(eventName);
		
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());
		
		
		try {
			
			String jsonStr = ioUtil.getStringFromFile(te.getParameterFilePath());
			if(StringUtils.isBlank(jsonStr)) {
				jsonReporter.appendContent(reportDTO, "参数文件读取异常");
				throw new Exception();
			}
			
			LaGouClawingBO clawingEventBO = null;
			try {
				clawingEventBO = new Gson().fromJson(jsonStr, LaGouClawingBO.class);
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			if(clawingEventBO == null) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			d = webDriverService.buildFireFoxWebDriver();
			
			try {
				d.get(clawingEventBO.getMainUrl());
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
			}
			
			tryCloseCityChoseNotify(d);
			
			boolean operatorFlag = tryLogin(d, reportDTO, clawingEventBO);
			if(!operatorFlag) {
				throw new Exception("登录异常");
			}
			
			threadSleepRandomTime(3000L, 5000L);
			
			Set<String> jobInfoUrlSet = new HashSet<String>();
			Set<String> jobInfoUrlSubSet = new HashSet<String>();
			List<String> keywordList = List.of("测试", "白盒测试", "测试开发", "自动化测试");
			
			for(String keyword : keywordList) {
				collectJobInfoUrlByKeyWord(d, jobInfoUrlSubSet, keyword, clawingEventBO.getMainUrl());
				jobInfoUrlSet.addAll(jobInfoUrlSubSet);
				jobInfoUrlSubSet.clear();
			}
			
			viewJobInfoUrl(d, jobInfoUrlSet);
			
			System.out.println("varifyButtonCount: " + varifyButtonCount);
			
		} catch (Exception e) {
			e.printStackTrace();
			TakeScreenshotSaveDTO screenshotDTO = new TakeScreenshotSaveDTO();
			screenshotDTO.setDriver(d);
			ScreenshotSaveResult screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath,
					null);
			
			UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			jsonReporter.appendContent(reportDTO, "异常: " + e.toString());
			
		} finally {
			tryQuitWebDriver(d, reportDTO);
			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
		}
		
		return r;
	}
	

	private boolean tryLogin(WebDriver d, JsonReportDTO reportDTO, LaGouClawingBO clawingEventBO) throws InterruptedException {
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("a").addClass("login").addAttribute("rel", "nofollow");
		try {
			WebElement loginButton = d.findElement(By.xpath(x.getXpath()));
			loginButton.click();
			threadSleepRandomTime();
		} catch (Exception e) {
			System.out.println("can not find login button");
			return false;
		}
		
		x.start("input").addClass("input login_enter_password HtoC_JS").addType("text");
		try {
			WebElement usernameInput = d.findElement(By.xpath(x.getXpath()));
			usernameInput.click();
			usernameInput.clear();
			usernameInput.sendKeys(clawingEventBO.getUsername());
			threadSleepRandomTime();
		} catch (Exception e) {
			System.out.println("can not find usernameInput");
			return false;
		}
		
		x.start("input").addClass("input login_enter_password HtoC_JS").addType("password");
		try {
			WebElement passwordInput = d.findElement(By.xpath(x.getXpath()));
			passwordInput.click();
			passwordInput.clear();
			passwordInput.sendKeys(clawingEventBO.getPassword());
		} catch (Exception e) {
			System.out.println("can not find password input");
			return false;
		}
		
		x.start("div").addClass("login-btn login-password sense_login_password btn-green");
		try {
			WebElement loginSubmitButton = d.findElement(By.xpath(x.getXpath()));
			loginSubmitButton.click();
			return true;
		} catch (Exception e) {
			System.out.println("can not find loginSubmitButton");
			return false;
		}
		
	}

	private boolean inputSearch(WebDriver d, String keyword, String mainUrl) {
		try {
			d.get(mainUrl);
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
		}
		
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("input").addType("text").addId("search_input").addClass("search_input ui-autocomplete-input");
		try {
			WebElement keywordInput = d.findElement(By.xpath(x.getXpath()));
			keywordInput.click();
			keywordInput.clear();
			keywordInput.sendKeys(keyword);
			keywordInput.sendKeys(Keys.ENTER);
			
			threadSleepRandomTime();
			tryCloseRedPackNotify(d);
			
			threadSleepRandomTime();
			clickSortByNew(d);
			
			return true;
		} catch (Exception e) {
			System.out.println("can not find keywordInput");
			return false;
		}
	}
	
	private void tryCloseCityChoseNotify(WebDriver d) {
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("div").addId("cboxContent")
		.findChild("div").addId("cboxLoadedContent")
		.findChild("div").addId("changeCityBox")
		.findChild("ul").addClass("clearfix")
		.findChild("li")
		;
		
		List<WebElement> cityElements = null;
		try {
			cityElements = d.findElements(By.xpath(x.getXpath()));
		} catch (Exception e) {
			return;
		}
		
		if(cityElements == null || cityElements.isEmpty()) {
			return;
		}
		
		WebElement tmpCityElement = cityElements.get(0);
		if(!tmpCityElement.isDisplayed() && !tmpCityElement.isEnabled()) {
			return;
		}
		
		for(WebElement i : cityElements) {
			if(i.getText().contains("广州")) {
				i.click();
				return;
			}
		}
	}

	private void tryCloseRedPackNotify(WebDriver d) {
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("div").addClass("body-btn");
		try {
			List<WebElement> btnList = d.findElements(By.xpath(x.getXpath()));
			
			for(WebElement ele : btnList) {
				if(ele.isDisplayed() && "给也不要".equals(ele.getText())) {
					ele.click();
					return;
				}
			}
			
		} catch (Exception e) {
			System.out.println("can not find notify button");
		}
	}

	private void clickSortByNew(WebDriver d) {
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("div").addClass("item order")
		.findChild("a")
		;
		try {
			List<WebElement> btnList = d.findElements(By.xpath(x.getXpath()));
			
			for(WebElement ele : btnList) {
				if(ele.isDisplayed() && "最新".equals(ele.getText())) {
					ele.click();
					return;
				}
			}
			
		} catch (Exception e) {
			System.out.println("can not find sort by new button");
		}
	}

	private void jobInfoPageHandle(WebDriver d, Set<String> jobInfoUrlList) {
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("div").addId("s_position_list").findChild("ul").addClass("item_con_list");
		
		String mainListXpath = x.getXpath();
		try {
			x.setXpath(mainListXpath).findChild("li");
			List<WebElement> liList = d.findElements(By.xpath(x.getXpath()));
			
			WebElement tmpLi = null;
			WebElement jobInfoA = null;
			for(int i = 0; i < liList.size() && jobInfoUrlList.size() < keywordClickMaxCount; i++) {
				x.setXpath(mainListXpath)
				.findChild("li", i + 1)
				;
				tmpLi = d.findElement(By.xpath(x.getXpath()));
				
				if(inFilter(tmpLi.getAttribute("data-company"))) {
					continue;
				} else {
					System.out.println("add: " + tmpLi.getAttribute("data-company"));
//					TODO  可以完善信息
				}
				
				x.setXpath(mainListXpath).findChild("li", i + 1)
				.findChild("div").addClass("list_item_top")
				.findChild("div").addClass("position")
				.findChild("div").addClass("p_top")
				.findChild("a")
				;
				jobInfoA = d.findElement(By.xpath(x.getXpath()));
				jobInfoUrlList.add(jobInfoA.getAttribute("href"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("can not find sort by new button");
		}
	}

	private boolean clickNextPage(WebDriver d) {
//		<span hidefocus="hidefocus" action="next" class="pager_next ">下一页<strong class="pager_lgthen "></strong></span>
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("span")
//		.addClass("pager_next")
		;
		try {
			List<WebElement> btnList = d.findElements(By.xpath(x.getXpath()));
			
			for(WebElement ele : btnList) {
				if(ele.isEnabled() && "下一页".equals(ele.getText())) {
					if(ele.getAttribute("class").contains("pager_next_disabled")) {
						return false;
					} else {
						ele.click();
						return true;
					}
				}
			}
			return false;
			
		} catch (Exception e) {
			System.out.println("can not find next page button");
			return false;
		}
	}

	private boolean collectJobInfoUrlByKeyWord(WebDriver d, Set<String> jobInfoUrlList, String keyword, String mainUrl) throws InterruptedException {
		boolean operatorFlag = inputSearch(d, keyword, mainUrl);
		if(!operatorFlag) {
			return operatorFlag;
		}
		
		threadSleepRandomTime();
		
		while(jobInfoUrlList.size() < keywordClickMaxCount && operatorFlag) {
			jobInfoPageHandle(d, jobInfoUrlList);
			if(jobInfoUrlList.size() < keywordClickMaxCount) {
				operatorFlag = clickNextPage(d);
			}
		}
		
		return operatorFlag;
	}
	
	private void viewJobInfoUrl(WebDriver d, Set<String> jobInfoUrlSet) throws InterruptedException {
		int count = 0;
		for(String url : jobInfoUrlSet) {
			d.get(url);
			threadSleepRandomTime();
			
			XpathBuilderBO x = new XpathBuilderBO();
			
			try {
				x.start("a").addId("btn");
				WebElement varifyButton = d.findElement(By.xpath(x.getXpath()));
				varifyButton.click();
				varifyButtonCount++;
			} catch (Exception e) {
			}
			count++;
			System.out.println(count + " : " + jobInfoUrlSet.size());
		}
	}
}
