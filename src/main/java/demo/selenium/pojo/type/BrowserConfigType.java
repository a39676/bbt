package demo.selenium.pojo.type;

public enum BrowserConfigType {
	
	/** chrome */
	chrome76("chrome76", "./seleniumWebDriver/chromeDriver76.exe", "webdriver.chrome.driver"),
	chrome45("chrome45", "./seleniumWebDriver/chromeDriver45.exe", "webdriver.chrome.driver"),
	/** fireFox ---> gecko */
	gecko("gecko", "./seleniumWebDriver/geckodriver-v0.24.0-win64/geckodriver.exe", "webdriver.gecko.driver"),
	/** edge */
	edge("edge", "./seleniumWebDriver/MicrosoftWebDriver.exe", "webdriver.edge.driver"),
	/** ie */
	ie("id", "./seleniumWebDriver/IEDriverServer.exe", "webdriver.ie.driver"),
	
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
