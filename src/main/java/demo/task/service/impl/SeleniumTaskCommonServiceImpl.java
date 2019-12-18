package demo.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import demo.autoTestBase.testEvent.service.impl.TestEventServiceImpl;
import demo.baseCommon.service.CommonService;

public abstract class SeleniumTaskCommonServiceImpl extends CommonService  {
	
	@Autowired
	protected TestEventServiceImpl testEventService;
	
}
