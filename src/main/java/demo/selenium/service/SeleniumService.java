package demo.selenium.service;

import demo.selenium.pojo.result.TestEventResult;
import demo.selenium.pojo.testCases.TestCaseDemo;

public interface SeleniumService {

	TestEventResult testDemo(TestCaseDemo testCase);

}
