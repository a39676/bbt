package demo.experiment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import autoTest.testEvent.common.pojo.dto.AutomationTestInsertEventDTO;
import autoTest.testEvent.scheduleClawing.pojo.type.ScheduleClawingType;
import autoTest.testModule.pojo.type.TestModuleType;
import demo.autoTestBase.testEvent.pojo.bo.TestEventBO;
import demo.autoTestBase.testEvent.service.TestEventService;
import demo.baseCommon.controller.CommonController;
import demo.experiment.pojo.constant.TestUrl;
import demo.experiment.service.TestService;

@Controller
@RequestMapping(value = { TestUrl.testRoot })
public class TestController extends CommonController {

	@Autowired
	private TestService testService;

	@GetMapping(value = "/test")
	@ResponseBody
	public String test() throws Exception {
		return testService.testing("something");
	}

	@Autowired
	private TestEventService testEventService;

	@GetMapping(value = "/t1")
	@ResponseBody
	public TestEventBO getDailyData() throws Exception {

		AutomationTestInsertEventDTO eventDTO = new AutomationTestInsertEventDTO();
		eventDTO.setTestEventId(0L);
		eventDTO.setTestModuleType(TestModuleType.SCHEDULE_CLAWING.getId());
		eventDTO.setFlowType(ScheduleClawingType.CRYPTO_COIN.getId());

		return testEventService.reciveTestEventAndRun(eventDTO);
	}

//	@Autowired
//	private TmpTaskService tmpTaskService;
//	
//	@GetMapping(value = "/t1")
//	public void t1() {
//		tmpTaskService.t1();
//	}

//	@Autowired
//	private V2exJobInfoCollectionService s;
//	
//	@GetMapping(value = "/t2")
//	@ResponseBody
//	public String t2() {
//		TestEventBO tbo = new TestEventBO();
//		s.clawing(tbo);
//		return ":";
//	}
}
