package demo.scriptCore.localClawing.complex.service.impl;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import demo.scriptCore.localClawing.complex.service.AlibabaLocalHtmlHandleService;
import demo.selenium.service.impl.AutomationTestCommonService;

@Service
public class AlibabaLocalHtmlHandleServiceImpl extends AutomationTestCommonService
		implements AlibabaLocalHtmlHandleService {

	private static final String localPageUrl = "file:///C:/Users/daven/auxiliary/pf/temu/products/%E4%BA%9A%E5%85%8B%E5%8A%9B%E4%BB%BF%E7%9C%9F%E5%86%B0%E5%9D%97%E9%AB%98%E9%80%8F%E5%A4%9C%E5%85%89%E7%BB%86%E9%97%AA%E6%96%B9%E5%BD%A2%E5%81%87%E5%86%B0%E5%9D%97%E6%91%84%E5%BD%B1%E6%8B%8D%E6%91%84%E9%81%93%E5%85%B7%20diy%E5%86%B0%E5%9D%97%E6%91%86%E4%BB%B6%20-%20%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4.htm";
	private static final String localSaveFolderPathStr = System.getProperty("user.home")
			+ "\\auxiliary\\pf\\temu\\products\\202508\\20250821_冰块";
	private static final String subTitleXpathModel = "/html[1]/body[1]/div[4]/div[1]/div[2]/div[1]/div[3]/div[8]/div[1]/div[2]/div[2]/div[%d]/div[1]/span[1]";
	private static final String subTitleImgXpathModel = "/html[1]/body[1]/div[4]/div[1]/div[2]/div[1]/div[3]/div[8]/div[1]/div[2]/div[2]/div[%d]/div[1]/div[1]/img[1] ";

	@Override
	public void downloading() {
		download();
	}

	private void download() {
		WebDriver d = webDriverService.buildChromeWebDriver();
		d.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

		try {
			d.get(localPageUrl);
		} catch (Exception e) {
		}

		String subTitleXpath = String.format(subTitleXpathModel, 1);

		try {
			if (!loadingCheck(d, subTitleXpath, 1000L, 15)) {
				System.out.println("Real timeout");
			}
		} catch (InterruptedException e2) {
			e2.printStackTrace();
		}

		try {
			for (int i = 1; i < 42; i++) {
				subTitleXpath = String.format(subTitleXpathModel, i);
				String subTitleImgXpath = String.format(subTitleImgXpathModel, i);
				WebElement subTitle = d.findElement(By.xpath(subTitleXpath));
				System.out.println(subTitle.getText());
				WebElement subTitleImg = d.findElement(By.xpath(subTitleImgXpath));
				String src = subTitleImg.getAttribute("src");
				System.out.println(src);
				src = src.replaceAll("jpg_sum\\.", "");
				System.out.println(src);
				src = src.replaceAll(
						"file:///C:/Users/daven/auxiliary/pf/temu/products/%E4%BA%9A%E5%85%8B%E5%8A%9B%E4%BB%BF%E7%9C%9F%E5%86%B0%E5%9D%97%E9%AB%98%E9%80%8F%E5%A4%9C%E5%85%89%E7%BB%86%E9%97%AA%E6%96%B9%E5%BD%A2%E5%81%87%E5%86%B0%E5%9D%97%E6%91%84%E5%BD%B1%E6%8B%8D%E6%91%84%E9%81%93%E5%85%B7%20diy%E5%86%B0%E5%9D%97%E6%91%86%E4%BB%B6%20-%20%E9%98%BF%E9%87%8C%E5%B7%B4%E5%B7%B4_files",
						"https://cbu01.alicdn.com/img/ibank");
				System.out.println(src);
				webATToolService.saveImg(src, subTitle.getText(), localSaveFolderPathStr);
				System.out.println(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		tryQuitWebDriver(d);
	}

}
