package demo.scriptCore.scheduleClawing.pojo.dto;

import java.time.LocalDateTime;

public class EducationInfoUrlDTO {

	private LocalDateTime collectDate;
	private String url;
	private String title;

	public LocalDateTime getCollectDate() {
		return collectDate;
	}

	public void setCollectDate(LocalDateTime collectDate) {
		this.collectDate = collectDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String toString() {
		return "EducationInfoUrlDTO [collectDate=" + collectDate + ", url=" + url + ", title=" + title + "]";
	}

}
