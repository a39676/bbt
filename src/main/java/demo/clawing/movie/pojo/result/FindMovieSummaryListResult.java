package demo.clawing.movie.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import io.swagger.annotations.ApiModelProperty;

public class FindMovieSummaryListResult extends CommonResult {

	@ApiModelProperty("电影列表")
	private List<FindMovieSummaryElementResult> movieInfoList;

	public List<FindMovieSummaryElementResult> getMovieInfoList() {
		return movieInfoList;
	}

	public void setMovieInfoList(List<FindMovieSummaryElementResult> movieInfoList) {
		this.movieInfoList = movieInfoList;
	}

	@Override
	public String toString() {
		return "FindMovieSummaryListResult [movieInfoList=" + movieInfoList + "]";
	}

}
