package demo.movie.pojo.result;

import java.time.LocalDateTime;

public class FindMovieSummaryElementResult {

	private Long id;
	private String cnTitle;
	private Long clickCounting;
	private String introduction;
	private LocalDateTime releaseTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCnTitle() {
		return cnTitle;
	}

	public void setCnTitle(String cnTitle) {
		this.cnTitle = cnTitle;
	}

	public Long getClickCounting() {
		return clickCounting;
	}

	public void setClickCounting(Long clickCounting) {
		this.clickCounting = clickCounting;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public LocalDateTime getReleaseTime() {
		return releaseTime;
	}

	public void setReleaseTime(LocalDateTime releaseTime) {
		this.releaseTime = releaseTime;
	}

	@Override
	public String toString() {
		return "FindMovieSummaryElementResult [id=" + id + ", cnTitle=" + cnTitle + ", clickCounting=" + clickCounting
				+ ", introduction=" + introduction + ", releaseTime=" + releaseTime + "]";
	}

}
