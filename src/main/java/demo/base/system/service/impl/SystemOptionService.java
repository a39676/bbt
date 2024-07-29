package demo.base.system.service.impl;

import java.io.File;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import demo.config.customComponent.OptionFilePathConfigurer;
import jakarta.annotation.PostConstruct;
import net.sf.json.JSONObject;
import toolPack.ioHandle.FileUtilCustom;

@Service
public class SystemOptionService {

	private final Logger log = LoggerFactory.getLogger(getClass());

	private String envName = null;
	private Boolean isDebuging = null;
	private String shutdownKey = null;
	private String cthulhuHostname = null;
	private String worker1Hostname = null;
	private String ip = null;

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

	public String getWorker1Hostname() {
		return worker1Hostname;
	}

	public void setWorker1Hostname(String worker1Hostname) {
		this.worker1Hostname = worker1Hostname;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
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
//			SystemOptionService tmp = new Gson().fromJson(jsonStr, this.getClass());
//			BeanUtils.copyProperties(tmp, this);
			JSONObject json = JSONObject.fromObject(jsonStr);
			this.envName = json.getString("envName");
			this.isDebuging = json.getBoolean("isDebuging");
			this.shutdownKey = json.getString("shutdownKey");
			this.cthulhuHostname = json.getString("cthulhuHostname");
			this.worker1Hostname = json.getString("worker1Hostname");
			log.error("system constant loaded");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("system option loading error: " + e.getLocalizedMessage());
		}
	}

	@Override
	public String toString() {
		return "SystemOptionService [log=" + log + ", envName=" + envName + ", isDebuging=" + isDebuging
				+ ", shutdownKey=" + shutdownKey + ", cthulhuHostname=" + cthulhuHostname + ", worker1Hostname="
				+ worker1Hostname + ", ip=" + ip + "]";
	}

}
