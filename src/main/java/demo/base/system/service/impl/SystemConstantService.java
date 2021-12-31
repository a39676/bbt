package demo.base.system.service.impl;

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
public class SystemConstantService extends CommonService {

	@Value("${optionFilePath.system}")
	private String optionFilePath;

	private String envName = null;
	private Boolean isDebuging = null;
	private String shutdownKey = null;

	public String getEnvName() {
		return envName;
	}

	public void setEnvName(String envName) {
		this.envName = envName;
	}

	public Boolean getIsDebuging() {
		return isDebuging;
	}

	public void setIsDebuging(Boolean isDebuging) {
		this.isDebuging = isDebuging;
	}

	public String getShutdownKey() {
		return shutdownKey;
	}

	public void setShutdownKey(String shutdownKey) {
		this.shutdownKey = shutdownKey;
	}

	public void refreshConstant() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			SystemConstantService tmp = new Gson().fromJson(jsonStr, SystemConstantService.class);
			BeanUtils.copyProperties(tmp, this);
		} catch (Exception e) {
			log.error("system constant loading error: " + e.getLocalizedMessage());
		}
	}
}
