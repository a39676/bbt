package demo.scriptCore.scheduleClawing.service;

import org.springframework.web.servlet.ModelAndView;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface WuYiJobRefreshService {

	TestEventBO clawing(TestEventBO te);

	ModelAndView watchMeList();

}
