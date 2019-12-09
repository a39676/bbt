package demo.clawing.movie.pojo.dto;

import java.time.LocalDateTime;
import java.util.List;

public class MovieRecordFindByConditionDTO {

	private boolean isDelete = false;
	private String url;
	private List<String> urlList;
	private LocalDateTime createTimeStart = LocalDateTime.now().minusMonths(3L);
	private LocalDateTime createTimeEnd;
	private boolean wasClaw;
	private Long caseId;

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public boolean getWasClaw() {
		return wasClaw;
	}

	public void setWasClaw(boolean wasClaw) {
		this.wasClaw = wasClaw;
	}

	public List<String> getUrlList() {
		return urlList;
	}

	public void setUrlList(List<String> urlList) {
		this.urlList = urlList;
	}

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
		return "MovieRecordFindByConditionDTO [isDelete=" + isDelete + ", url=" + url + ", urlList=" + urlList
				+ ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", wasClaw=" + wasClaw
				+ ", caseId=" + caseId + "]";
	}

}