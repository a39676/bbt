package demo.movie.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.By.ByTagName;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.service.impl.SystemConstantService;
import demo.movie.mapper.MovieInfoMapper;
import demo.movie.mapper.MovieIntroductionMapper;
import demo.movie.mapper.MovieRecordMapper;
import demo.movie.pojo.constant.MovieClawingConstant;
import demo.movie.pojo.dto.MovieRecordFindByConditionDTO;
import demo.movie.pojo.po.MovieInfo;
import demo.movie.pojo.po.MovieIntroduction;
import demo.movie.pojo.po.MovieRecord;
import demo.movie.service.HomeFeiClawingService;
import demo.movie.service.MovieClawingOptionService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.WebDriverService;
import demo.selenium.service.impl.JavaScriptServiceImpl;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.type.MovieTestCaseType;
import ioHandle.FileUtilCustom;

@Service
public final class HomeFeiClawingServiceImpl extends MovieClawingCommonService implements HomeFeiClawingService {

	@Autowired
	private FileUtilCustom iou;
	
	@Autowired
	private SystemConstantService constantService;
	@Autowired
	private SeleniumGlobalOptionService globalOptionService;
	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
	@Autowired
	private MovieClawingOptionService optionService;
	@Autowired
	private JavaScriptServiceImpl jsUtil;

	@Autowired
	private MovieRecordMapper recordMapper;
	@Autowired
	private MovieInfoMapper infoMapper;
	@Autowired
	private MovieIntroductionMapper introduectionMapper;
	
	private String mainUrl = "http://bbs.homefei.me";
	private String part1 = mainUrl + "/thread-htm-fid-108.html";
	private String part2 = mainUrl + "/thread-htm-fid-55.html";
	private String part3 = mainUrl + "/thread-htm-fid-115.html";

	@Override
	public int fixMovieClawingTestEventStatus() {
		return eventMapper.fixMovieClawingTestEventStatus();
	}
	
	private TestEvent collectionTestEvent() {
		TestEvent te = new TestEvent();
		te.setCaseId(MovieTestCaseType.homeFeiCollection.getId());
		te.setId(snowFlake.getNextId());
		te.setEventName(MovieTestCaseType.homeFeiCollection.getEventName());
		return te;
	}
	
	private TestEvent downloadTestEvent() {
		TestEvent te = new TestEvent();
		te.setCaseId(MovieTestCaseType.homeFeiDownload.getId());
		te.setId(snowFlake.getNextId());
		te.setEventName(MovieTestCaseType.homeFeiDownload.getEventName());
		return te;
	}
	
	@Override
	public void collection() {
		if(existsRuningEvent()) {
			return;
		}
		TestEvent te = collectionTestEvent();
		startEvent(te);
		String envName = constantService.getValByName("envName");
		int clawPageCount = 2;
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		try {
			String mainWindowHandler = d.getWindowHandle();

			login(d, te);
			Thread.sleep(2500L);
			if(!"dev".equals(envName)) {
				dailyCheckIn(d, mainWindowHandler, te);
			}

			Set<String> postLinks = new HashSet<String>();

			partHandle(d, clawPageCount, postLinks, part1);
			partHandle(d, clawPageCount, postLinks, part2);
			partHandle(d, clawPageCount, postLinks, part3);
			List<String> linksList = new ArrayList<String>(postLinks);
			
			MovieRecordFindByConditionDTO dto = new MovieRecordFindByConditionDTO();
			dto.setUrlList(linksList);
			dto.setCaseId(te.getCaseId());
			List<MovieRecord> records = recordMapper.findByCondition(dto);

			for (MovieRecord i : records) {
				postLinks.remove(i.getUrl());
			}
			linksList = new ArrayList<String>(postLinks);
			topicLinksSave(linksList, te);
			
			endEvent(te, true);
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			endEvent(te, false);
			auxTool.takeScreenshot(d, te);
		} finally {
			if (d != null) {
				d.quit();
			}
		}
	}
	
	@Override
	public void download() {
		if(existsRuningEvent()) {
			return;
		}
		TestEvent te = downloadTestEvent();
		startEvent(te);
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		try {
			login(d, te);

			Thread.sleep(2500L);

			MovieRecordFindByConditionDTO dto = new MovieRecordFindByConditionDTO();
			dto.setCaseId(MovieTestCaseType.homeFeiCollection.getId());
			dto.setWasClaw(false);
			List<MovieRecord> records = recordMapper.findByCondition(dto);
			
			for(MovieRecord r : records) {
				subLinkHandle(d, r);
			}
			
			endEvent(te, true);
		} catch (Exception e) {
			log.error("error:{}, url: {}" + e.getMessage() + d.getCurrentUrl());
			endEvent(te, false);
			auxTool.takeScreenshot(d, te);
		} finally {
			if (d != null) {
				d.quit();
			}
		}
		
	}
	
	private void partHandle(WebDriver d, int clawPageCount, Set<String> postLinks, String url) throws InterruptedException {
		try {
			d.get(url);
			Thread.sleep(1200L);
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
		}
		
		boolean hasNextPage = true;
		for (int i = 0; i < clawPageCount - 1 && hasNextPage == true; i++) {
			postLinks.addAll(pageHandle(d, i));
			Thread.sleep(1200L);
			if (i < clawPageCount - 1) {
				hasNextPage = nextPage(d);
			}
		}
	}

	private void login(WebDriver d, TestEvent te) {
		
		try {
			d.get(mainUrl);
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
		}
		
		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("input", "id", "nav_pwuser");
		By usernameInputBy = auxTool.byXpathBuilder(byXpathConditionBo);

		byXpathConditionBo = ByXpathConditionBO.build("input", "id", "showpwd");
		By pwdInputBy = auxTool.byXpathBuilder(byXpathConditionBo);

		byXpathConditionBo = ByXpathConditionBO.build("button", "type", "submit").addCondition("name", "head_login");
		By loginButtonBy = auxTool.byXpathBuilder(byXpathConditionBo);

		try {
			WebElement usernameInput = d.findElement(usernameInputBy);
			WebElement pwdInput = d.findElement(pwdInputBy);
			WebElement loginButton = d.findElement(loginButtonBy);

			usernameInput.clear();
			usernameInput.sendKeys(optionService.getHomeFeiUsername());
			pwdInput.clear();
			pwdInput.sendKeys(optionService.getHomeFeiPwd());
			loginButton.click();
		} catch (Exception e) {
			log.error("homeFei login error {} " + e.getMessage());
			auxTool.takeScreenshot(d, te);
		}

	}

	private void dailyCheckIn(WebDriver d, String mainWindowHandler, TestEvent te) {
		try {
			List<WebElement> bList = d.findElements(By.tagName("b"));
			WebElement tmpEle = null;
			WebElement checkInInterfaceButton = null;
			for (int i = 0; i < bList.size() && checkInInterfaceButton == null; i++) {
				tmpEle = bList.get(i);
				if ("签到领奖".equals(tmpEle.getText())) {
					checkInInterfaceButton = tmpEle;
				}
			}
			if (checkInInterfaceButton == null) {
				log.debug("homeFei can not found check in button");
				auxTool.takeScreenshot(d, te);
				return;
			}

			checkInInterfaceButton.click();

			Thread.sleep(2200L);

			Set<String> windows = d.getWindowHandles();
			for (String w : windows) {
				if (!w.equals(mainWindowHandler)) {
					d.switchTo().window(w);
				}
			}

			ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("div", "id", "punch");
			By checkInButtonBy = auxTool.byXpathBuilder(byXpathConditionBo);
			WebElement checkInButton = d.findElement(checkInButtonBy);
			checkInButton.click();
			d.close();
			d.switchTo().window(mainWindowHandler);

		} catch (Exception e) {
			log.debug("homeFei daily check in fail {} " + e.getMessage());
			auxTool.takeScreenshot(d, te);
		} finally {
			Set<String> windows = d.getWindowHandles();
			for (String w : windows) {
				if (!w.equals(mainWindowHandler)) {
					d.switchTo().window(w);
					d.close();
				}
			}
			d.switchTo().window(mainWindowHandler);
		}

	}

	private List<String> pageHandle(WebDriver d, int page) {
		ByXpathConditionBO byXpathConditionBo = ByXpathConditionBO.build("a", "name", "readlink");
		By subLinkBy = auxTool.byXpathBuilder(byXpathConditionBo);
		
		List<WebElement> linkEleList = d.findElements(subLinkBy);
		List<String> subLinks = new ArrayList<String>();
		
		for (WebElement ele : linkEleList) {
			subLinks.add(ele.getAttribute("href"));
		}

		return subLinks;
	}
	
	private boolean nextPage(WebDriver d) {
		By nextPageButtonBy = auxTool.byXpathBuilder("a", "class", "pages_next");
		WebElement nextPageButton = null;
		try {
			nextPageButton = d.findElement(nextPageButtonBy);
			nextPageButton.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	private void subLinkHandle(WebDriver d, MovieRecord record) throws InterruptedException {
		
		try {
			d.get(record.getUrl());
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
		}
		Thread.sleep(800L);
		jsUtil.scrollToButton(d);
		
		Long movieId = record.getMovieId();

		/*
		 * TODO 
		 * 太多a标签, 必须增加条件
		 * 未处理单帖多资源的情况
		 * 包括 既有种子 又有 磁力链接, 并且内容重复...
		 */
		List<WebElement> aTagList = d.findElements(By.tagName("a"));
//		List<String> magnetList = new ArrayList<String>();
		
		WebElement targetA = null;
		WebElement tmpA = null;
		
		boolean hasMagnetLink = false;
		
		for (int i = 0; i < aTagList.size() && targetA == null; i++) {
			tmpA = aTagList.get(i);
			if (StringUtils.isNotBlank(tmpA.getAttribute("id")) && StringUtils.isNotBlank(tmpA.getAttribute("onclick"))
					&& tmpA.getAttribute("href") != null && tmpA.getAttribute("href").contains("action=download")) {
				targetA = tmpA;
			} else if (tmpA.getAttribute("href") != null && tmpA.getAttribute("href").startsWith(MovieClawingConstant.magnetPrefix)) {
				targetA = tmpA;
				hasMagnetLink = true; 
			}
		}
		
		
		/* 
		 * 部分非资源主题(公告, 通知等), 仅记录, 避免下次爬取 
		 * 2019/09/30
		 * 开始发现部分18+资源采取回复后可下载torrent
		 * 因帖子回复前无直接展示下载链接
		 * 此方法会暂时回避此类资源
		 * */
		if(targetA == null) {
			record.setWasClaw(true);
			recordMapper.updateByPrimaryKeySelective(record);
			return;
		}

		String magnetUrl = null;
		if(hasMagnetLink) {
			magnetUrl = targetA.getAttribute("href");
		} else {
			WebElement font = targetA.findElement(By.tagName("font"));
			if(!font.getText().endsWith("torrent")) {
				recordMapper.insertSelective(record);
				return;
			}
			magnetUrl = handleTorrentDownload(d, targetA, font.getText());
			if(magnetUrl == null) {
//				TODO
			}
		}
		
		WebElement tpcDiv = d.findElement(By.id("read_tpc"));
		List<WebElement> imgs = tpcDiv.findElements(ByTagName.tagName("img"));
		saveMovieImg(imgs, movieId);
		
		saveMovieInfo(tpcDiv, movieId);
		
		record.setWasClaw(true);
		recordMapper.updateByPrimaryKeySelective(record);
	}
	
	private void saveMovieInfo(WebElement tpcDiv, Long newMovieId) {
		String content = tpcDiv.getText();
		MovieInfo info = new MovieInfo();
		info.setId(newMovieId);
		
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

		String savePath = introductionSavePath + File.separator + newMovieId + ".txt";
		iou.byteToFile(content.getBytes(), savePath);

		MovieIntroduction po = new MovieIntroduction();
		po.setMovieId(newMovieId);
		po.setIntroPath(savePath);
		introduectionMapper.insertSelective(po);
	}
	
	private String handleTorrentDownload(WebDriver d, WebElement targetA, String torrentFileName) {
		String downloadFolderPath = globalOptionService.getDownloadDir();
		
		targetA.click();
		List<WebElement> buttons = d.findElements(By.tagName("button"));
		WebElement tmpButton = null;
		WebElement targetButton = null;
		for(int i = 0; i < buttons.size() && targetButton == null; i++) {
			tmpButton = buttons.get(i);
			if(tmpButton.getAttribute("onclick") != null && tmpButton.getAttribute("onclick").contains("action=download")) {
				targetButton = tmpButton;
			}
		}
		
		targetButton.click();
		try {
			Thread.sleep(800L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String torrentPath = downloadFolderPath + File.separator + torrentFileName;
		String magnetUrl = getMangetUrlFromTorrent(torrentPath);
		return magnetUrl;
	}

	private int topicLinksSave(List<String> topicLinks, TestEvent te) {
		MovieRecord po = null;
		int count = 0;
		for(String u : topicLinks) {
			po = new MovieRecord();
			po.setCaseId(te.getCaseId());
			po.setId(snowFlake.getNextId());
			po.setMovieId(snowFlake.getNextId());
			po.setUrl(u);
			count += recordMapper.insertSelective(po);
		}
		return count;
	}

}
