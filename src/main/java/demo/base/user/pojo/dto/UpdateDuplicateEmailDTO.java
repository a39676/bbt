package demo.base.user.pojo.dto;

public class UpdateDuplicateEmailDTO {

	private String oldEmail;
	private Long userId;
	private String newEmail = "";

	public String getOldEmail() {
		return oldEmail;
	}

	public void setOldEmail(String oldEmail) {
		this.oldEmail = oldEmail;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNewEmail() {
		return newEmail;
	}

	public void setNewEmail(String newEmail) {
		this.newEmail = newEmail;
	}

}
