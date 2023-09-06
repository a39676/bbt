package demo.scriptCore.localClawing.hades.pojo.dto.response;

public class HadeLoginResponseDataDTO {

	private String accessToken;

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String toString() {
		return "HadeLoginResponseDataDTO [accessToken=" + accessToken + "]";
	}

}
