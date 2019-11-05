package demo.movie.service.impl;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dateTimeHandle.DateTimeHandle;
import dateTimeHandle.DateUtilCustom;
import demo.movie.pojo.result.DoubanSubClawingResult;
import demo.movie.service.DoubanClawingService;
import demo.selenium.pojo.bo.XpathBuilderBO;
import demo.selenium.service.SeleniumAuxiliaryToolService;
import demo.selenium.service.impl.JavaScriptServiceImpl;
import demo.testCase.pojo.po.TestEvent;

@Service
public final class DoubanClawingServiceImpl extends MovieClawingCommonService implements DoubanClawingService {

	@Autowired
	private SeleniumAuxiliaryToolService auxTool;
	@Autowired
	private JavaScriptServiceImpl jsUtil;

	private String mainUrl = "https://www.douban.com/";

	@Override
	public DoubanSubClawingResult clawing(WebDriver d, String cnTitle, TestEvent te) {
		/*
		 * TODO
		 * 未处理
		 * 匹配导演/演员/电影类型
		 * 图片/海报
		 * 等信息
		 * 
		 */
		DoubanSubClawingResult r = new DoubanSubClawingResult();
		StringBuffer report = new StringBuffer();
		
		try {
			
			search(d, cnTitle);
			
			String targetLink = findTargetLink(d);
			
			if(targetLink == null) {
				throw new Exception("can not find target link for: " + cnTitle + " at: " + d.getCurrentUrl() + System.lineSeparator());
			}
			
			handleInfo(d, targetLink, r);
			
		} catch (Exception e) {
			log.error("error:{}, url: {}" + e.getMessage() + d.getCurrentUrl());
			auxTool.takeScreenshot(d, te);
			
		} finally {
			r.setMessage(report.toString());
			/*
			 * 非本方法创建的 web driver  不在本方法结束!!!
			 */
//			if (d != null) {
//				d.quit();
//			}
		}
		
		return r;
	}
	
	private void search(WebDriver d, String keyWord) {
		d.get(mainUrl);

		XpathBuilderBO xpathBuilder = new XpathBuilderBO();
		xpathBuilder.start("input").addAttribute("type", "text").addAttribute("name", "q");
		By movieNameInputBy = By.xpath(xpathBuilder.getXpath());
		
		WebElement movieNameInput = d.findElement(movieNameInputBy);
		movieNameInput.clear();
		movieNameInput.sendKeys(keyWord);
		
		xpathBuilder.start("input").addAttribute("type", "submit").addAttribute("value", "搜索");
		By homePageSearchButtonBy = By.xpath(xpathBuilder.getXpath());
		WebElement homePageSearchButton = d.findElement(homePageSearchButtonBy);
		homePageSearchButton.click();
	}

	private String findTargetLink(WebDriver d) {
		String targetLink = null;
		XpathBuilderBO xpathBuilder = new XpathBuilderBO();
		xpathBuilder.start("a").addAttribute("target", "_blank");
		By aLinkBy = By.xpath(xpathBuilder.getXpath());
		List<WebElement> aLinkList = d.findElements(aLinkBy);
		WebElement tmpA = null;
		String tmpHref = null;
		String tmpAttr = null;
		for(int i = 0; targetLink == null && i < aLinkList.size(); i++) {
			tmpA = aLinkList.get(i);
			tmpHref = tmpA.getAttribute("href");
			if(tmpHref != null && tmpHref.startsWith("https://www.douban.com/")) {
				tmpAttr = tmpA.getAttribute("onclick");
				if(tmpAttr != null && tmpAttr.startsWith("moreurl")) {
					targetLink = tmpHref;
				}
			}
		}
		return targetLink;
	}

	private void handleInfo(WebDriver d, String targetLink, DoubanSubClawingResult r) {
		try {
			d.get(targetLink);
		} catch (TimeoutException e) {
			jsUtil.windowStop(d);
		}
		XpathBuilderBO x = new XpathBuilderBO();
		
		x.start("span").addAttribute("property", "v:itemreviewed");
		By titleSpanBy = By.xpath(x.getXpath());
		WebElement titleSpan = d.findElement(titleSpanBy);
		String sourceTitle = titleSpan.getText();
		
		int firstSpaceIndex = sourceTitle.indexOf(" ");
		String cnTitle = null;
		String originalTitle = null;
		if(firstSpaceIndex == -1) {
			cnTitle = sourceTitle;
			originalTitle = sourceTitle;
		} else {
			cnTitle = sourceTitle.substring(0, firstSpaceIndex);
			originalTitle = sourceTitle.substring(firstSpaceIndex + 1, sourceTitle.length());
		}
		r.setCnTitle(cnTitle);
		r.setOriginalTitle(originalTitle);
		
		x.start("a").addAttribute("class", "more-actor");
		By moreActorBy = By.xpath(x.getXpath());
		WebElement moreActorA = null;
		try {
			moreActorA = d.findElement(moreActorBy);
		} catch (Exception e) {
		}
		if(moreActorA != null) {
			moreActorA.click();
		}
		
		
		WebElement infoDiv = d.findElement(By.id("info"));
		String info = infoDiv.getText();
		r.setInfo(info);
		
		String RegionInfo = null;
		String crewsInfo = null;
		String[] lines = info.split(System.lineSeparator());
		if(lines.length <= 1) {
			lines = info.split("\n");
		}
		for(String l : lines) {
			if(l.startsWith("制片国家/地区")) {
				RegionInfo = l.replaceAll("制片国家/地区: ", "");
			} else if(l.startsWith("主演")) {
				crewsInfo = l.replaceAll("主演: ", "");
			}
		}
		r.setRegion(RegionInfo);
		r.setCrewInfo(crewsInfo);
		
		x.start("span").addAttribute("property", "v:summary");
		By summaryBy = By.xpath(x.getXpath());
		WebElement summarySpan = d.findElement(summaryBy);
		String summary = summarySpan.getText();
		r.setIntroduction(summary);
		
		x.start("span").addAttribute("property", "v:initialReleaseDate");
		WebElement releaseDateSpan = d.findElement(By.xpath(x.getXpath()));
		String releaseDateStr = releaseDateSpan.getText();
		releaseDateStr = releaseDateStr.replaceAll("[^\\d-/\\\\]", "");
		
		Date releaseDate = DateUtilCustom.stringToDateUnkonwFormat(releaseDateStr);
		if(releaseDate == null) {
			return;
		}
		LocalDateTime releaseDateTime = DateTimeHandle.dateToLocalDateTime(releaseDate);
		r.setReleaseTime(releaseDateTime);
		
//		By initialReleaseDateBy = auxTool.byXpathBuilder("span", "property", "v:initialReleaseDate");
//		WebElement releaseDateSpan = d.findElement(initialReleaseDateBy);
//		String releaseDateStr = releaseDateSpan.getText();
	}
	
}
