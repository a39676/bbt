package demo.scriptCore.localClawing.pojo.dto;

public class HsbcTabletQuickFixDataDTO {

	private Long phone;
	private Long idNumber;

	public Long getPhone() {
		return phone;
	}

	public void setPhone(Long phone) {
		this.phone = phone;
	}

	public Long getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(Long idNumber) {
		this.idNumber = idNumber;
	}

	@Override
	public String toString() {
		return "HsbcTabletQuickFixDataDTO [phone=" + phone + ", idNumber=" + idNumber + "]";
	}

	public HsbcTabletQuickFixDataDTO(Long phone, Long idNumber) {
		this.phone = phone;
		this.idNumber = idNumber;
	}
}
