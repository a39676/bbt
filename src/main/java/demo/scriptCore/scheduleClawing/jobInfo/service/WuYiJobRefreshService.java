package demo.scriptCore.scheduleClawing.jobInfo.service;

import org.springframework.web.servlet.ModelAndView;

import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;

public interface WuYiJobRefreshService {

	TestEventBO clawing(TestEventBO te);

	ModelAndView watchMeList();

}
