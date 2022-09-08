package demo.scriptCore.scheduleClawing.jobInfo.pojo.po;

import java.time.LocalDateTime;

public class WuyiWatchMe {
	private Long id;

	private String companyName;

	private String companyLink;

	private LocalDateTime watchTime;

	private Integer degreeOfInterest;

	private String myResumeName;

	private LocalDateTime createTime;

	private Boolean isDelete;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName == null ? null : companyName.trim();
	}

	public String getCompanyLink() {
		return companyLink;
	}

	public void setCompanyLink(String companyLink) {
		this.companyLink = companyLink == null ? null : companyLink.trim();
	}

	public LocalDateTime getWatchTime() {
		return watchTime;
	}

	public void setWatchTime(LocalDateTime watchTime) {
		this.watchTime = watchTime;
	}

	public Integer getDegreeOfInterest() {
		return degreeOfInterest;
	}

	public void setDegreeOfInterest(Integer degreeOfInterest) {
		this.degreeOfInterest = degreeOfInterest;
	}

	public String getMyResumeName() {
		return myResumeName;
	}

	public void setMyResumeName(String myResumeName) {
		this.myResumeName = myResumeName == null ? null : myResumeName.trim();
	}

	public LocalDateTime getCreateTime() {
		return createTime;
	}

	public void setCreateTime(LocalDateTime createTime) {
		this.createTime = createTime;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

	@Override
	public String toString() {
		return "WuyiWatchMe [id=" + id + ", companyName=" + companyName + ", companyLink=" + companyLink
				+ ", watchTime=" + watchTime + ", degreeOfInterest=" + degreeOfInterest + ", myResumeName="
				+ myResumeName + ", createTime=" + createTime + ", isDelete=" + isDelete + "]";
	}

}