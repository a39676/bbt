package demo.movie.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;

public class FindMovieDetailResult extends CommonResult {

	private Long movieId;
	private String originalTitle;
	private String cnTitle;
	private List<String> imgList;
	private String introduction;
	private List<String> magnetUrlList;
	private Long clickCounting;

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public String getOriginalTitle() {
		return originalTitle;
	}

	public void setOriginalTitle(String originalTitle) {
		this.originalTitle = originalTitle;
	}

	public String getCnTitle() {
		return cnTitle;
	}

	public void setCnTitle(String cnTitle) {
		this.cnTitle = cnTitle;
	}

	public List<String> getImgList() {
		return imgList;
	}

	public void setImgList(List<String> imgList) {
		this.imgList = imgList;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public List<String> getMagnetUrlList() {
		return magnetUrlList;
	}

	public void setMagnetUrlList(List<String> magnetUrlList) {
		this.magnetUrlList = magnetUrlList;
	}

	public Long getClickCounting() {
		return clickCounting;
	}

	public void setClickCounting(Long clickCounting) {
		this.clickCounting = clickCounting;
	}

	@Override
	public String toString() {
		return "FindMovieDetailResult [movieId=" + movieId + ", originalTitle=" + originalTitle + ", cnTitle=" + cnTitle
				+ ", imgList=" + imgList + ", introduction=" + introduction + ", magnetUrlList=" + magnetUrlList
				+ ", clickCounting=" + clickCounting + "]";
	}

}
