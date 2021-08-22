package demo.scriptCore.scheduleClawing.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import at.report.pojo.dto.JsonReportOfCaseDTO;
import at.screenshot.pojo.result.ScreenshotSaveResult;
import at.xpath.pojo.bo.XpathBuilderBO;
import autoTest.testModule.pojo.type.TestModuleType;
import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.scriptCore.common.service.JobClawingCommonService;
import demo.scriptCore.localClawing.pojo.bo.MaiMaiClawingBO;
import demo.scriptCore.scheduleClawing.pojo.bo.MaiMaiRunningBO;
import demo.scriptCore.scheduleClawing.pojo.constant.MaiMaiScheduleClawingConstant;
import demo.scriptCore.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.scriptCore.scheduleClawing.service.MaiMaiScheduleClawingService;
import demo.selenium.pojo.bo.BuildTestEventBO;
import image.pojo.result.UploadImageToCloudinaryResult;

@Service
public class MaiMaiScheduleClawingServiceImpl extends JobClawingCommonService implements MaiMaiScheduleClawingService {

	private String eventName = "maiMaiLocalClawing";
	private String userDataFileName = "maiMaiSign.json";

	private TestModuleType testModuleType = TestModuleType.scheduleClawing;
	private ScheduleClawingType testFlowType = ScheduleClawingType.MAI_MAI;

	private TestEvent buildClawingEvent() {
		String paramterFolderPath = getParameterSaveingPath(eventName);
		File paramterFile = new File(paramterFolderPath + File.separator + userDataFileName);
		if (!paramterFile.exists()) {
//			TODO
			return null;
		}

		BuildTestEventBO bo = new BuildTestEventBO();
		bo.setTestModuleType(testModuleType);
		bo.setEventId(testFlowType.getId());
		bo.setFlowName(testFlowType.getFlowName());
		bo.setParameterFilePath(paramterFile.getAbsolutePath());
		return buildTestEvent(bo);
	}

	@Override
	public InsertTestEventResult insertClawingEvent() {
		TestEvent te = buildClawingEvent();
		return testEventService.insertExecuteTestEvent(te);
	}

	@Override
	public TestEventBO clawing(TestEvent te) {
		CommonResult r = new CommonResult();

		TestEventBO tbo = auxTool.beforeRunning(te);

		WebDriver d = null;
		try {

			String jsonStr = ioUtil.getStringFromFile(te.getParameterFilePath());
			if (StringUtils.isBlank(jsonStr)) {
				reportService.caseReportAppendContent(tbo.getReport(), "参数文件读取异常");
				throw new Exception();
			}

			MaiMaiClawingBO clawingEventBO = null;
			try {
				clawingEventBO = new Gson().fromJson(jsonStr, MaiMaiClawingBO.class);
			} catch (Exception e) {
				reportService.caseReportAppendContent(tbo.getReport(), "参数文件结构异常");
				throw new Exception();
			}

			if (clawingEventBO == null) {
				reportService.caseReportAppendContent(tbo.getReport(), "参数文件结构异常");
				throw new Exception();
			}

			d = tbo.getWebDriver();
			String mainWindowHandle = d.getWindowHandle();

			try {
				d.get(clawingEventBO.getMainUrl());
			} catch (TimeoutException e) {
				jsUtil.windowStop(d);
			}

			boolean operatorFlag = tryLogin(d, tbo.getReport(), clawingEventBO);
			if (!operatorFlag) {
				reportService.caseReportAppendContent(tbo.getReport(), "登录异常");
				throw new Exception("登录异常");
			}

			threadSleepRandomTime();

			operatorFlag = changeToExploer(d);
			if (!operatorFlag) {
				reportService.caseReportAppendContent(tbo.getReport(), "查找'发现'按钮异常");
				throw new Exception("查找'发现'按钮异常");
			}

			threadSleepRandomTime(3000L, 5000L);

			reportService.caseReportAppendContent(tbo.getReport(), "准备点赞");

			MaiMaiRunningBO runningBO = new MaiMaiRunningBO();

			runningBO.setShareAndLikeSpanClass(findClassOfShareAndLikeSpan(d));
			if (runningBO.getShareAndLikeSpanClass() == null) {
				reportService.caseReportAppendContent(tbo.getReport(), "查找'分享'按钮的 class 属性异常");
				throw new Exception("查找'分享'按钮的 class 属性异常");
			}

			for (int i = 1; i <= clawingEventBO.getPageCount(); i++) {
				clickLikes(d, runningBO);
				skipToPageEnd(d);
				operatorFlag = pageLoadSuccess(d);
				if (!operatorFlag) {
					reportService.caseReportAppendContent(tbo.getReport(), "page loading time out");
					throw new Exception("page loading time out");
				}
				threadSleepRandomTime(1500L, 2500L);
			}
			reportService.caseReportAppendContent(tbo.getReport(), "本次点赞: " + runningBO.getClickLikeCount());

			if (!isAddFriendReachLimitToday()) {
				reportService.caseReportAppendContent(tbo.getReport(), "准备添加目标好友");
				tryAddFriend(d, mainWindowHandle, runningBO);
				reportService.caseReportAppendContent(tbo.getReport(), "添加完毕");
			} else {
				reportService.caseReportAppendContent(tbo.getReport(), "本日添加好友已达上限");
			}

			r.setIsSuccess();

		} catch (Exception e) {
			e.printStackTrace();
			ScreenshotSaveResult screenSaveResult = screenshot(d, eventName);

			UploadImageToCloudinaryResult uploadImgResult = uploadImgToCloudinary(screenSaveResult.getSavingPath());
			reportService.caseReportAppendImage(tbo.getReport(), uploadImgResult.getImgUrl());
			reportService.caseReportAppendContent(tbo.getReport(), "异常: " + e.toString());

		} finally {
			tryQuitWebDriver(d, tbo.getReport());
			saveReport(tbo);
		}

		return r;
	}

	private boolean tryLogin(WebDriver d, JsonReportOfCaseDTO reportDTO, MaiMaiClawingBO clawingEventBO)
			throws InterruptedException {
		XpathBuilderBO x = new XpathBuilderBO();

		x.start("a").addClass("primary-button").addId("loginBtn");
		try {
			WebElement loginButton = d.findElement(By.xpath(x.getXpath()));
			loginButton.click();
			threadSleepRandomTime();
		} catch (Exception e) {
			System.out.println("can not find login button");
			return false;
		}

		x.start("input").addClass("loginPhoneInput").addType("text");
		try {
			WebElement usernameInput = d.findElement(By.xpath(x.getXpath()));
			usernameInput.click();
			usernameInput.clear();
			usernameInput.sendKeys(clawingEventBO.getUsername());
			threadSleepRandomTime();
		} catch (Exception e) {
			System.out.println("can not find usernameInput");
			return false;
		}

		x.start("input").addType("password").addId("login_pw");
		try {
			WebElement passwordInput = d.findElement(By.xpath(x.getXpath()));
			passwordInput.click();
			passwordInput.clear();
			passwordInput.sendKeys(clawingEventBO.getPassword());
		} catch (Exception e) {
			System.out.println("can not find password input");
			return false;
		}

		x.start("input").addClass("loginBtn").addType("submit");
		try {
			WebElement loginSubmitButton = d.findElement(By.xpath(x.getXpath()));
			loginSubmitButton.click();
			return true;
		} catch (Exception e) {
			System.out.println("can not find loginSubmitButton");
			return false;
		}

	}

	private boolean changeToExploer(WebDriver d) {
		XpathBuilderBO x = new XpathBuilderBO();
		x.start("a").addAttribute("href", "/web/feed_explore");

		try {
			WebElement exploreButton = d.findElement(By.xpath(x.getXpath()));
			exploreButton.click();
			return true;
		} catch (Exception e) {
			System.out.println("can not find exeplore button");
			return false;
		}
	}

	private boolean clickLikes(WebDriver d, MaiMaiRunningBO classStoreBO) throws InterruptedException {
		XpathBuilderBO x = new XpathBuilderBO();
		try {
			x.start("span").addClass(classStoreBO.getShareAndLikeSpanClass());
			String toolBarElementXPath = x.getXpath();

			/* 工具栏, 文字描述元素 */
			List<WebElement> toolBarElementList = null;
			/* 未点赞数, 文字元素 */
			List<WebElement> notLikeYetIcons = new ArrayList<>();

			int waitCounting = 10;
			for (int i = 0; i < waitCounting && toolBarElementList == null; i++) {
				try {
					toolBarElementList = d.findElements(By.xpath(toolBarElementXPath));
				} catch (Exception e) {
					skipToPageEnd(d);
					threadSleepRandomTime(2000L, 3000L);
				}
			}

			if (toolBarElementList == null) {
				return false;
			}

			/*
			 * 暂时工具栏固定只有3个元素, 第三个肯定是点赞按钮; 如果未点赞, style="color: rgb(157, 166, 191);" 如果已点赞,
			 * style="color: rgb(59, 122, 255);" 故 用蓝色 255 判断是否已经点赞
			 */
			try {
				String tmpStyle = null;
				WebElement tmpEle = null;
				for (int i = 0; (i + 3) < toolBarElementList.size(); i = i + 3) {
					tmpEle = toolBarElementList.get(i + 2);
					tmpStyle = tmpEle.getAttribute("style");
					if (!tmpStyle.contains("255")) {
						notLikeYetIcons.add(toolBarElementList.get(i + 2));
					}
				}
			} catch (Exception e) {
			}

			for (WebElement icon : notLikeYetIcons) {
				try {
					icon.click();
					classStoreBO.setClickLikeCount(classStoreBO.getClickLikeCount() + 1);
					threadSleepRandomTime(100L, 300L);
				} catch (Exception e) {
				}
			}

			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private boolean pageIsLoading(WebDriver d) {
		XpathBuilderBO x = new XpathBuilderBO();

//		加载中 则是此元素
//		<span class="loader-text">加载中...</span>

		x.start("span").addClass("loader-text");

		try {
			WebElement loadingSpan = d.findElement(By.xpath(x.getXpath()));
			if (loadingSpan.isDisplayed() && jsUtil.isVisibleInViewport(d, loadingSpan)) {
				return true;
			}

//			发现页可能会出现加载失败 
//			<div class="cursor-pointer" style="padding: 20px; text-align: center; font-size: 14px; color: rgb(22, 65, 185); height: 40px;">加载失败，点击重试</div>

			x.start("div").addClass("cursor-pointer");
			List<WebElement> cursorPointerDivList = d.findElements(By.xpath(x.getXpath()));
			for (WebElement ele : cursorPointerDivList) {
				if ("加载失败，点击重试".equals(ele.getText())) {
					ele.click();
					return true;
				}
			}

			return false;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean pageLoadSuccess(WebDriver d) throws InterruptedException {
		boolean flag = !pageIsLoading(d);
		if (flag) {
			return flag;
		}

		for (int i = 0; i < 30 && !flag; i++) {
			flag = !pageIsLoading(d);
			Thread.sleep(1000L);
		}

		return flag;
	}

	private List<WebElement> collectMessageShareButtons(WebDriver d, MaiMaiRunningBO bo) {
		XpathBuilderBO x = new XpathBuilderBO();
		List<WebElement> resultSpanList = new ArrayList<>();

		try {
			x.start("span").addClass(bo.getShareAndLikeSpanClass());
			List<WebElement> sourctSpanList = d.findElements(By.xpath(x.getXpath()));

			for (WebElement ele : sourctSpanList) {
				if (ele.getText().contains("分享")) {
					resultSpanList.add(ele);
				}
			}

		} catch (Exception e) {
		}
		return resultSpanList;
	}

	/**
	 * 此处已经点击打开分享框, 无论是否目标消息 后续逻辑需要注意关闭分享框
	 * 
	 * @param d
	 * @param messageShareButton
	 * @return
	 */
	private boolean isTargetMessage(WebDriver d, WebElement messageShareButton) {
		XpathBuilderBO x = new XpathBuilderBO();

		messageShareButton.click();

		x.start("div").addId("popup_container").findChild("div").addClass("popup_container ").findChild("div")
				.findChild("div").findChild("div").addAttributeStartWith("class", "sc-").findChild("div")
				.addAttributeStartWith("class", "sc-");

		WebElement memberInfoDiv = null;
		try {
			memberInfoDiv = d.findElement(By.xpath(x.getXpath()));
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		String memberInfo = memberInfoDiv.getText();
		return (inTargetTitleList(memberInfo) && !inJobBlackList(memberInfo));
	}

	/**
	 * 此处必须为已经打开分享框的状态 否则页面代码判断异常
	 * 
	 * @param d
	 * @param mainWindowHandle
	 * @throws InterruptedException
	 */
	private void targetMessageAddFriend(WebDriver d, String mainWindowHandle) throws InterruptedException {
		XpathBuilderBO x = new XpathBuilderBO();

		x.start("div").addId("popup_container").findChild("div").addClass("popup_container ").findChild("div")
				.findChild("div").findChild("div").addAttributeStartWith("class", "sc-").findChild("div")
				.addAttributeStartWith("class", "sc-").findParent();

		WebElement memberInfoDiv = null;
		try {
			memberInfoDiv = d.findElement(By.xpath(x.getXpath()));
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		/* 点击个人信息页 打开新窗口 */
		memberInfoDiv.click();

		Set<String> windowHandles = d.getWindowHandles();
		if (windowHandles.size() < 2) {
			return;
		}
		for (String handle : windowHandles) {
			if (!handle.equals(mainWindowHandle)) {
				d.switchTo().window(handle);
			}
		}

		/* 点击个人信息页 新开窗口之后 需要等待新窗口加载 */
		x.start("div").addContainsText("立刻打通职场人脉:");
		if (!auxTool.loadingCheck(d, x.getXpath())) {
			return;
		}

		/* 2020-04-23 非广东 or 广州区域, 不考虑添加 */
		x.start("span").addAttributeStartWith("class", "icon_address_gray");
		try {
			WebElement addressEle = d.findElement(By.xpath(x.getXpath()));
			if (!addressEle.getText().contains("广东") && !addressEle.getText().contains("广州")) {
				return;
			}
		} catch (Exception e) {
			return;
		}

		threadSleepRandomTime();

		x.start("div").addClass("btn btn-xsm btn-secondary btn-block");
		List<WebElement> elementList = d.findElements(By.xpath(x.getXpath()));
		WebElement addFriendButton = null;
		String tmpTitle = null;
		for (int i = 0; i < elementList.size() && addFriendButton == null; i++) {
			tmpTitle = elementList.get(i).getText();
			if (tmpTitle != null && tmpTitle.contains("加好友")) {
				addFriendButton = elementList.get(i);
			}
		}

		if (addFriendButton == null) {
			return;
		} else {
			addFriendButton.click();
			threadSleepRandomTime();
			if (isAlterAddFriendReachLimit(d)) {
				addFriendReachLimitRreshKey();
				return;
			}
		}

		addMoreFriend(d);

	}

	private void tryCloseMemberInfoDiv(WebDriver d) {
		XpathBuilderBO x = new XpathBuilderBO();
		x.start("img").addClass("cursor-pointer").addAttribute("src", "/static/images/closed.png");
		try {
			d.findElement(By.xpath(x.getXpath())).click();
		} catch (Exception e) {
		}
	}

	private void tryAddFriend(WebDriver d, String mainWindowHandle, MaiMaiRunningBO bo) throws Exception {
		List<WebElement> messageShareButtonList = collectMessageShareButtons(d, bo);
		if (messageShareButtonList == null) {
			throw new Exception("找不到任何分享按钮");
		}
		WebElement tmpShareButton = null;
		for (int i = 0; i < messageShareButtonList.size(); i++) {
			tmpShareButton = messageShareButtonList.get(i);
			if (isTargetMessage(d, tmpShareButton) && !isAddFriendReachLimitToday()) {
				targetMessageAddFriend(d, mainWindowHandle);
				threadSleepRandomTime();
				closeOtherWindow(d, mainWindowHandle);
			}
			threadSleepRandomTime(200L, 400L);
			tryCloseMemberInfoDiv(d);
		}
	}

	private void addMoreFriend(WebDriver d) throws InterruptedException {
		XpathBuilderBO x = new XpathBuilderBO();

		x.start("div").addClass("Tappable-inactive add_friend_list_item");
		String moreFriendListXpath = x.getXpath();

		x.start("div").addClass("sc-bwzfXH kZacyY sc-bdVaJa iXEeCK");
		String addFriendButtonListXpath = x.getXpath();

		List<WebElement> moreFriendsList = null;
		List<WebElement> addFriendButtonList = null;
		try {
			moreFriendsList = d.findElements(By.xpath(moreFriendListXpath));
			addFriendButtonList = d.findElements(By.xpath(addFriendButtonListXpath));
		} catch (Exception e) {
			return;
		}

		WebElement tmpElement = null;
		String tmpTitle = null;
		for (int i = 0; i < moreFriendsList.size(); i++) {
			tmpElement = moreFriendsList.get(i);
			tmpTitle = tmpElement.getText();
			if (inTargetTitleList(tmpTitle) && !inJobBlackList(tmpTitle)) {
				addFriendButtonList.get(i).click();
				threadSleepRandomTime();
				if (isAlterAddFriendReachLimit(d)) {
					addFriendReachLimitRreshKey();
					return;
				}
			}
		}

	}

	private boolean isAlterAddFriendReachLimit(WebDriver d) {
		XpathBuilderBO x = new XpathBuilderBO();
		x.start("div").addClass("layerChoose");

		try {
			WebElement layer = d.findElement(By.xpath(x.getXpath()));
			String text = layer.getText();
			if (layer.isDisplayed() && (text != null && text.contains("基础名额已用完"))) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return true;
		}
	}

	private boolean isAddFriendReachLimitToday() {
		String v = redisConnectService.getValByName(MaiMaiScheduleClawingConstant.addFriendsLimitFlagRedisKey);
		if ("true".equals(v)) {
			return true;
		}
		return false;
	}

	private void addFriendReachLimitRreshKey() {
		LocalDateTime endOfToday = localDateTimeHandler.atEndOfDay(LocalDateTime.now());
		Long secondGap = ChronoUnit.SECONDS.between(LocalDateTime.now(), endOfToday);
		redisConnectService.setValByName(MaiMaiScheduleClawingConstant.addFriendsLimitFlagRedisKey, "true", secondGap,
				TimeUnit.SECONDS);
	}

	private String findClassOfShareAndLikeSpan(WebDriver d) {
		String targetClass = null;

		XpathBuilderBO x = new XpathBuilderBO();
		x.start("div").addId("pingback-item-1");

		WebElement messageDiv = null;
		try {
			messageDiv = d.findElement(By.xpath(x.getXpath()));
		} catch (Exception e) {
			return targetClass;
		}

		if (messageDiv == null) {
			x.start("div").addId("pingback-item-2");
			try {
				messageDiv = d.findElement(By.xpath(x.getXpath()));
			} catch (Exception e) {
				return targetClass;
			}
		}

		if (messageDiv == null) {
			return targetClass;
		}

		/**
		 * 根据相对路径, 锁定一批 span, 应该是 分享 评论 点赞 的工具栏 但 此层 包含三个图标span + ("分享" + 评论数 +
		 * 点赞数(字符span)) 而且 共用一个 class , 获取其中之一即可
		 */
		x.findChild("div").findChild("div").findChild("div").findChild("span");

		List<WebElement> sourceSpanList = null;
		try {
			sourceSpanList = d.findElements(By.xpath(x.getXpath()));

			for (int i = 0; i < sourceSpanList.size() && targetClass == null; i++) {
				if (StringUtils.isNotBlank(sourceSpanList.get(i).getAttribute("class"))) {
					targetClass = sourceSpanList.get(i).getAttribute("class");
				}
			}
		} catch (Exception e) {
			return targetClass;
		}

		return targetClass;
	}
}
