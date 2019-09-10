package demo.clawing.service.impl;

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

import demo.baseCommon.service.CommonService;
import demo.clawing.mapper.ImageStoreMapper;
import demo.clawing.mapper.MeizituGroupRecordMapper;
import demo.clawing.pojo.po.ImageStore;
import demo.clawing.pojo.po.MeizituGroupRecord;
import demo.clawing.pojo.po.MeizituGroupRecordExample;
import demo.clawing.pojo.po.MeizituGroupRecordExample.Criteria;
import demo.clawing.pojo.type.ImageType;
import demo.clawing.service.ClawingMeiZiTuGlobalOptionService;
import demo.clawing.service.ClawingMeiZiTuService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.pojo.po.TestEvent;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.WebDriverService;
import httpHandel.HttpUtil;

@Service
public class ClawingMeiZiTuServiceImpl extends CommonService implements ClawingMeiZiTuService {

	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
//	@Autowired
//	private JavaScriptCommonUtil jsUtil;
	@Autowired
	private ClawingMeiZiTuGlobalOptionService optionService;
	
	@Autowired
	private ImageStoreMapper imageStoreMapper;
	@Autowired
	private MeizituGroupRecordMapper groupRecordMapper;
	
	private String mainUrl = "https://www.mzitu.com";
	
	@Override
	public void meiZiTuMain() {
		TestEvent te = new TestEvent();
		te.setId(1L);
		te.setEventName("clawingMedicineTest");
		WebDriver d = webDriverService.buildFireFoxWebDriver();
		
		String mainWindow = d.getWindowHandle();
		
		try {
			d.get(mainUrl);
			
			List<WebElement> groups = meiZiTuGroupFinder(d);
			
			WebElement ele = null;
			for(int i = 0;i < groups.size(); i++) {
			/*
			 * TODO
			 * what if fail?
			 */
				ele = groups.get(i);
				groupHandler(ele, d);
				groupRecord(ele.getAttribute("href"));
				d.switchTo().window(mainWindow);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (d != null) {
				d.quit();
			}
		}
	}
	
	private List<WebElement> meiZiTuGroupFinder(WebDriver d) {
		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("a", "target", "_blank");
		By groupFindBy = auxTool.byXpathBuilder(byXpathConditionBo);
		
		byXpathConditionBo = ByXpathConditionBO.build("img", "class", "lazy");
		By groupImgBy = auxTool.byXpathBuilder(byXpathConditionBo);
		
		List<WebElement> sourceA = d.findElements(groupFindBy);
		List<WebElement> targetA = new ArrayList<WebElement>();
		WebElement tmpEle = null;
		for(WebElement i : sourceA) {
			if(i.getAttribute("href") != null && i.getAttribute("href").matches(mainUrl + "/\\d{1,9}")) {
				try {
					tmpEle = i.findElement(groupImgBy);
				} catch (Exception e) {
				}
				if(tmpEle != null) {
					targetA.add(i);
				}
				tmpEle = null;
			}
		}
		return targetA;
	}

	private void groupHandler(WebElement ele, WebDriver d) throws InterruptedException {
		System.out.println("handing :" + ele.getAttribute("href"));
		if(hasClawedThisGroup(ele.getAttribute("href"))) {
			return;
		}
		
		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("img", "class", "lazy");
		By targetImgBy = auxTool.byXpathBuilder(byXpathConditionBo);
		
		WebElement targetButton = ele.findElement(targetImgBy);
		targetButton.click();
		
		Set<String> windowHandlesSet = d.getWindowHandles();
		List<String> windowHandles = new ArrayList<String>(windowHandlesSet);
		String title = null;
		WebElement titleEle = null;
		
		byXpathConditionBo = ByXpathConditionBO.build("h2", "class", "main-title");
		By titleBy = auxTool.byXpathBuilder(byXpathConditionBo);
		for(int i = 0; i < windowHandles.size() && title == null; i++) {
			d.switchTo().window(windowHandles.get(i));
			try {
				titleEle = d.findElement(titleBy);
				title = titleEle.getText();
			} catch (Exception e) {
//				
			}
		}
		
		String mainFolerPath = optionService.getMeiZiTuFolder();
		File folder = new File(mainFolerPath + File.separator + title);
		boolean createFolderFlag = false;
		if(!folder.exists() || !folder.isDirectory()) {
			createFolderFlag = folder.mkdirs();
		}
		if(!createFolderFlag) {
//			TODO
		}
		
		WebElement nextPageButton = fineNextPageButton(d);
		
		long leftLimit = 1500L;
	    long rightLimit = 2500L;
	    RandomDataGenerator r = new RandomDataGenerator();
	    long randomSleep = 0L;
		while(nextPageButton != null) {
			randomSleep = r.nextLong(leftLimit, rightLimit);
			System.out.println("randomSleep: " + randomSleep);
			Thread.sleep(randomSleep);
			subImgHandler(d, title);
			nextPageButton.click();
			nextPageButton = fineNextPageButton(d);
		} 
		if (nextPageButton == null) {
			try {
				subImgHandler(d, title);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		d.close();
	}
	
	private void subImgHandler(WebDriver d, String title) {
		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("img", "alt", title);
		By imgBy = auxTool.byXpathBuilder(byXpathConditionBo);
		WebElement imgEle = null;
		try {
			imgEle = d.findElement(imgBy);
		} catch (Exception e) {
			try {
				imgEle = auxTool.fluentWait(d, imgBy);
			} catch (Exception e2) {
				TestEvent t = new TestEvent();
				t.setEventName("mzt");
				t.setId(snowFlake.getNextId());
				auxTool.takeScreenshot(d, t);
				return;
			}
		}
		
		String currentUrl = d.getCurrentUrl();
		
		String folderPath = optionService.getMeiZiTuFolder();
		String fileName = auxTool.findFileNameFromUrl(imgEle.getAttribute("src"));
		String filePath = folderPath + File.separator + title + File.separator + fileName;
		File tmpFile = new File(filePath);
		if(tmpFile.exists()) {
			System.out.println(fileName + "exists");
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
		
		System.out.println(fileName);
		Long newImgId = snowFlake.getNextId();
		ImageStore po = new ImageStore();
		po.setId(newImgId);
		po.setImagePath(filePath);
		po.setImageType(ImageType.meizi.getCode().byteValue());
		imageStoreMapper.insertSelective(po);
	}
	
	private WebElement fineNextPageButton(WebDriver d) {
		By nextPageButtonBy = By.partialLinkText("下一页»");
		WebElement nextPageButton = null;
		try {
			nextPageButton = d.findElement(nextPageButtonBy);
		} catch (Exception e) {
			// TODO: handle exception
		}
		if(nextPageButton == null) {
			d.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
			List<WebElement> spans = d.findElements(ByTagName.tagName("span"));
			for(WebElement i : spans) {
				if(i.getText() != null && i.getText().contains("下一页")) {
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
		if(StringUtils.isBlank(url)) {
			return false;
		}
		MeizituGroupRecordExample example = new MeizituGroupRecordExample();
		Criteria c = example.createCriteria();
		c.andGroupUrlEqualTo(url);
		List<MeizituGroupRecord> p = groupRecordMapper.selectByExample(example);
		if(p != null && p.size() > 0) {
			return true;
		}
		return false;
	}
	
}
