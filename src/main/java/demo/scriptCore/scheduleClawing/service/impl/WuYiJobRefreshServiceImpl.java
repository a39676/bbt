package demo.scriptCore.scheduleClawing.service.impl;

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

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.bo.DailySignAccountBO;
import autoTest.testEvent.scheduleClawing.pojo.bo.WuYiJobClawingBO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.scheduleClawing.mapper.WuyiWatchMeMapper;
import demo.scriptCore.scheduleClawing.pojo.po.WuyiWatchMe;
import demo.scriptCore.scheduleClawing.pojo.po.WuyiWatchMeExample;
import demo.scriptCore.scheduleClawing.pojo.vo.WuyiWatchMeVO;
import demo.scriptCore.scheduleClawing.service.WuYiJobRefreshService;

@Service
public class WuYiJobRefreshServiceImpl extends AutomationTestCommonService implements WuYiJobRefreshService {

	@Autowired
	private WuyiWatchMeMapper wuyiWatcheMeMapper;

	@Override
	public TestEventBO clawing(TestEventBO tbo) {
		CommonResult r = new CommonResult();

		ScheduleClawingType caseType = ScheduleClawingType.WU_YI_JOB;
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(caseType.getFlowName());

		String wuYiRunCountKey = "wuYiRunCountKey";
		String runCountStr = redisOriginalConnectService.getValByName(wuYiRunCountKey);
		int runCount = 1;
		try {
			runCount = Integer.parseInt(runCountStr);
		} catch (Exception e) {
			redisOriginalConnectService.setValByName(wuYiRunCountKey, "1");
		}

		try {

			String jsonStr = tbo.getParamStr();
			if (StringUtils.isBlank(jsonStr)) {
				reportService.caseReportAppendContent(caseReport, "参数文件读取异常");
				throw new Exception();
			}

			WuYiJobClawingBO clawingOptionBO = null;
			try {
				clawingOptionBO = new Gson().fromJson(jsonStr, WuYiJobClawingBO.class);
			} catch (Exception e) {
				reportService.caseReportAppendContent(caseReport, "参数文件结构异常");
				throw new Exception();
			}

			if (clawingOptionBO == null) {
				reportService.caseReportAppendContent(caseReport, "参数文件结构异常");
				throw new Exception();
			}

			if (runCount % clawingOptionBO.getRefreshStep() == 0) {
				runCount = 0;
			}

			if (!login(tbo.getWebDriver(), caseReport, clawingOptionBO)) {
				r.failWithMessage("登录失败");
				throw new Exception();
			}

			threadSleepRandomTime();

			// TODO 此任务暂不采取截图, 直至完成规范化
//			ScreenshotSaveResult screenSaveResult = screenshot(tbo.getWebDriver(), tbo.getFlowName());
//			ImageSavingResult uploadImgResult = saveImgToCX(screenSaveResult.getSavingPath(),
//					screenSaveResult.getFileName(), screenshotImageValidTime);
//			reportService.caseReportAppendImage(caseReport, uploadImgResult.getImgUrl());

			reportService.caseReportAppendContent(caseReport, "完成登录");

			catchWatchMe(tbo.getWebDriver(), caseReport);

			if (runCount == 0 && "1".equals(clawingOptionBO.getRefreshCV())) {
				if (!updateDetail(tbo.getWebDriver(), caseReport, clawingOptionBO)) {
					reportService.caseReportAppendContent(caseReport, "刷新简历失败");
					r.failWithMessage("更新失败");
					throw new Exception();
				} else {
					reportService.caseReportAppendContent(caseReport, "刷新简历完毕");
				}
			} else {
				reportService.caseReportAppendContent(caseReport, "暂不新简历");
			}

			redisOriginalConnectService.setValByName(wuYiRunCountKey, String.valueOf(runCount + 1));
			reportService.caseReportAppendContent(caseReport, "更新 redis 计数: " + (runCount));

			r.setIsSuccess();

		} catch (Exception e) {
			e.printStackTrace();
//			String htmlStr = jsUtil.getHtmlSource(d);

//			TODO 此任务暂不采取截图, 直至完成规范化
//			ScreenshotSaveResult screenSaveResult = screenshot(tbo.getWebDriver(), tbo.getFlowName());
//			ImageSavingResult uploadImgResult = saveImgToCX(screenSaveResult.getSavingPath(),
//					screenSaveResult.getFileName(), screenshotImageValidTime);
//			reportService.caseReportAppendImage(caseReport, uploadImgResult.getImgUrl());
			reportService.caseReportAppendContent(caseReport, "异常: " + e.toString());
//			jsonReporter.appendContent(reportDTO, htmlStr);

		} finally {
			tryQuitWebDriver(tbo.getWebDriver());
			sendAutomationTestResult(tbo);
		}

		return tbo;
	}

	private void findAndCLoseHomePop(WebDriver d, JsonReportOfCaseDTO reportDTO) {
		xPathBuilder.start("div").addClass("homePop");

		try {
			WebElement homepopDiv = d.findElement(By.xpath(xPathBuilder.getXpath()));
			if (homepopDiv == null || !homepopDiv.isDisplayed()) {
				reportService.caseReportAppendContent(reportDTO, "can not find homepop div");
				return;
			} else {
				reportService.caseReportAppendContent(reportDTO, "find homepop div");
			}

			xPathBuilder.findChild("div").addClass("in").findChild("div").addClass("close");

			WebElement homepopCloseDiv = d.findElement(By.xpath(xPathBuilder.getXpath()));
			homepopCloseDiv.click();
			reportService.caseReportAppendContent(reportDTO, "close homepop div");

		} catch (Exception e) {
			reportService.caseReportAppendContent(reportDTO, "close homepop div exception");
			reportService.caseReportAppendContent(reportDTO, e.getLocalizedMessage());
		}
	}

	private void findAndCloseLeadDiv(WebDriver d, JsonReportOfCaseDTO reportDTO) {

		xPathBuilder.start("div").addClass("lead");

		try {
			WebElement leadDiv = d.findElement(By.xpath(xPathBuilder.getXpath()));
			if (leadDiv == null || !leadDiv.isDisplayed()) {
				reportService.caseReportAppendContent(reportDTO, "can not find lead div");
				return;
			} else {
				reportService.caseReportAppendContent(reportDTO, "find lead div");
			}

			xPathBuilder.findChild("div").addClass("close");
			;

			WebElement leadCloseDiv = d.findElement(By.xpath(xPathBuilder.getXpath()));
			leadCloseDiv.click();
			reportService.caseReportAppendContent(reportDTO, "close lead div");

		} catch (Exception e) {
			reportService.caseReportAppendContent(reportDTO, "close lead div exception");
			reportService.caseReportAppendContent(reportDTO, e.getLocalizedMessage());
		}
	}

	private void findAndCloseGoAppDiv(WebDriver d, JsonReportOfCaseDTO reportDTO) {
		xPathBuilder.start("div").addClass("goApp");

		try {
			WebElement goAppDiv = d.findElement(By.xpath(xPathBuilder.getXpath()));
			if (goAppDiv == null || !goAppDiv.isDisplayed()) {
				reportService.caseReportAppendContent(reportDTO, "can not find go app div");
				return;
			} else {
				reportService.caseReportAppendContent(reportDTO, "find go app div");
			}

			xPathBuilder.findChild("em").addClass("close");
			;

			WebElement goAppCloseButton = d.findElement(By.xpath(xPathBuilder.getXpath()));
			goAppCloseButton.click();
			reportService.caseReportAppendContent(reportDTO, "close go app div");

		} catch (Exception e) {
			reportService.caseReportAppendContent(reportDTO, "close go app div exception");
			reportService.caseReportAppendContent(reportDTO, e.getLocalizedMessage());
		}
	}

	private boolean login(WebDriver d, JsonReportOfCaseDTO reportDTO, DailySignAccountBO dailySignBO) {
		try {
			try {
				d.get(dailySignBO.getMainUrl());
				reportService.caseReportAppendContent(reportDTO, "get home page");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				reportService.caseReportAppendContent(reportDTO, "get home page but timeout");
			}

			reportService.caseReportAppendContent(reportDTO, "try find lead div and go app div");
			findAndCLoseHomePop(d, reportDTO);
			threadSleepRandomTime();
			findAndCloseLeadDiv(d, reportDTO);
			threadSleepRandomTime();
			findAndCloseGoAppDiv(d, reportDTO);
			threadSleepRandomTime();
			reportService.caseReportAppendContent(reportDTO, "after close lead and go app div");

			// 向下翻动一下页面, 以使登录按钮出现在页面顶部
			jsUtil.scroll(d, 200);
			reportService.caseReportAppendContent(reportDTO, "after scroll");

//			try {
//				d.get("https://login.51job.com/login.php?display=h5");
//			} catch (TimeoutException te) {
//				jsonReporter.appendContent(reportDTO, "visit login page timeout");
//			} catch (Exception e) {
//				jsonReporter.appendContent(reportDTO, e.getLocalizedMessage());
//				jsonReporter.appendContent(reportDTO, "visit login page fail");
//				throw new Exception();
//			}

			xPathBuilder.start("header").addClass("nologin").findChild("a").addClass("my");
			try {
				WebElement loginPageButton = d.findElement(By.xpath(xPathBuilder.getXpath()));
				for (int i = 0; i < 10 && !loginPageButton.isDisplayed(); i++) {
					jsUtil.scroll(d, 200);
				}
				loginPageButton.click();
			} catch (Exception e) {
				reportService.caseReportAppendContent(reportDTO, "can NOT find login page button");
				throw new Exception();
			}

			reportService.caseReportAppendContent(reportDTO, "after visit login page");
			threadSleepRandomTimeLong();

			xPathBuilder.start().addId("tobydefault").findChild("a").addClass("leftlogin");
			try {
				WebElement loginWithUsernameAndPwdButton = d.findElement(By.xpath(xPathBuilder.getXpath()));
				for (int i = 0; i < 10 && !loginWithUsernameAndPwdButton.isDisplayed(); i++) {
					threadSleepRandomTime();
				}
				loginWithUsernameAndPwdButton.click();
			} catch (Exception e) {
				reportService.caseReportAppendContent(reportDTO, "can NOT find login with username and pwd button");
				throw new Exception();
			}
			threadSleepRandomTimeLong();

			xPathBuilder.start().addId("loginname");
			WebElement usernameInput = null;
			try {
				usernameInput = d.findElement(By.xpath(xPathBuilder.getXpath()));
			} catch (Exception e) {
				reportService.caseReportAppendContent(reportDTO, "找不到用户名输入框");
				throw new Exception();
			}
			usernameInput.click();
			usernameInput.clear();
			usernameInput.sendKeys(dailySignBO.getUsername());
			reportService.caseReportAppendContent(reportDTO, "input username");

			threadSleepRandomTimeLong();

			xPathBuilder.start("div").addClass("picture");
			WebElement logoClick = null;
			try {
				logoClick = d.findElement(By.xpath(xPathBuilder.getXpath()));
				logoClick.click();
				reportService.caseReportAppendContent(reportDTO, "close logo");
				/*
				 * 有时候 输入完用户帐号, 会遮挡住密码输入框, 引起报错
				 */
			} catch (Exception e) {
				reportService.caseReportAppendContent(reportDTO, "找不到Logo");
			}

			xPathBuilder.start().addId("password");
			WebElement pwdInput = null;
			try {
				pwdInput = d.findElement(By.xpath(xPathBuilder.getXpath()));
			} catch (Exception e) {
				reportService.caseReportAppendContent(reportDTO, "找不到密码输入框");
			}
			pwdInput.click();
			pwdInput.clear();
			pwdInput.sendKeys(dailySignBO.getPwd());
			reportService.caseReportAppendContent(reportDTO, "input pwd");

			threadSleepRandomTime(1000L, 3000L);

			xPathBuilder.start("button").addAttribute("id", "login_btn");
			WebElement loginButton = null;
			try {
				loginButton = d.findElement(By.xpath(xPathBuilder.getXpath()));
			} catch (Exception e) {
				reportService.caseReportAppendContent(reportDTO, "找不到登录按钮");
			}
			loginButton.click();
			reportService.caseReportAppendContent(reportDTO, "click login button");

			return true;
		} catch (Exception e) {

			reportService.caseReportAppendContent(reportDTO, "login exception");
			reportService.caseReportAppendContent(reportDTO, e.getLocalizedMessage());
			return false;
		}
	}

	private boolean updateDetail(WebDriver d, JsonReportOfCaseDTO reportDTO, WuYiJobClawingBO dailySignBO) {
		try {
			try {
				d.get("https://m.51job.com/my/my51job.php");
				reportService.caseReportAppendContent(reportDTO, "get 我的51job ");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				reportService.caseReportAppendContent(reportDTO, "get 我的51job  but timeout");
			}

			threadSleepRandomTime(1000L, 3000L);

			try {
				d.get("https://m.51job.com/resume/myresume.php");
				reportService.caseReportAppendContent(reportDTO, "get 我的简历");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				reportService.caseReportAppendContent(reportDTO, "get 我的简历 but timeout");
			}

			threadSleepRandomTime(1000L, 3000L);

			try {
				d.get(dailySignBO.getResumeDetailUrl());
				reportService.caseReportAppendContent(reportDTO, "get 指定简历");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				reportService.caseReportAppendContent(reportDTO, "get 指定简历 but timeout");
			}

			threadSleepRandomTime(1000L, 3000L);

			try {
				d.get(dailySignBO.getJobintentUrl());
				reportService.caseReportAppendContent(reportDTO, "get 指定简历编辑界面");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				reportService.caseReportAppendContent(reportDTO, "get 指定简历编辑界面 but timeout");
			}

			threadSleepRandomTime(1000L, 3000L);

			xPathBuilder.start("textarea").addAttribute("id", "intro");
			WebElement intentionDetailTextarea = null;
			try {
				intentionDetailTextarea = d.findElement(By.xpath(xPathBuilder.getXpath()));
			} catch (Exception e) {
				reportService.caseReportAppendContent(reportDTO, "无法找到简历简介编辑框");
				return false;
			}
			String intentionDetailSourceStr = intentionDetailTextarea.getText();
			String lineBreak = null;
			if (intentionDetailSourceStr.contains(System.lineSeparator())) {
				lineBreak = System.lineSeparator();
			} else if (intentionDetailSourceStr.contains("\r\n")) {
				lineBreak = "\r\n";
			} else if (intentionDetailSourceStr.contains("\n")) {
				lineBreak = "\n";
			} else if (intentionDetailSourceStr.contains("\r")) {
				lineBreak = "\r";
			}
			String[] sourceLines = intentionDetailSourceStr.split(lineBreak);
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < sourceLines.length; i++) {
				if (i == sourceLines.length - 1) {
					sb.append(snowFlake.getNextId());
				} else {
					sb.append(sourceLines[i]);
					sb.append(lineBreak);
				}
			}

			intentionDetailTextarea.clear();
			intentionDetailTextarea.sendKeys(sb.toString());

			reportService.caseReportAppendContent(reportDTO, "完成编辑内容");

			threadSleepRandomTime(1000L, 3000L);

			d.findElement(By.id("saveselfintro")).click();
			reportService.caseReportAppendContent(reportDTO, "点击保存");

			threadSleepRandomTime(1000L, 3000L);

			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean catchWatchMe(WebDriver d, JsonReportOfCaseDTO reportDTO) {
		try {
			try {
				d.get("https://m.51job.com/my/whosawrsm.php");
				reportService.caseReportAppendContent(reportDTO, "进入手机版 我的51job");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				reportService.caseReportAppendContent(reportDTO, "进入手机版 我的51job but timeout");
			}

			String companyLinkX = xPathBuilder.start("div").addAttribute("class", "new_l").findChild("a", 1).getXpath();
			String companyNameX = xPathBuilder.start("div").addAttribute("class", "new_l").findChild("a", 1).findChild("p")
					.addAttribute("class", "gs").getXpath();
			String resumeNameX = xPathBuilder.start("div").addAttribute("class", "new_l").findChild("a", 1).findChild("div")
					.addAttribute("class", "ms").findChild("b").findChild("span").getXpath();
			String likelyX = xPathBuilder.start("div").addAttribute("class", "new_l").findChild("a", 1).findChild("div")
					.addAttribute("class", "ms").findChild("p").addAttribute("class", "lk").findChild("object")
					.findChild("a").findChild("span") // class="s4"
					.getXpath();
			String watchTimeX = xPathBuilder.start("div").addAttribute("class", "new_l").findChild("a", 1).findChild("div")
					.addAttribute("class", "ms").findChild("p").addAttribute("class", "lk").findChild("em") // 查看时间:01-06
																											// 10:39
					.getXpath();

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
				reportService.caseReportAppendContent(reportDTO, "查找页面元素异常, 或尚无公司浏览记录");
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
				reportService.caseReportAppendContent(reportDTO, "拼凑 WuyiWatchMe PO 异常: " + e.toString());
				return false;
			}

			WuyiWatchMe lastWatchPO = wuyiWatcheMeMapper.findTheLastWatch();
			if (lastWatchPO != null && lastWatchPO.getWatchTime().isEqual(theWatchTime)) {
				if (lastWatchPO.getCompanyLink().equals(companyLinkDiv.getAttribute("href"))) {
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
		example.createCriteria().andIsDeleteEqualTo(false)
				.andCreateTimeGreaterThan(LocalDateTime.now().minusMonths(12));
		example.setOrderByClause("watch_time");
		List<WuyiWatchMe> poList = wuyiWatcheMeMapper.selectByExample(example);

		WuyiWatchMeVO vo = null;
		Map<String, WuyiWatchMeVO> voMap = new HashMap<>();
		for (WuyiWatchMe i : poList) {
			vo = voMap.get(i.getCompanyLink() + i.getCompanyName() + i.getMyResumeName());
			if (vo == null) {
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
				vo.setDegreeOfInterestAvg((vo.getDegreeOfInterestAvg() * vo.getWatchCount() + i.getDegreeOfInterest())
						/ (vo.getWatchCount() + 1));
				if (i.getWatchTime().isAfter(vo.getLastWatchTime())) {
					vo.setLastWatchTime(i.getWatchTime());
				}
				if (StringUtils.isBlank(vo.getMyResumeName()) || !vo.getMyResumeName().contains(i.getMyResumeName())) {
					vo.setMyResumeName(vo.getMyResumeName() + ", " + i.getMyResumeName());
				}
				vo.setWatchCount(vo.getWatchCount() + 1);
			}

			voMap.put(i.getCompanyLink() + i.getCompanyName() + i.getMyResumeName(), vo);
		}

		List<WuyiWatchMeVO> voList = new ArrayList<WuyiWatchMeVO>(voMap.values());
		Collections.sort(voList);
		v.addObject("voList", voList);
		return v;
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
}
