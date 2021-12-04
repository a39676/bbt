package demo.autoTestBase.testEvent.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.autoTestBase.testEvent.service.TestEventService;

@Controller
@RequestMapping(value = "/testEvent")
public class TestEventController {
	
	@Autowired
	private TestEventService teService;
	
	@GetMapping(value = "/checkExistsRuningEvent")
	@ResponseBody
	public boolean checkExistsRuningEvent() {
		teService.getRunningEventNameList();
		return teService.checkExistsRuningEvent();
	}
	
	@GetMapping(value = "/getRunningEventNameList")
	@ResponseBody
	public List<String> getRunningEventNameList() {
		return teService.getRunningEventNameList();
	}
	
	@GetMapping(value = "/fixRuningEventStatusManual")
	@ResponseBody
	public String fixRuningEventStatusByManual() {
		teService.fixRuningEventStatusByManual();
		return "done";
	}

}
