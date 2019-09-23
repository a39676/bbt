package demo.movie.service;

import demo.movie.pojo.result.FindMovieSummaryListResult;
import movie.pojo.dto.FindMovieSummaryListDTO;

public interface MovieInteractionService {

	FindMovieSummaryListResult findMovieSummaryList(FindMovieSummaryListDTO dto);

}
