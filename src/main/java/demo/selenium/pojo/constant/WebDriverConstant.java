package demo.selenium.pojo.constant;

import demo.selenium.pojo.type.BrowserConfigType;

public class WebDriverConstant {
	
	public static final int pageWaitingTimeoutSecond = 30;

	public static final String chrome76Driver = BrowserConfigType.chrome76.getDriver();
	public static final String chrome45Driver = BrowserConfigType.chrome45.getDriver();
	public static final String geckoDriver = BrowserConfigType.gecko.getDriver();
	public static final String edgeDriver = BrowserConfigType.edge.getDriver();
	public static final String ieDriver = BrowserConfigType.ie.getDriver();
	
}
