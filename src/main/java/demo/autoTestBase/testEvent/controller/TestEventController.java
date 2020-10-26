package demo.autoTestBase.testEvent.controller;

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
		return teService.checkExistsRuningEvent();
	}
	
	@GetMapping(value = "/fixRuningEventStatusManual")
	@ResponseBody
	public String fixRuningEventStatusManual() {
		teService.fixRuningEventStatusManual();
		return "done";
	}

}
