package demo.autoTestBase.testEvent.service.impl;

import java.io.File;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;

import demo.baseCommon.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class AutomationTestConstantService extends CommonService {

	@Value("${optionFilePath.automationTest}")
	private String optionFilePath;

	private Boolean breakFlag = false;

	public void refreshConstant() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			AutomationTestConstantService tmp = new Gson().fromJson(jsonStr, AutomationTestConstantService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("automation test constant loading error: " + e.getLocalizedMessage());
		}
	}

	public Boolean getBreakFlag() {
		return breakFlag;
	}

	public void setBreakFlag(Boolean breakFlag) {
		this.breakFlag = breakFlag;
	}

	@Override
	public String toString() {
		return "AutomationTestConstantService [optionFilePath=" + optionFilePath + ", breakFlag=" + breakFlag + "]";
	}

}
