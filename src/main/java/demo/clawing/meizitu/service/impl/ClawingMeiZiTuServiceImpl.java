package demo.clawing.meizitu.service.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.math3.random.RandomDataGenerator;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import at.pojo.bo.XpathBuilderBO;
import at.web.WebATToolService;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.clawing.meizitu.mapper.MeizituGroupRecordMapper;
import demo.clawing.meizitu.pojo.po.MeizituGroupRecord;
import demo.clawing.meizitu.service.ClawingMeiZiTuGlobalOptionService;
import demo.clawing.meizitu.service.ClawingMeiZiTuService;
import demo.interaction.image.mapper.ImageStoreMapper;
import demo.interaction.image.pojo.po.ImageStore;
import demo.interaction.image.pojo.type.ImageType;
import demo.selenium.service.JavaScriptService;
import demo.selenium.service.WebDriverService;
import demo.selenium.service.impl.AuxiliaryToolServiceImpl;
import demo.selenium.service.impl.SeleniumCommonService;
import toolPack.httpHandel.HttpUtil;

@Service
public class ClawingMeiZiTuServiceImpl extends SeleniumCommonService implements ClawingMeiZiTuService {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private AuxiliaryToolServiceImpl auxTool;
	@Autowired
	private WebATToolService webATToolService;
	@Autowired
	private JavaScriptService jsUtil;
	@Autowired
	private ClawingMeiZiTuGlobalOptionService optionService;

	@Autowired
	private ImageStoreMapper imageStoreMapper;
	@Autowired
	private MeizituGroupRecordMapper groupRecordMapper;

	private String mainUrl = "https://www.mzitu.com";
	private RandomDataGenerator randomDateGenerator = new RandomDataGenerator();
	
	
	private TestEvent buildTestEvent() {
		TestEvent t = new TestEvent();
		t.setEventName("mzt");
		t.setId(1L);
		return t;
	}

	@Override
	public void meiZiTuMain() {
		TestEvent te = buildTestEvent();
		te.toString();
		WebDriver d = webDriverService.buildChrome76WebDriver();

		String mainWindow = d.getWindowHandle();

		try {
			d.get(mainUrl);

			List<WebElement> groups = null;
			WebElement singleGroup = null;
			WebElement nextPageButton = findNextPageButton(d);

			while (nextPageButton != null) {
				d.switchTo().window(mainWindow);
				ThreadSleepRandomTime();
				groups = meiZiTuGroupFinder(d);
				for (int i = 0; i < groups.size(); i++) {
					/*
					 * TODO what if fail?
					 */
					singleGroup = groups.get(i);
					groupHandler(singleGroup, d, mainWindow);

					d.switchTo().window(mainWindow);
				}
				d.switchTo().window(mainWindow);
				nextPageButton = findNextPageButton(d);
				nextPageButton.click();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			tryQuitWebDriver(d);
		}
	}

	private List<WebElement> meiZiTuGroupFinder(WebDriver d) {
		XpathBuilderBO xb = new XpathBuilderBO();
		xb.start("a").addAttribute("target", "_blank");
		By groupFindBy = By.xpath(xb.getXpath());

		xb.start("img").addAttribute("class", "lazy");
		By groupImgBy = By.xpath(xb.getXpath());

		List<WebElement> sourceA = d.findElements(groupFindBy);
		List<WebElement> targetA = new ArrayList<WebElement>();
		WebElement tmpEle = null;
		for (WebElement i : sourceA) {
			if (i.getAttribute("href") != null && i.getAttribute("href").matches(mainUrl + "/\\d{1,9}")) {
				try {
					tmpEle = i.findElement(groupImgBy);
				} catch (Exception e) {
				}
				if (tmpEle != null) {
					targetA.add(i);
				}
				tmpEle = null;
			}
		}
		return targetA;
	}

	private void groupHandler(WebElement ele, WebDriver d, String mainWindowHandler) throws InterruptedException {
		String url = ele.getAttribute("href");
		if (hasClawedThisGroup(url)) {
			return;
		}

		jsUtil.openNewTab(d, url);
		String urlSuffix = url.substring(url.lastIndexOf("/") + 1, url.length());

		Set<String> windowHandlesSet = d.getWindowHandles();
		List<String> windowHandles = new ArrayList<String>(windowHandlesSet);
		windowHandles.remove(mainWindowHandler);
		String title = null;
		WebElement titleEle = null;

		XpathBuilderBO xb = new XpathBuilderBO();
		xb.start("h2").addAttribute("class", "main-title");
		By titleBy = By.xpath(xb.getXpath());
		
		String tmpWindowHandle = null;
		for (int i = 0; title == null; i++) {
			tmpWindowHandle = windowHandles.get(i % windowHandles.size());
			d.switchTo().window(tmpWindowHandle);
			try {
				titleEle = d.findElement(titleBy);
				title = titleEle.getText();
			} catch (Exception e) {
				try {
					titleEle = auxTool.fluentWait(d, titleBy);
					title = titleEle.getText();
				} catch (Exception e2) {
				}

			}
		}

		String mainFolerPath = optionService.getMeiZiTuFolder();
		File folder = new File(mainFolerPath + File.separator + urlSuffix);
		if (!folder.exists() || !folder.isDirectory()) {
			boolean createFolderFlag = folder.mkdirs();
			if (!createFolderFlag) {
//				TODO
			}
		}

		WebElement nextPageButton = findNextPageButton(d);

		while (nextPageButton != null) {
			ThreadSleepRandomTime();
			subImgHandler(d, title, folder);
			nextPageButton.click();
			nextPageButton = findNextPageButton(d);
		}
		if (nextPageButton == null) {
			try {
				subImgHandler(d, title, folder);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		groupRecord(url);
		d.close();
	}

	private void subImgHandler(WebDriver d, String title, File folder) {
		TestEvent t = buildTestEvent();
		t.toString();
		By imgBy = ByTagName.tagName("img");
		List<WebElement> imgs = null;
		try {
			imgs = d.findElements(imgBy);
		} catch (Exception e) {
			return;
		}
		
		WebElement imgEle = null;
		
		String subTitle = null;
		if(title.length() < 10) {
			subTitle = title.substring(0, title.length() - 1);
		} else {
			subTitle = title.substring(0, 10);
		}
		
		String src = null;
		int width = 0;
		int height = 0;
		WebElement ele = null;
		for(int i = 0; i < imgs.size() && imgEle == null; i++) {
			ele = imgs.get(i);
			src = ele.getAttribute("src");
			if(src != null && src.startsWith("https://i5.meizitu") 
					&& ele.getAttribute("alt") != null && ele.getAttribute("alt").startsWith(subTitle)) {
				if(numericUtil.matchInteger(ele.getAttribute("width")) && numericUtil.matchInteger(ele.getAttribute("height"))) {
					width = Integer.parseInt(ele.getAttribute("width"));
					height = Integer.parseInt(ele.getAttribute("height"));
					if(width > 300 && height > 300) {
						imgEle = ele;
					}
				}
			}
		}
		
		if(imgEle == null) {
			return;
		}
		
		String currentUrl = d.getCurrentUrl();

		String fileName = webATToolService.findFileNameFromUrl(imgEle.getAttribute("src"));
		String filePath = folder.getAbsolutePath() + File.separator + fileName;
		File tmpFile = new File(filePath);
		if (tmpFile.exists()) {
			return;
		}

		HashMap<String, String> m = new HashMap<>();
		m.put("Referer", currentUrl);

		HttpUtil h = new HttpUtil();
		InputStream is = null;
		try {
			is = h.sendRequestGetInputStreamReader(h.get, null, imgEle.getAttribute("src"), m);
			Files.copy(is, Paths.get(filePath));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		Long newImgId = snowFlake.getNextId();
		ImageStore po = new ImageStore();
		po.setId(newImgId);
		po.setImagePath(filePath);
		po.setImageType(ImageType.meizi.getCode().byteValue());
		imageStoreMapper.insertSelective(po);
	}

	private WebElement findNextPageButton(WebDriver d) {
		By nextPageButtonBy = By.partialLinkText("下一页»");
		WebElement nextPageButton = null;
		try {
			nextPageButton = d.findElement(nextPageButtonBy);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if (nextPageButton == null) {
			d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			List<WebElement> spans = d.findElements(ByTagName.tagName("span"));
			for (WebElement i : spans) {
				if (i.getText() != null && i.getText().contains("下一页")) {
					nextPageButton = i;
				}
			}
		}

		return nextPageButton;
	}

	private void groupRecord(String url) {
		MeizituGroupRecord po = new MeizituGroupRecord();
		po.setId(snowFlake.getNextId());
		po.setGroupUrl(url);
		groupRecordMapper.insertSelective(po);
	}

	private boolean hasClawedThisGroup(String url) {
		if (StringUtils.isBlank(url)) {
			return false;
		}
		MeizituGroupRecord p = groupRecordMapper.hasClawedThisGroup(url);
		if (p != null) {
			return true;
		}
		return false;
	}
	
	private void ThreadSleepRandomTime() throws InterruptedException {
		long leftLimit = 400L;
		long rightLimit = 700L;
		long randomSleep = randomDateGenerator.nextLong(leftLimit, rightLimit);
		Thread.sleep(randomSleep);
	}

}
