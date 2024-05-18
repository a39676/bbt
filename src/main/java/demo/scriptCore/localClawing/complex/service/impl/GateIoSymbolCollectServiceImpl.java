package demo.scriptCore.localClawing.complex.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import demo.scriptCore.localClawing.complex.service.GateIoSymbolCollectService;
import demo.selenium.service.impl.AutomationTestCommonService;

@Service
public class GateIoSymbolCollectServiceImpl extends AutomationTestCommonService implements GateIoSymbolCollectService {

	private int maxSymbolPage = 70;
	private int defaultSymbolPageSize = 30;
	private int maxWaitingPageRefresh = 10;
	private String symbolXpathModule = "/html[1]/body[1]/div[1]/div[2]/div[1]/div[1]/div[3]/div[2]/div[1]/table[1]/tbody[1]/tr[%d]/td[1]/div[1]/div[1]/a[1]/div[1]/div[1]/span[1]";
	private String symbolPageButtonXpathModule = "//button[contains(text(),'%d')]";
	private List<String> resultSymbolList = new ArrayList<>();

	@Override
	public void collect() {
		WebDriver d = webDriverService.buildChromeWebDriver();

		try {
			prefix(d);
			String tmpSymbolXpath = String.format(symbolXpathModule, 1);
			if (!loadingCheck(d, tmpSymbolXpath)) {
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		List<String> symbolListInPage = collectSymbol(d);
		resultSymbolList.addAll(symbolListInPage);
		boolean refreshFlag = false;

		for (int pageNum = 2; pageNum <= maxSymbolPage; pageNum++, refreshFlag = false) {
			System.out.println("Checking page: " + pageNum);
			String tmpButtonXpath = String.format(symbolPageButtonXpathModule, pageNum);
			try {
				WebElement tmpPageButton = d.findElement(By.xpath(tmpButtonXpath));
				tmpPageButton.click();
			} catch (Exception e) {
				System.out.println("Can NOT find page button of: " + pageNum);
				continue;
			}

			try {
				Thread.sleep(1000L);
			} catch (Exception e) {
				e.printStackTrace();
			}

			for (int waitingCounter = 0; waitingCounter < maxWaitingPageRefresh && !refreshFlag; waitingCounter++) {
				refreshFlag = pageHadRefresh(d);
				try {
					Thread.sleep(500L);
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (!refreshFlag) {
					System.out.println("Page button of: " + pageNum + " NOT refresh yet, waiting.");
				}
			}
			if (!refreshFlag) {
				System.out.println("Page button of: " + pageNum + " Failed, skip.");
				continue;
			}
			symbolListInPage = collectSymbol(d);
			if (symbolListInPage.isEmpty()) {
				System.out.println("Page: " + pageNum + ", collect failed");
				continue;
			}
			System.out.println("After checking page: " + pageNum);
			for (String symbol : symbolListInPage) {
				if (!resultSymbolList.contains(symbol)) {
					resultSymbolList.add(symbol);
				}
			}
			System.out.println(resultSymbolList);
		}

		tryQuitWebDriver(d);
		System.out.println("Size: " + resultSymbolList.size());
		System.out.println(resultSymbolList);
	}

	private List<String> collectSymbol(WebDriver d) {
		List<String> symbolList = new ArrayList<>();
		for (int i = 1; i <= defaultSymbolPageSize; i++) {
			String tmpSymbolXpath = String.format(symbolXpathModule, i);
			try {
				WebElement symbolEle = d.findElement(By.xpath(tmpSymbolXpath));
				symbolList.add(symbolEle.getText());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return symbolList;
	}

	private boolean pageHadRefresh(WebDriver d) {
		String tmpSymbolXpath = String.format(symbolXpathModule, defaultSymbolPageSize);
		try {
			if (!loadingCheck(d, tmpSymbolXpath)) {
				return false;
			}
			WebElement symbolEle = d.findElement(By.xpath(tmpSymbolXpath));
			if (resultSymbolList.get(resultSymbolList.size() - 1).equals(symbolEle.getText())) {
				return false;
			}
			return true;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	private void prefix(WebDriver d) {
		d.get("https://www.gate.io/");
//		try {
//			Thread.sleep(3000L);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		d.findElement(By.xpath("//span[contains(text(),'I Got it')]")).click();
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		d.findElement(By.xpath("//body/div[@id='__next']/div[1]/div[1]/div[2]/div[8]/a[1]/div[1]/div[1]")).click();

	}
}
