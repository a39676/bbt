package demo.scriptCore.scheduleClawing.pojo.dto;

import java.time.LocalDateTime;

public class EducationInfoOptionUrlDTO {

	private LocalDateTime recrodDate;

	private String url;

	public LocalDateTime getRecrodDate() {
		return recrodDate;
	}

	public void setRecrodDate(LocalDateTime recrodDate) {
		this.recrodDate = recrodDate;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public String toString() {
		return "EducationInfoOptionUrlDTO [recrodDate=" + recrodDate + ", url=" + url + "]";
	}

}
