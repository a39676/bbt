package demo.clawing.movie.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.pojo.bo.XpathBuilderBO;
import at.pojo.dto.JsonReportDTO;
import at.pojo.dto.TakeScreenshotSaveDTO;
import at.pojo.result.ScreenshotSaveResult;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.clawing.movie.mapper.MovieInfoMapper;
import demo.clawing.movie.mapper.MovieIntroductionMapper;
import demo.clawing.movie.mapper.MovieRecordMapper;
import demo.clawing.movie.pojo.constant.MovieClawingConstant;
import demo.clawing.movie.pojo.dto.MovieRecordFindByConditionDTO;
import demo.clawing.movie.pojo.po.MovieInfo;
import demo.clawing.movie.pojo.po.MovieIntroduction;
import demo.clawing.movie.pojo.po.MovieRecord;
import demo.clawing.movie.pojo.result.DoubanSubClawingResult;
import demo.clawing.movie.pojo.type.MovieClawingCaseType;
import demo.clawing.movie.service.DyttClawingService;
import demo.selenium.service.pojo.bo.BuildTestEventBO;
import image.pojo.result.UploadImageToCloudinaryResult;
import toolPack.ioHandle.FileUtilCustom;

@Service
public final class DyttClawingServiceImpl extends MovieClawingCommonService implements DyttClawingService {

	@Autowired
	private FileUtilCustom iou;
	
	@Autowired
	private MovieInfoMapper infoMapper;
	@Autowired
	private MovieRecordMapper recordMapper;
	@Autowired
	private MovieIntroductionMapper introduectionMapper;

	private String mainUrl = "https://www.dytt8.net";
	private String newMovie = mainUrl + "/html/gndy/dyzz/index.html";

	private String eventName = "dytt";
	
	private String getScreenshotSaveingPath() {
		return globalOptionService.getScreenshotSavingFolder() + File.separator + eventName;
	}
	
	private String getReportOutputPath() {
		return globalOptionService.getReportOutputFolder() + File.separator + eventName;
	}
	
	private TestEvent buildTestEvent() {
		MovieClawingCaseType t = MovieClawingCaseType.dytt;
		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(TestModuleType.movieClawing);
		bo.setCaseId(t.getId());
		bo.setEventName(t.getEventName());
		return buildTestEvent(bo);
	}
	
	@Override
	public InsertTestEventResult insertclawingEvent() {
		TestEvent te = buildTestEvent();
		return testEventService.insertTestEvent(te);
	}
	
	@Override
	public CommonResultBBT clawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		JsonReportDTO reportDTO = new JsonReportDTO();
		
		String reportOutputFolderPath = getReportOutputPath();
		
		reportDTO.setOutputReportPath(reportOutputFolderPath + File.separator + te.getId());
		
		WebDriver d = null;
		int maxClawPageCount = 3;

		try {
			d = webDriverService.buildFireFoxWebDriver();
			d.get(newMovie);
			
			while(maxClawPageCount > 0) {
				pageHandler(d, te, reportDTO);
				swithToNextPage(d);
				maxClawPageCount--;
			}
			
			r.setIsSuccess();
		} catch (Exception e) {
			jsonReporter.appendContent(reportDTO, e.toString());
		} finally {
			tryQuitWebDriver(d);
		}
		
		try {
			String reportOutputPath = reportDTO.getOutputReportPath() + File.separator + te.getId() + ".json";
			if(jsonReporter.outputReport(reportDTO, reportOutputPath)) {
				updateTestEventReportPath(te, reportOutputPath);
			}
		} catch (Exception e) {
		}
		
		return r;
	}
	
	private void pageHandler(WebDriver d, TestEvent te, JsonReportDTO reportDTO) throws Exception {
		String mainWindowHandle = d.getWindowHandle();
		Set<String> windowHandles = null;
		d.switchTo().window(mainWindowHandle);
		
		jsonReporter.appendContent(reportDTO, "切换窗口");

		XpathBuilderBO xpathBuilder = new XpathBuilderBO();
		xpathBuilder.start("a").addAttribute("class", "ulink");
		By targetAListBy = By.xpath(xpathBuilder.getXpath());

		List<WebElement> targetAList = d.findElements(targetAListBy);
		
		jsonReporter.appendContent(reportDTO, "找到" + targetAList.size() + "个链接");
		
		WebElement ele = null;
		for (int i = 0; i < targetAList.size(); i++) {
			ele = targetAList.get(i);
			singleMovieHandle(d, ele, mainWindowHandle, te, reportDTO);
			jsonReporter.appendContent(reportDTO, "处理第" + i + "个");
			windowHandles = d.getWindowHandles();
			if (windowHandles.size() > 5) {
				jsonReporter.appendContent(reportDTO, "窗口多开数量异常");
				throw new Exception();
			}
			d.switchTo().window(mainWindowHandle);
			jsonReporter.appendContent(reportDTO, "返回主窗口");
		}
	}

	private void swithToNextPage(WebDriver d) throws InterruptedException {
		Thread.sleep(800L);
		String oldHandle = d.getWindowHandle();
		WebElement nextPageButton = d.findElement(By.linkText("下一页"));
		nextPageButton.click();
		Thread.sleep(500L);
		Set<String> handlers = d.getWindowHandles();
		for(String h : handlers) {
			if(!h.equals(oldHandle)) {
				d.close();
				d.switchTo().window(h);
			}
		}
	}
	
	private void singleMovieHandle(WebDriver d, WebElement ele, String mainWindowHandler, TestEvent te, JsonReportDTO reportDTO) throws Exception {
		if (ele == null || StringUtils.isBlank(ele.getAttribute("href"))) {
			return;
		}
		
		String screenshotPath = getScreenshotSaveingPath();
		
		String subUrl = ele.getAttribute("href");
		jsonReporter.appendContent(reportDTO, "正在处理: " + subUrl);
		MovieRecordFindByConditionDTO findMovieRecordDTO = new MovieRecordFindByConditionDTO();
		findMovieRecordDTO.setUrl(subUrl);
		findMovieRecordDTO.setWasClaw(true);
		findMovieRecordDTO.setCaseId(MovieClawingCaseType.dytt.getId());
		List<MovieRecord> records = recordMapper.findByCondition(findMovieRecordDTO);
		if (records != null && records.size() > 0) {
			jsonReporter.appendContent(reportDTO, "本条url 已经处理过:" + subUrl);
			return;
		}
		
		ele.click();
		Thread.sleep(1200L);

		String currentUrl = null;
		String targetWindowHandle = null;

		Set<String> windows = d.getWindowHandles();
		if(windows.size() < 2) {
			Thread.sleep(3200L);
		}
		for (String w : windows) {
			d.switchTo().window(w);
			currentUrl = d.getCurrentUrl();
			if (targetWindowHandle == null && currentUrl.contains(subUrl)) {
				targetWindowHandle = w;
			}
		}

		if (targetWindowHandle == null) {
			jsonReporter.appendContent(reportDTO, "无法找到目标窗口");
			throw new Exception();
		} else {
			d.switchTo().window(targetWindowHandle);
			jsonReporter.appendContent(reportDTO, "切换到目标窗口");
		}
		currentUrl = d.getCurrentUrl();

		XpathBuilderBO xpathBuilder = new XpathBuilderBO();
		xpathBuilder.start("div").addAttribute("id", "Zoom");
		By divZoomBy = By.xpath(xpathBuilder.getXpath());
		Thread.sleep(300L);
		WebElement divZoom = null;

		try {
			divZoom = d.findElement(divZoomBy);
			jsonReporter.appendContent(reportDTO, "找到内容主div");
			Long newMovieId = snowFlake.getNextId();

			List<WebElement> imgs = divZoom.findElements(ByTagName.tagName("img"));
			jsonReporter.appendContent(reportDTO, "准备处理图片数据");
			saveMovieImg(imgs, newMovieId);

			List<WebElement> aTags = divZoom.findElements(ByTagName.tagName("a"));
			jsonReporter.appendContent(reportDTO, "准备匹配磁链");
			handleMovieMagnetUrl(aTags, newMovieId);

			List<WebElement> pTags = divZoom.findElements(ByTagName.tagName("p"));
			jsonReporter.appendContent(reportDTO, "准备匹配详细信息");
			saveMovieInfo(d, pTags, newMovieId, te, reportDTO);


			MovieRecord record = new MovieRecord();
			record.setUrl(currentUrl);
			record.setId(snowFlake.getNextId());
			record.setWasClaw(true);
			record.setMovieId(newMovieId);
			record.setCaseId(MovieClawingCaseType.dytt.getId());
			recordMapper.insertSelective(record);
			
			jsonReporter.appendContent(reportDTO, "更新db for: " + currentUrl);

		} catch (Exception e) {
			e.printStackTrace();
			jsonReporter.appendContent(reportDTO, e.toString());
			TakeScreenshotSaveDTO screenshotDTO = new TakeScreenshotSaveDTO();
			screenshotDTO.setDriver(d);
			ScreenshotSaveResult screenSaveResult = screenshotService.screenshotSave(screenshotDTO, screenshotPath, null);
			
			UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			jsonReporter.appendImage(reportDTO, uploadImgResult.getImgUrl());
			
		} finally {
			if (!mainWindowHandler.equals(d.getWindowHandle())) {
				d.close();
				jsonReporter.appendContent(reportDTO, "关闭子窗口");
			}
		}
	}

	private void handleMovieMagnetUrl(List<WebElement> aTags, Long movieId) {
		String href = null;
		for (WebElement ele : aTags) {
			href = ele.getAttribute("href");
			if (href.startsWith(MovieClawingConstant.magnetPrefix)) {
				saveMovieMagnetUrl(href, movieId);
			}
		}
	}

	private void saveMovieInfo(WebDriver d, List<WebElement> pTags, Long movieId, TestEvent te, JsonReportDTO reportDTO) {
		WebElement targetP = null;
		WebElement tmpEle = null;
		
		jsonReporter.appendContent(reportDTO, "准备处理dytt 来源信息");
		for (int i = 0; i < pTags.size() && targetP == null; i++) {
			tmpEle = pTags.get(i);
			if (StringUtils.isNotBlank(tmpEle.getText())) {
				targetP = tmpEle;
			}
		}

		String content = targetP.getText();
		
		MovieInfo info = new MovieInfo();
		info.setId(movieId);
		
		List<String> lines = Arrays.asList(content.split("◎"));
		for(String line : lines) {
			if(line.startsWith("片") && line.contains("名")) {
				info.setOriginalTitle(line.replaceAll("片　　名　", ""));
			} else if(line.startsWith("译") && line.contains("名")) {
				info.setCnTitle(line.replaceAll("译　　名　", ""));
			}
		}
		
		jsonReporter.appendContent(reportDTO, "初步处理 dytt 信息, 准备前往 douban");
		
		DoubanSubClawingResult doubanResult = null;
		if(StringUtils.isNotBlank(info.getCnTitle())) {
			doubanResult = doubanService.clawing(d, info.getCnTitle(), te);
		} else {
			doubanResult = doubanService.clawing(d, info.getOriginalTitle(), te);
		}
		
		if(doubanResult.isSuccess()) {
			jsonReporter.appendContent(reportDTO, "成功处理 douban 返回信息");
		} else {
			jsonReporter.appendContent(reportDTO, "douban 返回信息处理异常");
		}
		
		info.setCnTitle(doubanResult.getCnTitle());
		info.setOriginalTitle(doubanResult.getOriginalTitle());
		info.setNationId(detectMovieRegion(doubanResult.getRegion()).longValue());
		
		infoMapper.insertSelective(info);
		
		jsonReporter.appendContent(reportDTO, "成功插入详情介绍");

		String savePath = introductionSavePath + File.separator + movieId + ".txt";
		if(StringUtils.isNotBlank(doubanResult.getIntroduction())) {
			iou.byteToFile(doubanResult.getIntroduction().getBytes(StandardCharsets.UTF_8), savePath);
		}
		
		jsonReporter.appendContent(reportDTO, "保存详情文档");

		MovieIntroduction po = new MovieIntroduction();
		po.setMovieId(movieId);
		po.setIntroPath(savePath);
		introduectionMapper.insertSelective(po);
		
		jsonReporter.appendContent(reportDTO, "更新详情保存路径到db");
	}

}
