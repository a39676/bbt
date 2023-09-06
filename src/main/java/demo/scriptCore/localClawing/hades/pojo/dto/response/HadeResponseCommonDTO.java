package demo.scriptCore.localClawing.hades.pojo.dto.response;

public class HadeResponseCommonDTO {

	private Integer code;
	private String success;
	private String message;

	public Integer getCode() {
		return code;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return "HadeResponseCommonDTO [code=" + code + ", success=" + success + ", message=" + message + "]";
	}

}
