package demo.movieInteraction.pojo.vo;

import java.time.LocalDateTime;

public class MovieRecommendVO {

	private Long movieId;
	private String movieTitle;
	private String imgUrl;
	private LocalDateTime createTime;
	private LocalDateTime releaseTime;
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
