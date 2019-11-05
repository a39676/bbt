package demo.movieInteraction.service;

import javax.servlet.http.HttpServletRequest;

import demo.movie.pojo.result.FindMovieDetailResult;
import demo.movie.pojo.result.FindMovieSummaryListResult;
import movie.pojo.dto.FindMovieDetailDTO;
import movie.pojo.dto.FindMovieSummaryListDTO;

public interface MovieInteractionService {

	FindMovieSummaryListResult findMovieSummaryList(FindMovieSummaryListDTO dto);

	FindMovieDetailResult findMovieDetail(HttpServletRequest request, FindMovieDetailDTO dto);

	void movieClickCountingRedisToOrm();

	Long findMovieClickCount(Long movieId);

}
