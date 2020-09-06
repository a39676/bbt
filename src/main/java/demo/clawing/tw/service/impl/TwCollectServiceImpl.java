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
		System.out.println("download: " + urlStr + " filename: " + filename);
		try {
			FileUtils.copyURLToFile(new URL(urlStr), new File("D:/gameElement/待处理/tw" + "/" + filename + ".gif"), 10000,
					10000);
		} catch (Exception e) {
			e.printStackTrace();
			FileUtils.copyURLToFile(new URL(urlStr), new File("D:/gameElement/待处理/tw" + "/" + snowFlake.getNextId() + ".gif"), 10000,
					10000);
		}
	}

	@Override
	public void skillCollecting() {
		WebDriver d = webDriverService.buildFireFoxWebDriver();

		skillCollecting(d, "http://tw.17173.com/skill/skill.shtml");
		try {
			threadSleepRandomTime(100L, 300L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String skillDataUrl = "http://tw.17173.com/skill/skill-%s.shtml";
		for (int i = 2; i < 9; i++) {
			skillCollecting(d, String.format(skillDataUrl, String.valueOf(i)));
			try {
				threadSleepRandomTime(100L, 300L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		tryQuitWebDriver(d);
	}

	private void skillCollecting(WebDriver d, String url) {
		d.get(url);
		XpathBuilderBO x = new XpathBuilderBO();

		x.start("table").addId("table1").findChild("tbody");

		String targetTBodyXpath = x.getXpath();

		boolean exceptionFlag = false;

		WebElement tmpImg = null;
		WebElement tmpSkillNameTD = null;

		String tmpImageUrl = null;

		for (int trIndex = 4; !exceptionFlag; trIndex += 1) {
			x.setXpath(targetTBodyXpath).findChild("tr", trIndex);
			try {
				d.findElement(By.xpath(x.getXpath()));
			} catch (Exception e) {
				exceptionFlag = true;
				continue;
			}

			x.setXpath(targetTBodyXpath).findChild("tr", trIndex).findChild("td", 2);
			try {
				tmpSkillNameTD = d.findElement(By.xpath(x.getXpath()));
			} catch (Exception e) {
				continue;
			}

			x.setXpath(targetTBodyXpath).findChild("tr", trIndex).findChild("td", 1).findChild("img");
			try {
				tmpImg = d.findElement(By.xpath(x.getXpath()));
				tmpImageUrl = tmpImg.getAttribute("src");
			} catch (Exception e) {
				continue;
			}

			if (!exceptionFlag) {
				try {
					download(tmpImageUrl, tmpSkillNameTD.getText());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	@Override
	public void equipmentCollecting() {
		String dateUrl = "http://tw.17173.com/wuqi/18-2.shtml";

		WebDriver d = webDriverService.buildFireFoxWebDriver();

		equipmentCollectHandle(d, dateUrl);
//		for (int i = 1; i < 10; i++) {
//			equipmentCollectHandle(d, String.format(dateUrl, "0" + i));
//		}
//		for (int i = 10; i <= 24; i++) {
//			equipmentCollectHandle(d, String.format(dateUrl, String.valueOf(i)));
//		}

		tryQuitWebDriver(d);
	}
	
	private void equipmentCollectHandle(WebDriver d, String url) {
		d.get(url);
		XpathBuilderBO x = new XpathBuilderBO();
		x.start("table").addId("table1").findChild("tbody");
		String targetTbodyPath = x.getXpath();

		int trIndex = 2;

		WebElement tmpImg = null;
		WebElement tmpNameElement = null;

		String tmpImageUrl = null;
		String tmpName = null;

		boolean exceptionFlag = false;
		for (; !exceptionFlag; trIndex += 1) {
			
			x.setXpath(targetTbodyPath).findChild("tr", trIndex);
			try {
				d.findElement(By.xpath(x.getXpath()));
			} catch (Exception e) {
				exceptionFlag = true;
				continue;
			}
			
			if(tmpName == null) {
				x.setXpath(targetTbodyPath).findChild("tr", trIndex + 1).findChild("td", 2);
				tmpNameElement = d.findElement(By.xpath(x.getXpath()));
				if("赌博".equals(tmpNameElement.getText())) {
					x.setXpath(targetTbodyPath).findChild("tr", trIndex + 1).findChild("td", 1);
					tmpNameElement = d.findElement(By.xpath(x.getXpath()));
					tmpName = tmpNameElement.getText();
				}
				continue;
				
			} else {
				x.setXpath(targetTbodyPath).findChild("tr", trIndex + 1).findChild("td", 1).findChild("div")
				.findChild("img");
				try {
					tmpImg = d.findElement(By.xpath(x.getXpath()));
					tmpImageUrl = tmpImg.getAttribute("src");
					System.out.println("get: " + tmpImageUrl);
					download(tmpImageUrl, tmpName);
					tmpName = null;
				} catch (Exception e) {
					continue;
				}
			}
		}
	}
	
	@Override
	public void equipmentCollecting2(String sub, String prefix) {
		String modelUrl = "http://images.17173.com/tw/images2/wuqi/%s/%s%s.gif";
		String urlStr = null;
		for(int i = 1; i < 10; i++) {
			urlStr = String.format(modelUrl, sub, prefix, ("0" + i));
			try {
				download(urlStr, String.valueOf(i));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		for(int i = 10; i <= 30; i++) {
			urlStr = String.format(modelUrl, sub, prefix, i);
			try {
				download(urlStr, String.valueOf(i));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
