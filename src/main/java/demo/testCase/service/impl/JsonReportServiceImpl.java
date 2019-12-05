package demo.testCase.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.jsonReport.pojo.bo.TestReportBO;
import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.jsonReport.pojo.result.FindReportByTestEventIdResult;
import dateTimeHandle.DateTimeHandle;
import demo.baseCommon.service.CommonService;
import demo.testCase.mapper.TestEventMapper;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.service.JsonReportService;
import ioHandle.FileUtilCustom;

@Service
public class JsonReportServiceImpl extends CommonService implements JsonReportService {

	@Autowired
	private TestEventMapper eventMapper;
	@Autowired
	private FileUtilCustom ioUtil;
	
	private Long reportPageMinSize = 3L;
	private Long reportPageNormalSize = 10L;
	private Long reportPageMaxSize = 50L;
	
	@Override
	public List<TestReportBO> findReportsByCondition(FindTestEventPageByConditionDTO dto) {
		
		if(dto.getLimit() != null ) {
			if(dto.getLimit() > reportPageMaxSize) {
				dto.setLimit(reportPageMaxSize);
			} else if(dto.getLimit() < reportPageMinSize) {
				dto.setLimit(reportPageMinSize);
			}
		} else {
			dto.setLimit(reportPageNormalSize);
		}
		
		if(dto.getEndTime() != null) {
			dto.setEndTime(dto.getEndTime().minusSeconds(1L));
		}
		
		List<TestEvent> poList = eventMapper.findTestEventPageByCondition(dto);
		List<TestReportBO> boList = new ArrayList<>();
		for(TestEvent i : poList) {
			boList.add(buildBOByPO(i));
		}
		
		return boList;
	}
	
	private TestReportBO buildBOByPO(TestEvent te) {
		TestReportBO bo = new TestReportBO();
		bo.setCaseId(te.getCaseId());
		bo.setEndTime(te.getEndTime());
		bo.setEventName(te.getEventName());
		bo.setId(te.getId());
		bo.setIsPass(te.getIsPass());
		bo.setModuleId(te.getModuleId());
		bo.setProjectId(te.getProjectId());
//		bo.setReportPath(te.getReportPath());
		bo.setCreateTime(te.getCreateTime());
		bo.setCreateTimeStr(DateTimeHandle.dateToStr(te.getCreateTime()));
		bo.setStartTime(te.getStartTime());
		bo.setStartTimeStr(DateTimeHandle.dateToStr(te.getStartTime()));
		bo.setEndTime(te.getEndTime());
		bo.setEndTimeStr(DateTimeHandle.dateToStr(te.getEndTime()));
		
		return bo;
	}

	@Override
	public FindReportByTestEventIdResult findReportByTestEventId(FindReportByTestEventIdDTO dto) {
		FindReportByTestEventIdResult r = new FindReportByTestEventIdResult();
		if(dto.getTestEventId() == null) {
			r.setReportStr("报告不存在, 可能是此报告已过期删除");
			return r;
		}
		
		TestEvent po = eventMapper.selectByPrimaryKey(dto.getTestEventId());
		if(po == null || StringUtils.isBlank(po.getReportPath()) || "null".equals(po.getReportPath())) {
			r.setReportStr("报告不存在, 可能是以下情况之一(测试任务未运行或运行中, 此报告已过期删除)");
			return r;
		}
		
		String reportStr = ioUtil.getStringFromFile(po.getReportPath());
		r.setReportStr(reportStr);
		r.setIsSuccess();
		
		return r;
	}
}