package demo.scriptCore.scheduleClawing.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.pojo.type.AutomationTestFlowResultType;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testEvent.searchingDemo.pojo.dto.UnderWayMonthTestDTO;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.scheduleClawing.pojo.dto.UnderWayExamFormDTO;
import demo.scriptCore.scheduleClawing.pojo.dto.UnderWayTestQuestionAndAnswerSubDTO;
import demo.scriptCore.scheduleClawing.service.UnderWayMonthTestService;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class UnderWayMonthTestServiceImpl extends AutomationTestCommonService implements UnderWayMonthTestService {

	@Autowired
	private FileUtilCustom ioUtil;

	private final String questionAndAnswerFilePathStr = "/home/u2/bbt/tmp/underWayQuestionAndAnswer.json";

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
			d.manage().timeouts().pageLoadTimeout(15L, TimeUnit.SECONDS);

			login(d, dto);

			threadSleepRandomTime();

//			answerCollect(d, dto);

			findExam(d, dto);

			threadSleepRandomTime();

			if (!examEnterCheck(d, dto)) {
				reportService.caseReportAppendContent(caseReport, "未正常进入问卷");
				throw new Exception("未正常进入问卷");
			}

			startExam(d, dto);

			threadSleepRandomTime(15000L, 20000L);

			fillExam(d, dto);

			threadSleepRandomTime();

			reportService.caseReportAppendContent(caseReport,
					"Done, " + localDateTimeHandler.dateToStr(LocalDateTime.now()));
			r.setResultType(AutomationTestFlowResultType.PASS);

		} catch (Exception e) {
			reportService.caseReportAppendContent(caseReport, "异常: " + e.toString());

		} finally {
			tbo.getCaseResultList().add(r);
			tbo.getReport().getCaseReportList().add(caseReport);
			tryQuitWebDriver(d);
			sendAutomationTestResult(tbo);
		}

		return tbo;
	}

	private void login(WebDriver d, UnderWayMonthTestDTO dto) throws InterruptedException {
		d.get(dto.getLoginUrl());
		auxTool.loadingCheck(d, "//input[@id='username_text']");

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

	private void findExam(WebDriver d, UnderWayMonthTestDTO dto) {
		if (!d.getCurrentUrl().equals(dto.getHomePageUrl())) {
			d.get(dto.getHomePageUrl());
		}

		try {
			threadSleepRandomTime(1000L, 5000L);
		} catch (Exception e) {
		}

		WebElement examEnter = d.findElement(By.xpath(dto.getExamEnterXPath()));
		examEnter.click();
	}

	private boolean examEnterCheck(WebDriver d, UnderWayMonthTestDTO dto) throws InterruptedException {
		return auxTool.loadingCheck(d, "//h4[contains(text(),'进入考试前请仔细阅读考试说明')]");
	}

	private void startExam(WebDriver d, UnderWayMonthTestDTO dto) {
		WebElement examEnter = d.findElement(By.xpath("//a[contains(text(),'已阅读完毕，确认进入考场')]"));
		examEnter.click();
		System.out.println("startExam end");
	}

	private void fillExam(WebDriver d, UnderWayMonthTestDTO dto) {
		System.out.println("fillExam start");
		String questionAndAnswerStr = ioUtil.getStringFromFile(questionAndAnswerFilePathStr);
		UnderWayExamFormDTO formDTO = buildObjFromJsonCustomization(questionAndAnswerStr, UnderWayExamFormDTO.class);

//		/html[1]/body[1]/div[2]/div[1]/div[2]/form[1]/div[1]/div[questionIndex]/p[1]
		for (Integer questionIndex = 1; questionIndex < 135; questionIndex++) {
			WebElement questionP = d.findElement(
					By.xpath("/html[1]/body[1]/div[2]/div[1]/div[2]/form[1]/div[1]/div[" + questionIndex + "]/p[1]"));
			String questionStr = questionP.getText();
			questionStr = questionStr.substring(questionStr.indexOf('、') + 1, questionStr.length());

			UnderWayTestQuestionAndAnswerSubDTO questionDTO = findQuestionSubDTO(formDTO, questionStr);

			List<String> answerList = questionDTO.getAnswer();

			if (questionIndex > 90) {
				System.out.println((questionIndex - 90) + ", ");
			} else if (questionIndex > 61) {
				System.out.println((questionIndex - 61) + ", ");
			} else {
				System.out.println(questionIndex + ", ");
			}
			for(String ans : answerList) {
				System.out.println(ans);
			}

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
							.findElement(By.xpath("/html[1]/body[1]/div[2]/div[1]/div[2]/form[1]/div[1]/div[" + questionIndex + "]/ul[1]/li[" + answerSelectorIndex + "]/input[1]"));
					String inputId = answerInput.getAttribute("id");
					

					if (answerList.contains(answerStr)) {
//						try {
//							WebElement targetInput = d.findElement(By.id(inputId));
//							targetInput.click();
//						} catch (Exception e) {
//							jsUtil.execute(d, "document.getElementById('"+inputId+"').checked = true;");
//						}
						jsUtil.execute(d, "document.getElementById('"+inputId+"').checked = true;");
						answerList.remove(answerStr);
					}
				} catch (NoSuchElementException noElement) {
					System.out.println("can NOT find Element :" + answerSelectorIndex + "for: " + answerStr);
					try {
						threadSleepRandomTime(9000L, 10000L);
					} catch (Exception e2) {
					}
					break answerSelectorTag;
				} catch (Exception e) {
				}
			}
			try {
//				threadSleepRandomTime(2000L, 3000L);
			} catch (Exception e2) {
			}
		}
	}

	private UnderWayTestQuestionAndAnswerSubDTO findQuestionSubDTO(UnderWayExamFormDTO formDTO, String question) {
		for (UnderWayTestQuestionAndAnswerSubDTO questionDTO : formDTO.getQuestionAndAnswerList()) {
			if (questionDTO.getQuestion().equals(question)) {
				return questionDTO;
			}
		}
		return null;
	}

	@SuppressWarnings("unused")
	private void answerCollect(WebDriver d, UnderWayMonthTestDTO dto) {
		d.get(dto.getAnswerFormUrl());

		try {
			auxTool.loadingCheck(d, "/html[1]/body[1]/div[2]/div[1]/div[2]/div[2]/h4[1]");
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
		ioUtil.byteToFile(json.toString().getBytes(), "/home/u2/bbt/tmp/underWayQuestionAndAnswer.json");
	}
}
