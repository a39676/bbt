package demo.testCase.service.impl;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import demo.baseCommon.service.CommonService;
import demo.testCase.mapper.TestEventMapper;
import demo.testCase.pojo.po.TestEvent;
import ioHandle.FileUtilCustom;

public abstract class TestEventCommonService extends CommonService {

	@Autowired
	protected TestEventMapper eventMapper;
	@Autowired
	protected FileUtilCustom fileUtil;
	
	protected boolean enableMultipleTestEvent = false;
	protected int multipleRunTestEventCount = 1;
	
	protected String findTestEventReportFolder() {
		String windowFolder = "d:\\auxiliary\\testEventReport";
		String linuxFolder = "/home/u2/testEventReport";
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
		return eventMapper.insertSelective(te);
	}
	
//	protected int endEventSuccess(TestEvent te, String report) {
//		return endEvent(te, true, report);
//	}
//	
//	protected int endEventSuccess(TestEvent te) {
//		return endEvent(te, true, null);
//	}
//	
//	protected int endEventFail(TestEvent te, String report) {
//		return endEvent(te, false, report);
//	}
//	
//	protected int endEventFail(TestEvent te) {
//		return endEvent(te, false, null);
//	}
	protected int endEvent(TestEvent te, boolean successFlag) {
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
		if(eventMapper.existsRuningEvent() == 0) {
			return false;
		} 
		return true;
	}

}
