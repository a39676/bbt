package demo.autoTestBase.testEvent.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import demo.autoTestBase.testEvent.mapper.TestEventMapper;
import demo.autoTestBase.testEvent.pojo.constant.TestEventOptionConstant;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
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
			// TODO: handle exception
		}
		return null;
	}
	
	protected int insertEventQueue(TestEvent te) {
		return eventMapper.insertSelective(te);
	}
	
	protected List<TestEvent> findTestEventNotRunYet() {
		return eventMapper.findTestEventNotRunYet();
	}
	
	protected int startEvent(TestEvent te) {
		te.setStartTime(LocalDateTime.now());
		constantService.setValByName(runningEventRedisKey, "true");
		return eventMapper.updateByPrimaryKeySelective(te);
	}
	
	protected int endEvent(TestEvent te, boolean successFlag) {
		constantService.setValByName(runningEventRedisKey, "false");
		return endEvent(te, successFlag, null);
	}
	
	protected int endEvent(TestEvent te, boolean successFlag, String report) {
		te.setEndTime(LocalDateTime.now());
		te.setIsPass(successFlag);
		
		if(StringUtils.isNotBlank(report)) {
			String folerPath = findTestEventReportFolder();
			if(folerPath != null) {
				saveTestEventReport(te, folerPath, report);
			}
		}
		
		return eventMapper.updateByPrimaryKeySelective(te);
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
