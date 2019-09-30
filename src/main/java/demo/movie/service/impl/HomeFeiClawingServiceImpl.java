package demo.movie.service.impl;

import java.io.File;
import java.util.ArrayList;
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
import ioHandle.FileUtilCustom;

@Service
public final class HomeFeiClawingServiceImpl extends MovieClawingCommonService implements HomeFeiClawingService {

	@Autowired
	private FileUtilCustom iou;
	
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
	private String part4 = mainUrl + "/thread-htm-fid-123.html";

	@Override
	protected TestEvent buildTesetEvent() {
		TestEvent te = new TestEvent();
		te.setCaseId(6L);
		te.setId(snowFlake.getNextId());
		te.setEventName("homeFei");
		return te;
	}
	
	@Override
	public void clawing() {
		if(existsRuningEvent()) {
			return;
		}
		TestEvent te = buildTesetEvent();
		startEvent(te);
		int clawPageCount = 20;
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		try {
			d.get(mainUrl);

			String mainWindowHandler = d.getWindowHandle();

			login(d, te);
			Thread.sleep(2500L);
			dailyCheckIn(d, mainWindowHandler, te);

			List<String> postLinks = new ArrayList<String>();

			partHandle(d, clawPageCount, postLinks, part1);
			partHandle(d, clawPageCount, postLinks, part2);
			partHandle(d, clawPageCount, postLinks, part3);
			partHandle(d, clawPageCount, postLinks, part4);
			
			for(int i = 0; i < postLinks.size(); i++) {
				postLinkHandle(d, postLinks);
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
	
	private void partHandle(WebDriver d, int clawPageCount, List<String> postLinks, String url) throws InterruptedException {
		d.get(url);
		Thread.sleep(1200L);
		for (int i = 0; i < clawPageCount; i++) {
			postLinks.addAll(pageHandle(d, i));
			Thread.sleep(1200L);
			if (i < clawPageCount - 1) {
				nextPage(d);
			}
		}
	}

	private void login(WebDriver d, TestEvent te) {
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
	
	private void nextPage(WebDriver d) {
		By nextPageButtonBy = auxTool.byXpathBuilder("a", "class", "pages_next");
		WebElement nextPageButton = d.findElement(nextPageButtonBy);
		nextPageButton.click();
	}

	private void postLinkHandle(WebDriver d, List<String> subLinks) throws InterruptedException {
		MovieRecordFindByConditionDTO dto = new MovieRecordFindByConditionDTO();
		dto.setUrlList(subLinks);
		List<MovieRecord> records = recordMapper.findByCondition(dto);

		for (MovieRecord i : records) {
			subLinks.remove(i.getUrl());
		}

		for (String url : subLinks) {
			subLinkHandle(d, url);
		}
	}

	private void subLinkHandle(WebDriver d, String url) throws InterruptedException {
		d.get(url);
		Thread.sleep(800L);
		jsUtil.scrollToButton(d);
		
		Long newMovieId = snowFlake.getNextId();

		List<WebElement> aTagList = d.findElements(By.tagName("a"));
		WebElement targetA = null;
		WebElement tmpA = null;
		
		boolean isMagnetLink = false;
		
		for (int i = 0; i < aTagList.size() && targetA == null; i++) {
			tmpA = aTagList.get(i);
			if (StringUtils.isNotBlank(tmpA.getAttribute("id")) && StringUtils.isNotBlank(tmpA.getAttribute("onclick"))
					&& tmpA.getAttribute("href") != null && tmpA.getAttribute("href").contains("action=download")) {
				targetA = tmpA;
			} else if (tmpA.getAttribute("href") != null && tmpA.getAttribute("href").startsWith("magnet:?xt")) {
				targetA = tmpA;
				isMagnetLink = true; 
			}
		}
		
		/* 
		 * 部分非资源主题(公告, 通知等), 仅记录, 避免下次爬取 
		 * 2019/09/30
		 * 开始发现部分18+资源采取回复后可下载torrent
		 * 因帖子回复前无直接展示下载链接
		 * 此方法会暂时回避此类资源
		 * */
		MovieRecord record = new MovieRecord();
		record.setUrl(url);
		record.setId(snowFlake.getNextId());
		if(targetA == null) {
			recordMapper.insertSelective(record);
			return;
		}

		String magnetUrl = null;
		if(isMagnetLink) {
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
		saveMovieImg(imgs, newMovieId);
		
		saveMovieInfo(tpcDiv, newMovieId);
		
		recordMapper.insertSelective(record);
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

	

}
