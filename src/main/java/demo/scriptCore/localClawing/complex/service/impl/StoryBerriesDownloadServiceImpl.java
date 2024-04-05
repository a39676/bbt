package demo.scriptCore.localClawing.complex.service.impl;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import demo.scriptCore.localClawing.complex.pojo.dto.StoryBerriesOptionDTO;
import demo.scriptCore.localClawing.complex.service.StoryBerriesDownloadService;
import demo.selenium.service.impl.AutomationTestCommonService;

@Service
public class StoryBerriesDownloadServiceImpl extends AutomationTestCommonService
		implements StoryBerriesDownloadService {

	private static String mainSavingFolderPath = "d:/tmp/englishBook";
	private static StoryBerriesOptionDTO optionDTO = null;

	@Override
	public void downloading(String url) {
		loadOption(url);

		download();
	}

	private StoryBerriesOptionDTO loadOption(String url) {
		optionDTO = new StoryBerriesOptionDTO();
		optionDTO.setTargetUrl(url);
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

			WebElement bottomSpan = d
					.findElement(By.xpath("/html[1]/body[1]/div[1]/footer[1]/div[1]/div[1]/div[1]/span[1]"));

			while (!jsUtil.isVisibleInViewport(bottomSpan)) {
				d.findElement(By.xpath("//body")).sendKeys(Keys.PAGE_DOWN);
				d.findElement(By.xpath("//body")).sendKeys(Keys.PAGE_DOWN);
				d.findElement(By.xpath("//body")).sendKeys(Keys.PAGE_DOWN);
				Thread.sleep(800L);
			}

			int pageIndex = 0;
			try {
				WebElement headerImg1 = d.findElement(By.xpath(
						"/html[1]/body[1]/div[1]/div[1]/div[2]/main[1]/div[1]/div[1]/article[1]/header[1]/div[1]/div[4]/figure[1]/img[1]"));
				webATToolService.saveImg(headerImg1, String.valueOf(pageIndex), savingFolderPath);
				pageIndex++;
			} catch (Exception e) {
				e.printStackTrace();
			}

			try {
				WebElement headerImg2 = d.findElement(By.xpath(
						"/html[1]/body[1]/div[1]/div[1]/div[2]/main[1]/div[1]/div[1]/article[1]/div[1]/div[1]/div[3]/figure[1]/div[1]"));
				webATToolService.saveImg(headerImg2, String.valueOf(pageIndex), savingFolderPath);
				pageIndex++;
			} catch (Exception e) {
				e.printStackTrace();
			}

			List<WebElement> imgList = new ArrayList<>();
			try {
				imgList.addAll(d.findElements(By.xpath(
						"/html[1]/body[1]/div[1]/div[1]/div[2]/main[1]/div[1]/div[1]/article[1]/div[1]/div[1]/p/img")));
			} catch (Exception e) {
			}

			WebElement tmpImg = null;
			for (int i = 0; i < imgList.size(); i++) {
				tmpImg = imgList.get(i);
				try {
					System.out.println(tmpImg.getAttribute("src"));
					webATToolService.saveImg(tmpImg, String.valueOf(pageIndex), savingFolderPath);
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

}
