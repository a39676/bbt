//package demo.dailySign.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//
//import demo.config.costom_component.SnowFlake;
//import demo.dailySign.pojo.constant.QuQiUrl;
//import demo.dailySign.pojo.type.DailySignCaseType;
//import demo.dailySign.service.QuQiDailySignService;
//import demo.testCase.pojo.po.TestEvent;
//import demo.testCase.pojo.type.TestModuleType;
//
//@Controller
//@RequestMapping(value = QuQiUrl.root)
//public class QuQiController {
//
//	@Autowired
//	private QuQiDailySignService service;
//	
//	@Autowired
//	private SnowFlake snowFlake;
//	
//	@GetMapping(value = QuQiUrl.sign)
//	public void testSign() {
//		TestEvent te = new TestEvent();
//		TestModuleType t = TestModuleType.dailySign;
//		te.setCaseId(DailySignCaseType.quqi.getId());
//		te.setModuleId(t.getId());
//		te.setId(snowFlake.getNextId());
//		te.setEventName(t.getEventName());
//		
//		service.clawing(te);
//	}
//}
