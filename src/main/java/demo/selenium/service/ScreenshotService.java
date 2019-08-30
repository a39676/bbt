package demo.selenium.service;

import demo.selenium.pojo.dto.ScreenshotSaveDTO;
import demo.selenium.pojo.result.ScreenshotSaveResult;

public interface ScreenshotService {
	
	/**
	 * 保存测试过程中的截图
	 * @param dto
	 * @return
	 */
	ScreenshotSaveResult screenshotSave(ScreenshotSaveDTO dto);
}
