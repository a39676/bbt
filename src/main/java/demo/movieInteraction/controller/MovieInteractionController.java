package demo.movieInteraction.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.movie.pojo.result.FindMovieDetailResult;
import demo.movie.pojo.result.FindMovieSummaryListResult;
import demo.movieInteraction.service.MovieInteractionService;
import movie.pojo.constant.MovieInteractionUrl;
import movie.pojo.dto.FindMovieDetailDTO;
import movie.pojo.dto.FindMovieSummaryListDTO;
import movie.pojo.type.MovieRegionType;

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
	
	@PostMapping(value = MovieInteractionUrl.movieDetail)
	@ResponseBody
	public FindMovieDetailResult findMovieDetail(@RequestBody FindMovieDetailDTO dto) {
		return service.findMovieDetail(dto);
	}
	
	@GetMapping(value = "/movieRegionType")
	@ResponseBody
	public String movieRegionType() {
		Map<String, String> l = new HashMap<String, String>();
		for(MovieRegionType i : MovieRegionType.values()) {
			l.put(i.getCode().toString(), i.getName());
		}
		return l.toString();
	}
}
