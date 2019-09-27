package demo.movie.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.movie.pojo.result.FindMovieSummaryListResult;
import demo.movie.service.MovieInteractionService;
import movie.pojo.constant.MovieInteractionUrl;
import movie.pojo.dto.FindMovieSummaryListDTO;

@Controller
@RequestMapping(value = MovieInteractionUrl.root)
public class MovieInteractionController {

	@Autowired
	private MovieInteractionService service;
	
	@PostMapping(value = MovieInteractionUrl.simpleList)
	@ResponseBody
	public FindMovieSummaryListResult findMovieSummaryList(@RequestBody FindMovieSummaryListDTO dto) {
		return service.findMovieSummaryList(dto);
	}
	
}
