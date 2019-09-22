package demo.movie.pojo.dto;

import java.time.LocalDateTime;

public class MovieRecordFindByConditionDTO {

	private boolean isDelete = false;
	private String url;
	private LocalDateTime createTimeStart = LocalDateTime.now().minusMonths(3L);
	private LocalDateTime createTimeEnd;

	public boolean isDelete() {
		return isDelete;
	}

	public void setDelete(boolean isDelete) {
		this.isDelete = isDelete;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public LocalDateTime getCreateTimeStart() {
		return createTimeStart;
	}

	public void setCreateTimeStart(LocalDateTime createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public LocalDateTime getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(LocalDateTime createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	@Override
	public String toString() {
		return "MovieRecordFindByConditionDTO [isDelete=" + isDelete + ", url=" + url + ", createTimeStart="
				+ createTimeStart + ", createTimeEnd=" + createTimeEnd + "]";
	}

}
