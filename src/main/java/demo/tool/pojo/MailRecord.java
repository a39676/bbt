package demo.tool.pojo;

import java.util.Date;

public class MailRecord {
	private Integer mailId;

	private Long userId;

	private Date validTime;

	private String mailKey;

	private Integer mailType;

	private Boolean wasUsed;

	private Date createTime;

	private Date usedTime;

	private Integer sendCount;

	private Date resendTime;

	public Integer getMailId() {
		return mailId;
	}

	public void setMailId(Integer mailId) {
		this.mailId = mailId;
	}

	public Date getValidTime() {
		return validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	public Boolean getWasUsed() {
		return wasUsed;
	}

	public String getMailKey() {
		return mailKey;
	}

	public void setMailKey(String mailKey) {
		this.mailKey = mailKey == null ? null : mailKey.trim();
	}

	public Integer getMailType() {
		return mailType;
	}

	public void setMailType(Integer mailType) {
		this.mailType = mailType;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "MailRecord [mailId=" + mailId + ", userId=" + userId + ", validTime=" + validTime + ", mailKey="
				+ mailKey + ", mailType=" + mailType + ", wasUsed=" + wasUsed + ", createTime=" + createTime
				+ ", usedTime=" + usedTime + ", sendCount=" + sendCount + ", resendTime=" + resendTime + "]";
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public Integer getSendCount() {
		return sendCount;
	}

	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}

	public Date getResendTime() {
		return resendTime;
	}

	public void setResendTime(Date resendTime) {
		this.resendTime = resendTime;
	}


	public void setWasUsed(Boolean wasUsed) {
		this.wasUsed = wasUsed;
	}
}