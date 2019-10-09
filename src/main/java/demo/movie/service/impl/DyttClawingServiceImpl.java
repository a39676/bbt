package demo.movie.service.impl;

import java.io.File;
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

import demo.movie.mapper.MovieInfoMapper;
import demo.movie.mapper.MovieIntroductionMapper;
import demo.movie.mapper.MovieRecordMapper;
import demo.movie.pojo.constant.MovieClawingConstant;
import demo.movie.pojo.dto.MovieRecordFindByConditionDTO;
import demo.movie.pojo.po.MovieInfo;
import demo.movie.pojo.po.MovieIntroduction;
import demo.movie.pojo.po.MovieRecord;
import demo.movie.service.DyttClawingService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.type.MovieTestCaseType;
import ioHandle.FileUtilCustom;

@Service
public final class DyttClawingServiceImpl extends MovieClawingCommonService implements DyttClawingService {

	@Autowired
	private FileUtilCustom iou;

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
//	@Autowired
//	private JavaScriptService jsUtil;

	
	@Autowired
	private MovieInfoMapper infoMapper;
	@Autowired
	private MovieRecordMapper recordMapper;
	@Autowired
	private MovieIntroductionMapper introduectionMapper;

	private String mainUrl = "https://www.dytt8.net";
	private String newMovie = mainUrl + "/html/gndy/dyzz/index.html";

	private TestEvent buildTestEvent() {
		TestEvent te = new TestEvent();
		te.setCaseId(MovieTestCaseType.dytt.getId());
		te.setId(snowFlake.getNextId());
		te.setEventName(MovieTestCaseType.dytt.getEventName());
		return te;
	}
	
	@Override
	public String clawing() {
		if(existsRuningEvent()) {
			return "existsRuningEvent";
		}
		boolean exceptionFlag = false;
		TestEvent te = buildTestEvent();
		startEvent(te);
		
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		int maxClawPageCount = 3;

		try {
			d.get(newMovie);
			
			while(maxClawPageCount > 0) {
				pageHandler(d, te);
				swithToNextPage(d);
				maxClawPageCount--;
			}
			endEventSuccess(te);
		} catch (Exception e) {
			exceptionFlag = true;
			e.printStackTrace();
			endEventFail(te);
			auxTool.takeScreenshot(d, te);
		} finally {
			if (d != null) {
				d.quit();
			}
		}
		return "exceptionFlag: " + exceptionFlag;
	}
	
	private void pageHandler(WebDriver d, TestEvent te) throws Exception {
		String mainWindowHandle = d.getWindowHandle();
		Set<String> windowHandles = null;
		d.switchTo().window(mainWindowHandle);

		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("a", "class", "ulink");
		By targetAListBy = auxTool.byXpathBuilder(byXpathConditionBo);

		List<WebElement> targetAList = d.findElements(targetAListBy);
		WebElement ele = null;
		for (int i = 0; i < targetAList.size(); i++) {
			ele = targetAList.get(i);
			singleMovieHandle(d, ele, mainWindowHandle);
			windowHandles = d.getWindowHandles();
			if (windowHandles.size() > 5) {
				throw new Exception();
			}
			d.switchTo().window(mainWindowHandle);
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
	
	private void singleMovieHandle(WebDriver d, WebElement ele, String mainWindowHandler) throws Exception {
		if (ele == null || StringUtils.isBlank(ele.getAttribute("href"))) {
			return;
		}
		
		String subUrl = ele.getAttribute("href");
		MovieRecordFindByConditionDTO findMovieRecordDTO = new MovieRecordFindByConditionDTO();
		findMovieRecordDTO.setUrl(subUrl);
		findMovieRecordDTO.setWasClaw(true);
		findMovieRecordDTO.setCaseId(MovieTestCaseType.dytt.getId());
		List<MovieRecord> records = recordMapper.findByCondition(findMovieRecordDTO);
		if (records != null && records.size() > 0) {
			log.info(subUrl + " was clawed");
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
			log.info("can not find correct window");
			throw new Exception();
		} else {
			d.switchTo().window(targetWindowHandle);
			log.info("switch to : " + targetWindowHandle);
		}
		currentUrl = d.getCurrentUrl();

		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("div", "id", "Zoom");
		By divZoomBy = auxTool.byXpathBuilder(byXpathConditionBo);
		Thread.sleep(300L);
		WebElement divZoom = null;

		try {
			divZoom = d.findElement(divZoomBy);
			Long newMovieId = snowFlake.getNextId();

			List<WebElement> imgs = divZoom.findElements(ByTagName.tagName("img"));
			saveMovieImg(imgs, newMovieId);

			List<WebElement> aTags = divZoom.findElements(ByTagName.tagName("a"));
			handleMovieMagnetUrl(aTags, newMovieId);

			List<WebElement> pTags = divZoom.findElements(ByTagName.tagName("p"));
			saveMovieInfo(pTags, newMovieId);


			MovieRecord record = new MovieRecord();
			record.setUrl(currentUrl);
			record.setId(snowFlake.getNextId());
			record.setWasClaw(true);
			record.setMovieId(newMovieId);
			record.setCaseId(MovieTestCaseType.dytt.getId());
			recordMapper.insertSelective(record);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!mainWindowHandler.equals(d.getWindowHandle())) {
				d.close();
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

	private void saveMovieInfo(List<WebElement> pTags, Long movieId) {
		WebElement targetP = null;
		WebElement tmpEle = null;
		for (int i = 0; i < pTags.size() && targetP == null; i++) {
			tmpEle = pTags.get(i);
			if (StringUtils.isNotBlank(tmpEle.getText())) {
				targetP = tmpEle;
			}
		}

		String content = targetP.getText();
		content = content.replaceAll("【下载地址】", "").replaceAll("磁力链下载点击这里", "");
		
		MovieInfo info = new MovieInfo();
		info.setId(movieId);
		
		List<String> lines = Arrays.asList(content.split("◎"));
		for(String line : lines) {
			if(line.startsWith("片") && line.contains("名")) {
				info.setOriginalTitle(line.replaceAll("片　　名　", ""));
			} else if(line.startsWith("译") && line.contains("名")) {
				info.setCnTitle(line.replaceAll("译　　名　", ""));
			} else if(line.startsWith("产") && line.contains("地")) {
				info.setNationId(detectMovieRegion(line).longValue());
			} 
		}
		infoMapper.insertSelective(info);

		String savePath = introductionSavePath + File.separator + movieId + ".txt";
		iou.byteToFile(content.getBytes(), savePath);

		MovieIntroduction po = new MovieIntroduction();
		po.setMovieId(movieId);
		po.setIntroPath(savePath);
		introduectionMapper.insertSelective(po);
	}

}
