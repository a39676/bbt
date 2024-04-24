package demo.tool.service.impl;

import java.io.File;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.config.customComponent.OptionFilePathConfigurer;
import jakarta.annotation.PostConstruct;
import toolPack.ioHandle.FileUtilCustom;

@Scope("singleton")
@Service
public class CloudFlareOptionService extends CommonService {

	private String host;
	private String zonesApiRoot;
	private String dnsApiUrl;
	private String zoneId;
	private String targetHost;
	private String email;
	private String key;

	@PostConstruct
	public void refreshConstant() {
		File optionFile = new File(OptionFilePathConfigurer.CLOUD_FLARE);
		if (!optionFile.exists()) {
			return;
		}
		try {
			FileUtilCustom fileUtil = new FileUtilCustom();
			String jsonStr = fileUtil.getStringFromFile(OptionFilePathConfigurer.CLOUD_FLARE);
			CloudFlareOptionService tmp = buildObjFromJsonCustomization(jsonStr, this.getClass());
			BeanUtils.copyProperties(tmp, this);
			log.error("Cloud flare option loaded");
		} catch (Exception e) {
			log.error("Cloud flare option loading error: " + e.getLocalizedMessage());
		}
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getZonesApiRoot() {
		return zonesApiRoot;
	}

	public void setZonesApiRoot(String zonesApiRoot) {
		this.zonesApiRoot = zonesApiRoot;
	}

	public String getDnsApiUrl() {
		return dnsApiUrl;
	}

	public void setDnsApiUrl(String dnsApiUrl) {
		this.dnsApiUrl = dnsApiUrl;
	}

	public String getZoneId() {
		return zoneId;
	}

	public void setZoneId(String zoneId) {
		this.zoneId = zoneId;
	}

	public String getTargetHost() {
		return targetHost;
	}

	public void setTargetHost(String targetHost) {
		this.targetHost = targetHost;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String toString() {
		return "CloudFlareOptionService [host=" + host + ", zonesApiRoot=" + zonesApiRoot + ", dnsApiUrl=" + dnsApiUrl
				+ ", zoneId=" + zoneId + ", targetHost=" + targetHost + ", email=" + email + ", key=" + key + "]";
	}

}
