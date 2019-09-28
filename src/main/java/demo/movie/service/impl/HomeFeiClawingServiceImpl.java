package demo.movie.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.movie.mapper.MovieRecordMapper;
import demo.movie.pojo.dto.MovieRecordFindByConditionDTO;
import demo.movie.pojo.po.MovieRecord;
import demo.movie.service.HomeFeiClawingService;
import demo.movie.service.MovieClawingOptionService;
import demo.selenium.pojo.bo.ByXpathConditionBO;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.selenium.service.WebDriverService;
import demo.testCase.pojo.po.TestEvent;

@Service
public class HomeFeiClawingServiceImpl extends MovieClawingCommonService implements HomeFeiClawingService {

	@Autowired
	private SeleniumGlobalOptionService globalOptionService;
	@Autowired
	private WebDriverService webDriverService;
	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
	@Autowired
	private MovieClawingOptionService optionService;
//	@Autowired
//	private JavaScriptServiceImpl jsUtil;

	@Autowired
	private MovieRecordMapper recordMapper;

	private String mainUrl = "http://bbs.homefei.me";
	private String newMovie = mainUrl + "/thread-htm-fid-108.html";

	@Override
	public void clawing() {
		int clawPageCount = 20;
		TestEvent te = getTestEvent();
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		try {
			d.get(mainUrl);

			String mainWindowHandler = d.getWindowHandle();

			login(d);
			Thread.sleep(2500L);
			dailyCheckIn(d, mainWindowHandler);

			d.get(newMovie);
			List<String> postLinks = new ArrayList<String>();

			for (int i = 0; i < clawPageCount; i++) {
				postLinks.addAll(pageHandle(d));
				Thread.sleep(800L);
				if (i < clawPageCount - 1) {
					nextPage(d);
				}
			}

		} catch (Exception e) {
			log.error(e.getMessage());
			auxTool.takeScreenshot(d, te);
		} finally {
			if (d != null) {
				d.quit();
			}
		}
	}

	private TestEvent getTestEvent() {
		TestEvent te = new TestEvent();
		te.setId(6L);
		te.setEventName("homeFei");
		return te;
	}

	private void login(WebDriver d) {
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
			log.error(e.getMessage());
			auxTool.takeScreenshot(d, getTestEvent());
		}

	}

	private void dailyCheckIn(WebDriver d, String mainWindowHandler) {
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
				auxTool.takeScreenshot(d, getTestEvent());
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
			auxTool.takeScreenshot(d, getTestEvent());
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

	private List<String> pageHandle(WebDriver d) {
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

	public void postLinkHandle(WebDriver d, List<String> subLinks) throws InterruptedException {
		MovieRecordFindByConditionDTO dto = new MovieRecordFindByConditionDTO();
		dto.setUrlList(subLinks);
		List<MovieRecord> records = recordMapper.findByCondition(dto);

		for (MovieRecord i : records) {
			subLinks.remove(i.getUrl());
		}

//		TODO
		for (String url : subLinks) {
			subLinkHandle(d, url);
		}
	}

	public void subLinkHandle(WebDriver d, String url) throws InterruptedException {
		d.get(url);
		Thread.sleep(800L);

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

		String magnetUrl = null;
		if(isMagnetLink) {
			magnetUrl = targetA.getAttribute("href");
		} else {
			WebElement font = targetA.findElement(By.tagName("font"));
			magnetUrl = handleTorrentDownload(d, font.getText());
			if(magnetUrl == null) {
//				TODO
			}
		}
		
		/*
		 * TODO
		 * 未处理/收集简介, 图片
		 */
		
		MovieRecord record = new MovieRecord();
		record.setUrl(url);
		record.setId(snowFlake.getNextId());
		recordMapper.insertSelective(record);
	}
	
	private String handleTorrentDownload(WebDriver d, String torrentFileName) {
		String downloadFolderPath = globalOptionService.getDownloadDir();
		
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
