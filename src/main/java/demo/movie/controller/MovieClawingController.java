package demo.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.movie.service.DyttClawingService;
import demo.movie.service.HomeFeiClawingService;

@Controller
@RequestMapping(value = "/movieClawing")
public class MovieClawingController {

	@Autowired
	private DyttClawingService dytt;
	@Autowired
	private HomeFeiClawingService homeFei;
	
	@GetMapping(value = "/dytt")
	@ResponseBody
	public String dytt() {
		dytt.clawing();
		return "done";
	}
	
	@GetMapping(value = "/homeFei")
	@ResponseBody
	public String homeFei() {
		homeFei.clawing();
		return "done";
	}
	
	@GetMapping(value = "/test")
	@ResponseBody
	public String test() {
		homeFei.test();
		return "done";
	}
}
