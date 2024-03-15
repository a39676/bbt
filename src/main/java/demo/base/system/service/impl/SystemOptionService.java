package demo.base.system.service.impl;

import java.io.File;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.config.costomComponent.OptionFilePathConfigurer;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class SystemOptionService {

	private String envName = null;
	private Boolean isDebuging = null;
	private String shutdownKey = null;
	private String cthulhuHostname = null;

	private final Logger log = LoggerFactory.getLogger(getClass());

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

	public boolean isRaspberry() {
		return "raspberry".equals(getEnvName());
	}

	public String getShutdownKey() {
		return shutdownKey;
	}

	public void setShutdownKey(String shutdownKey) {
		this.shutdownKey = shutdownKey;
	}

	public String getCthulhuHostname() {
		return cthulhuHostname;
	}

	public void setCthulhuHostname(String cthulhuHostname) {
		this.cthulhuHostname = cthulhuHostname;
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
			this.cthulhuHostname = json.getString("cthulhuHostname");
			log.error("system constant loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("system option loading error: " + e.getLocalizedMessage());
		}
	}
}
