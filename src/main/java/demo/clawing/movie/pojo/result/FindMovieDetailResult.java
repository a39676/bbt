package demo.clawing.movie.pojo.result;

import java.util.List;

import auxiliaryCommon.pojo.result.CommonResult;
import io.swagger.annotations.ApiModelProperty;

public class FindMovieDetailResult extends CommonResult {

	@ApiModelProperty("id")
	private Long movieId;
	@ApiModelProperty("原语言电影名")
	private String originalTitle;
	@ApiModelProperty("中文电影名")
	private String cnTitle;
	@ApiModelProperty("相关图片(包括海报, 一般第一张是海报)")
	private List<String> imgList;
	@ApiModelProperty("简介")
	private String introduction;
	@ApiModelProperty("磁力链接列表")
	private List<String> magnetUrlList;
	@ApiModelProperty("点击统计")
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
