package demo.scriptCore.localClawing.pojo.dto;

public class HsbcOptionDTO {

	private String mainUrl = "https://www.hkg2vl0830-cn.p2g.netd2.hsbc.com.hk/PublicContent/wechat/wechat_library/VTM/prd-branch/index.html#/";
	private boolean mainlandFlag = false;
	private boolean mainlandPhoneFlag = false;
	private Long mainlandIdNumber = 352962198001016765L;
	private String staffId = "44123708"; //
	private int stepLong = 1;
	private Long indexNum = 9990104L;
	private String phoneNumber;

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public boolean getMainlandFlag() {
		return mainlandFlag;
	}

	public void setMainlandFlag(boolean mainlandFlag) {
		this.mainlandFlag = mainlandFlag;
	}

	public boolean getMainlandPhoneFlag() {
		return mainlandPhoneFlag;
	}

	public void setMainlandPhoneFlag(boolean mainlandPhoneFlag) {
		this.mainlandPhoneFlag = mainlandPhoneFlag;
	}

	public Long getMainlandIdNumber() {
		return mainlandIdNumber;
	}

	public void setMainlandIdNumber(Long mainlandIdNumber) {
		this.mainlandIdNumber = mainlandIdNumber;
	}

	public String getStaffId() {
		return staffId;
	}

	public void setStaffId(String staffId) {
		this.staffId = staffId;
	}

	public int getStepLong() {
		return stepLong;
	}

	public void setStepLong(int stepLong) {
		this.stepLong = stepLong;
	}

	public Long getIndexNum() {
		return indexNum;
	}

	public void setIndexNum(Long indexNum) {
		this.indexNum = indexNum;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return "HsbcOptionDTO [mainUrl=" + mainUrl + ", mainlandFlag=" + mainlandFlag + ", mainlandPhoneFlag="
				+ mainlandPhoneFlag + ", mainlandIdNumber=" + mainlandIdNumber + ", staffId=" + staffId + ", stepLong="
				+ stepLong + ", indexNum=" + indexNum + ", phoneNumber=" + phoneNumber + "]";
	}

}
