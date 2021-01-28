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

import demo.autoTestBase.captcha.service.CaptchaService;
import demo.baseCommon.service.CommonService;
import demo.selenium.service.OldDataDeleteService;
import demo.selenium.service.SeleniumGlobalOptionService;
import selenium.pojo.constant.SeleniumConstant;

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
		Path filePath = null;
		BasicFileAttributes attr = null;
		Date createDate = null;
		LocalDateTime createDateTime = null;
		for(File file : files) {
			if(file.isDirectory()) {
				log.error("find folder: " + file.getName());
				deleting(file.getAbsolutePath(), deadLine);
			} else {
				filePath = file.toPath();
				log.error("find file: " + filePath.toAbsolutePath().toString());
				attr = Files.readAttributes(filePath, BasicFileAttributes.class);
				createDate = new Date(attr.creationTime().toMillis());
				createDateTime = dateHandler.dateToLocalDateTime(createDate);
				log.error("get create date: " + createDateTime);
				if(createDateTime.isBefore(deadLine)) {
					System.out.println("file delete result: " + file.delete());
				}
			}
		}
	}
	
}
