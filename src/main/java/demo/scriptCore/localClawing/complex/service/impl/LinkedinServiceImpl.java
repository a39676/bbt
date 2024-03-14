package demo.scriptCore.localClawing.complex.service.impl;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.scriptCore.localClawing.complex.pojo.dto.LinkedinOptionDTO;
import demo.scriptCore.localClawing.complex.service.LinkedinService;
import demo.selenium.service.impl.AutomationTestCommonService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class LinkedinServiceImpl extends AutomationTestCommonService implements LinkedinService {

	private static String optionFilePath = "d:/home/u2/bbt/optionFile/tmp/linkedOption.json";
	private static LinkedinOptionDTO optionDTO = null;

	private LinkedinOptionDTO loadOption() {
		FileUtilCustom ioUtil = new FileUtilCustom();
		String optionJsonStr = ioUtil.getStringFromFile(optionFilePath);

		LinkedinOptionDTO dto = new Gson().fromJson(optionJsonStr, LinkedinOptionDTO.class);
		return dto;
	}

	@Override
	public void buildRelationship() {
		optionDTO = loadOption();
		WebDriver d = null;
		
		try {
			d = webDriverService.buildChromeWebDriver();
			d.get(optionDTO.getMainUrl());

			threadSleepRandomTime();

			loginPage(d);

			threadSleepRandomTime();

			if (!loginCheck(d)) {
				System.out.println("login failed");
				return;
			}

			tryCloseMsgOverlay(d);
			
			threadSleepRandomTime();

			getIntoRelationship(d);

			threadSleepRandomTime();

			System.out.println("Done");

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			tryQuitWebDriver(d);
		}

	}

	private void loginPage(WebDriver d) {

		d.get(optionDTO.getLoginUrl());
		
		try {
			threadSleepRandomTime();
			System.out.println(loadingCheck(d, "/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/form[1]/div[1]/input[1]"));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		WebElement usernameInput = d.findElement(By.xpath("/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/form[1]/div[1]/input[1]"));
		usernameInput.click();
		usernameInput.clear();
		usernameInput.sendKeys(optionDTO.getUsername());

		WebElement pwdInput = d.findElement(By.xpath("/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/form[1]/div[2]/input[1]"));
		pwdInput.click();
		pwdInput.clear();
		pwdInput.sendKeys(optionDTO.getPwd());

		WebElement loginButton = d
				.findElement(By.xpath("/html[1]/body[1]/div[1]/main[1]/div[2]/div[1]/form[1]/div[3]/button[1]"));
		loginButton.click();
	}

	private boolean loginCheck(WebDriver d) {
		try {
			return loadingCheck(d,
					"/html[1]/body[1]/div[6]/div[3]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/a[1]/div[2]");
		} catch (InterruptedException e) {
//			e.printStackTrace();
			return false;
		}
	}

	private void getIntoRelationship(WebDriver d) {
		WebElement relationshipButton = d
				.findElement(By.xpath("/html[1]/body[1]/div[6]/header[1]/div[1]/nav[1]/ul[1]/li[2]/a[1]"));
		relationshipButton.click();

		if (!relationshipPageLoadingCheck(d)) {
			return;
		}
		
		try {
			threadSleepRandomTimeLong();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		List<WebElement> buildRelationshipButtonList = findBuildRelationshipButtonList(d);
		if (buildRelationshipButtonList.isEmpty()) {
			return;
		}

		Double limit = optionDTO.getApplyRate() * buildRelationshipButtonList.size();
		Integer tmpIndex = null;
		Integer canNotClickCount = 0;
		
		for (int i = 1; i < limit; i++) {
			tmpIndex = ThreadLocalRandom.current().nextInt(buildRelationshipButtonList.size());
			try {
				buildRelationshipButtonList.get(tmpIndex).click();
//				element click intercepted: Element <span class="artdeco-button__text">...</span> is not clickable at point (752, 10). Other element would receive the click: <a aria-current="page" data-test-global-nav-link="mynetwork" data-resource="voyagerCommunicationsTabBadges" data-link-to="mynetwork" data-control-name="nav_mynetwork" data-alias="relationships" href="/mynetwork/" id="ember19" class="global-nav__primary-link global-nav__primary-link--active ember-view">...</a>
				threadSleepRandomTime();
			} catch (Exception e) {
				canNotClickCount++;
				System.out.println("can not click: " + canNotClickCount);
			}
			System.out.println("apply count: " + i);
			buildRelationshipButtonList = findBuildRelationshipButtonList(d);
		}

	}

	private List<WebElement> findBuildRelationshipButtonList(WebDriver d) {
		xPathBuilder.start("span").addClass("artdeco-button__text").addAttributeText(optionDTO.getBuildRelationshipButtonText());
		List<WebElement> list = d.findElements(By.xpath(xPathBuilder.getXpath()));
//		list = list.stream().filter(ele -> optionDTO.getBuildRelationshipButtonText().equals(ele.getText())).toList();
		return list;
	}

	private boolean relationshipPageLoadingCheck(WebDriver d) {
		try {
			return loadingCheck(d,
					"/html[1]/body[1]/div[6]/div[3]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/h2[1]");
		} catch (InterruptedException e) {
//			e.printStackTrace();
			return false;
		}
	}

	private void tryCloseMsgOverlay(WebDriver d) {
		try {
			d.findElement(By.xpath("/html[1]/body[1]/div[6]/aside[1]/div[1]/header[1]/div[3]/button[2]")).click();
		} catch (Exception e) {
		}
	}
}
