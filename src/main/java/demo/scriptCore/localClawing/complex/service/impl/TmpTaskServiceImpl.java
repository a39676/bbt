package demo.scriptCore.localClawing.complex.service.impl;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import demo.scriptCore.bingDemo.servcie.impl.BingDemoCommonService;
import demo.scriptCore.localClawing.complex.service.TmpTaskService;

@Service
public class TmpTaskServiceImpl extends BingDemoCommonService implements TmpTaskService {

	private static List<String> targetUrlList = new ArrayList<>();
	private String mainSavingFloder = "d:/tmp/gantz";
	private String counterStr = null;

	static {
		targetUrlList.add("https://www.maofly.com/manga/13916/475873.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/476026.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/476193.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/476329.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/476485.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/476485.html");
	}

	@Override
	public void t1() {

		WebDriver wd1 = null;
		WebDriver wd2 = null;
		String savingFloder = null;
		String url;
		Integer count = 33;
		String loadallButtonXpath = "//body/div[1]/div[2]/nav[1]/div[1]/a[6]";

		url = targetUrlList.get(0);
		counterStr = String.format("%02d", count);
		savingFloder = mainSavingFloder + "/" + counterStr;
		try {
			wd1 = webDriverService.buildChromeWebDriver();
			wd1.get(url);
		} catch (Exception e) {
			e.printStackTrace();
		}

		for (int i = 1; i < targetUrlList.size(); i++) {
			url = targetUrlList.get(i);
			counterStr = String.format("%02d", count);
			savingFloder = mainSavingFloder + "/" + counterStr;
			try {
				wd2 = webDriverService.buildChromeWebDriver();
				wd2.get(url);
				if (!loadingCheck(wd1, loadallButtonXpath)) {
					return;
				} else {
					WebElement loadallButton = wd1.findElement(By.xpath(loadallButtonXpath));
					loadallButton.click();
				}

				collectionImg(wd1, url, savingFloder);
			} catch (Exception e) {
				e.printStackTrace();
			}
			tryQuitWebDriver(wd1);
			count = count + 1;
			wd1 = wd2;
		}
	}

	private void collectionImg(WebDriver d, String url, String targetFolder) throws InterruptedException {
		String allComicImgXpath = xPathBuilder.setXpath("//body/div[1]/div[3]/div[1]/div[2]/div[1]").findChild("img")
				.getXpath();

		List<WebElement> comicImgList = d.findElements(By.xpath(allComicImgXpath));

		WebElement tmpImgEle = null;
		String tmpImgUrl = null;
		String filename = null;
		for (int i = 0; i < comicImgList.size(); i++) {
			tmpImgEle = comicImgList.get(i);
			tmpImgUrl = tmpImgEle.getAttribute("src");
			filename = String.format("%03d", i);
			download(tmpImgUrl, filename, targetFolder);
		}
	}

	private void download(String urlStr, String filename, String targetFloder) {
		System.out.println("download: " + urlStr + " filename: " + filename);
		if (StringUtils.isBlank(filename)) {
			filename = String.valueOf(snowFlake.getNextId());
		}
		filename = filename.trim();
		try {
			FileUtils.copyURLToFile(new URL(urlStr), new File(targetFloder + "/" + filename + ".png"), 10000, 10000);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				FileUtils.copyURLToFile(new URL(urlStr), new File(targetFloder + "/" + snowFlake.getNextId() + ".png"),
						10000, 10000);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
