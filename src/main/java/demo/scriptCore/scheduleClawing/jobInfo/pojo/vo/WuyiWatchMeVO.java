package demo.scriptCore.scheduleClawing.jobInfo.pojo.vo;

import java.time.LocalDateTime;

public class WuyiWatchMeVO implements Comparable<WuyiWatchMeVO> {

	private String companyName;
	private String companyLink;
	private String myResumeName;
	private Integer watchCount;
	private LocalDateTime lastWatchTime;
	private Double degreeOfInterestAvg;
	private Integer degreeOfInterest;

	public String getCompanyName() {
		return companyName;
	}

	public WuyiWatchMeVO setCompanyName(String companyName) {
		this.companyName = companyName;
		return this;
	}

	public String getCompanyLink() {
		return companyLink;
	}

	public WuyiWatchMeVO setCompanyLink(String companyLink) {
		this.companyLink = companyLink;
		return this;
	}

	public String getMyResumeName() {
		return myResumeName;
	}

	public WuyiWatchMeVO setMyResumeName(String myResumeName) {
		this.myResumeName = myResumeName;
		return this;
	}

	public Integer getWatchCount() {
		return watchCount;
	}

	public WuyiWatchMeVO setWatchCount(Integer watchCount) {
		this.watchCount = watchCount;
		return this;
	}

	public LocalDateTime getLastWatchTime() {
		return lastWatchTime;
	}

	public WuyiWatchMeVO setLastWatchTime(LocalDateTime lastWatchTime) {
		this.lastWatchTime = lastWatchTime;
		return this;
	}

	public Double getDegreeOfInterestAvg() {
		return degreeOfInterestAvg;
	}

	public WuyiWatchMeVO setDegreeOfInterestAvg(Double degreeOfInterestAvg) {
		this.degreeOfInterestAvg = degreeOfInterestAvg;
		return this;
	}

	public Integer getDegreeOfInterest() {
		return degreeOfInterest;
	}

	public WuyiWatchMeVO setDegreeOfInterest(Integer degreeOfInterest) {
		this.degreeOfInterest = degreeOfInterest;
		return this;
	}

	@Override
	public String toString() {
		return "WuyiWatchMeVO [companyName=" + companyName + ", companyLink=" + companyLink + ", myResumeName="
				+ myResumeName + ", watchCount=" + watchCount + ", lastWatchTime=" + lastWatchTime
				+ ", degreeOfInterestAvg=" + degreeOfInterestAvg + ", degreeOfInterest=" + degreeOfInterest + "]";
	}
	
	@Override
	public int compareTo(WuyiWatchMeVO o) {
		int c = compareLastWatchTime(o, this);
		if(c == 0) {
			return compareDegreeOfInterestAvg(o, this);
		} else {
			return c;
		}
	}
	
	private int compareLastWatchTime(WuyiWatchMeVO o, WuyiWatchMeVO t) {
		if (o.lastWatchTime == null || t.lastWatchTime == null) {
			if (o.lastWatchTime == null && t.lastWatchTime == null) {
				return 0;
			} else if (o.lastWatchTime == null) {
				return -1;
			} else if (t.lastWatchTime == null) {
				return 1;
			} else {
				return 0;
			}
		} else {
			if (t.lastWatchTime.isAfter(o.lastWatchTime)) {
				return -1;
			} else if (t.lastWatchTime.isBefore(o.lastWatchTime)) {
				return 1;
			} else {
				return 0;
			}
		}
	}
	
	private int compareDegreeOfInterestAvg(WuyiWatchMeVO o, WuyiWatchMeVO t) {
		if (o.degreeOfInterestAvg == null || t.degreeOfInterestAvg == null) {
			if (o.degreeOfInterestAvg == null && t.degreeOfInterestAvg == null) {
				return 0;
			} else if (o.degreeOfInterestAvg == null) {
				return -1;
			} else if (t.degreeOfInterestAvg == null) {
				return 1;
			} else {
				return 0;
			}
		} else {
			if (t.degreeOfInterestAvg > o.degreeOfInterestAvg) {
				return -1;
			} else if (t.degreeOfInterestAvg < o.degreeOfInterestAvg) {
				return 1;
			} else {
				return 0;
			}
		}
	}

}
