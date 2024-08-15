package demo.scriptCore.localClawing.complex.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Service;

import demo.scriptCore.localClawing.complex.service.BinanceSymbolCollectService;
import demo.selenium.service.impl.AutomationTestCommonService;

@Service
public class BinanceSymbolCollectServiceImpl extends AutomationTestCommonService
		implements BinanceSymbolCollectService {

	private int maxSymbolPage = 13;
	private int defaultSymbolPageSize = 30;
	private String symbolXpathModule =    "/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/main[1]/div[1]/div/div[2]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[2]/div[%d]/div[1]/a[1]/div[1]/div[1]/div[2]/div[1]";
	private String marketCapXpathModule = "/html[1]/body[1]/div[3]/div[1]/div[1]/div[1]/main[1]/div[1]/div/div[2]/div[1]/div[1]/div[2]/div[3]/div[1]/div[1]/div[2]/div[%d]/div[1]/div[4]";
	private boolean hadFoundTenBillionEnd = false;
	private boolean hadFoundOneBillionEnd = false;
	private String tenBillionEndSymbol = null;
	private String oneBillionEndSymbol = null;

	@Override
	public void collect() {
		List<String> resultSymbolList = new ArrayList<>();
		hadFoundTenBillionEnd = false;
		hadFoundOneBillionEnd = false;
		tenBillionEndSymbol = null;
		oneBillionEndSymbol = null;

		WebDriver d = webDriverService.buildChromeWebDriver();
		String mainUrl = "https://www.binance.com/en/markets/overview";

		try {
			d.get(mainUrl);
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

		for (int pageNum = 2; pageNum <= maxSymbolPage; pageNum++) {
//			try {
//				d.get(mainUrl + "?p=" + pageNum);
//				System.out.println("Checking page: " + pageNum);
//				String tmpSymbolXpath = String.format(symbolXpathModule, 1);
//				if (!loadingCheck(d, tmpSymbolXpath)) {
//					return;
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//				return;
//			}

			String tmpButtonXpath = String.format("page-%d", pageNum);
			try {
				WebElement tmpPageButton = d.findElement(By.id(tmpButtonXpath));
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
			if (!pageHadRefresh(d)) {
				System.out.println("Page button of: " + pageNum + " NOT refresh yet, waiting.");
				pageNum--;
				continue;
			}

//			String tmpSymbolXpath = String.format(symbolXpathModule, 1);
//			WebElement symbolEle = d.findElement(By.xpath(tmpSymbolXpath));
//			if (resultSymbolList.contains(symbolEle.getText())) {
//				continue;
//			}

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
		System.out.println(tenBillionEndSymbol);
		System.out.println(oneBillionEndSymbol);
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

		if (!hadFoundTenBillionEnd) {
			tenBillionEndSymbol = findTenBillionEnd(d, symbolList);
			if (tenBillionEndSymbol != null) {
				hadFoundTenBillionEnd = true;
			}
		}
		if (hadFoundTenBillionEnd && !hadFoundOneBillionEnd) {
			oneBillionEndSymbol = findOneBillionEnd(d, symbolList);
			if (oneBillionEndSymbol != null) {
				hadFoundOneBillionEnd = true;
			}
		}

		return symbolList;
	}

	private String findTenBillionEnd(WebDriver d, List<String> symbolList) {
		for (int i = 1; i <= symbolList.size(); i++) {
			String tmpMarketCapXpath = String.format(marketCapXpathModule, i);
			try {
				WebElement marketCapEle = d.findElement(By.xpath(tmpMarketCapXpath));
				String marketCapStr = marketCapEle.getText();
				if (marketCapStr.endsWith("B") && marketCapStr.startsWith("$9.")) {
					return symbolList.get(i - 1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private String findOneBillionEnd(WebDriver d, List<String> symbolList) {
		for (int i = 1; i <= symbolList.size(); i++) {
			String tmpMarketCapXpath = String.format(marketCapXpathModule, i);
			try {
				WebElement marketCapEle = d.findElement(By.xpath(tmpMarketCapXpath));
				String marketCapStr = marketCapEle.getText();
				if (marketCapStr.endsWith("M")) {
					return symbolList.get(i - 1);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private boolean pageHadRefresh(WebDriver d) {
		String tmpSymbolXpath = String.format(symbolXpathModule, 1);
		try {
			if (!loadingCheck(d, tmpSymbolXpath)) {
				return false;
			} else {
				return true;
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}
}
