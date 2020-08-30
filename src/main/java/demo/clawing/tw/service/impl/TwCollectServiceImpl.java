package demo.clawing.tw.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import at.xpath.pojo.bo.XpathBuilderBO;
import demo.clawing.tw.service.TwCollectService;
import demo.selenium.service.impl.SeleniumCommonService;

@Service
public class TwCollectServiceImpl extends SeleniumCommonService implements TwCollectService {

	@Override
	public void monsterCollecting() {
		String monsterDataUrl = "http://tw.17173.com/monster/%s.shtml";

		WebDriver d = webDriverService.buildFireFoxWebDriver();

		for (int i = 1; i < 10; i++) {
			monsterCollectHandle(d, String.format(monsterDataUrl, "0" + i));
		}
		for (int i = 10; i < 16; i++) {
			monsterCollectHandle(d, String.format(monsterDataUrl, String.valueOf(i)));
		}

		tryQuitWebDriver(d);
	}

	private void monsterCollectHandle(WebDriver d, String url) {
		d.get(url);
		XpathBuilderBO x = new XpathBuilderBO();
		x.start("table").addAttribute("width", "675").addAttribute("border", "1").addAttribute("align", "center")
				.addAttribute("cellspacing", "0").addAttribute("bordercolor", "#cc9966").findChild("tbody");
		String targetTbodyPath = x.getXpath();

		int trIndex = 1;

		WebElement nameTd = null;
		WebElement tmpImg = null;

		String tmpMonsterName = null;
		String tmpImageUrl = null;

		boolean exceptionFlag = false;
		while (!exceptionFlag) {
			x.setXpath(targetTbodyPath).findChild("tr", trIndex).findChild("td", 1);
			try {
				nameTd = d.findElement(By.xpath(x.getXpath()));
				tmpMonsterName = nameTd.getText();
			} catch (Exception e) {
				exceptionFlag = true;
			}

			if (!exceptionFlag) {
				x.setXpath(targetTbodyPath).findChild("tr", trIndex + 1).findChild("td", 1).findChild("div")
						.findChild("img");
				try {
					tmpImg = d.findElement(By.xpath(x.getXpath()));
					tmpImageUrl = tmpImg.getAttribute("src");
				} catch (Exception e) {
					exceptionFlag = true;
				}

				if (!exceptionFlag) {
					try {
						download(tmpImageUrl, tmpMonsterName);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}

			trIndex += 7;
		}
	}

	private void download(String urlStr, String filename) throws IOException {
		FileUtils.copyURLToFile(new URL(urlStr), new File("D:/gameElement/待处理/tw" + "/" + filename + ".gif"), 10000, 10000);
	}

	public void skillCollecting() {
//		http://tw.17173.com/skill/skill.shtml
	}
	
}
