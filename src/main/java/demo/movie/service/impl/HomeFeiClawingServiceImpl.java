package demo.movie.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

import demo.base.system.pojo.bo.SystemConstantStore;
import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.movie.mapper.MovieInfoMapper;
import demo.movie.mapper.MovieIntroductionMapper;
import demo.movie.mapper.MovieMagnetUrlMapper;
import demo.movie.mapper.MovieRecordMapper;
import demo.movie.pojo.constant.MovieClawingConstant;
import demo.movie.pojo.dto.MovieIntroductionDTO;
import demo.movie.pojo.dto.MovieRecordFindByConditionDTO;
import demo.movie.pojo.po.MovieInfo;
import demo.movie.pojo.po.MovieIntroduction;
import demo.movie.pojo.po.MovieMagnetUrl;
import demo.movie.pojo.po.MovieRecord;
import demo.movie.pojo.result.DoubanSubClawingResult;
import demo.movie.service.DoubanClawingService;
import demo.movie.service.HomeFeiClawingService;
import demo.movie.service.MovieClawingOptionService;
import demo.selenium.pojo.bo.XpathBuilderBO;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.WebDriverService;
import demo.selenium.service.impl.JavaScriptServiceImpl;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.type.TestCaseType;
import demo.testCase.service.TestEventService;
import demo.tool.service.ComplexToolService;
import httpHandel.HttpUtil;
import ioHandle.FileUtilCustom;
import movie.pojo.constant.MovieInteractionUrl;
import net.sf.json.JSONObject;

@Service
public final class HomeFeiClawingServiceImpl extends MovieClawingCommonService implements HomeFeiClawingService {

	@Autowired
	private FileUtilCustom iou;
	@Autowired
	private HttpUtil httpUtil;
	@Autowired
	private ComplexToolService complexToolService;
	
	@Autowired
	private TestEventService testEventService;
	@Autowired
	private DoubanClawingService doubanService;
	
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
	@Autowired
	private MovieMagnetUrlMapper magnetUrlMapper;
	
	private String mainUrl = "http://bbs.homefei.me";
	private String part1 = mainUrl + "/thread-htm-fid-108.html";
	private String part2 = mainUrl + "/thread-htm-fid-55.html";
	private String part3 = mainUrl + "/thread-htm-fid-115.html";

	
	private TestEvent collectionTestEvent() {
		return buildTestEvent(TestCaseType.homeFeiCollection);
	}
	
	private TestEvent downloadTestEvent() {
		return buildTestEvent(TestCaseType.homeFeiDownload);
	}
	
	@Override
	public Integer insertCollectionEvent() {
		TestEvent te = collectionTestEvent();
		return testEventService.insertSelective(te);
	}
	
	@Override
	public Integer insertDownloadEvent() {
		TestEvent te = downloadTestEvent();
		return testEventService.insertSelective(te);
	}
	
	@Override
	public CommonResultBBT collection(TestEvent te) {
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();
		String envName = constantService.getValByName("envName");
		int clawPageMax = 2;
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		try {
			String mainWindowHandler = d.getWindowHandle();

			if(!login(d, te)) {
				report.append("login fail \n");
				throw new Exception("login fail");
			}
			Thread.sleep(2500L);
			if(!"dev".equals(envName)) {
				dailyCheckIn(d, mainWindowHandler, te);
			}

			Set<String> collectLinkSet = new HashSet<String>();

			int clawPageCount = 0;
			clawPageCount = partHandle(d, clawPageMax, collectLinkSet, part1);
			report.append("part1 handled, claw " + clawPageCount + " pages \n");
			clawPageCount = partHandle(d, clawPageMax, collectLinkSet, part2);
			report.append("part2 handled, claw " + clawPageCount + " pages \n");
			clawPageCount = partHandle(d, clawPageMax, collectLinkSet, part3);
			report.append("part3 handled, claw " + clawPageCount + " pages \n");
			
			List<String> linkListDTO = new ArrayList<String>(collectLinkSet);
			report.append("found " + collectLinkSet.size() + " links \n");
			
			MovieRecordFindByConditionDTO dto = new MovieRecordFindByConditionDTO();
			dto.setUrlList(linkListDTO);
			dto.setCaseId(te.getCaseId());
			List<MovieRecord> records = recordMapper.findByCondition(dto);

			for (MovieRecord i : records) {
				collectLinkSet.remove(i.getUrl());
			}
			report.append("after filter, found " + collectLinkSet.size() + " links \n");
			
			linkListDTO = new ArrayList<String>(collectLinkSet);
			topicLinksSave(linkListDTO, te);
			report.append("save " + linkListDTO.size() + " links \n");
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			log.error("error: {}, url: {}" + e.getMessage() + d.getCurrentUrl());
			report.append(e.getMessage() + "\n");
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
			if (d != null) {
				d.quit();
			}
		}
		return r;
	}
	
	@Override
	public CommonResultBBT download(TestEvent te) {
		/*
		 * TODO
		 * linux 上运行 出现多处不稳定, 暂时改为window下运行, 资料上传至服务器
		 * 需要处理电影简介的文档传输
		 */
		complexToolService.cleanTmpFiles(globalOptionService.getDownloadDir(), "torrent", LocalDateTime.now());
		
		CommonResultBBT r = new CommonResultBBT();
		StringBuffer report = new StringBuffer();
		// 实际运行时, 爬取一定页面后, 运行效率低下, 故需要重新启动浏览器
		int timeToReOpenBrowser = 5;
		MovieRecordFindByConditionDTO dto = new MovieRecordFindByConditionDTO();
		dto.setCaseId(TestCaseType.homeFeiCollection.getId());
		dto.setWasClaw(false);
		List<MovieRecord> records = recordMapper.findByCondition(dto);
		
		if(records == null || records.size() < 1) {
			report.append("There is not any homeFei resources waiting for download \n");
			r.setIsSuccess();
			r.setMessage(report.toString());
			return r;
		}
		
		int clawCount = 0;
		WebDriver d = null;
		MovieRecord tmpR = null;
		
		try {

			Thread.sleep(2500L);
			
			for(int i = 0; i < records.size(); i++) {
				tmpR = records.get(i);
				if(i % timeToReOpenBrowser == 0) {
					if(d != null) {
						d.quit();
					}
					d = webDriverService.buildFireFoxWebDriver();
					login(d, te);
				}
				if(subLinkHandle(d, tmpR, te)) {
					clawCount++;
				}
			}
			
			r.setIsSuccess();
			
		} catch (Exception e) {
			if("dev".equals(constantService.getValByName("envName"))) {
				e.printStackTrace();
			}
			if(d != null) {
				log.error("error:{}, url: {}" + e.getMessage() + d.getCurrentUrl());
				auxTool.takeScreenshot(d, te);
			}
			report.append(e.getMessage() + "\n");
			
		} finally {
			report.append("recordCount: " + records.size() + " clawCount: " + clawCount + " \n");
			r.setMessage(report.toString());
			if (d != null) {
				d.quit();
			}
		}
		
		return r;
		
	}
	
	private int partHandle(WebDriver d, int clawPageMax, Set<String> collectLinkSet, String url) throws InterruptedException {
		try {
			d.get(url);
			Thread.sleep(1200L);
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
		}
		
		boolean hasNextPage = true;
		int clawPageCount = 0;
		for (; clawPageCount < clawPageMax - 1 && hasNextPage == true; clawPageCount++) {
			collectLinkSet.addAll(pageHandle(d, clawPageCount));
			Thread.sleep(1200L);
			if (clawPageCount < clawPageMax - 1) {
				hasNextPage = nextPage(d);
			}
		}
		return clawPageCount;
	}

	private boolean login(WebDriver d, TestEvent te) {
		
		try {
			d.get(mainUrl);
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
		}
		
		XpathBuilderBO xpathBuilder = new XpathBuilderBO();

		xpathBuilder.start("input").addAttribute("id", "nav_pwuser");
		By usernameInputBy = By.xpath(xpathBuilder.getXpath());

		xpathBuilder.start("input").addAttribute("id", "showpwd");
		By pwdInputBy = By.xpath(xpathBuilder.getXpath());

		xpathBuilder.start("button").addAttribute("type", "submit").addAttribute("name", "head_login");
		By loginButtonBy = By.xpath(xpathBuilder.getXpath());

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
			
			return false;
		}
		
		return true;

	}

	private boolean dailyCheckIn(WebDriver d, String mainWindowHandler, TestEvent te) {
		boolean checkInFlag = false;
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
				return checkInFlag;
			}

			checkInInterfaceButton.click();

			Thread.sleep(2200L);

			Set<String> windows = d.getWindowHandles();
			for (String w : windows) {
				if (!w.equals(mainWindowHandler)) {
					d.switchTo().window(w);
				}
			}

			XpathBuilderBO xpathBuilder = new XpathBuilderBO();
			xpathBuilder.start("div").addAttribute("id", "punch");
			By checkInButtonBy = By.xpath(xpathBuilder.getXpath());
			WebElement checkInButton = d.findElement(checkInButtonBy);
			checkInButton.click();
			d.close();
			d.switchTo().window(mainWindowHandler);
			checkInFlag = true;

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
		return checkInFlag;
	}

	private List<String> pageHandle(WebDriver d, int page) {
		XpathBuilderBO xpathBuilder = new XpathBuilderBO();
		xpathBuilder.start("a").addAttribute("name", "readlink");
		By subLinkBy = By.xpath(xpathBuilder.getXpath());
		
		List<WebElement> linkEleList = d.findElements(subLinkBy);
		List<String> subLinks = new ArrayList<String>();
		
		for (WebElement ele : linkEleList) {
			subLinks.add(ele.getAttribute("href"));
		}

		return subLinks;
	}
	
	private boolean nextPage(WebDriver d) {
		XpathBuilderBO xpathBuilder = new XpathBuilderBO();
		xpathBuilder.start("a").addAttribute("class", "pages_next");
		By nextPageButtonBy = By.xpath(xpathBuilder.getXpath());
		WebElement nextPageButton = null;
		try {
			nextPageButton = d.findElement(nextPageButtonBy);
			nextPageButton.click();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * @param te 
	 * 
	 * 
	 */
	private boolean subLinkHandle(WebDriver d, MovieRecord record, TestEvent te) throws InterruptedException {
		
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
		 * 太多a标签, 最好能增加条件
		 */
		List<WebElement> aTagList = d.findElements(By.tagName("a"));
		
		WebElement tmpA = null;
		List<WebElement> targetAList = new ArrayList<WebElement>();
		
		boolean hasMagnetLink = false;
		
		String tmpHref = null;
		for (int i = 0; i < aTagList.size(); i++) {
			tmpA = aTagList.get(i);
			tmpHref = tmpA.getAttribute("href");
			if(tmpHref != null) {
				if (tmpHref.contains("action=download") &&
						StringUtils.isNotBlank(tmpA.getAttribute("id")) && StringUtils.isNotBlank(tmpA.getAttribute("onclick"))) {
					targetAList.add(tmpA);
				} else if (tmpHref.startsWith(MovieClawingConstant.magnetPrefix)) {
					targetAList.add(tmpA);
					hasMagnetLink = true; 
				}
			}
		}
		
		
		/* 
		 * 部分非资源主题(公告, 通知等), 仅记录, 避免下次爬取 
		 * 2019/09/30
		 * 开始发现部分18+资源采取回复后可下载torrent
		 * 因帖子回复前无直接展示下载链接
		 * 此方法会暂时回避此类资源
		 * */
		if(targetAList.size() < 1) {
			record.setWasClaw(true);
			record.setUpdateTime(LocalDateTime.now());
			recordMapper.updateByPrimaryKeySelective(record);
			return true;
		}

		List<String> magnetUrlList = new ArrayList<String>();
		if(hasMagnetLink) {
			for(WebElement targetA : targetAList) {
				magnetUrlList.add(targetA.getAttribute("href"));
			}
		} else {
			try {
				String magnetUrl = null;
				for(WebElement targetA : targetAList) {
					WebElement font = targetA.findElement(By.tagName("font"));
					magnetUrl = handleTorrentDownload(d, targetA, font.getText());
					if(magnetUrl != null) {
						magnetUrlList.add(magnetUrl);
					} else {
//					TODO
					}
				}
			} catch (Exception e) {
				record.setUpdateTime(LocalDateTime.now());
				record.setFailCount(record.getFailCount() + 1);
				recordMapper.updateByPrimaryKeySelective(record);
				return false;
			}
		}
		
		MovieMagnetUrl urlPO = null;
		for(String u : magnetUrlList) {
			urlPO = new MovieMagnetUrl();
			urlPO.setUrl(u);
			urlPO.setMovieId(movieId);
			urlPO.setId(snowFlake.getNextId());
			magnetUrlMapper.insertSelective(urlPO);
		}
		
		WebElement tpcDiv = d.findElement(By.id("read_tpc"));
		List<WebElement> imgs = tpcDiv.findElements(ByTagName.tagName("img"));
		saveMovieImg(imgs, movieId);
		
		saveMovieInfo(d, movieId, te);
		
		record.setWasClaw(true);
		record.setUpdateTime(LocalDateTime.now());
		recordMapper.updateByPrimaryKeySelective(record);
		return true;
	}
	
	private void saveMovieInfo(WebDriver d, Long newMovieId, TestEvent te) {
		/*
		 * TODO
		 * 2019-10-20
		 * 部分正则表达式有问题
		 */
		MovieInfo info = new MovieInfo();
		info.setId(newMovieId);

		String webTitle = d.getTitle();
		webTitle = webTitle.split("\\|")[0];
		
		String[] elements = webTitle.substring(1, webTitle.length() - 1).split("\\]\\[");
		String longMovieTitle = elements[2];
		String sourceTitle = null;
		int spaceIndex = longMovieTitle.indexOf(" ");
		if(spaceIndex > -1) {
			sourceTitle = longMovieTitle.substring(0, longMovieTitle.indexOf(" "));
		} else {
			sourceTitle = longMovieTitle;
		}
		
		
		DoubanSubClawingResult doubanResult = doubanService.clawing(d, sourceTitle, te);
		info.setCnTitle(doubanResult.getCnTitle());
		info.setOriginalTitle(doubanResult.getOriginalTitle());
		info.setNationId(detectMovieRegion(doubanResult.getRegion()).longValue());
		infoMapper.insertSelective(info);
		
		handleMovieIntroductionSend(newMovieId, doubanResult.getIntroduction());
		
	}
	
	private String handleTorrentDownload(WebDriver d, WebElement targetA, String torrentFileName) {
		if(!torrentFileName.endsWith(".torrent")) {
			return null;
		}
		
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

	@Override
	public void handleMovieIntroductionRecive(MovieIntroductionDTO dto) {
		if(StringUtils.isBlank(dto.getSk())) {
			return;
		}
		
		String sk = constantService.getValByName(SystemConstantStore.superAdminKey, true);
		if(!dto.getSk().equals(sk)) {
			return;
		}
		
		String savePath = getIntroductionSavePath() + File.separator + dto.getMovieId() + ".txt";
		iou.byteToFile(dto.getIntroduction().getBytes(StandardCharsets.UTF_8), savePath);

		MovieIntroduction po = new MovieIntroduction();
		po.setMovieId(dto.getMovieId());
		po.setIntroPath(savePath);
		introduectionMapper.insertSelective(po);
	}
	
	private void handleMovieIntroductionSend(Long movieId, String introduction) {
		MovieIntroductionDTO dto = new MovieIntroductionDTO();
		dto.setSk(constantService.getValByName(SystemConstantStore.superAdminKey));
		dto.setMovieId(movieId);
		dto.setIntroduction(introduction);
		
		String url = constantService.getValByName(SystemConstantStore.hostName1) + MovieInteractionUrl.root + MovieInteractionUrl.handleMovieIntroductionRecive;
//		String url = "http://127.0.0.1:10002" + MovieInteractionUrl.root + MovieInteractionUrl.handleMovieIntroductionRecive;
		
		try {
			JSONObject j = JSONObject.fromObject(dto);
			httpUtil.sendPostRestful(url, j.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
}
