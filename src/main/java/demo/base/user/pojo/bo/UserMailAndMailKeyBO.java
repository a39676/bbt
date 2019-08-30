package demo.base.user.pojo.bo;

import java.time.LocalDateTime;

import demo.tool.pojo.type.MailType;

public class UserMailAndMailKeyBO {

	private Long userId;

	private Long mailId;

	private String email;

	private String mailKey;

	private LocalDateTime validTime;

	/** {@link MailType} */
	private Integer mailType;

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMailKey() {
		return mailKey;
	}

	public void setMailKey(String mailKey) {
		this.mailKey = mailKey;
	}

	public LocalDateTime getValidTime() {
		return validTime;
	}

	public void setValidTime(LocalDateTime validTime) {
		this.validTime = validTime;
	}

	public Integer getMailType() {
		return mailType;
	}

	public void setMailType(Integer mailType) {
		this.mailType = mailType;
	}

	public Long getMailId() {
		return mailId;
	}

	public void setMailId(Long mailId) {
		this.mailId = mailId;
	}

	@Override
	public String toString() {
		return "UserMailAndMailKeyBO [userId=" + userId + ", mailId=" + mailId + ", email=" + email + ", mailKey="
				+ mailKey + ", validTime=" + validTime + ", mailType=" + mailType + "]";
	}

}
