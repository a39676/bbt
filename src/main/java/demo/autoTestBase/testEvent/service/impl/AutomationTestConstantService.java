package demo.autoTestBase.testEvent.service.impl;

import java.io.File;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import net.sf.json.JSONObject;
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
			this.breakFlag = json.getBoolean("breakFlag");
			this.oldDataLiveLimitMonth = json.getInt("oldDataLiveLimitMonth");
			this.eventFailLimitCounting = json.getInt("eventFailLimitCounting");
			this.failCountLiveMinutes = json.getInt("failCountLiveMinutes");
			this.limitOfRunningInTheSameTime = json.getInt("limitOfRunningInTheSameTime");
//			AutomationTestConstantService tmp = buildObjFromJsonCustomization(jsonStr, AutomationTestConstantService.class);
//			BeanUtils.copyProperties(tmp, this);
			log.error("automation test option loaded");
		} catch (Exception e) {
			log.error("automation test option loading error: " + e.getLocalizedMessage());
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
	
	public void addFailedTestResult(Long eventId) {
		if (failedTestResultMap.containsKey(eventId)) {
			failedTestResultMap.get(eventId).add(LocalDateTime.now());
		} else {
			List<LocalDateTime> list = new ArrayList<>();
			list.add(LocalDateTime.now());
			failedTestResultMap.put(eventId, list);
		}
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
