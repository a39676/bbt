package demo.base.system.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.config.costomComponent.OptionFilePathConfigurer;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class SystemOptionService extends CommonService {

	private String envName = null;
	private Boolean isDebuging = null;
	private String shutdownKey = null;
	private String cxHostname = null;

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

	public String getCxHostname() {
		return cxHostname;
	}

	public void setCxHostname(String cxHostname) {
		this.cxHostname = cxHostname;
	}

	@PostConstruct
	public void refreshConstant() {
		File optionFile = new File(OptionFilePathConfigurer.SYSTEM);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.SYSTEM);
			JSONObject json = JSONObject.fromObject(jsonStr);
			this.envName = json.getString("envName");
			this.isDebuging = json.getBoolean("isDebuging");
			this.shutdownKey = json.getString("shutdownKey");
			this.cxHostname = json.getString("cxHostname");
			log.error("system constant loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("system option loading error: " + e.getLocalizedMessage());
		}
	}
}
