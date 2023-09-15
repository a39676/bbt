package demo.scriptCore.localClawing.hades.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.scriptCore.localClawing.hades.ui.service.HadesCases;

@Controller
@RequestMapping(value = "/hades")
public class HadesCasesController {

	@Autowired
	private HadesCases hadesCases;

	@GetMapping(value = "/login")
	@ResponseBody
	public String login() {
		hadesCases.login();
		return "done";
	}
	
	@GetMapping(value = "/test")
	@ResponseBody
	public String test() {
		hadesCases.test();
		return "done";
	}
}
