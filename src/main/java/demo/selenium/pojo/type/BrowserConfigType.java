package demo.selenium.pojo.type;

public enum BrowserConfigType {
	
	/** chrome */
	chrome76_win("chrome76", "./seleniumWebDriver/chrome76Driver.exe", "webdriver.chrome.driver"),
	chrome45_win("chrome45", "./seleniumWebDriver/chrome45Driver.exe", "webdriver.chrome.driver"),
	chrome76_linux("chrome76", "./seleniumWebDriver/chrome76Driver", "webdriver.chrome.driver"),
	chrome45_linux("chrome45", "./seleniumWebDriver/chrome45Driver", "webdriver.chrome.driver"),
	/** fireFox ---> gecko */
	gecko_win("gecko", "./seleniumWebDriver/geckodriver-v0.24.0-win64.exe", "webdriver.gecko.driver"),
	gecko_linux("gecko", "./seleniumWebDriver/geckodriver-v0.24.0-linux", "webdriver.gecko.driver"),
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
