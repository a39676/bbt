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

	private String mainSavingFolder_linx = "/home/u2/bbt";
	private String mainSavingFolder_win = "d:" + mainSavingFolder_linx;
	private String tmpFolder = "/tmp";
	private String downloadFolder = tmpFolder;
	private String screenshotSavingFolder = "/screenshot";
	private String captchaScreenshotSavingFolder = "/captchaScreenshotSavingFolder";
	private String reportOutputFolder = "/reportOutputFolder";
	private String parameterSavingFolder = "/autoTestParameterFiles";

	private String downloadDirRedisKey = "seleniumDownloadDir";
	private String tmpFolderRedisKey = "tmpFolder";
	private String screenshotSavingFolderRedisKey = "seleniumScreenshotSavingDir";
	private String captchaScreenshotSavingFolderRedisKey = "captchaScreenshotSavingFolderRedisKey";
	private String reportOutputFolderRedisKey = "reportOutputFolderRedisKey";
	private String parameterSavingFolderRedisKey = "parameterSavingFolderRedisKey";

	private String winSeleniumWebDriverFolder = "d:/auxiliary/seleniumWebDriver";
	private String linuxSeleniumWebDriverFolder = "/home/u2/seleniumWebDriver";
	
	private String chrome85Path_win = winSeleniumWebDriverFolder + "/chrome85Driver.exe";
	private String chrome45Path_win = winSeleniumWebDriverFolder + "/chrome45Driver.exe";
	private String chrome90Path_linux = linuxSeleniumWebDriverFolder + "/chrome90Driver";
	private String chrome45Path_linux = linuxSeleniumWebDriverFolder + "/chrome45Driver";
	
	private String geckoPath_win = winSeleniumWebDriverFolder + "/geckodriver-v0.27.0-win64.exe";
	private String geckoPath_linux = linuxSeleniumWebDriverFolder + "/geckodriver-v0.27.0-linux";
	
	private String edgePath = winSeleniumWebDriverFolder + "/MicrosoftWebDriver.exe";
	private String iePath = winSeleniumWebDriverFolder + "/IEDriverServer.exe";
	private String operaPath = winSeleniumWebDriverFolder + "/operadriver.exe";

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

		constantService.setValByName(downloadDirRedisKey, downloadFolderPath);

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

		constantService.setValByName(tmpFolderRedisKey, tmpFolderPath);

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
		String screenshotSavingDir = constantService.getValByName(screenshotSavingFolderRedisKey);

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

		constantService.setValByName(screenshotSavingFolderRedisKey, screenshotSavingDir);

		checkFolderExists(screenshotSavingDir);
		return screenshotSavingDir;
	}
	
	@Override
	public String getCaptchaScreenshotSavingFolder() {
		String captchaScreenshotSavingDir = constantService.getValByName(captchaScreenshotSavingFolderRedisKey);

		if (StringUtils.isNotBlank(captchaScreenshotSavingDir)) {
			checkFolderExists(captchaScreenshotSavingDir);
			return pathChangeByDetectOS(captchaScreenshotSavingDir);
		}

		if (isWindows()) {
			captchaScreenshotSavingDir = mainSavingFolder_win + captchaScreenshotSavingFolder;
		} else {
			captchaScreenshotSavingDir = mainSavingFolder_linx + captchaScreenshotSavingFolder;
		}
		captchaScreenshotSavingDir = pathChangeByDetectOS(captchaScreenshotSavingDir);

		constantService.setValByName(captchaScreenshotSavingFolderRedisKey, captchaScreenshotSavingDir);

		checkFolderExists(captchaScreenshotSavingDir);
		return captchaScreenshotSavingDir;
	}
	
	@Override
	public String getReportOutputFolder() {
		String reportOutputDir = constantService.getValByName(reportOutputFolderRedisKey);

		if (StringUtils.isNotBlank(reportOutputDir)) {
			checkFolderExists(reportOutputDir);
			return pathChangeByDetectOS(reportOutputDir);
		}

		if (isWindows()) {
			reportOutputDir = mainSavingFolder_win + reportOutputFolder;
		} else {
			reportOutputDir = mainSavingFolder_linx + reportOutputFolder;
		}
		reportOutputDir = pathChangeByDetectOS(reportOutputDir);

		SystemConstant systemConstant = new SystemConstant();
		systemConstant.setConstantName(reportOutputFolderRedisKey);
		systemConstant.setConstantValue(reportOutputDir);
		constantService.setValByName(systemConstant);

		checkFolderExists(reportOutputDir);
		return reportOutputDir;
	}

	@Override
	public String getParameterSavingFolder() {
		String savingDir = constantService.getValByName(parameterSavingFolderRedisKey);

		if (StringUtils.isNotBlank(savingDir)) {
			checkFolderExists(savingDir);
			return pathChangeByDetectOS(savingDir);
		}

		if (isWindows()) {
			savingDir = mainSavingFolder_win + parameterSavingFolder;
		} else {
			savingDir = mainSavingFolder_linx + parameterSavingFolder;
		}
		savingDir = pathChangeByDetectOS(savingDir);

		SystemConstant systemConstant = new SystemConstant();
		systemConstant.setConstantName(parameterSavingFolderRedisKey);
		systemConstant.setConstantValue(savingDir);
		constantService.setValByName(systemConstant);

		checkFolderExists(savingDir);
		return savingDir;
	}
	
	@Override
	public String getChromePath() {
		String path = null;
		if (isWindows()) {
			path = chrome85Path_win;
		} else {
			path = chrome90Path_linux;
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
