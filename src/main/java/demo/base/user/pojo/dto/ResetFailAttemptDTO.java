package demo.base.user.pojo.dto;

public class ResetFailAttemptDTO {

	private String userName;
	private Long userId;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "UserAttemptQuerayParam [userName=" + userName + ", userId=" + userId + "]";
	}

}

