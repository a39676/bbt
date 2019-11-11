package demo.movieInteraction.pojo.result;

import java.util.List;

import demo.baseCommon.pojo.result.CommonResultBBT;
import demo.movieInteraction.pojo.vo.MovieRecommendVO;

public class FindMovieRecommendResult extends CommonResultBBT {

	private List<MovieRecommendVO> voList;

	public List<MovieRecommendVO> getVoList() {
		return voList;
	}

	public void setVoList(List<MovieRecommendVO> voList) {
		this.voList = voList;
	}

	@Override
	public String toString() {
		return "FindMovieRecommendResult [voList=" + voList + ", getCode()=" + getCode() + ", getResult()="
				+ getResult() + ", getMessage()=" + getMessage() + ", isSuccess()=" + isSuccess() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}

}
