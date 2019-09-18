package demo.testCase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.baseCommon.service.CommonService;
import demo.testCase.mapper.TestCaseMapper;
import demo.testCase.pojo.po.TestCase;
import demo.testCase.service.TestCaseService;

@Service
public class TestCaseServiceImpl extends CommonService implements TestCaseService {

	@Autowired
	private TestCaseMapper caseMapper;
	
	@Override
	public TestCase findByCaseCode(String caseCode) {
		return caseMapper.findByCaseCode(caseCode);
	}
}
