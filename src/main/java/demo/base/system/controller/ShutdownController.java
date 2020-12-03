package demo.base.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.base.system.service.ShutdownService;

@Controller
@RequestMapping(value = "/sd")
public class ShutdownController {

	@Autowired
	private ShutdownService shutdownService;

	@GetMapping("/sd")
	@ResponseBody
	public String shutdownContext(@RequestParam(value = "key") String key) {
		return shutdownService.shutdownContext(key);

	}

}
