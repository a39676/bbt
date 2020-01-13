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
import demo.clawing.dailySign.service.WuYiJobDailySignService;
import demo.selenium.service.impl.SeleniumCommonService;
import demo.selenium.service.pojo.bo.BuildTestEventBO;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service
public class WuYiJobDailySignServiceImpl extends SeleniumCommonService implements WuYiJobDailySignService {

	@Autowired
	private WuyiWatchMeMapper wuyiWatcheMeMapper;
	
	private String dailySignEventName = "wuYiJobDailySign";
	
	private String userDataFileName = "51jobDailySign.json";
	
	private TestModuleType testModuleType = TestModuleType.dailySign;
	private DailySignCaseType testCastType = DailySignCaseType.wuYiJob;

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
	public InsertTestEventResult insertDailySignEvent() {
		TestEvent te = buildDailySignEvent();
		return testEventService.insertTestEvent(te);
	}

	@Override
	public CommonResultBBT dailySign(TestEvent te) {
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
			
			catchWatchMe(d, reportDTO);
			if(runCount == 0) {
				if(!updateDetail(d, reportDTO)) {
					r.failWithMessage("更新失败");
					throw new Exception();
				}
			}
			
			constantService.setValByName(wuYiRunCountKey, String.valueOf(runCount + 1));
			
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
		XpathBuilderBO x = new XpathBuilderBO();
		
		try {
			try {
				d.get(dailySignBO.getMainUrl());
				jsonReporter.appendContent(reportDTO, "get");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}
			
			findAndCloseLeadDiv(d);
			
			x.start("div").addAttribute("id", "pageTop")
			.findChild("header")
			.findChild("p").addAttribute("class", "links")
			.findChild("a", 1)
			;
			
			d.findElement(By.xpath(x.getXpath())).click();
			
			x.start("input").addAttribute("id", "loginname");
			WebElement usernameInput = d.findElement(By.xpath(x.getXpath()));
			usernameInput.click();
			usernameInput.clear();
			usernameInput.sendKeys(dailySignBO.getUsername());
			
			x.start("input").addAttribute("id", "password");
			WebElement pwdInput = d.findElement(By.xpath(x.getXpath()));
			pwdInput.click();
			pwdInput.clear();
			pwdInput.sendKeys(dailySignBO.getPwd());
			
			x.start("button").addAttribute("id", "login_btn");
			WebElement loginButton = d.findElement(By.xpath(x.getXpath()));
			loginButton.click();
			
			return true;
		} catch (Exception e) {
			return false;
		}
		
		
		
	}

	private boolean updateDetail(WebDriver d, JsonReportDTO reportDTO) {
		XpathBuilderBO x = new XpathBuilderBO();
		
		try {
			try {
				d.get("https://m.51job.com/my/my51job.php");
				jsonReporter.appendContent(reportDTO, "get");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}
			
			try {
				d.get("https://m.51job.com/resume/myresume.php");
				jsonReporter.appendContent(reportDTO, "get");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}
			
			try {
				d.get("https://m.51job.com/resume/detail.php?userid=398934495");
				jsonReporter.appendContent(reportDTO, "get");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}
			
			try {
				d.get("https://m.51job.com/resume/jobintent.php?userid=398934495");
				jsonReporter.appendContent(reportDTO, "get");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get but timeout");
			}
			
			x.start("textarea").addAttribute("id", "intro");
			WebElement intentionDetailTextarea = d.findElement(By.xpath(x.getXpath()));
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
			
			d.findElement(By.id("saveresumefour")).click();
			
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
				jsonReporter.appendContent(reportDTO, "whosawrsm.php");
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
				jsonReporter.appendContent(reportDTO, "get whosawrsm.php but timeout");
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
			
			WebElement companyLinkDiv = d.findElement(By.xpath(companyLinkX));
			WebElement companyNameP = d.findElement(By.xpath(companyNameX));
			WebElement resumeNameDiv = d.findElement(By.xpath(resumeNameX));
			WebElement likelySpan = d.findElement(By.xpath(likelyX));
			WebElement watcheTimeEm = d.findElement(By.xpath(watchTimeX));
			
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
			
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
	
	@Override
	public ModelAndView watchMeList() {
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
