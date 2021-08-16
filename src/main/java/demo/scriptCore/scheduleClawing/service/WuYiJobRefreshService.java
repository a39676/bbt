package demo.scriptCore.scheduleClawing.service;

import org.springframework.web.servlet.ModelAndView;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;

public interface WuYiJobRefreshService {

	InsertTestEventResult insertClawingEvent();

	CommonResult clawing(TestEvent te);

	ModelAndView watchMeList();

}
