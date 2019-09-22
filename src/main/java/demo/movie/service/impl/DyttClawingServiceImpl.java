package demo.movie.service.impl;

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

import demo.baseCommon.service.CommonService;
import demo.image.mapper.ImageStoreMapper;
import demo.image.pojo.po.ImageStore;
import demo.image.pojo.type.ImageType;
import demo.movie.mapper.MovieImageMapper;
import demo.movie.mapper.MovieUrlMapper;
import demo.movie.pojo.po.MovieImage;
import demo.movie.pojo.po.MovieInfo;
import demo.movie.pojo.po.MovieUrl;
import demo.movie.service.DyttClawingService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;
import demo.testCase.pojo.po.TestEvent;

@Service
public class DyttClawingServiceImpl extends CommonService implements DyttClawingService {

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
	private MovieUrlMapper movieUrlMapper;
	
	private String mainUrl = "https://www.dytt8.net";
	private String newMovie = mainUrl + "/html/gndy/dyzz/index.html";
	
	@Override
	public void test() {
		TestEvent te = new TestEvent();
		te.setId(5L);
		te.setEventName("dyttTest");
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		String mainWindowHandle = d.getWindowHandle();
		
		try {
			Set<String> windowHandles = null;
			d.switchTo().window(mainWindowHandle);
			d.get(newMovie);
			
			ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("a", "class", "ulink");
			By targetAListBy = auxTool.byXpathBuilder(byXpathConditionBo);
			
			List<WebElement> targetAList = d.findElements(targetAListBy);
			for(WebElement ele : targetAList) {
				singleMovieHandle(d, ele, mainWindowHandle);
				windowHandles = d.getWindowHandles();
				if(windowHandles.size() > 5) {
					throw new Exception();
				}
				d.switchTo().window(mainWindowHandle);
			}
			/*
			 * TODO
			 */
			
		} catch (Exception e) {
			e.printStackTrace();
			auxTool.takeScreenshot(d, te);
		} finally {
			if (d != null) {
				d.quit();
			}
		}
	}
	
	private void singleMovieHandle(WebDriver d, WebElement ele, String mainWindowHandler) throws Exception {
//		TODO
		if(ele == null || StringUtils.isBlank(ele.getAttribute("href"))) {
			return;
		}
		ele.click();
		Thread.sleep(300L);
		
		String subUrl = ele.getAttribute("href");
		String currentUrl = null;
		String targetWindowHandle = null;
		
		Set<String> windows = d.getWindowHandles();
		for(String w : windows) {
			d.switchTo().window(w);
			currentUrl = d.getCurrentUrl();
			if(targetWindowHandle == null && currentUrl.contains(subUrl)) {
				targetWindowHandle = w;
			}
		}
		if(targetWindowHandle == null) {
			log.info("can not find correct window");
			throw new Exception();
		} else {
			d.switchTo().window(targetWindowHandle);
			log.info("switch to : " + targetWindowHandle);
		}
		currentUrl = d.getCurrentUrl();
		
		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("div", "id", "Zoom");
		By divZoomBy = auxTool.byXpathBuilder(byXpathConditionBo);
		
		WebElement divZoom = null;
		
		try {
			divZoom = d.findElement(divZoomBy);
			Long newMovieId = snowFlake.getNextId();

			List<WebElement> imgs = divZoom.findElements(ByTagName.tagName("img"));
			saveMovieImg(imgs, newMovieId);
			
			List<WebElement> aTags = divZoom.findElements(ByTagName.tagName("a"));
			saveMovieUrl(aTags, newMovieId);
			
			List<WebElement> pTags = divZoom.findElements(ByTagName.tagName("p"));
			saveMovieInfo(pTags, newMovieId);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(!mainWindowHandler.equals(d.getWindowHandle())) {
				d.close();
			}
		}
	}
	
	private void saveMovieImg(List<WebElement> imgs, Long movieId) {
		String src = null;
		for(WebElement i : imgs) {
			src = i.getAttribute("src");
			Dimension s = i.getSize();
			if(s.height > 200 && s.width > 200) {
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
	
	private void saveMovieUrl(List<WebElement> aTags, Long movieId) {
		String href = null;
		for(WebElement ele : aTags) {
			href = ele.getAttribute("href");
			if(href.startsWith("magnet:?xt")) {
				Long newMovieUrlId = snowFlake.getNextId();
				MovieUrl po = new MovieUrl();
				po.setId(newMovieUrlId);
				po.setMovieId(movieId);
				po.setUrl(href);
				movieUrlMapper.insertSelective(po);
			}
		}
	}

	private void saveMovieInfo(List<WebElement> pTags, Long movieId) {
		WebElement p = null;
		String detailInfo = null;
		WebElement tmpEle = null;
		for(int i = 0; i < pTags.size() && tmpEle == null; i++) {
			tmpEle = pTags.get(i);
			if(StringUtils.isNotBlank(tmpEle.getText())) {
				detailInfo = tmpEle.getText().replaceAll(" ", "");
				if(detailInfo.contains("片名")) {
					p = tmpEle;
					System.out.println(p);
				}
			}
		}
		
		List<String> lines = Arrays.asList(detailInfo.split("\\s"));
//		MovieInfo m = new MovieInfo();
//		for(String line : lines) {
//			if(line.contains("译名")) {
//				m.setCnTitle(cnTitle);
//			}
//		}
		System.out.println(lines);
	}
}
