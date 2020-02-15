package demo.clawing.dailySign.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;

import at.pojo.bo.XpathBuilderBO;
import at.pojo.dto.JsonReportDTO;
import at.pojo.dto.TakeScreenshotSaveDTO;
import at.pojo.result.ScreenshotSaveResult;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.dailySign.mapper.WuyiWatchMeMapper;
import demo.clawing.dailySign.pojo.bo.DailySignAccountBO;
import demo.clawing.dailySign.pojo.po.WuyiWatchMe;
import demo.clawing.dailySign.pojo.po.WuyiWatchMeExample;
import demo.clawing.dailySign.pojo.type.DailySignCaseType;
import demo.clawing.dailySign.pojo.vo.WuyiWatchMeVO;
import demo.clawing.dailySign.service.ZhiLianDailySignService;
import demo.selenium.service.impl.SeleniumCommonService;
import demo.selenium.service.pojo.bo.BuildTestEventBO;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service
public class ZhiLianDailySignServiceImpl extends SeleniumCommonService implements ZhiLianDailySignService {

	/*
	 * TODO
	 * 待重建类似 51job 的功能
	 */
	@Autowired
	private WuyiWatchMeMapper wuyiWatcheMeMapper;
	
	private String dailySignEventName = "zhiLianDailySign";
	
	private String userDataFileName = "zhiLianDailySign.json";
	
	private TestModuleType testModuleType = TestModuleType.dailySign;
	private DailySignCaseType testCastType = DailySignCaseType.zhiLian;

	
	private TestEvent buildDailySignEvent() {
		String paramterFolderPath = getParameterSaveingPath(dailySignEventName);
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
	public InsertTestEventResult insertDailySignEvent() {
		TestEvent te = buildDailySignEvent();
		return testEventService.insertTestEvent(te);
	}

	@Override
	public CommonResultBBT dailySign(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		
		String wuYiRunCountKey = "zhiLianRunCountKey";
		String runCountStr = constantService.getValByName(wuYiRunCountKey);
		int runCount = 0;
		if(StringUtils.isBlank(runCountStr) || "null".equals(runCountStr) || !numericUtil.matchInteger(runCountStr)) {
			constantService.setValByName(wuYiRunCountKey, "1");
			runCount = 1;
		} else {
			runCount = Integer.parseInt(runCountStr);
		}
		
		if(runCount % 4 == 0) {
			runCount = 0;
		}
		
		JsonReportDTO reportDTO = new JsonReportDTO();
		WebDriver d = null;
		
		String screenshotPath = getScreenshotSaveingPath(dailySignEventName);
		String reportOutputFolderPath = getReportOutputPath(dailySignEventName);
		
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());
		
		try {
			
			String jsonStr = ioUtil.getStringFromFile(te.getParameterFilePath());
			if(StringUtils.isBlank(jsonStr)) {
				jsonReporter.appendContent(reportDTO, "参数文件读取异常");
				throw new Exception();
			}
			
			DailySignAccountBO dailySignBO = null;
			try {
				dailySignBO = new Gson().fromJson(jsonStr, DailySignAccountBO.class);
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			if(dailySignBO == null) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}

			d = webDriverService.buildFireFoxWebDriver();
			
			if(!login(d, reportDTO, dailySignBO)) {
				r.failWithMessage("登录失败");
				throw new Exception();
			}
			
			jsonReporter.appendContent(reportDTO, "完成登录");
			
			catchWatchMe(d, reportDTO);
			if(runCount == 0) {
				if(!updateDetail(d, reportDTO)) {
					jsonReporter.appendContent(reportDTO, "刷新简历失败");
					r.failWithMessage("更新失败");
					throw new Exception();
				} else {
					jsonReporter.appendContent(reportDTO, "刷新简历完毕");
				}
			} else {
				jsonReporter.appendContent(reportDTO, "暂不新简历");
			}
			
			constantService.setValByName(wuYiRunCountKey, String.valueOf(runCount + 1));
			jsonReporter.appendContent(reportDTO, "更新 redis 计数");
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
//			String htmlStr = jsUtil.getHtmlSource(d);
			TakeScreenshotSaveDTO screenshotDTO = new TakeScreenshotSaveDTO();
			screenshotDTO.setDriver(d);
			ScreenshotSaveResult screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath,
					null);
			
			UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			jsonReporter.appendContent(reportDTO, "异常: " + e.toString());
//			jsonReporter.appendContent(reportDTO, htmlStr);
			
		} finally {
			tryQuitWebDriver(d, reportDTO);
			if (jsonReporter.outputReport(reportDTO, reportDTO.getOutputReportPath(), te.getId() + ".json")) {
				updateTestEventReportPath(te, reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json");
			}
		}
		
		return r;
	}
	
	private void findAndCloseLeadDiv(WebDriver d) {
//		FIXME
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("div").addAttribute("id", "lead");
		
		try {
			WebElement leadDiv = d.findElement(By.xpath(x.getXpath()));
			if(leadDiv == null || !leadDiv.isDisplayed()) {
				return;
			}
			
			x.start("div").addAttribute("id", "lead")
			.findChild("div").addAttribute("class", "img")
			.findChild("div").addAttribute("class", "close closeloginpop")
			;
			
			WebElement leadCloseButton = d.findElement(By.xpath(x.getXpath()));
			leadCloseButton.click();
		} catch (Exception e) {
			
		}
	}
	
	private boolean login(WebDriver d, JsonReportDTO reportDTO, DailySignAccountBO dailySignBO) {
//		FIXME
		XpathBuilderBO x = new XpathBuilderBO();
		
		try {
			try {
				d.get(dailySignBO.getMainUrl());
				jsonReporter.appendContent(reportDTO, "get home page");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get home page but timeout");
			}
			
			findAndCloseLeadDiv(d);
			
			x.start("div").addAttribute("id", "pageTop")
			.findChild("header")
			.findChild("p").addAttribute("class", "links")
			.findChild("a", 1)
			;
			
			d.findElement(By.xpath(x.getXpath())).click();
			
			x.start("input").addAttribute("id", "loginname");
			WebElement usernameInput = null;
			try {
				usernameInput = d.findElement(By.xpath(x.getXpath()));
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "找不到用户名输入框");
				throw new Exception();
			}
			usernameInput.click();
			usernameInput.clear();
			usernameInput.sendKeys(dailySignBO.getUsername());
			
			threadSleepRandomTime(1000L, 3000L);
			
			x.start("input").addAttribute("id", "password");
			WebElement pwdInput = null;
			try {
				pwdInput = d.findElement(By.xpath(x.getXpath()));
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "找不到密码输入框");
			}
			pwdInput.click();
			pwdInput.clear();
			pwdInput.sendKeys(dailySignBO.getPwd());
			
			threadSleepRandomTime(1000L, 3000L);
			
			x.start("button").addAttribute("id", "login_btn");
			WebElement loginButton = null;
			try {
				loginButton = d.findElement(By.xpath(x.getXpath()));
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "找不到登录按钮");
			}
			loginButton.click();
			
			threadSleepRandomTime(1000L, 3000L);
			
			return true;
		} catch (Exception e) {
			return false;
		}
		
		
		
	}

	private boolean updateDetail(WebDriver d, JsonReportDTO reportDTO) {
//		FIXME
		XpathBuilderBO x = new XpathBuilderBO();
		
		try {
			try {
				d.get("https://m.51job.com/my/my51job.php");
				jsonReporter.appendContent(reportDTO, "get 我的51job ");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get 我的51job  but timeout");
			}
			
			threadSleepRandomTime(1000L, 3000L);
			
			try {
				d.get("https://m.51job.com/resume/myresume.php");
				jsonReporter.appendContent(reportDTO, "get 我的简历");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get 我的简历 but timeout");
			}
			
			threadSleepRandomTime(1000L, 3000L);
			
			try {
				d.get("https://m.51job.com/resume/detail.php?userid=398934495");
				jsonReporter.appendContent(reportDTO, "get 指定简历");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get 指定简历 but timeout");
			}
			
			threadSleepRandomTime(1000L, 3000L);
			
			try {
				d.get("https://m.51job.com/resume/jobintent.php?userid=398934495");
				jsonReporter.appendContent(reportDTO, "get 指定简历编辑界面");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get 指定简历编辑界面 but timeout");
			}
			
			threadSleepRandomTime(1000L, 3000L);
			
			x.start("textarea").addAttribute("id", "intro");
			WebElement intentionDetailTextarea = null;
			try {
				intentionDetailTextarea = d.findElement(By.xpath(x.getXpath()));
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "无法找到简历简介编辑框");
				return false;
			}
			String now = localDateTimeHandler.dateToStr(LocalDateTime.now());
			String timeMarkStr = "自动签到时间: " + now;
			String intentionDetailSourceStr = intentionDetailTextarea.getText();
			String lineBreak = null;
			if(intentionDetailSourceStr.contains(System.lineSeparator())) {
				lineBreak = System.lineSeparator();
			} else if(intentionDetailSourceStr.contains("\r\n")) {
				lineBreak = "\r\n";
			} else if(intentionDetailSourceStr.contains("\n")) {
				lineBreak = "\n";
			} else if(intentionDetailSourceStr.contains("\r")) {
				lineBreak = "\r";
			}
			String[] sourceLines = intentionDetailSourceStr.split(lineBreak); 
			StringBuffer sb = new StringBuffer();
			for(int i = 0; i < sourceLines.length; i++) {
				if(i == 0) {
					sb.append(timeMarkStr);
				} else {
					sb.append(sourceLines[i]);
				}
				sb.append(lineBreak);
			}
			
			intentionDetailTextarea.clear();
			intentionDetailTextarea.sendKeys(sb.toString());
			
			jsonReporter.appendContent(reportDTO, "完成编辑内容");
			
			threadSleepRandomTime(1000L, 3000L);
			
			d.findElement(By.id("saveresumefour")).click();
			jsonReporter.appendContent(reportDTO, "点击保存");
			
			threadSleepRandomTime(1000L, 3000L);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	private boolean catchWatchMe(WebDriver d, JsonReportDTO reportDTO) {
//		FIXME
		XpathBuilderBO x = new XpathBuilderBO();
		
		try {
			try {
				d.get("https://m.51job.com/my/whosawrsm.php");
				jsonReporter.appendContent(reportDTO, "进入手机版 我的51job");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "进入手机版 我的51job but timeout");
			}
			
			
			String companyLinkX = x.start("div").addAttribute("class", "new_l")
					.findChild("a", 1)
					.getXpath();
			String companyNameX = x.start("div").addAttribute("class", "new_l")
					.findChild("a", 1)
					.findChild("p").addAttribute("class", "gs")
					.getXpath();
			String resumeNameX = x.start("div").addAttribute("class", "new_l")
					.findChild("a", 1)
					.findChild("div").addAttribute("class", "ms")
					.findChild("b").findChild("span")
					.getXpath();
			String likelyX = x.start("div").addAttribute("class", "new_l")
					.findChild("a", 1)
					.findChild("div").addAttribute("class", "ms")
					.findChild("p").addAttribute("class", "lk")
					.findChild("object").findChild("a").findChild("span") // class="s4"
					.getXpath()
					;
			String watchTimeX = x.start("div").addAttribute("class", "new_l")
					.findChild("a", 1)
					.findChild("div").addAttribute("class", "ms")
					.findChild("p").addAttribute("class", "lk")
					.findChild("em")  // 查看时间:01-06 10:39
					.getXpath()
					;

			WebElement companyLinkDiv = null;
			WebElement companyNameP = null;
			WebElement resumeNameDiv = null;
			WebElement likelySpan = null;
			WebElement watcheTimeEm = null;
			try {
				companyLinkDiv = d.findElement(By.xpath(companyLinkX));
				companyNameP = d.findElement(By.xpath(companyNameX));
				resumeNameDiv = d.findElement(By.xpath(resumeNameX));
				likelySpan = d.findElement(By.xpath(likelyX));
				watcheTimeEm = d.findElement(By.xpath(watchTimeX));
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "查找页面元素异常");
				return false;
			}
			
			threadSleepRandomTime(1000L, 3000L);
			
			LocalDateTime theWatchTime = null;
			WuyiWatchMe newPO = new WuyiWatchMe();
			try {
				LocalDateTime now = LocalDateTime.now();
				Integer year = now.getYear();
				
				String watchTimeSourceStr = watcheTimeEm.getText();
				watchTimeSourceStr = watchTimeSourceStr.replaceAll("查看时间:", year.toString() + "-") + ":00";
				
				theWatchTime = localDateTimeHandler.stringToLocalDateTimeUnkonwFormat(watchTimeSourceStr);
				newPO.setWatchTime(theWatchTime);
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "拼凑 WuyiWatchMe PO 异常: " + e.toString());
				return false;
			}
			
			WuyiWatchMe lastWatchPO = wuyiWatcheMeMapper.findTheLastWatch();
			if(lastWatchPO != null && lastWatchPO.getWatchTime().isEqual(theWatchTime)) {
				if(lastWatchPO.getCompanyLink().equals(companyLinkDiv.getAttribute("href"))) {
					return true;
				}
			}
			
			newPO.setId(snowFlake.getNextId());
			newPO.setCompanyLink(companyLinkDiv.getAttribute("href"));
			newPO.setCompanyName(companyNameP.getText().replaceAll("企业搜索", ""));
			newPO.setMyResumeName(resumeNameDiv.getText().replaceAll("查看：", ""));
			try {
				String likelyClass = likelySpan.getAttribute("class");
				newPO.setDegreeOfInterest(Integer.parseInt(likelyClass.replaceAll("s", "")));
			} catch (Exception e) {
				
			}
			
			wuyiWatcheMeMapper.insertSelective(newPO);
			
			threadSleepRandomTime(1000L, 3000L);
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public ModelAndView watchMeList() {
//		FIXME
		ModelAndView v = new ModelAndView("tmpJSP/51JobWatchMe");
		WuyiWatchMeExample example = new WuyiWatchMeExample();
		example.createCriteria().andIsDeleteEqualTo(false).andCreateTimeGreaterThan(LocalDateTime.now().minusMonths(12));
		example.setOrderByClause("watch_time");
		List<WuyiWatchMe> poList = wuyiWatcheMeMapper.selectByExample(example);
		
		WuyiWatchMeVO vo = null;
		Map<String, WuyiWatchMeVO> voMap = new HashMap<String, WuyiWatchMeVO>();
		for(WuyiWatchMe i : poList) {
			vo = voMap.get(i.getCompanyLink() + i.getCompanyName());
			if(vo == null) {
				vo = new WuyiWatchMeVO();
				vo.setCompanyLink(i.getCompanyLink());
				vo.setCompanyName(i.getCompanyName());
				vo.setDegreeOfInterest(i.getDegreeOfInterest());
				vo.setDegreeOfInterestAvg(i.getDegreeOfInterest().doubleValue());
				vo.setLastWatchTime(i.getWatchTime());
				vo.setMyResumeName(i.getMyResumeName());
				vo.setWatchCount(1);
			} else {
				vo.setDegreeOfInterest(i.getDegreeOfInterest());
				vo.setDegreeOfInterestAvg((vo.getDegreeOfInterestAvg() * vo.getWatchCount() + i.getDegreeOfInterest()) / (vo.getWatchCount() + 1));
				if(i.getWatchTime().isAfter(vo.getLastWatchTime())) {
					vo.setLastWatchTime(i.getWatchTime());
				}
				if(StringUtils.isBlank(vo.getMyResumeName()) || !vo.getMyResumeName().contains(i.getMyResumeName())) {
					vo.setMyResumeName(vo.getMyResumeName() + ", " + i.getMyResumeName());
				}
				vo.setWatchCount(vo.getWatchCount() + 1);
			}
			
			voMap.put(i.getCompanyLink() + i.getCompanyName(), vo);
		}
		
		List<WuyiWatchMeVO> voList = new ArrayList<WuyiWatchMeVO>(voMap.values());
		Collections.sort(voList);
		v.addObject("voList", voList);
		return v;
	}
}
