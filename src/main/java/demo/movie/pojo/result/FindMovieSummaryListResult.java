package demo.movie.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import demo.movie.pojo.po.MovieInfo;

public class FindMovieSummaryListResult extends CommonResult {

	private List<MovieInfo> movieInfoList;

	public List<MovieInfo> getMovieInfoList() {
		return movieInfoList;
	}

	public void setMovieInfoList(List<MovieInfo> movieInfoList) {
		this.movieInfoList = movieInfoList;
	}

	@Override
	public String toString() {
		return "FindMovieSummaryListResult [movieInfoList=" + movieInfoList + ", getCode()=" + getCode()
				+ ", getResult()=" + getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess()
				+ ", toString()=" + super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ "]";
	}

}
