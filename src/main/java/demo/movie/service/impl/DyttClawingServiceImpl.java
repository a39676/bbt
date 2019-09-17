package demo.movie.service.impl;

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
import demo.movie.pojo.po.MovieImage;
import demo.movie.service.DyttClawingService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.pojo.po.TestEvent;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;

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
				ahrefHandle(d, ele);
				windowHandles = d.getWindowHandles();
				if(windowHandles.size() > 5) {
					throw new Exception();
				}
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
	
	private void ahrefHandle(WebDriver d, WebElement ele) {
//		TODO
		if(ele == null || StringUtils.isBlank(ele.getAttribute("href"))) {
			return;
		}
		ele.click();
		
		String subUrl = ele.getAttribute("href");
		String currentUrl = null;
		String currentWindowHandle = null;
		
		Set<String> windows = d.getWindowHandles();
		for(String w : windows) {
			d.switchTo().window(w);
			currentUrl = d.getCurrentUrl();
			if(currentWindowHandle == null && currentUrl.contains(subUrl)) {
				currentWindowHandle = w;
			}
		}
		d.switchTo().window(currentWindowHandle);
		currentUrl = d.getCurrentUrl();
		
		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("div", "id", "Zoom");
		By divZoomBy = auxTool.byXpathBuilder(byXpathConditionBo);
		
		WebElement divZoom = null;
		
		try {
			divZoom = d.findElement(divZoomBy);
			List<WebElement> imgs = divZoom.findElements(ByTagName.tagName("img"));
			Long newMovieId = snowFlake.getNextId();
			saveMovieImg(imgs, newMovieId);
			
			d.close();
		} catch (Exception e) {
//			auxTool.takeScreenshot(d, te);
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
}
