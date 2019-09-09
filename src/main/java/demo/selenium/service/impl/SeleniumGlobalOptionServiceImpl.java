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
	protected SystemConstantService constantService;

	protected String mainSavingFolder_win = "d:/auxiliary";
	protected String mainSavingFolder_linx = "/home/u2";
	protected String downloadFolder = "/tmp";
	protected String screenshotSavingFolder = "/screenshot";

	protected String downloadDirRedisKey = "seleniumDownloadDir";
	protected String screenshotSavingFloderRedisKey = "seleniumScreenshotSavingDir";

	protected String seleniumWebDriverFolder = "./seleniumWebDriver";
	protected String chrome76Path_win = seleniumWebDriverFolder + "/chrome76Driver.exe";
	protected String chrome45Path_win = seleniumWebDriverFolder + "/chrome45Driver.exe";
	protected String chrome76Path_linux = seleniumWebDriverFolder + "/chrome76Driver";
	protected String chrome45Path_linux = seleniumWebDriverFolder + "/chrome45Driver";
	protected String geckoPath_win = seleniumWebDriverFolder + "/geckodriver-v0.24.0-win64.exe";
	protected String geckoPath_linux = seleniumWebDriverFolder + "/geckodriver-v0.24.0-linux";
	protected String edgePath = seleniumWebDriverFolder + "/MicrosoftWebDriver.exe";
	protected String iePath = seleniumWebDriverFolder + "/IEDriverServer.exe";

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
	
	protected boolean checkFolderExists(String path) {
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

	protected String pathChangeByDetectOS(String oldPath) {
		if(isWindows()) {
			return oldPath.replaceAll("/", "\\\\");
		} else {
			return oldPath.replaceAll("\\\\", "/");
		}
	}
}
