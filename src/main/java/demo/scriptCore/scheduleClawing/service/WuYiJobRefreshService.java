package demo.scriptCore.scheduleClawing.service;

import org.springframework.web.servlet.ModelAndView;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;

public interface WuYiJobRefreshService {

	InsertTestEventResult insertClawingEvent();

	TestEventBO clawing(TestEvent te);

	ModelAndView watchMeList();

}
