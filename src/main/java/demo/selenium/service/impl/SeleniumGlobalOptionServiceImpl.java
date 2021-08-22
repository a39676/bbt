package demo.selenium.service.impl;

import java.io.File;

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
	private String parameterSavingFolder = MAIN_FOLDER_PATH + "/autoTestParameterFiles";

	private String winSeleniumWebDriverFolder = "d:/auxiliary/seleniumWebDriver";
	private String linuxSeleniumWebDriverFolder = "/home/u2/seleniumWebDriver";

	private String chromePath_win = winSeleniumWebDriverFolder + "/chromeDriver.exe";
	private String chrome45Path_win = winSeleniumWebDriverFolder + "/chrome45Driver.exe";
	private String chromePath_linux = linuxSeleniumWebDriverFolder + "/chromeDriver";
	private String chrome45Path_linux = linuxSeleniumWebDriverFolder + "/chrome45Driver";

	private String geckoPath_win = winSeleniumWebDriverFolder + "/geckodriver.exe";
	private String geckoPath_linux = linuxSeleniumWebDriverFolder + "/geckodriver";

	private String edgePath = winSeleniumWebDriverFolder + "/MicrosoftWebDriver.exe";
	private String iePath = winSeleniumWebDriverFolder + "/IEDriverServer.exe";
	private String operaPath = winSeleniumWebDriverFolder + "/operadriver.exe";

	@Override
	public String getDownloadDir() {
		return downloadFolderPath;
	}

	@Override
	public String getTmpDir() {
		return tmpFolder;
	}

	@Override
	public boolean checkFolderExists(String path) {
		File f = new File(path);
		if (!f.exists() || !f.isDirectory()) {
			return f.mkdirs();
		} else {
			return true;
		}
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
	public String getParameterSavingFolder() {
		return parameterSavingFolder;
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
	public String getChrome45Path() {
		String path = null;
		if (isWindows()) {
			path = chrome45Path_win;
		} else {
			path = chrome45Path_linux;
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

	@Override
	public String getOperaPath() {
		return pathChangeByDetectOS(operaPath);
	}

}
