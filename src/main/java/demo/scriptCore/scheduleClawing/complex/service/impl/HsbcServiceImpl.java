package demo.scriptCore.scheduleClawing.complex.service.impl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;

import autoTest.report.pojo.dto.JsonReportOfCaseDTO;
import autoTest.testEvent.common.pojo.result.AutomationTestCaseResult;
import autoTest.testEvent.common.pojo.type.AutomationTestFlowResultType;
import autoTest.testEvent.scheduleClawing.hsbc.pojo.dto.HsbcWechatPreregistDTO;
import autoTest.testEvent.scheduleClawing.hsbc.pojo.type.HsbcIdType;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.scriptCore.scheduleClawing.complex.service.HsbcService;
import demo.selenium.service.impl.AutomationTestCommonService;
import tool.pojo.type.InternationalityType;

@Service
public class HsbcServiceImpl extends AutomationTestCommonService implements HsbcService {

	@Override
	public TestEventBO weixinPreReg(TestEventBO tbo) {
		WebDriver d = null;
		ScheduleClawingType caseType = ScheduleClawingType.HSBC_WECHAT_PREREGIST;
		JsonReportOfCaseDTO caseReport = initCaseReportDTO(caseType.getFlowName());
		AutomationTestCaseResult r = initCaseResult(caseType.getFlowName());

		try {
			HsbcWechatPreregistDTO dto = buildTestEventParamFromJsonCustomization(tbo.getParamStr(), HsbcWechatPreregistDTO.class);
			
			if (dto == null) {
				reportService.caseReportAppendContent(caseReport, "读取参数异常");
				return tbo;
			}
			dto.setStaffId(String.valueOf(ThreadLocalRandom.current().nextLong(44000000L, 44999999L)));

			d = webDriverService.buildChromeWebDriver();

			d.get(dto.getMainUrl());

			welcomePage(d);

			threadSleepRandomTime();
			
			phoneInfoRecordPrefixPart(d, dto);
			
			threadSleepRandomTimeLong();
			
			if(phoneIsReuse(d)) {
				phoneReusePreregistFlow(d, dto);
			}
			
			threadSleepRandomTime();
			
			phoneInfoRecordSuffixPart(d, dto);

			threadSleepRandomTime();

			selectBankBranch(d, dto);

			threadSleepRandomTime();

			inputPersonalInfo(d, dto);

			threadSleepRandomTime();

			connections(d, dto);

			threadSleepRandomTime();

			jobInfos(d);

			threadSleepRandomTime();

			taxDeclaration(d);

			threadSleepRandomTime();

			tAndC(d);

			threadSleepRandomTime();

			confirm(d);
			
			try {
				threadSleepRandomTime();
				
				confirm(d);
				
				Thread.sleep(16000);
			} catch (Exception e) {
			}
			
			reportService.caseReportAppendContent(caseReport, "Done, " + localDateTimeHandler.dateToStr(LocalDateTime.now()));
			r.setResultType(AutomationTestFlowResultType.PASS);
			
		} catch (Exception e) {
			if(systemOptionService.isDev()) {
				e.printStackTrace();
			}
			reportService.caseReportAppendContent(caseReport, "异常: " + e.toString());
		}
		
		tbo.getCaseResultList().add(r);
		tbo.getReport().getCaseReportList().add(caseReport);
		if(!tryQuitWebDriver(d)) {
			sendingMsg("Web driver quit failed, " + caseType.getFlowName());
		}
		sendAutomationTestResult(tbo);

		return tbo;
	}
	
	private void phoneReusePreregistFlow(WebDriver d, HsbcWechatPreregistDTO dto) throws Exception {
		d.get(dto.getMainUrl());
		
		threadSleepRandomTime();
		
		welcomePageForPhoneReuse(d);
		
		threadSleepRandomTime();
		
		phoneInfoRecordPrefixPart(d, dto);
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
	
	private void phoneInfoRecordPrefixPart(WebDriver d, HsbcWechatPreregistDTO dto) throws Exception {
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
		
		if(webATToolService.alertExists(d)) {
			Alert alert = d.switchTo().alert();
			throw new Exception(alert.getText());
		}

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
		Double randomSms = ((Math.random() * (999999 - 100001)) + 100001);
		smsVerifyInput.sendKeys(String.valueOf(randomSms.intValue()));

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
	
	private void selectBankBranch(WebDriver d, HsbcWechatPreregistDTO dto) throws InterruptedException {
		
		String branchCitySelectorXpath = "//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[3]/div[1]/select[1]";
		String bankBranchSelectorXpath = "//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[5]/div[1]/select[1]";
		
		loadingCheck(d, branchCitySelectorXpath);
		threadSleepRandomTime();
		
		if(dto.getOpenAccountInLivingCity() && dto.getCityNameOfOpeningAccountBranch() != null) {
			selectorSelectByKeyword(d, branchCitySelectorXpath, dto.getCityNameOfOpeningAccountBranch());
		} else {
			selectorRandomSelect(d, branchCitySelectorXpath, 1, null);
		}

		threadSleepRandomTime();
		selectorRandomSelect(d, bankBranchSelectorXpath, 1, null);

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

	private void inputPersonalInfo(WebDriver d, HsbcWechatPreregistDTO dto) throws InterruptedException {
		loadingCheck(d, "//input[@id='lastName']");
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

		// createIdCardYearSelectEle
		selectorRandomSelect(d, "//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[29]/div[1]/span[1]/select[1]", 3, null);

		// createIdCardMonthSelectEle
		selectorRandomSelect(d, "//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[29]/div[1]/span[2]/select[1]", 1, null);

		// createIdCardDaySelectEle
		selectorRandomSelect(d, "//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[29]/div[1]/span[3]/select[1]", 1, null);

		// idCardValidYearSelector
		selectorRandomSelect(d, "//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[30]/div[1]/span[1]/select[1]", 1, 5);

		// idCardValidMonthSelector
		selectorRandomSelect(d, "//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[30]/div[1]/span[2]/select[1]", 1, null);

		// idCardValidDaySelector
		selectorRandomSelect(d, "//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[30]/div[1]/span[3]/select[1]", 1, null);
		
		if(HsbcIdType.MAIN_LAND_ID.getId().equals(dto.getIdType())) {
			WebElement femaleRadio = d.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[10]/div[2]/ul[1]/li[1]/label[1]"));
			WebElement maleRadio = d.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[10]/div[2]/ul[1]/li[2]/label[1]"));
			
			String idNum = dto.getIdNumber();
			String genderCodeStr = String.valueOf(idNum.charAt(idNum.length() - 2));
			try {
				int genderCode = Integer.parseInt(genderCodeStr);
				if(genderCode % 2 == 0) {
					femaleRadio.click();
				} else {
					maleRadio.click();
				}
			} catch (Exception e) {
			}
		}
		
		if(InternationalityType.CN.equals(areaType) && HsbcIdType.PASSPORT.getId().equals(dto.getIdType())) {
			WebElement otherIdNumberInput = d.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[34]/input[1]"));
			otherIdNumberInput.click();
			otherIdNumberInput.clear();
			Random random = new Random();
			otherIdNumberInput.sendKeys(String.valueOf(random.nextInt(999999 - 100000) + 100000));
			
			// otherIdCardValidYearSelectEle			
			selectorRandomSelect(d, "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[35]/div[1]/span[1]/select[1]", 1, null);			

			// otheridCardValidMonthSelectEle
			selectorRandomSelect(d, "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[35]/div[1]/span[2]/select[1]", 1, null);

			// otherIdCardValidDaySelectEle
			selectorRandomSelect(d, "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[35]/div[1]/span[3]/select[1]", 1, null);
			
			// otherIdCardCertificateIssuingAgencySelectEle			
			selectorRandomSelect(d, "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[37]/div[2]/select[1]", 1, null);			
			
		}

		WebElement continueButton = d.findElement(By.xpath("//button[contains(text(),'继续')]"));
		continueButton.click();
	}

	private void connections(WebDriver d, HsbcWechatPreregistDTO dto) throws InterruptedException {
		String provinceSelectorXpath = "//select[@id='auto_contactProvince']";
		String citySelectorXpath = "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[3]/div[1]/div[2]/select[2]";
		String districtSelectorXpath = "//select[@id='auto_contactDistrict']";
		
		loadingCheck(d, provinceSelectorXpath);

		if(dto.getOpenAccountInLivingCity()) {
			String provinceName = findProvinceNameByCityName(dto);
			
			selectorSelectByKeyword(d, provinceSelectorXpath, provinceName);
			threadSleepRandomTime();
			selectorSelectByKeyword(d, citySelectorXpath, dto.getCityNameOfOpeningAccountBranch());
			threadSleepRandomTime();
			selectorRandomSelect(d, districtSelectorXpath, 1, null);
			
		} else {
			selectorRandomSelect(d, provinceSelectorXpath, 1, 10);
			threadSleepRandomTime();
			selectorRandomSelect(d, citySelectorXpath, 1, null);
			threadSleepRandomTime();
			selectorRandomSelect(d, districtSelectorXpath, 1, null);
		}
		

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

//		addressSinceYearSelectEle
		selectorRandomSelect(d, "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[6]/select[1]", 5, 10);
//		addressSinceMonthSelectEle
		selectorRandomSelect(d, "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[6]/select[2]", 5, 10);
		threadSleepRandomTime();
//		addressSinceDaySelectEle
		selectorRandomSelect(d, "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[6]/select[3]", 5, 10);


		WebElement continueButton = d.findElement(By.xpath(
				"/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[9]/div[1]/div[1]/button[1]"));
		continueButton.click();
	}
	
	private String findProvinceNameByCityName(HsbcWechatPreregistDTO dto) {
		if(dto.getProvinceRegionMap() == null) {
			return null;
		}
		String inputCityname = dto.getCityNameOfOpeningAccountBranch();
		for(Entry<String, List<String>> entry : dto.getProvinceRegionMap().entrySet()) {
			List<String> citynameList = entry.getValue();
			for(String cityname : citynameList) {
				if(cityname.contains(inputCityname)) {
					return entry.getKey();
				}
			}
		}
		return null;
	}

	private void jobInfos(WebDriver d) throws InterruptedException {
		loadingCheck(d, "//body/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[2]/div[2]/select[1]");
		
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

	private void taxDeclaration(WebDriver d) throws InterruptedException {
		loadingCheck(d, "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[2]/div[1]/div[4]/div[1]/div[1]/label[1]");
		
		WebElement declarationCheckbox = d.findElement(By.xpath(
				"/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[2]/div[2]/div[1]/div[4]/div[1]/div[1]/label[1]"));
		declarationCheckbox.click();

		tryClickAlert(d);

		WebElement continueButton = d.findElement(By.xpath(
				"/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[3]/div[1]/div[1]/div[1]/button[1]"));
		continueButton.click();

		tryClickAlert(d);
	}

	private void tAndC(WebDriver d) throws InterruptedException {
		loadingCheck(d, "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[5]/input[1]");
		
		WebElement declarationCheckbox = d
				.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[5]/input[1]"));
		declarationCheckbox.click();

		tryClickAlert(d);

		WebElement continueButton = d
				.findElement(By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[4]/div[1]/button[1]"));
		continueButton.click();

		tryClickAlert(d);
	}

	private void confirm(WebDriver d) throws InterruptedException {

		tryClickAlert(d);

		loadingCheck(d, "/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[3]/div[3]/div[1]/button[1]");
		
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
