package demo.movieInteraction.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.movie.pojo.dto.MovieIntroductionDTO;
import demo.movie.pojo.result.FindMovieDetailResult;
import demo.movie.pojo.result.FindMovieSummaryListResult;
import demo.movie.service.HomeFeiClawingService;
import demo.movieInteraction.service.MovieInteractionService;
import movie.pojo.constant.MovieInteractionUrl;
import movie.pojo.dto.FindMovieDetailDTO;
import movie.pojo.dto.FindMovieSummaryListDTO;
import movie.pojo.type.MovieRegionType;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = MovieInteractionUrl.root)
public class MovieInteractionController {

	@Autowired
	private MovieInteractionService movieInteractionService;
	@Autowired
	private HomeFeiClawingService homeFeiClawingService;
	
	@PostMapping(value = MovieInteractionUrl.simpleList)
	@ResponseBody
	public FindMovieSummaryListResult findMovieSummaryList(@RequestBody FindMovieSummaryListDTO dto) {
		return movieInteractionService.findMovieSummaryList(dto);
	}
	
	@PostMapping(value = MovieInteractionUrl.movieDetail)
	@ResponseBody
	public FindMovieDetailResult findMovieDetail(@RequestBody FindMovieDetailDTO dto) {
		return movieInteractionService.findMovieDetail(dto);
	}
	
	@GetMapping(value = MovieInteractionUrl.movieRegionType)
	@ResponseBody
	public JSONArray movieRegionType() {
		JSONObject j = null;
		JSONArray ja = new JSONArray();
		for(MovieRegionType i : MovieRegionType.values()) {
			j = new JSONObject();
			j.put("code", i.getCode());
			j.put("name", i.getName());
			j.put("remark", i.getRemark());
			ja.add(j);
		}
		
		return ja;
	}
	
	@PostMapping(value = MovieInteractionUrl.handleMovieIntroductionRecive)
	@ResponseBody
	public CommonResult handleMovieIntroductionRecive(@RequestBody MovieIntroductionDTO dto) {
		homeFeiClawingService.handleMovieIntroductionRecive(dto);
		CommonResult r = new CommonResult();
		r.setIsSuccess();
		return r;
	}
}
