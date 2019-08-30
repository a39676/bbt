package demo.selenium.service.impl;

import java.io.File;
import java.time.LocalDateTime;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dateTimeHandle.DateTimeHandle;
import demo.baseCommon.pojo.constant.DateTimeConstant;
import demo.baseCommon.service.CommonService;
import demo.selenium.mapper.ScreenshotMapper;
import demo.selenium.pojo.constant.FileSuffixNameConstant;
import demo.selenium.pojo.constant.WebDriverGlobalOption;
import demo.selenium.pojo.dto.ScreenshotSaveDTO;
import demo.selenium.pojo.po.Screenshot;
import demo.selenium.pojo.result.ScreenshotSaveResult;
import demo.selenium.service.ScreenshotService;

@Service
public class ScreenshotServiceImpl extends CommonService implements ScreenshotService {

	@Autowired
	private ScreenshotMapper screenshotMapper;
	
	public ScreenshotSaveResult screenshotSave(ScreenshotSaveDTO dto) {
//		TODO
		/*
		 *.需要将截图上传至云?
		 * 需要将截图的保存路径录入数据库, 必须关联测试事件id
		 */
		ScreenshotSaveResult result = new ScreenshotSaveResult();
		try {
			String targetFolderPath = WebDriverGlobalOption.screenShotSavingFolder + "/" + dto.getEventName() + "/" + dto.getEventId();
			File targetFolder = new File(targetFolderPath);
			if(!targetFolder.exists()) {
				targetFolder.mkdirs();
			}
			String targetFileName = 
					String.format(
							WebDriverGlobalOption.screenSHotFilenameFormat, 
							dto.getFileName(), 
							DateTimeHandle.dateToStr(LocalDateTime.now(), DateTimeConstant.datetimeCompactFormat), 
							FileSuffixNameConstant.png);
			String targetFilePath = targetFolderPath + "/" + targetFileName;
			FileUtils.copyFile(dto.getScreenShotFile(), new File(targetFilePath));
			
			Screenshot i = screenshotMapper.selectByPrimaryKey(1L);
			System.out.println(i.toString());
			Long newScreenShotId = snowFlake.getNextId(); 
			Screenshot po = new Screenshot();
			po.setId(newScreenShotId);
			po.setLocalPath(targetFilePath);
			po.setTestEventId(dto.getEventId());
			screenshotMapper.insertSelective(po);
			
			
			result.setIsSuccess();
			result.setSavingPath(targetFilePath);
			result.setScreenShotId(newScreenShotId);
			log.info("scr save at {}", targetFilePath);
		} catch (Exception e) {
			e.printStackTrace();
			result.failWithMessage(e.toString());
		}
		
		return result;
	}
}
