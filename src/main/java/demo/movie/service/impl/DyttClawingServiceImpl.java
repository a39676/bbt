package demo.movie.service.impl;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.image.mapper.ImageStoreMapper;
import demo.image.pojo.po.ImageStore;
import demo.image.pojo.type.ImageType;
import demo.movie.mapper.MovieImageMapper;
import demo.movie.mapper.MovieInfoMapper;
import demo.movie.mapper.MovieIntroductionMapper;
import demo.movie.mapper.MovieMagnetUrlMapper;
import demo.movie.mapper.MovieRecordMapper;
import demo.movie.pojo.dto.MovieRecordFindByConditionDTO;
import demo.movie.pojo.po.MovieImage;
import demo.movie.pojo.po.MovieInfo;
import demo.movie.pojo.po.MovieIntroduction;
import demo.movie.pojo.po.MovieMagnetUrl;
import demo.movie.pojo.po.MovieRecord;
import demo.movie.service.DyttClawingService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;
import demo.testCase.pojo.po.TestEvent;
import ioHandle.FileUtilCustom;
import movie.pojo.type.MovieRegionType;

@Service
public class DyttClawingServiceImpl extends MovieClawingCommonService implements DyttClawingService {

	@Autowired
	private FileUtilCustom iou;

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
//	@Autowired
//	private JavaScriptService jsUtil;

	@Autowired
	private ImageStoreMapper imageStoreMapper;
	@Autowired
	private MovieImageMapper movieImageMapper;
	@Autowired
	private MovieInfoMapper infoMapper;
	@Autowired
	private MovieMagnetUrlMapper movieMangetUrlMapper;
	@Autowired
	private MovieRecordMapper recordMapper;
	@Autowired
	private MovieIntroductionMapper introduectionMapper;

	private String mainUrl = "https://www.dytt8.net";
	private String newMovie = mainUrl + "/html/gndy/dyzz/index.html";

	@Override
	public void clawing() {
		TestEvent te = new TestEvent();
		te.setId(5L);
		te.setEventName("dyttTest");
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		int maxClawPageCount = 20;

		try {
			d.get(newMovie);
			
			while(maxClawPageCount > 0) {
				pageHandler(d, te);
				swithToNextPage(d);
				maxClawPageCount--;
			}

		} catch (Exception e) {
			e.printStackTrace();
			auxTool.takeScreenshot(d, te);
		} finally {
			if (d != null) {
				d.quit();
			}
		}
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
			saveMovieMagnetUrl(aTags, newMovieId);

			List<WebElement> pTags = divZoom.findElements(ByTagName.tagName("p"));
			saveMovieInfo(pTags, newMovieId);


			MovieRecord record = new MovieRecord();
			record.setUrl(currentUrl);
			record.setId(snowFlake.getNextId());
			recordMapper.insertSelective(record);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (!mainWindowHandler.equals(d.getWindowHandle())) {
				d.close();
			}
		}
	}

	private void saveMovieImg(List<WebElement> imgs, Long movieId) {
		String src = null;
		for (WebElement i : imgs) {
			src = i.getAttribute("src");
			Dimension s = i.getSize();
			if (s.height > 200 && s.width > 200) {
				Long newImgId = snowFlake.getNextId();
				ImageStore po = new ImageStore();
				po.setId(newImgId);
				po.setImagePath(src);
				po.setImageType(ImageType.moviePoster.getCode().byteValue());
				imageStoreMapper.insertSelective(po);

				MovieImage record = new MovieImage();
				record.setMovidId(movieId);
				record.setImageId(newImgId);
				movieImageMapper.insertSelective(record);
			}
		}
	}

	private void saveMovieMagnetUrl(List<WebElement> aTags, Long movieId) {
		String href = null;
		for (WebElement ele : aTags) {
			href = ele.getAttribute("href");
			if (href.startsWith("magnet:?xt")) {
				Long newMovieMagnetUrlId = snowFlake.getNextId();
				MovieMagnetUrl po = new MovieMagnetUrl();
				po.setId(newMovieMagnetUrlId);
				po.setMovieId(movieId);
				po.setUrl(href);
				movieMangetUrlMapper.insertSelective(po);
			}
		}
	}

	private void saveMovieInfo(List<WebElement> pTags, Long movieId) {
		WebElement p = null;
		WebElement tmpEle = null;
		for (int i = 0; i < pTags.size() && tmpEle == null; i++) {
			tmpEle = pTags.get(i);
			if (StringUtils.isNotBlank(tmpEle.getText())) {
				p = tmpEle;
			}
		}

		String content = p.getText();
		content = content.replaceAll("【下载地址】", "").replaceAll("磁力链下载点击这里", "");
		
		MovieInfo info = new MovieInfo();
		info.setId(movieId);
		
		List<String> lines = Arrays.asList(content.split("◎"));
		for(String line : lines) {
			if(line.contains("片") && line.contains("名") && line.matches("片\\s+名.*")) {
				info.setEngTitle(line.replaceAll("片　　名　", ""));
			} else if(line.contains("译") && line.contains("名") && line.matches("译\\s+名.*")) {
				info.setCnTitle(line.replaceAll("译　　名　", ""));
			} else if(line.contains("产") && line.contains("地") && line.matches("产\\s+地.*")) {
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
	
	private Integer detectMovieRegion(String countryDesc) {
		if(countryDesc == null) {
			return MovieRegionType.otherMovie.getCode();
			
		} else if (StringUtils.containsAny(countryDesc, "美", "英", "欧", "法", "加", "意")) {
			return MovieRegionType.eurAndAmerica.getCode();
			
		} else if (StringUtils.containsAny(countryDesc, "日", "韩")) {
			return MovieRegionType.jpAndKr.getCode();
			
		} else if (StringUtils.containsAny(countryDesc, "中", "大陆", "香", "台")) {
			return MovieRegionType.domestic.getCode();
		} 
		
		return MovieRegionType.otherMovie.getCode();
	}
}
