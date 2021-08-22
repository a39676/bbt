package demo.selenium.service;

public interface SeleniumGlobalOptionService {

	String getDownloadDir();
	String getScreenshotSavingFolder();
	String getCaptchaScreenshotSavingFolder();
	String getParameterSavingFolder();

	String getChromePath();
	String getChrome45Path();

	String getGeckoPath();

	String getEdgePath();

	String getIePath();

	boolean checkFolderExists(String path);

	String getTmpDir();
	String getOperaPath();

}
