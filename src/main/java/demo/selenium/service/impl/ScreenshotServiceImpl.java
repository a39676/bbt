//package demo.selenium.service.impl;
//
//import java.io.File;
//import java.time.LocalDateTime;
//
//import org.apache.commons.io.FileUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import at.pojo.constant.WebDriverConstant;
//import at.pojo.dto.ScreenshotSaveDTO;
//import at.pojo.result.ScreenshotSaveResult;
//import constant.FileSuffixNameConstant;
//import dateTimeHandle.DateTimeHandle;
//import demo.baseCommon.pojo.constant.DateTimeConstant;
//import demo.selenium.mapper.ScreenshotMapper;
//import demo.selenium.pojo.po.Screenshot;
//import demo.selenium.service.SeleniumGlobalOptionService;
//
//@Service
//public class ScreenshotServiceImpl  {
//
//	@Autowired
//	private SeleniumGlobalOptionService globalOptionService;
//	@Autowired
//	private ScreenshotMapper screenshotMapper;
//	
//	public ScreenshotSaveResult screenshotSave(ScreenshotSaveDTO dto) {
//		String targetFolderPath = globalOptionService.getScreenshotSavingFolder() + "/" + dto.getEventName() + "/" + dto.getEventId();
//		return screenshotSave(dto, targetFolderPath);
//	}
//	
//	public ScreenshotSaveResult captchaScreenshotSave(ScreenshotSaveDTO dto) {
//		String targetFolderPath = globalOptionService.getCaptchaScreenshotSavingFolder() + "/" + dto.getEventName() + "/" + dto.getEventId();
//		return screenshotSave(dto, targetFolderPath);
//	}
//	
//	private ScreenshotSaveResult screenshotSave(ScreenshotSaveDTO dto, String targetFolderPath) {
////		TODO
//		/*
//		 *.需要将截图上传至云?
//		 * 需要将截图的保存路径录入数据库, 必须关联测试事件id
//		 */
//		ScreenshotSaveResult result = new ScreenshotSaveResult();
//		try {
//			File targetFolder = new File(targetFolderPath);
//			if(!targetFolder.exists()) {
//				targetFolder.mkdirs();
//			}
//			String targetFileName = 
//					String.format(
//							WebDriverConstant.screenShotFilenameFormat, 
//							dto.getFileName(), 
//							DateTimeHandle.dateToStr(LocalDateTime.now(), DateTimeConstant.datetimeCompactFormat), 
//							FileSuffixNameConstant.png);
//			String targetFilePath = targetFolderPath + "/" + targetFileName;
//			FileUtils.copyFile(dto.getScreenShotFile(), new File(targetFilePath));
//			
//			Long newScreenShotId = snowFlake.getNextId(); 
//			Screenshot po = new Screenshot();
//			po.setId(newScreenShotId);
//			po.setLocalPath(targetFilePath);
//			po.setTestEventId(dto.getEventId());
//			screenshotMapper.insertSelective(po);
//			
//			result.setIsSuccess();
//			result.setSavingPath(targetFilePath);
//			result.setScreenShotId(newScreenShotId);
//		} catch (Exception e) {
//			e.printStackTrace();
//			result.failWithMessage(e.toString());
//		}
//		
//		return result;
//	}
//}
