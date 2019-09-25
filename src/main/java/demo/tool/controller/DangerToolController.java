package demo.tool.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.baseCommon.controller.CommonController;
import demo.tool.mapper.DangerMapper;

@Controller
@RequestMapping(value = "/danger")
public class DangerToolController extends CommonController {

	@Autowired
	private DangerMapper dangerMapper;

	@GetMapping(value = "/deleteAllMovieInfo")
	@ResponseBody
	public String deleteAllMovieInfo() {
		dangerMapper.deleteAllMovieInfo();
		dangerMapper.deleteAllMovieIntroduction();
		dangerMapper.deleteAllMovieMagnetUrl();
		dangerMapper.deleteAllMovieRecord();
		return "done";
	}
}
