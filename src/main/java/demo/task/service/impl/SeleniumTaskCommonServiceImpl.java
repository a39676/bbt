package demo.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import demo.autoTestBase.testEvent.service.impl.TestEventServiceImpl;

public abstract class SeleniumTaskCommonServiceImpl  {
	
	@Autowired
	protected TestEventServiceImpl testEventService;
	
}
