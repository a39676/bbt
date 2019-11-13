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
import demo.baseCommon.service.CommonService;
import demo.selenium.pojo.constant.SeleniumConstant;
import demo.selenium.service.OldDataDeleteService;
import demo.selenium.service.SeleniumGlobalOptionService;

@Service
public class OldDataDeleteServiceImpl extends CommonService implements OldDataDeleteService {

	@Autowired
	private SeleniumGlobalOptionService globalOptionService;
	
	@Override
	public void deleteOldDownload() throws IOException {
		LocalDateTime oldHistoryLimit = LocalDateTime.now().minusMonths(SeleniumConstant.maxHistoryMonth);
		
		String downloadDirPath = globalOptionService.getDownloadDir();
		File downloadDir = new File(downloadDirPath);
		File[] torrents = downloadDir.listFiles();
		Path file = null;
		BasicFileAttributes attr = null;
		Date createDate = null;
		LocalDateTime createDateTime = null;
		for(File f : torrents) {
			file = f.toPath();
			attr = Files.readAttributes(file, BasicFileAttributes.class);
			createDate = new Date(attr.creationTime().toMillis());
			createDateTime = DateTimeUtilCommon.dateToLocalDateTime(createDate);
			if(createDateTime.isBefore(oldHistoryLimit)) {
				f.delete();
			}
		}
		
	}
	
	@Override
	public void deleteOldScreenshot() throws IOException {
		LocalDateTime oldHistoryLimit = LocalDateTime.now().minusMonths(SeleniumConstant.maxHistoryMonth);
		
		String downloadDirPath = globalOptionService.getScreenshotSavingFolder();
		File downloadDir = new File(downloadDirPath);
		File[] torrents = downloadDir.listFiles();
		Path file = null;
		BasicFileAttributes attr = null;
		Date createDate = null;
		LocalDateTime createDateTime = null;
		for(File f : torrents) {
			file = f.toPath();
			attr = Files.readAttributes(file, BasicFileAttributes.class);
			createDate = new Date(attr.creationTime().toMillis());
			createDateTime = DateTimeUtilCommon.dateToLocalDateTime(createDate);
			if(createDateTime.isBefore(oldHistoryLimit)) {
				f.delete();
			}
		}
		
	}
	
}
