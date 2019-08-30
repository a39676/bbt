package demo.mobile.pojo.po;

public class MobileNum {

	private Integer prefix;
	private Integer midNum;
	private Long geographicalId;
	private Long mobileOperatorId;

	public Integer getPrefix() {
		return prefix;
	}

	public void setPrefix(Integer prefix) {
		this.prefix = prefix;
	}

	public Integer getMidNum() {
		return midNum;
	}

	public void setMidNum(Integer midNum) {
		this.midNum = midNum;
	}

	public Long getGeographicalId() {
		return geographicalId;
	}

	public void setGeographicalId(Long geographicalId) {
		this.geographicalId = geographicalId;
	}

	public Long getMobileOperatorId() {
		return mobileOperatorId;
	}

	public void setMobileOperatorId(Long mobileOperatorId) {
		this.mobileOperatorId = mobileOperatorId;
	}

	@Override
	public String toString() {
		return "MobileNum [prefix=" + prefix + ", midNum=" + midNum + ", geographicalId=" + geographicalId
				+ ", mobileOperatorId=" + mobileOperatorId + "]";
	}

}