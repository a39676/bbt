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
	
//	@GetMapping(value = "/dytt")
//	@ResponseBody
//	public String dytt() {
//		dytt.clawing();
//		return "done";
//	}
//	
//	@GetMapping(value = "/homeFeiCollection")
//	@ResponseBody
//	public String homeFeiCollection() {
//		return homeFei.collection();
//	}
//	
//	@GetMapping(value = "/homeFeiDownload")
//	@ResponseBody
//	public String homeFeiDownload() {
//		return homeFei.download();
//	}
	
}
