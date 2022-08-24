package demo.scriptCore.scheduleClawing.pojo.dto;

import java.time.LocalDateTime;

public class CollectUrlHistoryDTO implements Comparable<CollectUrlHistoryDTO> {

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
		return "CollectUrlHistoryDTO [recrodDate=" + recrodDate + ", url=" + url + "]";
	}

	@Override
	public int compareTo(CollectUrlHistoryDTO o) {
		if (o.getRecrodDate() == null || this.getRecrodDate() == null) {
			if (o.getRecrodDate() == null && this.getRecrodDate() == null) {
				return 0;
			} else if (o.getRecrodDate() == null) {
				return 1;
			} else if (this.getRecrodDate() == null) {
				return -1;
			} else {
				return 0;
			}
		} else {
			if (this.getRecrodDate().isAfter(o.getRecrodDate())) {
				return 1;
			} else if (this.getRecrodDate().isBefore(o.getRecrodDate())) {
				return -1;
			} else {
				return 0;
			}
		}
	}
	
}
