package demo.selenium.pojo.constant;

import demo.selenium.pojo.type.BrowserConfigType;

public class WebDriverConstant {
	
	public static final int pageWaitingTimeoutSecond = 30;

	public static final String chromeDriver = BrowserConfigType.chrome.getDriver();
	public static final String fireFoxDriver = BrowserConfigType.fireFox.getDriver();
	public static final String geckoDriver = BrowserConfigType.gecko.getDriver();
	public static final String edgeDriver = BrowserConfigType.edge.getDriver();
	
}
