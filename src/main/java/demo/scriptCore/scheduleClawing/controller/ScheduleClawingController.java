package demo.scriptCore.scheduleClawing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.autoTestBase.testEvent.pojo.result.InsertTestEventResult;
import demo.scriptCore.scheduleClawing.pojo.constant.ScheduleClawingUrl;
import demo.scriptCore.scheduleClawing.service.WuYiJobRefreshService;
import demo.scriptCore.scheduleClawing.service.impl.MaiMaiScheduleClawingServiceImpl;

@Controller
@RequestMapping(value = ScheduleClawingUrl.root)
public class ScheduleClawingController {

	@Autowired
	private WuYiJobRefreshService wuyiService;
	@Autowired
	private MaiMaiScheduleClawingServiceImpl maiMaiLocalClawingServiceImpl;
	
	@GetMapping(value = ScheduleClawingUrl.insertWuYiSign)
	@ResponseBody
	public InsertTestEventResult insertWuYiSign() {
		return wuyiService.insertClawingEvent();
	}
	
	@GetMapping(value = ScheduleClawingUrl.insertMaiMai)
	@ResponseBody
	public InsertTestEventResult insertMaiMaiLocalClawing() {
		return maiMaiLocalClawingServiceImpl.insertClawingEvent();
	}
	
}
