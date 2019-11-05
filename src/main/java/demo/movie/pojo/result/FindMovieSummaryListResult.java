package demo.movie.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;

public class FindMovieSummaryListResult extends CommonResult {

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
