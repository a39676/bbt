package demo.scriptCore.localClawing.complex.service.impl;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.scriptCore.localClawing.complex.pojo.dto.StoryBerriesOptionDTO;
import demo.scriptCore.localClawing.complex.service.StoryBerriesDownloadService;
import demo.selenium.service.impl.AutomationTestCommonService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class StoryBerriesDownloadServiceImpl extends AutomationTestCommonService
		implements StoryBerriesDownloadService {

	private static String optionFilePath = "d:/home/u2/bbt/optionFile/tmp/storyBerries.json";
	private static String mainSavingFolderPath = "d:/tmp/englishBook";
	private static StoryBerriesOptionDTO optionDTO = null;

	@Override
	public void downloading() {
		loadOption();

		download();
	}

	private StoryBerriesOptionDTO loadOption() {
		FileUtilCustom ioUtil = new FileUtilCustom();
		String optionJsonStr = ioUtil.getStringFromFile(optionFilePath);

		optionDTO = new Gson().fromJson(optionJsonStr, StoryBerriesOptionDTO.class);

		return optionDTO;
	}

	private void download() {
		WebDriver d = webDriverService.buildChromeWebDriver();
		try {
			d.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

			d.get(optionDTO.getTargetUrl());

			String title = d.getTitle();
			title = title.substring(0, title.indexOf("|"));
			title = title.replaceAll("[^0-9A-Za-z_]", "");
			String savingFolderPath = mainSavingFolderPath + "/" + title;
			File savingFolder = new File(savingFolderPath);
			if (!savingFolder.exists()) {
				if (!savingFolder.mkdirs()) {
					log.error("Can NOT create saving folder: " + savingFolderPath);
					return;
				}
			}

			loadingCheck(d,
					"/html[1]/body[1]/div[1]/div[1]/div[2]/main[1]/div[1]/div[1]/article[1]/header[1]/div[1]/div[4]/figure[1]/img[1]");

			for (int i = 0; i < 5; i++) {
				d.findElement(By.xpath("//body")).sendKeys(Keys.PAGE_DOWN);
				d.findElement(By.xpath("//body")).sendKeys(Keys.PAGE_DOWN);
				Thread.sleep(2000L);
			}

			int pageIndex = 0;
			try {
				WebElement headerImg1 = d.findElement(By.xpath(
						"/html[1]/body[1]/div[1]/div[1]/div[2]/main[1]/div[1]/div[1]/article[1]/header[1]/div[1]/div[4]/figure[1]/img[1]"));
				webATToolService.saveImg(headerImg1, (pageIndex + ".jpg"), savingFolderPath);
				pageIndex++;
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				WebElement headerImg2 = d.findElement(By.xpath(
						"/html[1]/body[1]/div[1]/div[1]/div[2]/main[1]/div[1]/div[1]/article[1]/div[1]/div[1]/div[3]/figure[1]/div[1]"));
				webATToolService.saveImg(headerImg2, (pageIndex + ".jpg"), savingFolderPath);
				pageIndex++;
			} catch (Exception e) {
				e.printStackTrace();
			}

			List<WebElement> imgList = d.findElements(By.xpath(
					"/html[1]/body[1]/div[1]/div[1]/div[2]/main[1]/div[1]/div[1]/article[1]/div[1]/div[1]/p/img[1]"));

			String urlModel = findUrlModel(imgList);

			WebElement tmpImg = imgList.get(0);
			webATToolService.saveImg(tmpImg, (pageIndex + ".jpg"), savingFolderPath);
			pageIndex++;

			for (int i = 0; i < imgList.size(); i++) {
				String src = String.format(urlModel, i);
				try {
					webATToolService.saveImg(src, (pageIndex + ".jpg"), savingFolderPath);
					pageIndex++;
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		tryQuitWebDriver(d);
	}

	private String findUrlModel(List<WebElement> imgList) {
		WebElement tmpImg1 = imgList.get(1);
		WebElement tmpImg2 = imgList.get(2);

		String src1 = tmpImg1.getAttribute("src");
		String src2 = tmpImg2.getAttribute("src");

		int targetIndex = 0;
		for (int i = src1.length(); i > 0 && targetIndex == 0; i--) {
			if (!String.valueOf(src1.charAt(i - 1)).equals(String.valueOf(src2.charAt(i - 1)))) {
				targetIndex = i;
			}
		}

		src1 = src1.substring(0, targetIndex - 1) + "%d" + src1.substring(targetIndex, src1.length());

		return src1;
	}

}
