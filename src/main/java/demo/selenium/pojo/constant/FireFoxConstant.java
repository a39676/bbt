package demo.selenium.pojo.constant;

public class FireFoxConstant extends WebDriverConstant {
	
	public static final String downloadDir = "browser.download.dir";

	public static final String folderList = "browser.download.folderList";
	public static final String downloadShowWhenStarting = "browser.download.manager.showWhenStarting";
	public static final String browserDownloadUseDownloadDir = "browser.download.useDownloadDir";
	public static final String neverAskSaveToDisk = "browser.helperApps.neverAsk.saveToDisk";

	public static final int downloadToUserDesktop = 0;
	public static final int downloadToDownloadsFolder = 1;
	public static final int downloadToTheLocationSpecifiedForTheMostRecentDownload = 2;
}
