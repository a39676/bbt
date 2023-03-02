package demo.scriptCore.localClawing.complex.service.impl;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.localClawing.complex.pojo.dto.TextbookDownloadOptionDTO;
import demo.scriptCore.localClawing.complex.pojo.dto.TextbookDownloadSubOption;
import demo.scriptCore.localClawing.complex.service.TextbookDownloadService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class TextbookDownloadServiceImpl extends AutomationTestCommonService implements TextbookDownloadService {

	private static String optionFilePath = "d:/home/u2/bbt/optionFile/tmp/textBookDownloadOption.json";
	private static TextbookDownloadOptionDTO optionDTO = null;

	private TextbookDownloadOptionDTO loadOption() {
		FileUtilCustom ioUtil = new FileUtilCustom();
		String optionJsonStr = ioUtil.getStringFromFile(optionFilePath);

		optionDTO = new Gson().fromJson(optionJsonStr, TextbookDownloadOptionDTO.class);

		return optionDTO;
	}

	@Override
	public void downloading() {
		loadOption();

		download();
	}

	private void download() {
		WebDriver d = webDriverService.buildChromeWebDriver();
		d.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

		File savingPath = null;
		for (TextbookDownloadSubOption subOption : optionDTO.getOptionList()) {

			savingPath = new File(subOption.getSavePath());
			if (!savingPath.exists()) {
				savingPath.mkdirs();
			}
			
			try {
				d.get(subOption.getStartUrl());
			} catch (Exception e) {
			}

			try {
				if (!auxTool.loadingCheck(d,
						"//body/div[@id='__nuxt']/div[@id='__layout']/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/img",
						1000L, 15)) {
					System.out.println("Real timeout");
				}
			} catch (InterruptedException e2) {
				e2.printStackTrace();
			}

			List<String> urlStrList = findAllUrl(d);
			String tmpUrl = null;

			for (int i = 0; i < urlStrList.size(); i++) {
				tmpUrl = urlStrList.get(i);

				try {
					d.get(tmpUrl);
				} catch (Exception e) {
				}

				try {
					if (!auxTool.loadingCheck(d,
							"//body/div[@id='__nuxt']/div[@id='__layout']/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/img")) {
						System.out.println("Real timeout");
					}
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}

				List<WebElement> imgList = null;
				WebElement tmpImg = null;
				try {

					System.out.println("get: " + tmpUrl);

					imgList = d.findElements(By.xpath(
							"//body/div[@id='__nuxt']/div[@id='__layout']/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/img"));

					for (int j = 0; j < imgList.size(); j++) {
						tmpImg = imgList.get(j);
//					System.out.println(tmpImg.getAttribute("src"));
						webATToolService.saveImg(tmpImg, (i + "_" + j + ".jpg"), subOption.getSavePath());
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}

		d.quit();
	}

	private List<String> findAllUrl(WebDriver d) {
		List<WebElement> urlElementList = d.findElements(
				By.xpath("/html[1]/body[1]/div[1]/div[1]/div[1]/div[1]/div[2]/div[2]/div[4]/div[2]/ul[1]/li/a[1]"));
		List<String> urlStrList = new ArrayList<>();
		for (WebElement ele : urlElementList) {
			urlStrList.add(ele.getAttribute("href"));
		}
		return urlStrList;
	}

}
