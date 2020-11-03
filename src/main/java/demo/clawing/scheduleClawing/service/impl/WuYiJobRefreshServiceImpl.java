package demo.clawing.scheduleClawing.service.impl;

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

import at.report.pojo.dto.JsonReportDTO;
import at.screenshot.pojo.result.ScreenshotSaveResult;
import at.xpath.pojo.bo.XpathBuilderBO;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.scheduleClawing.mapper.WuyiWatchMeMapper;
import demo.clawing.scheduleClawing.pojo.bo.DailySignAccountBO;
import demo.clawing.scheduleClawing.pojo.bo.WuYiJobClawingBO;
import demo.clawing.scheduleClawing.pojo.po.WuyiWatchMe;
import demo.clawing.scheduleClawing.pojo.po.WuyiWatchMeExample;
import demo.clawing.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.clawing.scheduleClawing.pojo.vo.WuyiWatchMeVO;
import demo.clawing.scheduleClawing.service.WuYiJobRefreshService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import image.pojo.result.ImageSavingResult;
import selenium.pojo.constant.SeleniumConstant;

@Service
public class WuYiJobRefreshServiceImpl extends SeleniumCommonService implements WuYiJobRefreshService {

	@Autowired
	private WuyiWatchMeMapper wuyiWatcheMeMapper;
	
	private String dailySignEventName = "wuYiJobDailySign";
	
	private String userDataFileName = "51jobDailySign.json";
	
	private TestModuleType testModuleType = TestModuleType.scheduleClawing;
	private ScheduleClawingType testCastType = ScheduleClawingType.wuYiJob;

	/*
	 * TODO 
	 * 
	 * 已经新建 test_process 表, 但批量增加 test_event 并有顺序要求的情况, 难以封装, 后期可能视具体情况, 固定代码实现
	 * 
	 * 因每个任务的 参数BO 均不同, 不再封装公共方法
	 * 
	 * 考虑在 SeleniumCommonService 新建以参数文件数量, 对应新建多个子任务的方法
	 * 每个 testEvent 很可能对应多个参数文件, 那么加入任务的时候, 如果不声明指定的参数文件,
	 * 则应该所有参数配置都跑一次 但当时新建任务时 if(无指定参数文件的任务 && 对应的参数文件夹下有多个参数搭配文件) { 就应该同时建立N个子任务,
	 * 不是等运行时才建立子任务, 保证数据排序, 并且对应多个子任务有多个报告 新建子任务后, 就可以生成一份报告, 报告内是对应各个子报告的链接 考虑选用
	 * processId 做关联? }
	 */

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
	public InsertTestEventResult insertClawingEvent() {
		TestEvent te = buildDailySignEvent();
		return testEventService.insertTestEvent(te);
	}

	@Override
	public CommonResultBBT clawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		
		String wuYiRunCountKey = "wuYiRunCountKey";
		String runCountStr = constantService.getValByName(wuYiRunCountKey);
		int runCount = 0;
		if(StringUtils.isBlank(runCountStr) || "null".equals(runCountStr) || !numericUtil.matchInteger(runCountStr)) {
			constantService.setValByName(wuYiRunCountKey, "1");
			runCount = 1;
		} else {
			runCount = Integer.parseInt(runCountStr);
		}
		
		JsonReportDTO reportDTO = new JsonReportDTO();
		WebDriver d = null;
		
		String reportOutputFolderPath = getReportOutputPath(dailySignEventName);
		LocalDateTime screenshotImageValidTime = LocalDateTime.now().plusMonths(SeleniumConstant.maxHistoryMonth);
		
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());
		
		try {
			
			String jsonStr = ioUtil.getStringFromFile(te.getParameterFilePath());
			if(StringUtils.isBlank(jsonStr)) {
				jsonReporter.appendContent(reportDTO, "参数文件读取异常");
				throw new Exception();
			}
			
			WuYiJobClawingBO clawingOptionBO = null;
			try {
				clawingOptionBO = new Gson().fromJson(jsonStr, WuYiJobClawingBO.class);
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			if(clawingOptionBO == null) {
				jsonReporter.appendContent(reportDTO, "参数文件结构异常");
				throw new Exception();
			}
			
			if(runCount % clawingOptionBO.getRefreshStep() == 0) {
				runCount = 0;
			}

			d = webDriverService.buildChromeWebDriverMobileEmulation();
			
			if(!login(d, reportDTO, clawingOptionBO)) {
				r.failWithMessage("登录失败");
				throw new Exception();
			}
			
			threadSleepRandomTime();
			
			jsonReporter.appendContent(reportDTO, "完成登录");
			
			catchWatchMe(d, reportDTO);
			
			if(runCount == 0 && "1".equals(clawingOptionBO.getRefreshCV())) {
				if(!updateDetail(d, reportDTO, clawingOptionBO)) {
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
			jsonReporter.appendContent(reportDTO, "更新 redis 计数: " + (runCount));
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			e.printStackTrace();
//			String htmlStr = jsUtil.getHtmlSource(d);
			
			ScreenshotSaveResult screenSaveResult = screenshot(d, te.getEventName());
			
			ImageSavingResult uploadImgResult = saveImgToCX(screenSaveResult.getSavingPath(), screenSaveResult.getFileName(), screenshotImageValidTime);
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
	
	private void findAndCloseLeadDiv(WebDriver d, JsonReportDTO reportDTO) {
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("div").addClass("lead");
		
		try {
			WebElement leadDiv = d.findElement(By.xpath(x.getXpath()));
			if(leadDiv == null || !leadDiv.isDisplayed()) {
				jsonReporter.appendContent(reportDTO, "can not find lead div");
				return;
			} else {
				jsonReporter.appendContent(reportDTO, "find lead div");
			}
			
			x.findChild("div").addClass("close");
			;
			
			WebElement leadCloseDiv = d.findElement(By.xpath(x.getXpath()));
			leadCloseDiv.click();
			jsonReporter.appendContent(reportDTO, "close lead div");
			
		} catch (Exception e) {
			jsonReporter.appendContent(reportDTO, "close lead div exception");
			jsonReporter.appendContent(reportDTO, e.getLocalizedMessage());
		}
	}
	
	private void findAndCloseGoAppDiv(WebDriver d, JsonReportDTO reportDTO) {
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("div").addClass("goApp");
		
		try {
			WebElement goAppDiv = d.findElement(By.xpath(x.getXpath()));
			if(goAppDiv == null || !goAppDiv.isDisplayed()) {
				jsonReporter.appendContent(reportDTO, "can not find go app div");
				return;
			} else {
				jsonReporter.appendContent(reportDTO, "find go app div");
			}
			
			x.findChild("em").addClass("close");
			;
			
			WebElement goAppCloseButton = d.findElement(By.xpath(x.getXpath()));
			goAppCloseButton.click();
			jsonReporter.appendContent(reportDTO, "close go app div");
			
		} catch (Exception e) {
			jsonReporter.appendContent(reportDTO, "close go app div exception");
			jsonReporter.appendContent(reportDTO, e.getLocalizedMessage());
		}
	}
	
	private boolean login(WebDriver d, JsonReportDTO reportDTO, DailySignAccountBO dailySignBO) {
		XpathBuilderBO x = new XpathBuilderBO();
		
		try {
			try {
				d.get(dailySignBO.getMainUrl());
				jsonReporter.appendContent(reportDTO, "get home page");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get home page but timeout");
			}
			
			jsonReporter.appendContent(reportDTO, "try find lead div and go app div");
			findAndCloseLeadDiv(d, reportDTO);
			findAndCloseGoAppDiv(d, reportDTO);
			jsonReporter.appendContent(reportDTO, "after close lead and go app div");
			
			// 向下翻动一下页面, 以使登录按钮出现在页面顶部
			jsUtil.scroll(d, 200);
			jsonReporter.appendContent(reportDTO, "after scroll");
			
			x.start("div").addAttribute("id", "pageTop")
			.findChild("header")
			.findChild("a", 2)
			;
			WebElement loginPageButton = d.findElement(By.xpath(x.getXpath()));
			loginPageButton.click();
			jsonReporter.appendContent(reportDTO, "click login page button");
			
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
			jsonReporter.appendContent(reportDTO, "input username");
			
			threadSleepRandomTime(1000L, 3000L);
			
			x.start("div").addClass("picture");
			WebElement logoClick = null;
			try {
				logoClick = d.findElement(By.xpath(x.getXpath()));
				logoClick.click();
				jsonReporter.appendContent(reportDTO, "close logo");
				/*
				 * 有时候 输入完用户帐号, 会遮挡住密码输入框, 引起报错
				 */
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "找不到Logo");
			}
			
			
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
			jsonReporter.appendContent(reportDTO, "input pwd");
			
			threadSleepRandomTime(1000L, 3000L);
			
			x.start("button").addAttribute("id", "login_btn");
			WebElement loginButton = null;
			try {
				loginButton = d.findElement(By.xpath(x.getXpath()));
			} catch (Exception e) {
				jsonReporter.appendContent(reportDTO, "找不到登录按钮");
			}
			loginButton.click();
			jsonReporter.appendContent(reportDTO, "click login button");
			
			return true;
		} catch (Exception e) {
			
			jsonReporter.appendContent(reportDTO, "login exception");
			jsonReporter.appendContent(reportDTO, e.getLocalizedMessage());
			return false;
		}
	}

	private boolean updateDetail(WebDriver d, JsonReportDTO reportDTO, WuYiJobClawingBO dailySignBO) {
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
				d.get(dailySignBO.getResumeDetailUrl());
				jsonReporter.appendContent(reportDTO, "get 指定简历");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get 指定简历 but timeout");
			}
			
			threadSleepRandomTime(1000L, 3000L);
			
			try {
				d.get(dailySignBO.getJobintentUrl());
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
		ModelAndView v = new ModelAndView("showData/51JobWatchMe");
		WuyiWatchMeExample example = new WuyiWatchMeExample();
		example.createCriteria().andIsDeleteEqualTo(false).andCreateTimeGreaterThan(LocalDateTime.now().minusMonths(12));
		example.setOrderByClause("watch_time");
		List<WuyiWatchMe> poList = wuyiWatcheMeMapper.selectByExample(example);
		
		WuyiWatchMeVO vo = null;
		WuyiWatchMeVO keyVO = null;
		Map<WuyiWatchMeVO, WuyiWatchMeVO> voMap = new HashMap<WuyiWatchMeVO, WuyiWatchMeVO>();
		for(WuyiWatchMe i : poList) {
			keyVO = new WuyiWatchMeVO();
			keyVO.setCompanyLink(i.getCompanyLink());
			keyVO.setCompanyName(i.getCompanyName());
			keyVO.setMyResumeName(i.getMyResumeName());
			vo = voMap.get(keyVO);
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
			
			voMap.put(keyVO, vo);
		}
		
		List<WuyiWatchMeVO> voList = new ArrayList<WuyiWatchMeVO>(voMap.values());
		Collections.sort(voList);
		v.addObject("voList", voList);
		return v;
	}
	
}
