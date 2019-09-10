package demo.selenium.service;

public interface SeleniumGlobalOptionService {

	String getDownloadDir();

	String getScreenshotSavingFolder();

	String getChrome76Path();
	String getChrome45Path();

	String getGeckoPath();

	String getEdgePath();

	String getIePath();

	String pathChangeByDetectOS(String oldPath);

	boolean checkFolderExists(String path);


}
