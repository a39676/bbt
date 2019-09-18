package demo.testCase.service;

import demo.testCase.pojo.po.TestCase;

public interface TestCaseService {

	TestCase findByCaseCode(String caseCode);

}
