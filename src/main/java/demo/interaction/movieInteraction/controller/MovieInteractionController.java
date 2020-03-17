package demo.interaction.movieInteraction.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import demo.clawing.movie.pojo.result.FindMovieDetailResult;
import demo.clawing.movie.pojo.result.FindMovieSummaryListResult;
import demo.interaction.movieInteraction.pojo.result.FindMovieRecommendResult;
import demo.interaction.movieInteraction.service.MovieInteractionService;
import io.swagger.annotations.ApiOperation;
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
//	@Autowired
//	private HomeFeiClawingService homeFeiClawingService;
	
	@PostMapping(value = MovieInteractionUrl.simpleList)
	@ApiOperation(value = "电影子栏目列表")
	@ResponseBody
	public FindMovieSummaryListResult findMovieSummaryList(@RequestBody FindMovieSummaryListDTO dto) {
		return movieInteractionService.findMovieSummaryList(dto);
	}
	
	@ApiOperation(value = "电影详情")
	@PostMapping(value = MovieInteractionUrl.movieDetail)
	@ResponseBody
	public FindMovieDetailResult findMovieDetail(@RequestBody FindMovieDetailDTO dto, HttpServletRequest request) {
		return movieInteractionService.findMovieDetail(request, dto);
	}
	
	@ApiOperation(value = "电影类型")
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
	
//	@ApiOperation(value = "请忽略")
//	@PostMapping(value = MovieInteractionUrl.handleMovieIntroductionRecive)
//	@ResponseBody
//	public CommonResult handleMovieIntroductionRecive(@RequestBody MovieIntroductionDTO dto) {
//		homeFeiClawingService.handleMovieIntroductionRecive(dto);
//		CommonResult r = new CommonResult();
//		r.setIsSuccess();
//		return r;
//	}
	
	@ApiOperation(value = "电影推荐列表")
	@PostMapping(value = MovieInteractionUrl.recommend)
	@ResponseBody
	public FindMovieRecommendResult recommend() {
		FindMovieRecommendResult r = movieInteractionService.findMovieRecommend();
		return r;
	}
}
