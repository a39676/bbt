package demo.clawing.scheduleClawing.service;

import org.springframework.web.servlet.ModelAndView;

import demo.autoTestBase.testEvent.pojo.po.TestEvent;
import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.baseCommon.pojo.result.CommonResultBBT;

public interface WuYiJobRefreshService {

	InsertTestEventResult insertClawingEvent();

	CommonResultBBT clawing(TestEvent te);

	ModelAndView watchMeList();

}
