package demo.selenium.pojo.type;

public enum BrowserConfigType {
	
	/** chrome */
//	chrome("chrome", "C:/Program Files (x86)/Google/Chrome/Application/chrome.exe", "webdriver.chrome.driver"),
//	chrome("chrome", "C:/Program Files (x86)/Google/Chrome/Application/chromeDriver.exe", "webdriver.chrome.driver"),
	chrome76("chrome76", "D:/soft/chromeDriver76.exe", "webdriver.chrome.driver"),
	chrome45("chrome45", "D:/soft/chromeDriver45.exe", "webdriver.chrome.driver"),
	/** fireFox */
	fireFox("fireFox", "C:/Program Files/Mozilla Firefox/firefox.exe", "webdriver.firefox.marionette"),
	/** gecko */
	gecko("gecko", "D:/soft/geckodriver-v0.24.0-win64/geckodriver.exe", "webdriver.gecko.driver"),
	/** edge */
	edge("edge", "D:/soft/MicrosoftWebDriver.exe", "webdriver.edge.driver"),
	/** ie */
	ie("id", "d:/soft/IEDriverServer.exe", "webdriver.ie.driver"),
	
	;
	
	private String name;
	private String path;
	private String driver;
	
	BrowserConfigType(String name, String path, String driver) {
		this.name = name;
		this.path = path;
		this.driver = driver;
	}
	
	public String getName() {
		return name;
	}

	public String getPath() {
		return path;
	}
	
	public String getDriver() {
		return driver;
	}

	public static BrowserConfigType getType(String typeName) {
		for(BrowserConfigType t : BrowserConfigType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
}
