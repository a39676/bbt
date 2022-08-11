package demo.scriptCore.localClawing.service.impl;

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

import demo.scriptCore.localClawing.service.TmpTaskService;
import demo.scriptCore.scheduleClawing.service.impl.BingDemoCommonService;

@Service
public class TmpTaskServiceImpl extends BingDemoCommonService implements TmpTaskService {

	private static List<String> targetUrlList = new ArrayList<>();
	private String mainSavingFloder = "d:/tmp/gantz";
	private String counterStr = null;

	static {
//		targetUrlList.add("https://www.maofly.com/manga/13916/430996.html");
//		targetUrlList.add("https://www.maofly.com/manga/13916/463121.html");
//		targetUrlList.add("https://www.maofly.com/manga/13916/464186.html");
//		targetUrlList.add("https://www.maofly.com/manga/13916/468918.html");
//		targetUrlList.add("https://www.maofly.com/manga/13916/469621.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/470246.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/471159.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/471925.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/472599.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/473367.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/474003.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/474285.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/474803.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/475057.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/475256.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/475454.html");
		targetUrlList.add("https://www.maofly.com/manga/13916/475662.html");
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
		Integer count = 16;
		
		url = targetUrlList.get(0);
		counterStr = String.format("%02d", count);
		savingFloder = mainSavingFloder + "/" + counterStr;
		try {
			wd1 = webDriverService.buildChromeWebDriver();
			wd1.get(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i = 1; i < targetUrlList.size(); i++) {
			url = targetUrlList.get(i);
			counterStr = String.format("%02d", count);
			savingFloder = mainSavingFloder + "/" + counterStr;
			try {
				wd2 = webDriverService.buildChromeWebDriver();
				wd2.get(url);
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
//		String loadallButtonXpath = "//body/div[1]/div[2]/nav[1]/div[1]/a[6]";
		String allComicImgXpath = xPathBuilder.setXpath("//body/div[1]/div[3]/div[1]/div[2]/div[1]").findChild("img")
				.getXpath();

//		if (!auxTool.loadingCheck(d, loadallButtonXpath)) {
//			return;
//		}

//		WebElement loadallButton = d.findElement(By.xpath(loadallButtonXpath));
//		loadallButton.click();

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
			FileUtils.copyURLToFile(new URL(urlStr), new File(targetFloder + "/" + filename + ".png"), 10000,
					10000);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				FileUtils.copyURLToFile(new URL(urlStr),
						new File(targetFloder + "/" + snowFlake.getNextId() + ".png"), 10000, 10000);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
}
