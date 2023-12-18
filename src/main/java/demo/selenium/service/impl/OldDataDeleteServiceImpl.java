package demo.selenium.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testEvent.service.impl.AutomationTestOptionService;
import demo.baseCommon.service.CommonService;
import demo.selenium.service.OldDataDeleteService;
import demo.selenium.service.SeleniumGlobalOptionService;
import demo.tool.captcha.service.CaptchaService;

@Service
public class OldDataDeleteServiceImpl extends CommonService implements OldDataDeleteService {

	@Autowired
	private SeleniumGlobalOptionService globalOptionService;
	@Autowired
	private CaptchaService captchaService;
	@Autowired
	private AutomationTestOptionService automationTestConstantService;

	@Override
	public void deleteOldDownload() throws IOException {
		LocalDateTime oldHistoryLimit = LocalDateTime.now().minusMonths(automationTestConstantService.getOldDataLiveLimitMonth());

		deleting(globalOptionService.getDownloadDir(), oldHistoryLimit);

	}

	@Override
	public void deleteOldScreenshot() throws IOException {
		LocalDateTime oldHistoryLimit = LocalDateTime.now().minusMonths(automationTestConstantService.getOldDataLiveLimitMonth());

		deleting(globalOptionService.getScreenshotSavingFolder(), oldHistoryLimit);

	}

	@Override
	public void deleteOldCaptchaImg() throws IOException {
		LocalDateTime oldHistoryLimit = LocalDateTime.now().minusMonths(automationTestConstantService.getOldDataLiveLimitMonth());

		deleting(captchaService.getCaptchaSaveFolder(), oldHistoryLimit);

	}


	/**
	 * delete files ONLY, will not delete folder even though empty
	 * 
	 * @param folderPath
	 * @param deadLine
	 * @throws IOException
	 */
	private void deleting(String folderPath, LocalDateTime deadLine) throws IOException {

		File targetDir = new File(folderPath);
		if (!targetDir.exists() || !targetDir.isDirectory()) {
			return;
		}

		File[] files = targetDir.listFiles();
		Path filePath = null;
		BasicFileAttributes attr = null;
		Date createDate = null;
		LocalDateTime createDateTime = null;
		for (File file : files) {
			if (file.isDirectory()) {
				deleting(file.getAbsolutePath(), deadLine);
			} else {
				filePath = file.toPath();
				attr = Files.readAttributes(filePath, BasicFileAttributes.class);
				createDate = new Date(attr.creationTime().toMillis());
				createDateTime = dateHandler.dateToLocalDateTime(createDate);
				if (createDateTime.isBefore(deadLine)) {
					file.delete();
				}
			}
		}
	}

}
