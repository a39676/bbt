package demo.movie.service.impl;

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
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.movie.mapper.MovieInfoMapper;
import demo.movie.mapper.MovieIntroductionMapper;
import demo.movie.mapper.MovieRecordMapper;
import demo.movie.pojo.constant.MovieClawingConstant;
import demo.movie.pojo.dto.MovieRecordFindByConditionDTO;
import demo.movie.pojo.po.MovieInfo;
import demo.movie.pojo.po.MovieIntroduction;
import demo.movie.pojo.po.MovieRecord;
import demo.movie.pojo.result.DoubanSubClawingResult;
import demo.movie.pojo.type.MovieClawingCaseType;
import demo.movie.service.DyttClawingService;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.result.InsertTestEventResult;
import demo.testCase.pojo.type.TestModuleType;
import ioHandle.FileUtilCustom;

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

	private TestEvent buildTestEvent() {
		MovieClawingCaseType t = MovieClawingCaseType.dytt;
		return buildTestEvent(TestModuleType.movieClawing, t.getId(), t.getEventName());
	}
	
	@Override
	public InsertTestEventResult insertclawingEvent() {
		TestEvent te = buildTestEvent();
		return testEventService.insertTestEvent(te);
	}
	
	@Override
	public CommonResultBBT clawing(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();
		
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		int maxClawPageCount = 3;

		try {
			d.get(newMovie);
			
			while(maxClawPageCount > 0) {
				pageHandler(d, te);
				swithToNextPage(d);
				maxClawPageCount--;
			}
			
			r.setIsSuccess();
		} catch (Exception e) {
			report.append(e.getMessage() + "\n");
			
		} finally {
			r.setMessage(report.toString());
			if (d != null) {
				d.quit();
			}
		}
		
		return r;
	}
	
	private void pageHandler(WebDriver d, TestEvent te) throws Exception {
		String mainWindowHandle = d.getWindowHandle();
		Set<String> windowHandles = null;
		d.switchTo().window(mainWindowHandle);

		XpathBuilderBO xpathBuilder = new XpathBuilderBO();
		xpathBuilder.start("a").addAttribute("class", "ulink");
		By targetAListBy = By.xpath(xpathBuilder.getXpath());

		List<WebElement> targetAList = d.findElements(targetAListBy);
		WebElement ele = null;
		for (int i = 0; i < targetAList.size(); i++) {
			ele = targetAList.get(i);
			singleMovieHandle(d, ele, mainWindowHandle, te);
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
	
	private void singleMovieHandle(WebDriver d, WebElement ele, String mainWindowHandler, TestEvent te) throws Exception {
		if (ele == null || StringUtils.isBlank(ele.getAttribute("href"))) {
			return;
		}
		
		String subUrl = ele.getAttribute("href");
		MovieRecordFindByConditionDTO findMovieRecordDTO = new MovieRecordFindByConditionDTO();
		findMovieRecordDTO.setUrl(subUrl);
		findMovieRecordDTO.setWasClaw(true);
		findMovieRecordDTO.setCaseId(MovieClawingCaseType.dytt.getId());
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

		XpathBuilderBO xpathBuilder = new XpathBuilderBO();
		xpathBuilder.start("div").addAttribute("id", "Zoom");
		By divZoomBy = By.xpath(xpathBuilder.getXpath());
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
			saveMovieInfo(d, pTags, newMovieId, te);


			MovieRecord record = new MovieRecord();
			record.setUrl(currentUrl);
			record.setId(snowFlake.getNextId());
			record.setWasClaw(true);
			record.setMovieId(newMovieId);
			record.setCaseId(MovieClawingCaseType.dytt.getId());
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

	private void saveMovieInfo(WebDriver d, List<WebElement> pTags, Long movieId, TestEvent te) {
		WebElement targetP = null;
		WebElement tmpEle = null;
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
		
		DoubanSubClawingResult doubanResult = null;
		if(StringUtils.isNotBlank(info.getCnTitle())) {
			doubanResult = doubanService.clawing(d, info.getCnTitle(), te);
		} else {
			doubanResult = doubanService.clawing(d, info.getOriginalTitle(), te);
		}
		info.setCnTitle(doubanResult.getCnTitle());
		info.setOriginalTitle(doubanResult.getOriginalTitle());
		info.setNationId(detectMovieRegion(doubanResult.getRegion()).longValue());
		
		infoMapper.insertSelective(info);

		String savePath = introductionSavePath + File.separator + movieId + ".txt";
		iou.byteToFile(doubanResult.getIntroduction().getBytes(StandardCharsets.UTF_8), savePath);

		MovieIntroduction po = new MovieIntroduction();
		po.setMovieId(movieId);
		po.setIntroPath(savePath);
		introduectionMapper.insertSelective(po);
	}

}
