package demo.testCase.service;

import demo.testCase.pojo.bo.TestEventBO;
import demo.testCase.pojo.po.TestEvent;

public interface TestEventService {

	Integer insertSelective(TestEvent po);

	TestEvent runNewTestEvent(TestEventBO bo);

	Integer endTestEvent(TestEvent po, boolean success, String remark);

}
