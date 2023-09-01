package demo.scriptCore.scheduleClawing.complex.service.impl;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.common.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.common.pojo.type.AutomationTestFlowResultType;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.dto.UnderWayDTO;
import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.dto.UnderWayMonthTestDTO;
import autoTest.testEvent.scheduleClawing.searchingDemo.pojo.dto.UnderWayTrainProjectDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.scheduleClawing.complex.pojo.dto.UnderWayCourseDoneRequestDTO;
import demo.scriptCore.scheduleClawing.complex.pojo.dto.UnderWayCoursewareDTO;
import demo.scriptCore.scheduleClawing.complex.pojo.dto.UnderWayExamFormDTO;
import demo.scriptCore.scheduleClawing.complex.pojo.dto.UnderWayTestQuestionAndAnswerSubDTO;
import demo.scriptCore.scheduleClawing.complex.service.UnderWayService;
import demo.selenium.service.impl.AutomationTestCommonService;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@SuppressWarnings("unused")
@Service
public class UnderWayServiceImpl extends AutomationTestCommonService implements UnderWayService {

	@Autowired
	private FileUtilCustom ioUtil;
	@Autowired
	private UnderWayCacheService underWayCacheService;

	private final String questionAndAnswerFilePathStr = MAIN_FOLDER_PATH + "/tmp/underWayQuestionAndAnswer.json";

	@Override
	public TestEventBO monthTest(TestEventBO tbo) {
		WebDriver d = null;
		ScheduleClawingType caseType = ScheduleClawingType.UNDER_WAY_MONTH_TEST;
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(caseType.getFlowName());
		AutomationTestCaseResult r = initCaseResult(caseType.getFlowName());

		try {
			UnderWayMonthTestDTO dto = buildTestEventParamFromJsonCustomization(tbo.getParamStr(),
					UnderWayMonthTestDTO.class);

			if (dto == null) {
				reportService.caseReportAppendContent(caseReport, "读取参数异常");
				return tbo;
			}

			d = webDriverService.buildChromeWebDriver();
			d.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));

			login(d, dto, caseReport);

			threadSleepRandomTime();

			findExam(d, dto, caseReport);

			threadSleepRandomTime();

			while (!dto.getExamUrlList().isEmpty()) {

				getInExam(d, dto, caseReport);

				threadSleepRandomTime();

				if (!examEnterCheck(d)) {
					throw new Exception("未正常进入问卷");
				}

				startExam(d, dto);

				threadSleepRandomTime();

				fillExam(d, dto, caseReport);

				threadSleepRandomTime();

				backHome(d, dto, caseReport);

				threadSleepRandomTime();
			}

			threadSleepRandomTime();

			reportService.caseReportAppendContent(caseReport,
					"Done, " + localDateTimeHandler.dateToStr(LocalDateTime.now()));
			r.setResultType(AutomationTestFlowResultType.PASS);

		} catch (Exception e) {
			reportService.caseReportAppendContent(caseReport, "异常: " + e.toString());
		}
		tbo.getCaseResultList().add(r);
		tbo.getReport().getCaseReportList().add(caseReport);
		if (!tryQuitWebDriver(d)) {
			sendTelegramMsg("Web driver quit failed, " + caseType.getFlowName());
		}
		sendAutomationTestResult(tbo);

		return tbo;
	}

	private void login(WebDriver d, UnderWayDTO dto, JsonReportOfCaseDTO caseReport) throws InterruptedException {
		d.get(dto.getLoginUrl());
		reportService.caseReportAppendContent(caseReport, "Login with, " + dto.getUsername());
		loadingCheck(d, "//input[@id='username_text']");

		WebElement usernameInput = d.findElement(By.xpath("//input[@id='username_text']"));
		WebElement pwdInput = d.findElement(By.xpath("//input[@id='password_text']"));

		usernameInput.click();
		usernameInput.clear();
		usernameInput.sendKeys(dto.getUsername());

		pwdInput.click();
		pwdInput.clear();
		pwdInput.sendKeys(dto.getPwd());

		WebElement loginButton = d.findElement(By.xpath("//a[@id='sign_in_button_standard']"));
		loginButton.click();
	}

	private void backHome(WebDriver d, UnderWayMonthTestDTO dto, JsonReportOfCaseDTO caseReport) {
		if (!d.getCurrentUrl().equals(dto.getHomePageUrl())) {
			d.get(dto.getHomePageUrl());
		}
	}

	private void findExam(WebDriver d, UnderWayMonthTestDTO dto, JsonReportOfCaseDTO caseReport) throws Exception {
		reportService.caseReportAppendContent(caseReport, "Try to find exam");
		if (!d.getCurrentUrl().equals(dto.getHomePageUrl())) {
			d.get(dto.getHomePageUrl());
		}

		if (!loadingCheck(d, "/html[1]/body[1]/div[5]/div[1]/div[2]/div[2]/div[1]/h4[1]")) {
			throw new Exception("Can NOT load: " + dto.getHomePageUrl());
		} else {
			reportService.caseReportAppendContent(caseReport, "Load exam home page");
		}

		threadSleepRandomTime();

		String examLinkListXpath = xPathBuilder.start("a").getXpath();

		int searchCount = 5;
		while (searchCount > 0 && (dto.getExamUrlList() == null || dto.getExamUrlList().isEmpty())) {
			List<WebElement> examLinkList = d.findElements(By.xpath(examLinkListXpath));

			for (int i = 0; i < examLinkList.size(); i++) {
				String url = examLinkList.get(i).getAttribute("href");
				if (url != null && url.contains("examId")) {
					reportService.caseReportAppendContent(caseReport, "Find exam: " + url);
					dto.addExamUrlList(url);
				}
			}

			if (dto.getExamUrlList() == null && searchCount > 0) {
				reportService.caseReportAppendContent(caseReport,
						"Can not find any exams, will try again, count down: " + searchCount);
				searchCount--;
				threadSleepRandomTime();
			}
		}

		if (dto.getExamUrlList() == null || dto.getExamUrlList().isEmpty()) {
			throw new Exception("Can not find any exams");
		} else {
			reportService.caseReportAppendContent(caseReport, "Find " + dto.getExamUrlList().size() + " exams");
		}
	}

	private boolean getInExam(WebDriver d, UnderWayMonthTestDTO dto, JsonReportOfCaseDTO caseReport)
			throws InterruptedException {
		boolean isTarget = false;
		for (int examIndex = 0; examIndex < dto.getExamUrlList().size(); examIndex++) {
			String url = dto.getExamUrlList().get(examIndex);

			try {
				d.get(url);
			} catch (Exception e) {
				reportService.caseReportAppendContent(caseReport, "Can NOT visit: " + url + ", will try again later");
			}
			if (!examEnterCheck(d)) {
				examIndex--;
				continue;
			}

			WebElement examTitle = d.findElement(By.xpath("/html[1]/body[1]/div[5]/div[1]/div[2]/h2[1]"));
			String examName = examTitle.getText();

			for (int i = 0; i < dto.getExamNameList().size() && !isTarget; i++) {
				isTarget = (examName.contains(dto.getExamNameList().get(i)));
			}

			if (!isTarget) {
				reportService.caseReportAppendContent(caseReport, examName + ", is NOT in target, skip");
				dto.getExamUrlList().remove(examIndex);
				examIndex--;
			} else {
				reportService.caseReportAppendContent(caseReport, examName + ", had found, ready to start exam");
				dto.getExamUrlList().remove(examIndex);
				return isTarget;
			}
		}
		return isTarget;
	}

	private boolean examEnterCheck(WebDriver d) throws InterruptedException {
		return loadingCheck(d, "//h4[contains(text(),'进入考试前请仔细阅读考试说明')]");
	}

	private void startExam(WebDriver d, UnderWayMonthTestDTO dto) {
		WebElement examEnter = d.findElement(By.xpath("//a[contains(text(),'已阅读完毕，确认进入考场')]"));
		examEnter.click();
	}

	private void fillExam(WebDriver d, UnderWayMonthTestDTO dto, JsonReportOfCaseDTO caseReport)
			throws InterruptedException {
		/*
		 * TODO 关闭初始提示 暂时被关闭全屏限制, 无法再获取 xpath
		 */

		String questionAndAnswerStr = ioUtil.getStringFromFile(questionAndAnswerFilePathStr);
		UnderWayExamFormDTO formDTO = buildObjFromJsonCustomization(questionAndAnswerStr, UnderWayExamFormDTO.class);

		if (!loadingCheck(d, "//a[contains(text(),'提交试卷')]")) {
			return;
		}

		jsUtil.scrollToBottom(d);

//		/html[1]/body[1]/div[2]/div[1]/div[2]/form[1]/div[1]/div[questionIndex]/p[1]
		List<WebElement> questionList = d
				.findElements(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[2]/form[1]/div[1]/div"));
		for (Integer questionIndex = 1; questionIndex <= questionList.size(); questionIndex++) {
			WebElement questionP = d.findElement(
					By.xpath("/html[1]/body[1]/div[2]/div[1]/div[2]/form[1]/div[1]/div[" + questionIndex + "]/p[1]"));
			String questionStr = questionP.getText();
			questionStr = questionStr.substring(questionStr.indexOf('、') + 1, questionStr.length());

			UnderWayTestQuestionAndAnswerSubDTO questionDTO = findQuestionSubDTO(formDTO, questionStr);

			List<String> answerList = questionDTO.getAnswer();

			reportService.caseReportAppendContent(caseReport, questionIndex + ", " + questionStr);
			reportService.caseReportAppendContent(caseReport, answerList.toString());

			answerSelectorTag: for (Integer answerSelectorIndex = 1; answerSelectorIndex < 7
					&& !answerList.isEmpty(); answerSelectorIndex++) {
//				/html[1]/body[1]/div[2]/div[1]/div[2]/form[1]/div[1]/div[90]/ul[1]/li[5]/input[1]
				String answerStr = null;
				try {
					WebElement answerLabel = d
							.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[2]/form[1]/div[1]/div["
									+ questionIndex + "]/ul[1]/li[" + answerSelectorIndex + "]/label[1]"));
					answerStr = answerLabel.getText();
					answerStr = answerStr.substring(2, answerStr.length());

					WebElement answerInput = d
							.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[2]/form[1]/div[1]/div["
									+ questionIndex + "]/ul[1]/li[" + answerSelectorIndex + "]/input[1]"));
					String inputId = answerInput.getAttribute("id");

					if (answerList.contains(answerStr)) {
						WebElement targetInput = d.findElement(By.id(inputId));
						targetInput.click();
						threadSleepRandomTime(1000L, 2000L);
//						jsUtil.execute(d, "document.getElementById('" + inputId + "').checked = true;");
						answerList.remove(answerStr);
					}
				} catch (NoSuchElementException noElement) {
					reportService.caseReportAppendContent(caseReport,
							"Can NOT find Element :" + answerSelectorIndex + "for: " + answerStr);
					try {
						threadSleepRandomTime(9000L, 10000L);
					} catch (Exception e2) {
					}
					break answerSelectorTag;
				} catch (Exception e) {
				}
			}

		}

//		提交试卷
//		WebElement submitButton = d.findElement(By.xpath("//a[contains(text(),'提交试卷')]"));
//		submitButton.click();
//		try {
//			threadSleepRandomTime(2000L, 3000L);
//		} catch (Exception e2) {
//		}
//		WebElement ensureSubmitButton = d.findElement(By.xpath("//a[contains(text(),'确定')]"));
//		ensureSubmitButton.click();

//		保存试卷
		String saveExamXpath = xPathBuilder.start("a").addAttribute("data-action", "saveExam").getXpath();
		WebElement saveButton = d.findElement(By.xpath(saveExamXpath));
		saveButton.click();
		try {
			threadSleepRandomTime(2000L, 3000L);
		} catch (Exception e2) {
		}
		WebElement ensureSaveButton = d.findElement(By.xpath("//a[contains(text(),'确定')]"));
		ensureSaveButton.click();

	}

	private UnderWayTestQuestionAndAnswerSubDTO findQuestionSubDTO(UnderWayExamFormDTO formDTO, String question) {
		for (UnderWayTestQuestionAndAnswerSubDTO questionDTO : formDTO.getQuestionAndAnswerList()) {
			if (questionDTO.getQuestion().equals(question)) {
				return questionDTO;
			}
		}
		return null;
	}

	private void answerCollect(WebDriver d, UnderWayMonthTestDTO dto, JsonReportOfCaseDTO caseReport) {
		d.get(dto.getAnswerFormUrl());

		try {
			loadingCheck(d, "/html[1]/body[1]/div[2]/div[1]/div[2]/div[2]/h4[1]");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		UnderWayExamFormDTO formDTO = new UnderWayExamFormDTO();

		for (Integer questionIndex = 1; questionIndex < 135; questionIndex++) {
			UnderWayTestQuestionAndAnswerSubDTO subDTO = new UnderWayTestQuestionAndAnswerSubDTO();

			WebElement tmpQuestion = d.findElement(
					By.xpath("/html[1]/body[1]/div[2]/div[1]/div[2]/div[2]/div[" + questionIndex + "]/p[1]"));
			String questionStr = tmpQuestion.getText();
			questionStr = questionStr.substring(questionStr.indexOf('、') + 1, questionStr.length());

			subDTO.setQuestion(questionStr);

//			A、B、D
//			A -> 65
//			正确 错误
			WebElement correctAnswerDiv = d.findElement(By.xpath(
					"/html[1]/body[1]/div[2]/div[1]/div[2]/div[2]/div[" + questionIndex + "]/div[2]/div[1]/div[1]"));

			String answerTagStr = correctAnswerDiv.getText();
			if (answerTagStr.matches("^.*[A-Z].*$")) {
				String[] answerTags = answerTagStr.split("、");
				for (String answerTag : answerTags) {
					answerTag = answerTag.toUpperCase();
					int answerIndex = (int) answerTag.charAt(0) - 64;
					WebElement answerLabel = d.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[2]/div[2]/div["
							+ questionIndex + "]/ul[1]/li[" + answerIndex + "]/label[1]"));
					String answerStr = answerLabel.getText();
					answerStr = answerStr.substring(2, answerStr.length());

					subDTO.addAnswer(answerStr);
				}
			} else {
				subDTO.addAnswer(answerTagStr);
			}
//			/html[1]/body[1]/div[2]/div[1]/div[2]/div[2]/div[题目]/ul[1]/li[选项]/label[1]

			formDTO.addQuestionAndAnswer(subDTO);
		}

		JSONObject json = JSONObject.fromObject(formDTO);
		ioUtil.byteToFile(json.toString().getBytes(), (MAIN_FOLDER_PATH + "/tmp/underWayQuestionAndAnswer.json"));
	}

	@Override
	public TestEventBO trainProject(TestEventBO tbo) {
		WebDriver d = null;
		ScheduleClawingType caseType = ScheduleClawingType.UNDER_WAY_TRAIN_PROJECT;
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(caseType.getFlowName());
		AutomationTestCaseResult r = initCaseResult(caseType.getFlowName());

		Map<String, List<UnderWayCoursewareDTO>> coursewareIdMap = new HashMap<>();

		try {
			UnderWayTrainProjectDTO dto = buildTestEventParamFromJsonCustomization(tbo.getParamStr(),
					UnderWayTrainProjectDTO.class);

			if (dto == null) {
				reportService.caseReportAppendContent(caseReport, "读取参数异常");
				return tbo;
			}

			d = webDriverService.buildChromeWebDriver();
			d.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(120));

			login(d, dto, caseReport);

			threadSleepRandomTime();

			String token = findGlobalToken(d);

			List<String> trainProjectIdList = findTrainProject(d, dto, caseReport);

			if (trainProjectIdList.isEmpty()) {
				throw new Exception("NO train project waiting");
			}

			threadSleepRandomTime();

			for (String trainProjectId : trainProjectIdList) {
				String trainProjectUrl = dto.getHomePageUrl() + "/ocs/trainProjectDetail.html?trainProjectId="
						+ trainProjectId;
				List<UnderWayCoursewareDTO> coursewareDtoList = getIntoTrainProject(d, trainProjectUrl, caseReport);
				if (!coursewareDtoList.isEmpty()) {
					coursewareIdMap.put(trainProjectId, coursewareDtoList);
				}
			}

			threadSleepRandomTime();

			for (String trainProjectId : trainProjectIdList) {
				String urlStr = dto.getHomePageUrl();
				urlStr = urlStr.replaceAll("/web", "");
				urlStr = urlStr + "/bingosoft-train-courseonline-api/pc/userLearnNew?access_token=" + token;
				urlStr = urlStr + "&_=" + (String.valueOf(new Date().getTime()));
//				Set<Cookie> cookies = d.manage().getCookies();

				List<UnderWayCoursewareDTO> coursewareDtoList = coursewareIdMap.get(trainProjectId);
				LocalDateTime requestStartTime = LocalDateTime.now();
				for (UnderWayCoursewareDTO courseDTO : coursewareDtoList) {
					UnderWayCourseDoneRequestDTO requestDTO = new UnderWayCourseDoneRequestDTO();
					requestDTO.setUrlStr(urlStr);
					requestDTO.setTrainProjectId(trainProjectId);
					requestDTO.setCoursewareId(courseDTO.getCoursewareId());
					requestDTO.setSeconds(courseDTO.getSeconds());
					requestStartTime = requestStartTime.plusSeconds(courseDTO.getSeconds());
					requestDTO.setStartTime(requestStartTime);
					underWayCacheService.getCacheRequestList().add(requestDTO);
				}
			}

			reportService.caseReportAppendContent(caseReport,
					"Done, " + localDateTimeHandler.dateToStr(LocalDateTime.now()));
			r.setResultType(AutomationTestFlowResultType.PASS);

		} catch (Exception e) {
			e.printStackTrace();
			reportService.caseReportAppendContent(caseReport, "异常: " + e.toString());
		}
		tbo.getCaseResultList().add(r);
		tbo.getReport().getCaseReportList().add(caseReport);
		if (!tryQuitWebDriver(d)) {
			sendTelegramMsg("Web driver quit failed, " + caseType.getFlowName());
		}
		sendAutomationTestResult(tbo);

		return tbo;
	}

	private String findGlobalToken(WebDriver d) throws Exception {
		if (!loadingCheck(d, "/html[1]/body[1]/div[5]/div[4]/div[1]/div[1]/h4[1]", 10000L, 10)) {
			throw new Exception("Can NOT make sure login");
		}

		jsUtil.execute(d, "document.querySelector('#exam-test').setAttribute('token', Global.token);");

		WebElement target = d.findElement(By.id("exam-test"));
		return target.getAttribute("token");
	}

	private List<String> findTrainProject(WebDriver d, UnderWayTrainProjectDTO dto, JsonReportOfCaseDTO caseReport)
			throws Exception {
		reportService.caseReportAppendContent(caseReport, "Try to find train projects");
		if (!d.getCurrentUrl().equals(dto.getHomePageUrl())) {
			d.get(dto.getHomePageUrl());
		}

		jsUtil.scrollToBottom(d);

		if (!loadingCheck(d, "/html[1]/body[1]/div[5]/div[4]/div[1]/div[1]/h4[1]")) {
			throw new Exception("Can NOT load: " + dto.getHomePageUrl());
		} else {
			reportService.caseReportAppendContent(caseReport, "Load train project home page");
		}

		threadSleepRandomTime();

		List<String> trainProjectIdList = new ArrayList<>();

		String trainProjectSpanXpathFormat = "/html[1]/body[1]/div[5]/div[4]/div[1]/div[1]/ul[1]/li[%d]/a[1]/span[1]";
		String trainProjectLinkXpathFormat = "/html[1]/body[1]/div[5]/div[4]/div[1]/div[1]/ul[1]/li[%d]/a[1]";

		try {
			for (int i = 1;; i++) {
				String trainProjectXpath = String.format(trainProjectSpanXpathFormat, i);
				WebElement span = d.findElement(By.xpath(trainProjectXpath));
				String spanClassName = span.getAttribute("class");
				if (!"tag-nocompleted".equals(spanClassName)) {
					continue;
				}
				WebElement linkElement = d.findElement(By.xpath(String.format(trainProjectLinkXpathFormat, i)));
				String urlStr = linkElement.getAttribute("href");
				if (!urlStr.contains("trainProjectId=")) {
					continue;
				}
				int trainProjectIdIndex = urlStr.indexOf("trainProjectId=");
				String projectId = urlStr.substring(trainProjectIdIndex + 15, urlStr.length());
				trainProjectIdList.add(projectId);
			}
		} catch (Exception e) {
			return trainProjectIdList;
		}
	}

	private List<UnderWayCoursewareDTO> getIntoTrainProject(WebDriver d, String trainProjectUrl,
			JsonReportOfCaseDTO caseReport) throws Exception {
		reportService.caseReportAppendContent(caseReport, "Try to get into train project");
		d.get(trainProjectUrl);

		if (!loadingCheck(d,
				"/html[1]/body[1]/div[5]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]/ul[1]/li[1]")) {
			throw new Exception("Can NOT load train project: " + trainProjectUrl);
		} else {
			reportService.caseReportAppendContent(caseReport, "Can NOT load train project: " + trainProjectUrl);
		}

		threadSleepRandomTime();

		List<UnderWayCoursewareDTO> coursewareIdList = new ArrayList<>();

		String coursewareLiListXpath = "/html[1]/body[1]/div[5]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]/ul/li";
		String coursewareSecondSpanXpath = "/html[1]/body[1]/div[5]/div[1]/div[2]/div[2]/div[2]/div[1]/div[1]/div[1]/ul/li/p[1]/span[1]/i[1]";

		try {
			List<WebElement> courseLiList = d.findElements(By.xpath(coursewareLiListXpath));
			List<WebElement> coursewareSecondSpanList = d.findElements(By.xpath(coursewareSecondSpanXpath));

			for (int i = 1; i < courseLiList.size(); i++) {
				UnderWayCoursewareDTO coursewareDTO = new UnderWayCoursewareDTO();

				WebElement courseLi = courseLiList.get(i);
				String coursewareId = courseLi.getAttribute("_coursewareid");
				coursewareDTO.setCoursewareId(coursewareId);

				WebElement courseTimeI = coursewareSecondSpanList.get(i);
				String timeStr = courseTimeI.getText();

				int minIndex = timeStr.indexOf("分");
				Integer minutes = Integer.parseInt(timeStr.substring(0, minIndex));

				int secondIndex = timeStr.indexOf("秒");
				Integer seconds = Integer.parseInt(timeStr.substring(minIndex + 1, secondIndex));

				seconds = seconds + minutes * 60;
				coursewareDTO.setSeconds(seconds);

				coursewareIdList.add(coursewareDTO);
			}
		} catch (Exception e) {
			return coursewareIdList;
		}

		return coursewareIdList;
	}

	@Override
	public void checkAndSendCourseDoneRequest() {
		if (underWayCacheService.getCacheRequestList().isEmpty()) {
			return;
		}

		LocalDateTime now = LocalDateTime.now();

		for (int i = 0; i < underWayCacheService.getCacheRequestList().size(); i++) {
			UnderWayCourseDoneRequestDTO dto = underWayCacheService.getCacheRequestList().get(i);
			if (dto.getStartTime().isAfter(now)) {
				sendCourseDoneRequest(dto.getUrlStr(), dto.getTrainProjectId(), dto.getCoursewareId(),
						dto.getSeconds());
				underWayCacheService.getCacheRequestList().remove(i);
				i--;
			}
		}
	}

	private void sendCourseDoneRequest(String urlStr, String trainProjectId, String courseId, Integer seconds) {
		JSONObject json = new JSONObject();
		json.put("inputDataType", "pc");
		json.put("inputDataDevice", "pc");
		json.put("learnSecond", seconds);
		json.put("coursewareId", courseId);
		json.put("trainProjectId", trainProjectId);

		Map<String, String> requestPropertyMap = new HashMap<>();
		requestPropertyMap.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/112.0.0.0 Safari/537.36");
		requestPropertyMap.put("Content-Type", "application/json; charset=UTF-8");
		requestPropertyMap.put("Data-Type", "json; charset=UTF-8");

		HttpURLConnection con = null;
		StringBuilder response = new StringBuilder();

		byte[] postData = json.toString().getBytes(StandardCharsets.UTF_8);

		try {
			URL myurl = new URL(urlStr);
			con = (HttpURLConnection) myurl.openConnection();

			con.setDoOutput(true);
			con.setRequestMethod("POST");
//			String cookiesStr = "";
//			for (Cookie cookie : cookies) {
//				cookiesStr = cookiesStr + cookie.getName() + "=" + cookie.getValue() + "; ";
//			}
//			con.setRequestProperty("Cookie", cookiesStr);

			for (Entry<String, String> entry : requestPropertyMap.entrySet()) {
				con.setRequestProperty(entry.getKey(), entry.getValue());
			}

			if (postData != null) {
				DataOutputStream wr = new DataOutputStream(con.getOutputStream());
				wr.write(postData);
				wr.flush();
			}

			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String line;

			while ((line = in.readLine()) != null) {
				response.append(line);
				response.append(System.lineSeparator());
			}

			System.out.println(response);
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (con != null) {
				con.disconnect();
			}
		}
	}
}
