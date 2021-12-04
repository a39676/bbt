package demo.autoTestBase.testEvent.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Integer oldDataLiveLimitMonth = 3;
	private Integer eventFailLimitCounting = 3;
	private Integer failCountLiveMinutes = 30;
	private Integer limitOfRunningInTheSameTime = 1;

	private Map<Long, List<LocalDateTime>> failedTestResultMap = new HashMap<>();

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

	public Integer getOldDataLiveLimitMonth() {
		return oldDataLiveLimitMonth;
	}

	public void setOldDataLiveLimitMonth(Integer oldDataLiveLimitMonth) {
		this.oldDataLiveLimitMonth = oldDataLiveLimitMonth;
	}

	public Integer getEventFailLimitCounting() {
		return eventFailLimitCounting;
	}

	public void setEventFailLimitCounting(Integer eventFailLimitCounting) {
		this.eventFailLimitCounting = eventFailLimitCounting;
	}

	public Integer getFailCountLiveMinutes() {
		return failCountLiveMinutes;
	}

	public void setFailCountLiveMinutes(Integer failCountLiveMinutes) {
		this.failCountLiveMinutes = failCountLiveMinutes;
	}

	public Integer getLimitOfRunningInTheSameTime() {
		return limitOfRunningInTheSameTime;
	}

	public void setLimitOfRunningInTheSameTime(Integer limitOfRunningInTheSameTime) {
		this.limitOfRunningInTheSameTime = limitOfRunningInTheSameTime;
	}

	public Map<Long, List<LocalDateTime>> getFailedTestResultMap() {
		return failedTestResultMap;
	}

	public void setFailedTestResultMap(Map<Long, List<LocalDateTime>> failedTestResultMap) {
		this.failedTestResultMap = failedTestResultMap;
	}

	@Override
	public String toString() {
		return "AutomationTestConstantService [optionFilePath=" + optionFilePath + ", breakFlag=" + breakFlag
				+ ", oldDataLiveLimitMonth=" + oldDataLiveLimitMonth + ", eventFailLimitCounting="
				+ eventFailLimitCounting + ", failCountLiveMinutes=" + failCountLiveMinutes
				+ ", limitOfRunningInTheSameTime=" + limitOfRunningInTheSameTime + ", failedTestResultMap="
				+ failedTestResultMap + "]";
	}

}
