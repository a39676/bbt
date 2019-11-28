package demo.bingDemo.service;

import org.springframework.web.servlet.ModelAndView;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.bingDemo.pojo.dto.BingDemoDTO;
import demo.testCase.pojo.po.TestEvent;
import demo.testCase.pojo.result.InsertTestEventResult;

public interface BingDemoService {

	InsertTestEventResult insertclawingEvent(String keyword);

	CommonResultBBT clawing(TestEvent te);

	ModelAndView demo(BingDemoDTO dto);

}
