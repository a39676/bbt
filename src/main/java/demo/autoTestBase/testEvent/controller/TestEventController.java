package demo.autoTestBase.testEvent.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import demo.autoTestBase.testEvent.service.TestEventService;

@Controller
@RequestMapping(value = "/testEvent")
public class TestEventController {
	
	@SuppressWarnings("unused")
	@Autowired
	private TestEventService teService;

}
