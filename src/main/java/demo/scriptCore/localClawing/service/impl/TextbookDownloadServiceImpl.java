package demo.scriptCore.localClawing.service.impl;

import java.io.File;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.scriptCore.common.service.AutomationTestCommonService;
import demo.scriptCore.localClawing.pojo.dto.TextbookDownloadOptionDTO;
import demo.scriptCore.localClawing.service.TextbookDownloadService;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class TextbookDownloadServiceImpl extends AutomationTestCommonService implements TextbookDownloadService {

	private static String optionFilePath = "d:/home/u2/bbt/optionFile/tmp/textBookDownloadOption.json";
	private static TextbookDownloadOptionDTO optionDTO = null;

	private TextbookDownloadOptionDTO loadOption() {
		FileUtilCustom ioUtil = new FileUtilCustom();
		String optionJsonStr = ioUtil.getStringFromFile(optionFilePath);

		optionDTO = new Gson().fromJson(optionJsonStr, TextbookDownloadOptionDTO.class);

		File savingPath = new File(optionDTO.getSavePath());
		if (!savingPath.exists()) {
			savingPath.mkdirs();
		}

		return optionDTO;
	}

	@Override
	public void downloading() {
		loadOption();

		download();
	}

	private void download() {
		WebDriver d = webDriverService.buildChromeWebDriver();
		String tmpUrl;
		for (int i = optionDTO.getStartNum(); i <= optionDTO.getEndNum(); i++) {

			tmpUrl = optionDTO.getMainUrl().replaceAll(String.valueOf(optionDTO.getStartNum()), String.valueOf(i));
			List<WebElement> imgList = null;
			WebElement tmpImg = null;
			try {

				d.get(tmpUrl);

				System.out.println("get: " + tmpUrl);
				
				imgList = d.findElements(By.xpath("//body/div[@id='__nuxt']/div[@id='__layout']/div[1]/div[1]/div[2]/div[1]/div[1]/div[2]/img"));
				
				for(int j = 0; j < imgList.size(); j++) {
					tmpImg = imgList.get(j);
//					System.out.println(tmpImg.getAttribute("src"));
					webATToolService.saveImg(tmpImg, (i + "_" + j + ".jpg"), optionDTO.getSavePath());
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		d.quit();
	}

}
