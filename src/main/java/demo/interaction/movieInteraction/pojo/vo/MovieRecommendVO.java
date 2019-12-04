package demo.interaction.movieInteraction.pojo.vo;

import java.time.LocalDateTime;

import io.swagger.annotations.ApiModelProperty;

public class MovieRecommendVO {
	
	@ApiModelProperty("电影ID")
	private Long movieId;
	@ApiModelProperty("电影名")
	private String movieTitle;
	@ApiModelProperty("海报图片url")
	private String imgUrl;
	@ApiModelProperty("抓取时间")
	private LocalDateTime createTime;
	@ApiModelProperty("上映时间")
	private LocalDateTime releaseTime;
	@ApiModelProperty("点击数")
	private Long clickCount;

	public Long getMovieId() {
		return movieId;
	}

	public void setMovieId(Long movieId) {
		this.movieId = movieId;
	}

	public String getMovieTitle() {
		return movieTitle;
	}

	public void setMovieTitle(String movieTitle) {
		this.movieTitle = movieTitle;
	}

	public String getImgUrl() {
		return imgUrl;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public LocalDateTime getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(LocalDateTime releaseTime) {
		this.releaseTime = releaseTime;
	}

	public Long getClickCount() {
		return clickCount;
	}

	public void setClickCount(Long clickCount) {
		this.clickCount = clickCount;
	}

	@Override
	public String toString() {
		return "MovieRecommendVO [movieId=" + movieId + ", movieTitle=" + movieTitle + ", imgUrl=" + imgUrl
				+ ", createTime=" + createTime + ", releaseTime=" + releaseTime + ", clickCount=" + clickCount + "]";
	}

}
