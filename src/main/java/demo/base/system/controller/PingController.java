package demo.base.system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/ping")
public class PingController extends ExceptionController {
	
	@GetMapping(value = "/ping")
	@ResponseBody
	public String ping() {
		return "pong";
	}
	
}
