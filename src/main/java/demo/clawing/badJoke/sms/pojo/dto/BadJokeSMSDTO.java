package demo.clawing.badJoke.sms.pojo.dto;

public class BadJokeSMSDTO {

	private String mobileNum;

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	@Override
	public String toString() {
		return "BadJokeSMSDTO [mobileNum=" + mobileNum + "]";
	}

}
