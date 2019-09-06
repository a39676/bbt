package demo.selenium.service.impl;

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
	private String mainSavingFolder_linx = "/home/u1";
	private String downloadFolder = "/tmp";
	private String screenshotSavingFolder = "/screenShot";
	
	private String downloadDirRedisKey = "seleniumDownloadDir";
	private String screenshotSavingFloderRedisKey = "seleniumDownloadDir";
	

	private String seleniumWebDriverFolder = "./seleniumWebDriver";
	private String chrome76Path_win = seleniumWebDriverFolder + "/chrome76Driver.exe";
	private String chrome45Path_win = seleniumWebDriverFolder + "/chrome45Driver.exe";
	private String chrome76Path_linux = seleniumWebDriverFolder + "/chrome76Driver";
	private String chrome45Path_linux = seleniumWebDriverFolder + "/chrome45Driver";
	private String geckoPath_win = seleniumWebDriverFolder + "/geckodriver-v0.24.0-win64.exe";
	private String geckoPath_linux = seleniumWebDriverFolder + "/geckodriver-v0.24.0-linux";
	private String edgePath = seleniumWebDriverFolder + "/MicrosoftWebDriver.exe";
	private String iePath = seleniumWebDriverFolder + "/IEDriverServer.exe";
	
	
	@Override
	public String getDownloadDir() {
		String downloadDir = constantService.getValByName(downloadDirRedisKey);
		
		if(StringUtils.isNotBlank(downloadDir)) {
			return downloadDir;
		}
		
		if(isWindows()) {
			downloadDir = mainSavingFolder_win + downloadFolder;
		} else {
			downloadDir = mainSavingFolder_linx + downloadFolder;
		}
		
		SystemConstant systemConstant = new SystemConstant();
		systemConstant.setConstantName(downloadDirRedisKey);
		systemConstant.setConstantValue(downloadDir);
		constantService.setValByName(systemConstant);
		
		return downloadDir;
	}
	
	@Override
	public String getScreenshotSavingFolder() {
		String screenshotSavingDir = constantService.getValByName(screenshotSavingFloderRedisKey);
		
		if(StringUtils.isNotBlank(screenshotSavingDir)) {
			return screenshotSavingDir;
		}
		
		if(isWindows()) {
			screenshotSavingDir = mainSavingFolder_win + screenshotSavingFolder;
		} else {
			screenshotSavingDir = mainSavingFolder_linx + screenshotSavingFolder;
		}
		
		SystemConstant systemConstant = new SystemConstant();
		systemConstant.setConstantName(screenshotSavingFloderRedisKey);
		systemConstant.setConstantValue(screenshotSavingDir);
		constantService.setValByName(systemConstant);
		
		return screenshotSavingDir;
	}

	@Override
	public String getChrome76Path() {
		if(isWindows()) {
			return chrome76Path_win;
		} else {
			return chrome76Path_linux;
		}
	}
	
	@Override
	public String getChrome45Path() {
		if(isWindows()) {
			return chrome45Path_win;
		} else {
			return chrome45Path_linux;
		}
	}
	
	@Override
	public String getGeckoPath() {
		if(isWindows()) {
			return geckoPath_win;
		} else {
			return geckoPath_linux;
		}
	}
	
	@Override
	public String getEdgePath() {
		return edgePath;
	}
	
	@Override
	public String getIePath() {
		return iePath;
	}
}
