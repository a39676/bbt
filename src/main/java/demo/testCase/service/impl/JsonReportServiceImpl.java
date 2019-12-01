package demo.testCase.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.testCase.mapper.TestEventMapper;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.po.TestEventExample;
import demo.testCase.pojo.po.TestEventExample.Criteria;
import demo.testCase.service.JsonReportService;

@Service
public class JsonReportServiceImpl extends CommonService implements JsonReportService {

	@Autowired
	private TestEventMapper eventMapper;
	
	public List<TestEvent> findReportsByTestEvent(TestEvent te) {
//		TODO
		TestEventExample teExample = new TestEventExample();
		Criteria c = teExample.createCriteria();
		c.andIsDeleteEqualTo(false);
		c.andReportPathIsNotNull();
		if(te.getModuleId() != null) {
			c.andModuleIdEqualTo(te.getModuleId());
		}
		if(te.getId() != null) {
			c.andIdEqualTo(te.getId());
		}
		if(StringUtils.isNotBlank(te.getEventName())) {
			c.andEventNameLike(te.getEventName());
		}
		if(StringUtils.isNotBlank(te.getReportPath())) {
			c.andReportPathEqualTo(te.getReportPath());
		}
		
		return eventMapper.selectByExample(teExample);
	}
}
