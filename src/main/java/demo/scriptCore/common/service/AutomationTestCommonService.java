package demo.scriptCore.common.service;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;

import demo.selenium.pojo.bo.BuildTestEventBO;
import demo.selenium.service.impl.SeleniumCommonService;
import toolPack.dateTimeHandle.DateTimeUtilCommon;
import toolPack.ioHandle.FileUtilCustom;

public abstract class AutomationTestCommonService extends SeleniumCommonService {

	@Autowired
	private FileUtilCustom ioUtil;

	protected Path savingTestEventDynamicParam(BuildTestEventBO bo, String paramStr) {
		LocalDateTime now = LocalDateTime.now();
		String nowStr = localDateTimeHandler.dateToStr(now, DateTimeUtilCommon.dateTimeFormatNoSymbol);
		File mainFolder = new File(MAIN_FOLDER_PATH + File.separator + "automationTestDynamicParam" + File.separator + bo.getTestModuleType().getModuleName()
				+ File.separator + bo.getFlowName() + File.separator + nowStr);
		if(!mainFolder.exists() || !mainFolder.isDirectory()) {
			mainFolder.mkdirs();
		}
		File newParamFile = new File(mainFolder.getAbsolutePath() + File.separator + bo.getEventId().toString() + ".json");
		try {
			ioUtil.byteToFile(paramStr.getBytes(StandardCharsets.UTF_8), newParamFile.getAbsolutePath());
		} catch (Exception e) {
			log.error(bo.getFlowName() + ", save param file error: " + e.getLocalizedMessage());
		}
		
		return newParamFile.toPath();
	}

}
