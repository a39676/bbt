package demo.scriptCore.localClawing.service.impl;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.localClawing.pojo.dto.PrankOptionDTO;
import demo.scriptCore.localClawing.service.PrankService;
import io.netty.util.internal.ThreadLocalRandom;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class PrankServiceImpl extends AutomationTestCommonService implements PrankService {

	private static String optionFilePath = "d:/home/u2/bbt/optionFile/tmp/prankOption.json";
	private static PrankOptionDTO optionDTO = null;

	private PrankOptionDTO loadOption() {
		FileUtilCustom ioUtil = new FileUtilCustom();
		String optionJsonStr = ioUtil.getStringFromFile(optionFilePath);

		optionDTO = new Gson().fromJson(optionJsonStr, PrankOptionDTO.class);
		return optionDTO;
	}

	@Override
	public void prankBatch() {
		loadOption();

		for (int i = 0; i < 3; i++) {
			prank();
		}
	}

	private void prank() {
		WebDriver d = webDriverService.buildChromeWebDriver();
		try {

			d.get(optionDTO.getMainUrl());

			WebElement cnNameInput = d
					.findElement(By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[1]/div[2]/input[1]"));
			cnNameInput.click();
			cnNameInput.clear();
			cnNameInput.sendKeys(createRandomName());

			WebElement idInput = d
					.findElement(By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[2]/div[2]/input[1]"));
			idInput.click();
			idInput.clear();
			idInput.sendKeys(createRandomId());

			WebElement dateInput = d
					.findElement(By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[3]/div[2]/input[1]"));
			dateInput.click();

			todaySelector(d);

			locationRandomSelect(d);

			remoteWorkPerpareRandomSelect(d);

			alreadyInWorkCityRandomSelect(d);

			healthCodeRandomSelect(d);

			isolationRandomSelect(d);

			WebElement submitButton = d
					.findElement(By.xpath("/html[1]/body[1]/form[1]/div[5]/div[9]/div[3]/div[1]/div[1]/div[1]"));
			submitButton.click();

			threadSleepRandomTime();
		} catch (Exception e) {
			e.printStackTrace();
		}

		d.quit();
	}

	private String createRandomName() {
		int ranLastnameIndex = ThreadLocalRandom.current().nextInt(0, optionDTO.getLastname().size());
		int ranFirstnameIndex = ThreadLocalRandom.current().nextInt(0, optionDTO.getFirstname().size());
		return optionDTO.getLastname().get(ranLastnameIndex) + optionDTO.getFirstname().get(ranFirstnameIndex);
	}

	private String createRandomId() {
		return String.valueOf(ThreadLocalRandom.current().nextInt(optionDTO.getMinId(), optionDTO.getMaxId()));
	}

	private void todaySelector(WebDriver d) {
		d.switchTo().frame("div__calendarIframe");

		WebElement todayInput = d
				.findElement(By.xpath("/html[1]/body[1]/form[1]/table[1]/tbody[1]/tr[9]/th[2]/input[1]"));
		todayInput.click();

		d.switchTo().parentFrame();
		d.switchTo().defaultContent();
	}

	private void locationRandomSelect(WebDriver d) {
		if (ThreadLocalRandom.current().nextInt(0, 2) > 0) {
			WebElement locationSelect = d.findElement(
					By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[4]/div[2]/div[1]/div[1]"));
			locationSelect.click();
		} else {
			WebElement locationSelect = d.findElement(
					By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[4]/div[2]/div[2]/div[1]"));
			locationSelect.click();
		}
	}

	private void remoteWorkPerpareRandomSelect(WebDriver d) {
		if (ThreadLocalRandom.current().nextInt(0, 2) > 0) {
			WebElement locationSelect = d.findElement(
					By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[5]/div[2]/div[1]/div[1]"));
			locationSelect.click();
		} else {
			WebElement locationSelect = d.findElement(
					By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[5]/div[2]/div[2]/div[1]"));
			locationSelect.click();
		}
	}

	private void alreadyInWorkCityRandomSelect(WebDriver d) {
		if (ThreadLocalRandom.current().nextInt(0, 2) > 0) {
			WebElement locationSelect = d.findElement(
					By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[6]/div[2]/div[1]/div[1]"));
			locationSelect.click();
		} else {
			WebElement locationSelect = d.findElement(
					By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[6]/div[2]/div[2]/div[1]"));
			locationSelect.click();
		}
	}

	private void healthCodeRandomSelect(WebDriver d) {
		int random = ThreadLocalRandom.current().nextInt(0, 3);
		if (random == 0) {
			WebElement locationSelect = d.findElement(
					By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[8]/div[2]/div[1]/div[1]"));
			locationSelect.click();
		} else if (random == 1) {
			WebElement locationSelect = d.findElement(
					By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[8]/div[2]/div[2]/div[1]"));
			locationSelect.click();
		} else {
			WebElement locationSelect = d.findElement(
					By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[8]/div[2]/div[3]/div[1]"));
			locationSelect.click();
		}
	}

	private void isolationRandomSelect(WebDriver d) {
		if (ThreadLocalRandom.current().nextInt(0, 2) > 0) {
			WebElement locationSelect = d.findElement(
					By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[9]/div[2]/div[1]/div[1]"));
			locationSelect.click();
		} else {
			WebElement locationSelect = d.findElement(
					By.xpath("/html[1]/body[1]/form[1]/div[5]/div[4]/fieldset[1]/div[9]/div[2]/div[2]/div[1]"));
			locationSelect.click();
		}
	}

}
