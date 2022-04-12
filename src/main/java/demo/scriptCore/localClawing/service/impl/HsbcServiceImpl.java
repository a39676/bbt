package demo.scriptCore.localClawing.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.localClawing.pojo.dto.HsbcOptionDTO;
import demo.scriptCore.localClawing.service.HsbcService;
import io.netty.util.internal.ThreadLocalRandom;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class HsbcServiceImpl extends AutomationTestCommonService implements HsbcService {

	private static String optionFilePath = "d:/home/u2/bbt/optionFile/tmp/hsbcOption.json";
	private static HsbcOptionDTO optionDTO = null;
	private String phone = null;
	private String idNumber = null;
	
	private HsbcOptionDTO loadOption() {
		FileUtilCustom ioUtil = new FileUtilCustom();
		String optionJsonStr = ioUtil.getStringFromFile(optionFilePath);

		optionDTO = new Gson().fromJson(optionJsonStr, HsbcOptionDTO.class);
		return optionDTO;
	}

	@Override
	public void weixinPreRegBatch() {
		optionDTO = loadOption();
		
		if(StringUtils.isBlank(optionDTO.getPhoneNumber())) {
			if (optionDTO.getMainlandPhoneFlag()) {
				phone = String.valueOf(11110000000L + ThreadLocalRandom.current().nextLong(1000000, 9000000 + 1));
			} else {
				phone = String.valueOf(optionDTO.getIndexNum());
			}
		} else {
			phone = optionDTO.getPhoneNumber();
		}

		if (optionDTO.getMainlandFlag()) {
			idNumber = optionDTO.getMainlandIdNumber().toString();
			weixinPreReg();
		} else {
			for (int i = 0; i < optionDTO.getStepLong(); i++) {
				idNumber = String.valueOf(optionDTO.getIndexNum() + i);
				weixinPreReg();
			}
		}
	}

	@Override
	public void weixinPreReg() {
		WebDriver d = webDriverService.buildChromeWebDriver();

		try {
			d.get(optionDTO.getMainUrl());

			welcomePage(d);

			threadSleepRandomTime();

			phoneInfoRecord(d);

			threadSleepRandomTime();

			selectBankBranch(d);

			threadSleepRandomTime();

			inputPersonalInfo(d);

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

			threadSleepRandomTimeLong();
			threadSleepRandomTimeLong();

			System.out.println("phone: " + phone + ", idNumber: " + idNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}

		d.quit();
	}

	private void welcomePage(WebDriver d) {
		WebElement accept1 = d.findElement(By.id("landing_accept_input"));
		accept1.click();
		WebElement accept2 = d.findElement(By.id("landing_accept_callback"));
		accept2.click();
		WebElement start = d.findElement(By.id("landing_start"));
		start.click();
	}

	private void phoneInfoRecord(WebDriver d) {
		String regionPath = xPathBuilder.start("div").addClass("help-block1 phone-block").findChild("select")
				.getXpath();
		WebElement regionEle = d.findElement(By.xpath(regionPath));
		Select regionSelector = new Select(regionEle);
		if (!optionDTO.getMainlandPhoneFlag()) {
//			regionSelector.selectByValue("object:65");
			regionSelector.selectByIndex(2); // 澳门
		}

		String phoneInputPath = xPathBuilder.start("div").addClass("help-block1 phone-block").findChild("input")
				.getXpath();
		WebElement phoneInput = d.findElement(By.xpath(phoneInputPath));
		phoneInput.click();
		phoneInput.clear();
		phoneInput.sendKeys(phone);

		try {
			threadSleepRandomTime();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String nextStepPath = xPathBuilder.start("div").addClass("fadeIn5").getXpath();
		WebElement nextStep = d.findElement(By.xpath(nextStepPath));
		nextStep.click();

		try {
			threadSleepRandomTimeLong();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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

	private void selectBankBranch(WebDriver d) {
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
		employIdInput.sendKeys(optionDTO.getStaffId());

		try {
			threadSleepRandomTime();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		WebElement continueButton = d.findElement(By.xpath("//button[contains(text(),'继续')]"));
		continueButton.click();

	}

	private void inputPersonalInfo(WebDriver d) {
		WebElement lastNameInput = d.findElement(By.xpath("//input[@id='lastName']"));
		lastNameInput.click();
		lastNameInput.clear();
		lastNameInput.sendKeys("测");

		WebElement firstNameInput = d.findElement(By.xpath("//input[@id='firstName']"));
		firstNameInput.click();
		firstNameInput.clear();
		firstNameInput.sendKeys("试");

		WebElement regionSelectEle = d.findElement(By.xpath("//select[@id='auto_nationality']"));
		Select regionSelector = new Select(regionSelectEle);
		if (optionDTO.getMainlandFlag()) {
			regionSelector.selectByIndex(0);
			WebElement idCardCreatorInput = d.findElement(
					By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/section[1]/div[1]/div[2]/div[28]/input[1]"));
			idCardCreatorInput.click();
			idCardCreatorInput.clear();
			idCardCreatorInput.sendKeys("发证机关");
		} else {
			regionSelector.selectByIndex(3);
		}

		WebElement idCardNumbersInput = d.findElement(By.xpath("//input[@id='idCardNumbers']"));
		idCardNumbersInput.click();
		idCardNumbersInput.clear();
		idCardNumbersInput.sendKeys(idNumber);

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

}
