package demo.autoTestBase.testCase.service;

import demo.autoTestBase.testCase.pojo.po.TestCase;

public interface TestCaseService {

	TestCase findByCaseCode(String caseCode);

}
