package demo.selenium.service.impl;

import java.io.File;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.base.system.pojo.bo.SystemConstant;
import demo.base.system.service.impl.SystemConstantService;
import demo.baseCommon.service.CommonService;
import demo.selenium.service.SeleniumGlobalOptionService;

@Service
public class SeleniumGlobalOptionServiceImpl extends CommonService implements SeleniumGlobalOptionService {

	@Autowired
	private SystemConstantService constantService;

	private String mainSavingFolder_win = "d:/auxiliary";
	private String mainSavingFolder_linx = "/home/u2";
	private String downloadFolder = "/tmp";
	private String tmpFolder = "/tmp";
	private String screenshotSavingFolder = "/screenshot";

	private String downloadDirRedisKey = "seleniumDownloadDir";
	private String tmpFolderRedisKey = "tmpFolder";
	private String screenshotSavingFloderRedisKey = "seleniumScreenshotSavingDir";

	private String winSeleniumWebDriverFolder = "d:/auxiliary/seleniumWebDriver";
	private String linuxSeleniumWebDriverFolder = "/home/u2/seleniumWebDriver";
	private String chrome76Path_win = winSeleniumWebDriverFolder + "/chrome76Driver.exe";
	private String chrome45Path_win = winSeleniumWebDriverFolder + "/chrome45Driver.exe";
	private String chrome76Path_linux = linuxSeleniumWebDriverFolder + "/chrome76Driver";
	private String chrome45Path_linux = linuxSeleniumWebDriverFolder + "/chrome45Driver";
	private String geckoPath_win = winSeleniumWebDriverFolder + "/geckodriver-v0.24.0-win64.exe";
	private String geckoPath_linux = linuxSeleniumWebDriverFolder + "/geckodriver-v0.24.0-linux";
	private String edgePath = winSeleniumWebDriverFolder + "/MicrosoftWebDriver.exe";
	private String iePath = winSeleniumWebDriverFolder + "/IEDriverServer.exe";

	@Override
	public String getDownloadDir() {
		String downloadFolderPath = constantService.getValByName(downloadDirRedisKey);

		if (StringUtils.isNotBlank(downloadFolderPath)) {
			checkFolderExists(downloadFolderPath);
			return pathChangeByDetectOS(downloadFolderPath);
		}

		if (isWindows()) {
			downloadFolderPath = mainSavingFolder_win + downloadFolder;
		} else {
			downloadFolderPath = mainSavingFolder_linx + downloadFolder;
		}
		downloadFolderPath = pathChangeByDetectOS(downloadFolderPath);

		SystemConstant constant = new SystemConstant();
		constant.setConstantName(downloadDirRedisKey);
		constant.setConstantValue(downloadFolderPath);
		constantService.setValByName(constant);

		checkFolderExists(downloadFolderPath);
		return downloadFolderPath;
	}
	
	@Override
	public String getTmpDir() {
		String tmpFolderPath = constantService.getValByName(tmpFolderRedisKey);

		if (StringUtils.isNotBlank(tmpFolderPath)) {
			checkFolderExists(tmpFolderPath);
			return pathChangeByDetectOS(tmpFolderPath);
		}

		if (isWindows()) {
			tmpFolderPath = mainSavingFolder_win + tmpFolder;
		} else {
			tmpFolderPath = mainSavingFolder_linx + tmpFolder;
		}
		tmpFolderPath = pathChangeByDetectOS(tmpFolderPath);

		SystemConstant constant = new SystemConstant();
		constant.setConstantName(tmpFolderRedisKey);
		constant.setConstantValue(tmpFolderPath);
		constantService.setValByName(constant);

		checkFolderExists(tmpFolderPath);
		return tmpFolderPath;
	}
	
	@Override
	public boolean checkFolderExists(String path) {
		File f = new File(path);
		if(!f.exists() || !f.isDirectory()) {
			return f.mkdirs();
		} else {
			return true;
		}
	}

	@Override
	public String getScreenshotSavingFolder() {
		String screenshotSavingDir = constantService.getValByName(screenshotSavingFloderRedisKey);

		if (StringUtils.isNotBlank(screenshotSavingDir)) {
			checkFolderExists(screenshotSavingDir);
			return pathChangeByDetectOS(screenshotSavingDir);
		}

		if (isWindows()) {
			screenshotSavingDir = mainSavingFolder_win + screenshotSavingFolder;
		} else {
			screenshotSavingDir = mainSavingFolder_linx + screenshotSavingFolder;
		}
		screenshotSavingDir = pathChangeByDetectOS(screenshotSavingDir);

		SystemConstant systemConstant = new SystemConstant();
		systemConstant.setConstantName(screenshotSavingFloderRedisKey);
		systemConstant.setConstantValue(screenshotSavingDir);
		constantService.setValByName(systemConstant);

		checkFolderExists(screenshotSavingDir);
		return screenshotSavingDir;
	}

	@Override
	public String getChrome76Path() {
		String path = null;
		if (isWindows()) {
			path = chrome76Path_win;
		} else {
			path = chrome76Path_linux;
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

}
