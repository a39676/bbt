package demo.autoTestBase.testEvent.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import demo.autoTestBase.testEvent.mapper.TestEventMapper;
import demo.autoTestBase.testEvent.pojo.constant.TestEventOptionConstant;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.baseCommon.service.CommonService;
import toolPack.ioHandle.FileUtilCustom;

public abstract class TestEventCommonService extends CommonService {

	@Autowired
	protected TestEventMapper eventMapper;
	@Autowired
	protected FileUtilCustom fileUtil;
	
	protected String runningEventRedisKey = "runningEvent";

	protected String findTestEventReportFolder() {
		String windowFolder = TestEventOptionConstant.windowFolder;
		String linuxFolder = TestEventOptionConstant.linuxFolder;
		File f = null;
		try {
			if(isWindows()) {
				f = new File(windowFolder);
				if(!f.exists()) {
					if(!f.mkdirs()) {
						return null;
					}
				}
				return windowFolder;
			} else if(isLinux()) {
				f = new File(linuxFolder);
				if(!f.exists()) {
					if(!f.mkdirs()) {
						return null;
					}
				}
				return linuxFolder;
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	protected void startEvent(TestEvent te) {
		te.setStartTime(LocalDateTime.now());
		constantService.setValByName(runningEventRedisKey, "true");
	}
	
	protected CommonResultBBT endEvent(TestEvent te, boolean successFlag) {
		return endEvent(te, successFlag, null);
	}
	
	protected CommonResultBBT endEvent(TestEvent te, boolean successFlag, String report) {
		
		CommonResultBBT endEventResult = new CommonResultBBT();
		
		try {
			te.setEndTime(LocalDateTime.now());
			te.setIsPass(successFlag);
			
			if(StringUtils.isNotBlank(report)) {
				String folerPath = findTestEventReportFolder();
				if(folerPath != null) {
					saveTestEventReport(te, folerPath, report);
				}
			}
			constantService.setValByName(runningEventRedisKey, "false");
			int insertCount = eventMapper.insertSelective(te);
			
			if(insertCount > 0) {
				endEventResult.setIsSuccess();
			}
		} catch (Exception e) {
		}
		
		return endEventResult;
	}
	
	private void saveTestEventReport(TestEvent te, String folerPath, String report) {
		fileUtil.byteToFile(report.getBytes(StandardCharsets.UTF_8), folerPath + "/" + te.getId(), true);
	}
	
	protected boolean existsRuningEvent() {
		String runningEventStatus = constantService.getValByName(runningEventRedisKey);
		if("false".equals(runningEventStatus)) {
			return false;
		}
		return true;
	}

}
