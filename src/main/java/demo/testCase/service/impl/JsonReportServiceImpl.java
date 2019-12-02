package demo.testCase.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.testCase.mapper.TestEventMapper;
import demo.testCase.pojo.bo.TestReportBO;
import demo.testCase.pojo.dto.FindTestEventPageByConditionDTO;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.service.JsonReportService;

@Service
public class JsonReportServiceImpl extends CommonService implements JsonReportService {

	@Autowired
	private TestEventMapper eventMapper;
	
	@Override
	public List<TestReportBO> findReportsByTestEvent(FindTestEventPageByConditionDTO dto) {
		
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
		bo.setCreateTime(te.getCreateTime());
		bo.setEndTime(te.getEndTime());
		bo.setEventName(te.getEventName());
		bo.setId(te.getId());
		bo.setIsPass(te.getIsPass());
		bo.setModuleId(te.getModuleId());
		bo.setProjectId(te.getProjectId());
		bo.setReportPath(te.getReportPath());
		bo.setStartTime(te.getStartTime());
		
		return bo;
	}

	
}