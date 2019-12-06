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

import dateTimeHandle.DateTimeUtilCommon;
import demo.autoTestBase.captcha.service.CaptchaService;
import demo.baseCommon.service.CommonService;
import demo.selenium.pojo.constant.SeleniumConstant;
import demo.selenium.service.OldDataDeleteService;
import demo.selenium.service.SeleniumGlobalOptionService;

@Service
public class OldDataDeleteServiceImpl extends CommonService implements OldDataDeleteService {

	@Autowired
	private SeleniumGlobalOptionService globalOptionService;
	@Autowired
	private CaptchaService captchaService;
	
	@Override
	public void deleteOldDownload() throws IOException {
		LocalDateTime oldHistoryLimit = LocalDateTime.now().minusMonths(SeleniumConstant.maxHistoryMonth);
		
		deleting(globalOptionService.getDownloadDir(), oldHistoryLimit);
		
	}
	
	@Override
	public void deleteOldScreenshot() throws IOException {
		LocalDateTime oldHistoryLimit = LocalDateTime.now().minusMonths(SeleniumConstant.maxHistoryMonth);
		
		deleting(globalOptionService.getScreenshotSavingFolder(), oldHistoryLimit);
		
	}
	
	@Override
	public void deleteOldCaptchaImg() throws IOException {
		LocalDateTime oldHistoryLimit = LocalDateTime.now().minusMonths(SeleniumConstant.maxHistoryMonth);
		
		deleting(captchaService.getCaptchaSaveFolder(), oldHistoryLimit);
		
	}
	
	@Override
	public void deleteOldReport() throws IOException {
		LocalDateTime oldHistoryLimit = LocalDateTime.now().minusMonths(SeleniumConstant.maxHistoryMonth);
		
		deleting(globalOptionService.getReportOutputFolder(), oldHistoryLimit);
		
	}
	
	/**
	 * delete files ONLY, will not delete folder even though empty
	 * @param folderPath
	 * @param deadLine
	 * @throws IOException
	 */
	private void deleting(String folderPath, LocalDateTime deadLine) throws IOException {
		
		File targetDir = new File(folderPath);
		if(!targetDir.exists() || !targetDir.isDirectory()) {
			return;
		}
		
		File[] files = targetDir.listFiles();
		Path file = null;
		BasicFileAttributes attr = null;
		Date createDate = null;
		LocalDateTime createDateTime = null;
		for(File f : files) {
			if(f.isDirectory()) {
				deleting(f.getAbsolutePath(), deadLine);
			} else {
				file = f.toPath();
				attr = Files.readAttributes(file, BasicFileAttributes.class);
				createDate = new Date(attr.creationTime().toMillis());
				createDateTime = DateTimeUtilCommon.dateToLocalDateTime(createDate);
				if(createDateTime.isBefore(deadLine)) {
					f.delete();
				}
			}
		}
	}
	
}
