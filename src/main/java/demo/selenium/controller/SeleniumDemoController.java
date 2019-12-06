package demo.selenium.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.selenium.service.SeForJoke;

@Controller
@RequestMapping("/se")
public class SeleniumDemoController {

	@Autowired
	private SeForJoke joke;
	
	@GetMapping("/test")
	@ResponseBody
	public String test() {
		joke.test();
		return "hi";
	}
}
