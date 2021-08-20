package demo.tool.JsonReport.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import autoTest.jsonReport.pojo.bo.TestReportBO;
import autoTest.jsonReport.pojo.dto.FindReportByTestEventIdDTO;
import autoTest.jsonReport.pojo.dto.FindTestEventPageByConditionDTO;
import autoTest.jsonReport.pojo.result.FindReportByTestEventIdResult;
import demo.autoTestBase.testEvent.mapper.TestEventMapper;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.baseCommon.service.CommonService;
import demo.tool.JsonReport.service.JsonReportService;
import toolPack.ioHandle.FileUtilCustom;

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
		
		if(dto.getCreateEndTime() != null) {
			dto.setCreateEndTime(dto.getCreateEndTime().minusSeconds(1L));
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
		bo.setFlowId(te.getFlowId());
		bo.setEndTime(te.getEndTime());
		bo.setEventName(te.getEventName());
		bo.setId(te.getId());
		bo.setIsPass(te.getIsPass());
		bo.setModuleId(te.getModuleId());
		bo.setProjectId(te.getProjectId());
//		bo.setReportPath(te.getReportPath());
		bo.setCreateTime(te.getCreateTime());
		bo.setCreateTimeStr(localDateTimeHandler.dateToStr(te.getCreateTime()));
		bo.setStartTime(te.getStartTime());
		bo.setStartTimeStr(localDateTimeHandler.dateToStr(te.getStartTime()));
		bo.setEndTime(te.getEndTime());
		bo.setEndTimeStr(localDateTimeHandler.dateToStr(te.getEndTime()));
		
		return bo;
	}

	@Override
	public FindReportByTestEventIdResult findReportByTestEventId(FindReportByTestEventIdDTO dto) {
		FindReportByTestEventIdResult r = new FindReportByTestEventIdResult();
		if(dto.getTestEventId() == null) {
			return r;
		}
		
		TestEvent po = eventMapper.selectByPrimaryKey(dto.getTestEventId());
		if(po == null) {
			return r;
		}
		
		r.setId(po.getId());
		r.setCreateTime(po.getCreateTime());
		r.setStartTime(po.getStartTime());
		r.setEndTime(po.getEndTime());
		r.setTitle(po.getEventName());
		
		if(StringUtils.isNotBlank(po.getReportPath())) {
			String reportStr = ioUtil.getStringFromFile(po.getReportPath());
			r.setReportStr(reportStr);
		}
		
		r.setIsSuccess();
		
		return r;
	}
}