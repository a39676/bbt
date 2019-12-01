package demo.task.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import demo.testCase.service.impl.TestEventServiceImpl;

public abstract class SeleniumTaskCommonServiceImpl  {
	
	@Autowired
	protected TestEventServiceImpl testEventService;
	
}
