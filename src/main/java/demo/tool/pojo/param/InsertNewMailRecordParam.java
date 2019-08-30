package demo.tool.pojo.param;

import java.util.Date;

public class InsertNewMailRecordParam {

	private Long userId;

	private Date validTime;

	private String mailKey;

	private Integer mailType;

	boolean wasUsed = false;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public String getMailKey() {
		return mailKey;
	}

	public void setMailKey(String mailKey) {
		this.mailKey = mailKey;
	}

	public Integer getMailType() {
		return mailType;
	}

	public void setMailType(Integer mailType) {
		this.mailType = mailType;
	}

	public boolean getWasUsed() {
		return wasUsed;
	}

	public void setWasUsed(boolean wasUsed) {
		this.wasUsed = wasUsed;
	}

}
