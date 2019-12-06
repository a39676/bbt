package demo.autoTestBase.testCase.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.autoTestBase.testCase.mapper.TestCaseMapper;
import demo.autoTestBase.testCase.pojo.po.TestCase;
import demo.autoTestBase.testCase.service.TestCaseService;
import demo.baseCommon.service.CommonService;

@Service
public class TestCaseServiceImpl extends CommonService implements TestCaseService {

	@Autowired
	private TestCaseMapper caseMapper;
	
	@Override
	public TestCase findByCaseCode(String caseCode) {
		return caseMapper.findByCaseCode(caseCode);
	}
}
