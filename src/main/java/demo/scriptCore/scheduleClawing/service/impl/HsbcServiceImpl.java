package demo.scriptCore.scheduleClawing.service.impl;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testEvent.searchingDemo.pojo.dto.HsbcWechatPreregistDTO;
import autoTest.testEvent.searchingDemo.pojo.type.HsbcIdType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.scheduleClawing.service.HsbcService;
import tool.pojo.type.InternationalityType;

@Service
public class HsbcServiceImpl extends AutomationTestCommonService implements HsbcService {

	@Override
	public TestEventBO weixinPreReg(TestEventBO tbo) {
		WebDriver d = null;
		ScheduleClawingType caseType = ScheduleClawingType.HSBC_WECHAT_PREREGIST;
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(caseType.getFlowName());

		try {
			HsbcWechatPreregistDTO dto = auxTool.buildParamDTO(tbo, HsbcWechatPreregistDTO.class);
			if (dto == null) {
				reportService.caseReportAppendContent(caseReport, "读取参数异常");
				return tbo;
			}

			d = webDriverService.buildChromeWebDriver();

			d.get(dto.getMainUrl());

			welcomePage(d);

			threadSleepRandomTime();
			
			phoneInfoRecordPrefixPart(d, dto);
			
			threadSleepRandomTimeLong();
			
			if(phoneIsReuse(d)) {
				phoneReusePreregistFlow(d, dto);
			} else {
				normalPreregistFlow(d, dto);
			}

		} catch (Exception e) {
			reportService.caseReportAppendContent(caseReport, "异常: " + e.toString());

		} finally {
			tryQuitWebDriver(d);
			sendAutomationTestResult(tbo);
		}

		return tbo;
	}
	
	private void normalPreregistFlow(WebDriver d, HsbcWechatPreregistDTO dto) throws InterruptedException {
		
		phoneInfoRecordSuffixPart(d, dto);

		threadSleepRandomTime();

		selectBankBranch(d, dto);

		threadSleepRandomTime();

		inputPersonalInfo(d, dto);

		threadSleepRandomTime();

		connections(d);

		threadSleepRandomTime();

		jobInfos(d);

		threadSleepRandomTime();

		taxDeclaration(d);

		threadSleepRandomTime();

		tAndC(d);

		threadSleepRandomTime();

		confirm(d);
		
		threadSleepRandomTime();

		confirm(d);

		Thread.sleep(16000);
	}
	
	private void phoneReusePreregistFlow(WebDriver d, HsbcWechatPreregistDTO dto) throws InterruptedException {

		d.get(dto.getMainUrl());
		
		threadSleepRandomTime();
		
		welcomePageForPhoneReuse(d);
		
		phoneInfoRecordPrefixPart(d, dto);
		
		threadSleepRandomTime();
		
		phoneInfoRecordSuffixPart(d, dto);

		threadSleepRandomTime();

		selectBankBranch(d, dto);

		threadSleepRandomTime();

		inputPersonalInfo(d, dto);

		threadSleepRandomTime();

		connections(d);

		threadSleepRandomTime();

		jobInfos(d);

		threadSleepRandomTime();

		taxDeclaration(d);

		threadSleepRandomTime();

		tAndC(d);

		threadSleepRandomTime();

		confirm(d);
		
		threadSleepRandomTime();

		confirm(d);

		Thread.sleep(16000);
	}

	private void welcomePage(WebDriver d) {
		WebElement accept1 = d.findElement(By.id("landing_accept_input"));
		accept1.click();
		WebElement accept2 = d.findElement(By.id("landing_accept_callback"));
		accept2.click();
		WebElement start = d.findElement(By.id("landing_start"));
		start.click();
	}
	
	private void welcomePageForPhoneReuse(WebDriver d) {
		WebElement accept1 = d.findElement(By.id("landing_accept_input"));
		accept1.click();
		WebElement accept2 = d.findElement(By.id("landing_accept_callback"));
		accept2.click();
		WebElement start = d.findElement(By.id("landing_retrieve"));
		start.click();
	}
	
	private void phoneInfoRecordPrefixPart(WebDriver d, HsbcWechatPreregistDTO dto) {
		WebElement regionEle = d.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[4]/div[1]/select[1]"));
		Select regionSelector = new Select(regionEle);
		InternationalityType areaType = InternationalityType.getType(dto.getPhoneAreaType(), dto.getPhoneAreaName());
		int i = findTargetOptionIndex(d, "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[4]/div[1]/select[1]/option", areaType.getCnName());
		if(i == -1) {
			i = 0;
		}
		regionSelector.selectByIndex(i);

		String phoneInputPath = xPathBuilder.start("div").addClass("help-block1 phone-block").findChild("input")
				.getXpath();
		WebElement phoneInput = d.findElement(By.xpath(phoneInputPath));
		phoneInput.click();
		phoneInput.clear();
		phoneInput.sendKeys(dto.getPhoneNumber());
		
		String nextStepPath = xPathBuilder.start("div").addClass("fadeIn5").getXpath();
		WebElement nextStep = d.findElement(By.xpath(nextStepPath));
		nextStep.click();

	}
	
	private boolean phoneIsReuse(WebDriver d) {

		try {
			WebElement reuseReminder = d.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[7]/div[1]"));
			return reuseReminder.isDisplayed();
		} catch (Exception e) {
			return false;
		}
	}

	private void phoneInfoRecordSuffixPart(WebDriver d, HsbcWechatPreregistDTO dto) {
		String smsVerifyPath = xPathBuilder.start("div").addClass("account-tile__inner verifiDive").findChild("div", 1)
				.findChild("div", 1).findChild("div", 1).findChild("input").addType("tel").getXpath();
		WebElement smsVerifyInput = d.findElement(By.xpath(smsVerifyPath));
		smsVerifyInput.click();
		smsVerifyInput.clear();
		smsVerifyInput.sendKeys("456789");

		try {
			threadSleepRandomTime();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String buttonXpath = xPathBuilder.start("button").getXpath();
		List<WebElement> buttons = d.findElements(By.xpath(buttonXpath));
		for (WebElement button : buttons) {
			if (button.getAttribute("class").equals("btn verify ng-binding")) {
				button.click();
				return;
			}
		}

	}
	
	private void selectBankBranch(WebDriver d, HsbcWechatPreregistDTO dto) {
		String branchSelectPath = "//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[3]/div[1]/select[1]";
		WebElement branchSelectorEle = d.findElement(By.xpath(branchSelectPath));
		Select branchSelector = new Select(branchSelectorEle);
		branchSelector.selectByIndex(8); // 哈尔滨

		String branchSelectPath2 = "//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[5]/div[1]/select[1]";
		WebElement branchSelectorEle2 = d.findElement(By.xpath(branchSelectPath2));
		Select branchSelector2 = new Select(branchSelectorEle2);
		branchSelector2.selectByIndex(1); // 哈尔滨分行

		String employIdInputPath = "//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[7]/div[1]/input[2]";
		WebElement employIdInput = d.findElement(By.xpath(employIdInputPath));
		employIdInput.click();
		employIdInput.clear();
		employIdInput.sendKeys(dto.getStaffId());

		try {
			threadSleepRandomTime();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement continueButton = d.findElement(By.xpath("//button[contains(text(),'继续')]"));
		continueButton.click();

	}

	private void inputPersonalInfo(WebDriver d, HsbcWechatPreregistDTO dto) {
		WebElement lastNameInput = d.findElement(By.xpath("//input[@id='lastName']"));
		lastNameInput.click();
		lastNameInput.clear();
		lastNameInput.sendKeys(dto.getCustomerLastName());

		WebElement firstNameInput = d.findElement(By.xpath("//input[@id='firstName']"));
		firstNameInput.click();
		firstNameInput.clear();
		firstNameInput.sendKeys(dto.getCustomerFirstName());
		
		WebElement regionSelectEle = d.findElement(By.xpath("//select[@id='auto_nationality']"));
		Select regionSelector = new Select(regionSelectEle);
		
		InternationalityType areaType = InternationalityType.getType(dto.getAreaType(), dto.getAreaName());
		int i = findTargetOptionIndex(d, "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[12]/select[1]/option", areaType.getCnName());
		if(i == -1) {
			i = 0;
		}
		regionSelector.selectByIndex(i);
		
		WebElement certificateTypeSelectEle = d.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[17]/div[2]/select[1]"));
		Select certificateTypeSelector = new Select(certificateTypeSelectEle);
		
		HsbcIdType idType = HsbcIdType.getType(dto.getIdType());
		i = findTargetOptionIndex(d, "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[17]/div[2]/select[1]/option", idType.getCnName());
		if(i == -1) {
			i = 0;
		}
		certificateTypeSelector.selectByIndex(i);
		
		WebElement idCardCreatorInput = d.findElement(
				By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[28]/input[1]"));
		idCardCreatorInput.click();
		idCardCreatorInput.clear();
		idCardCreatorInput.sendKeys("发证机关");
		
		WebElement idCardNumbersInput = d.findElement(By.xpath("//input[@id='idCardNumbers']"));
		idCardNumbersInput.click();
		idCardNumbersInput.clear();
		idCardNumbersInput.sendKeys(dto.getIdNumber());

		WebElement createIdCardYearSelectEle = d.findElement(By
				.xpath("//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[29]/div[1]/span[1]/select[1]"));
		Select createIdCardYearSelector = new Select(createIdCardYearSelectEle);
		createIdCardYearSelector.selectByValue("number:2015");

		WebElement createIdCardMonthSelectEle = d.findElement(By
				.xpath("//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[29]/div[1]/span[2]/select[1]"));
		Select createIdCardMonthSelector = new Select(createIdCardMonthSelectEle);
		createIdCardMonthSelector.selectByValue("string:03");

		WebElement createIdCardDaySelectEle = d.findElement(By
				.xpath("//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[29]/div[1]/span[3]/select[1]"));
		Select createIdCardDaySelector = new Select(createIdCardDaySelectEle);
		createIdCardDaySelector.selectByValue("string:03");

		WebElement idCardValidYearSelectEle = d.findElement(By
				.xpath("//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[30]/div[1]/span[1]/select[1]"));
		Select idCardValidYearSelector = new Select(idCardValidYearSelectEle);
		idCardValidYearSelector.selectByIndex(3);

		WebElement idCardValidMonthSelectEle = d.findElement(By
				.xpath("//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[30]/div[1]/span[2]/select[1]"));
		Select idCardValidMonthSelector = new Select(idCardValidMonthSelectEle);
		idCardValidMonthSelector.selectByValue("string:03");

		WebElement idCardValidDaySelectEle = d.findElement(By
				.xpath("//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[30]/div[1]/span[3]/select[1]"));
		Select idCardValidDaySelector = new Select(idCardValidDaySelectEle);
		idCardValidDaySelector.selectByValue("string:03");
		
		if(InternationalityType.CN.equals(areaType) && HsbcIdType.PASSPORT.getId().equals(dto.getIdType())) {
			WebElement otherIdNumberInput = d.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[34]/input[1]"));
			otherIdNumberInput.click();
			otherIdNumberInput.clear();
			otherIdNumberInput.sendKeys("123456");
			
			WebElement otherIdCardValidYearSelectEle = d.findElement(By
					.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[35]/div[1]/span[1]/select[1]"));
			Select otherIdCardValidYearSelector = new Select(otherIdCardValidYearSelectEle);
			otherIdCardValidYearSelector.selectByIndex(2);

			WebElement otheridCardValidMonthSelectEle = d.findElement(By
					.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[35]/div[1]/span[2]/select[1]"));
			Select otherIdCardValidMonthSelector = new Select(otheridCardValidMonthSelectEle);
			otherIdCardValidMonthSelector.selectByIndex(2);

			WebElement otherIdCardValidDaySelectEle = d.findElement(By
					.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[35]/div[1]/span[3]/select[1]"));
			Select otherIdCardValidDaySelector = new Select(otherIdCardValidDaySelectEle);
			otherIdCardValidDaySelector.selectByIndex(2);
			
			WebElement otherIdCardCertificateIssuingAgencySelectEle = d.findElement(By
					.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[37]/div[2]/select[1]"));
			Select otherIdCardCertificateIssuingAgencySelector = new Select(otherIdCardCertificateIssuingAgencySelectEle);
			otherIdCardCertificateIssuingAgencySelector.selectByIndex(2);
			
		}

		WebElement continueButton = d.findElement(By.xpath("//button[contains(text(),'继续')]"));
		continueButton.click();
	}

	private void connections(WebDriver d) {

		WebElement provinceSelectEle = d.findElement(By.xpath("//select[@id='auto_contactProvince']"));
		Select provinceSelector = new Select(provinceSelectEle);
		provinceSelector.selectByIndex(4);

		try {
			threadSleepRandomTime();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement regionSelectEle = d.findElement(By.xpath(
				"/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[3]/div[1]/div[2]/select[2]"));
		Select regionSelector = new Select(regionSelectEle);
		regionSelector.selectByIndex(2);

		try {
			threadSleepRandomTime();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement regionSelectEle2 = d.findElement(By.xpath("//select[@id='auto_contactDistrict']"));
		Select regionSelector2 = new Select(regionSelectEle2);
		regionSelector2.selectByIndex(3);

		WebElement addressInput = d.findElement(By.xpath("//input[@id='auto_conAddress']"));
		addressInput.click();
		addressInput.clear();
		addressInput.sendKeys("测试地址");

		WebElement emailInput = d
				.findElement(By.xpath("//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[3]/div[3]/input[1]"));
		emailInput.click();
		emailInput.clear();
		emailInput.sendKeys("abc@test.com");

		WebElement addressCheckbox = d.findElement(
				By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[5]/ul[1]/li[1]"));
		addressCheckbox.click();

		WebElement addressSinceYearSelectEle = d.findElement(
				By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[6]/select[1]"));
		Select addressSinceYearSelector = new Select(addressSinceYearSelectEle);
		addressSinceYearSelector.selectByIndex(13);
		WebElement addressSinceMonthSelectEle = d.findElement(
				By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[6]/select[2]"));
		Select addressSinceMonthSelector = new Select(addressSinceMonthSelectEle);
		addressSinceMonthSelector.selectByIndex(3);

		try {
			threadSleepRandomTime();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement addressSinceDaySelectEle = d.findElement(
				By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[6]/select[3]"));
		Select addressSinceDaySelector = new Select(addressSinceDaySelectEle);
		addressSinceDaySelector.selectByIndex(3);

		WebElement continueButton = d.findElement(By.xpath(
				"/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[9]/div[1]/div[1]/button[1]"));
		continueButton.click();
	}

	private void jobInfos(WebDriver d) {
		WebElement jobSelectEle = d
				.findElement(By.xpath("//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[2]/div[2]/select[1]"));
		Select provinceSelector = new Select(jobSelectEle);
		provinceSelector.selectByIndex(4); // 家庭主妇

		WebElement salarySelectEle = d
				.findElement(By.xpath("//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[2]/div[8]/select[1]"));
		Select salarySelector = new Select(salarySelectEle);
		salarySelector.selectByIndex(10);

		WebElement continueButton = d.findElement(By.xpath(
				"/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[2]/div[9]/div[1]/button[1]"));
		continueButton.click();

	}

	private void taxDeclaration(WebDriver d) {

		WebElement declarationCheckbox = d.findElement(By.xpath(
				"/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[2]/div[1]/div[4]/div[1]/div[1]/label[1]"));
		declarationCheckbox.click();

		tryClickAlert(d);

		WebElement continueButton = d.findElement(By.xpath(
				"/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[3]/div[1]/div[1]/div[1]/button[1]"));
		continueButton.click();

		tryClickAlert(d);
	}

	private void tAndC(WebDriver d) {

		WebElement declarationCheckbox = d
				.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[5]/input[1]"));
		declarationCheckbox.click();

		tryClickAlert(d);

		WebElement continueButton = d
				.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[4]/div[1]/button[1]"));
		continueButton.click();

		tryClickAlert(d);
	}

	private void confirm(WebDriver d) {

		tryClickAlert(d);

		WebElement confirmButton = d.findElement(By.xpath(
				"/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[3]/div[3]/div[1]/button[1]"));
		confirmButton.click();

		tryClickAlert(d);
	}

	private void tryClickAlert(WebDriver d) {
		if (webATToolService.alertExists(d)) {
			try {
				d.switchTo().alert().accept();
				threadSleepRandomTime();
			} catch (Exception e) {
			}
		}
	}
	
	private int findTargetOptionIndex(WebDriver d, String optionXpath, String keyword) {
		List<WebElement> optionList = d.findElements(By.xpath(optionXpath));
		int i = 0;
		boolean findout = false;
		WebElement ele = null;
		String attrStr = null;
		for(; i < optionList.size() && !findout; i++) {
			ele = optionList.get(i);
			attrStr = ele.getAttribute("label");
			if(attrStr != null && attrStr.contains(keyword)) {
				return i;
			}
		}
		return -1;
	}

}
