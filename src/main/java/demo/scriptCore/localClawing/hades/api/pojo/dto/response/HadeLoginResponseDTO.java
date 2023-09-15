package demo.scriptCore.localClawing.hades.api.pojo.dto.response;

public class HadeLoginResponseDTO extends HadeResponseCommonDTO {

	private HadeLoginResponseDataDTO data;

	public HadeLoginResponseDataDTO getData() {
		return data;
	}

	public void setData(HadeLoginResponseDataDTO data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "HadeLoginResponseDTO [data=" + data + ", getCode()=" + getCode() + ", getSuccess()=" + getSuccess()
				+ ", getMessage()=" + getMessage() + ", toString()=" + super.toString() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + "]";
	}

}
