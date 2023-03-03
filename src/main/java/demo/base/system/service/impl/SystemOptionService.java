package demo.base.system.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class SystemOptionService extends CommonService {

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
	
	public boolean isDev() {
		return "dev".equals(getEnvName());
	}

	public String getShutdownKey() {
		return shutdownKey;
	}

	public void setShutdownKey(String shutdownKey) {
		this.shutdownKey = shutdownKey;
	}

	@PostConstruct
	public void refreshConstant() {
		File optionFile = new File(optionFilePath);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(optionFilePath);
			/*
			 * TODO 2023-03-03
			 * Temporarily unsure of the cause of the Gson mapping exception, temporarily replaced it with manual construction.
			 */
			JSONObject json = JSONObject.fromObject(jsonStr);
			this.envName = json.getString("envName");
			this.isDebuging = json.getBoolean("isDebuging");
			this.shutdownKey = json.getString("shutdownKey");
//			SystemOptionService tmp = new Gson().fromJson(jsonStr, SystemOptionService.class);
//			BeanUtils.copyProperties(tmp, this);
			log.error("system constant loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("system option loading error: " + e.getLocalizedMessage());
		}
	}
}
