package demo.selenium.service.impl;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.selenium.service.SeleniumGlobalOptionService;

@Scope("singleton")
@Service
public class SeleniumGlobalOptionServiceImpl extends CommonService implements SeleniumGlobalOptionService {

	private String downloadFolderPath = MAIN_FOLDER_PATH + "/tmp";
	private String tmpFolder = MAIN_FOLDER_PATH + "/tmp";
	private String screenshotSavingFolder = MAIN_FOLDER_PATH + "/screenshot";
	private String captchaScreenshotSavingFolder = MAIN_FOLDER_PATH + "/captchaScreenshotSavingFolder";

	private String winSeleniumWebDriverFolder = "c:/users/daven/auxiliary/seleniumWebDriver";
	private String linuxSeleniumWebDriverFolder = MAIN_FOLDER_PATH + "/seleniumWebDriver";

	private String chromePath_win = winSeleniumWebDriverFolder + "/chromedriver.exe";
	private String chromePath_linux = linuxSeleniumWebDriverFolder + "/chromeDriver";

	private String geckoPath_win = winSeleniumWebDriverFolder + "/geckodriver.exe";
	private String geckoPath_linux = linuxSeleniumWebDriverFolder + "/geckodriver";

	private String edgePath = winSeleniumWebDriverFolder + "/MicrosoftWebDriver.exe";
	private String iePath = winSeleniumWebDriverFolder + "/IEDriverServer.exe";

	@Override
	public String getDownloadDir() {
		return downloadFolderPath;
	}

	@Override
	public String getTmpDir() {
		return tmpFolder;
	}

	@Override
	public String getScreenshotSavingFolder() {
		return screenshotSavingFolder;
	}

	@Override
	public String getCaptchaScreenshotSavingFolder() {
		return captchaScreenshotSavingFolder;
	}

	@Override
	public String getChromePath() {
		String path = null;
		if (isWindows()) {
			path = chromePath_win;
		} else {
			path = chromePath_linux;
		}

		return pathChangeByDetectOS(path);
	}

	@Override
	public String getGeckoPath() {
		String path = null;
		if (isWindows()) {
			path = geckoPath_win;
		} else {
			path = geckoPath_linux;
		}
		return pathChangeByDetectOS(path);
	}

	@Override
	public String getEdgePath() {
		return pathChangeByDetectOS(edgePath);
	}

	@Override
	public String getIePath() {
		return pathChangeByDetectOS(iePath);
	}


}
