package demo.scriptCore.scheduleClawing.pojo.bo;

public class HuobiSocketConfigBO {

	private String accessKey;
	private String secretKey;

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}

	@Override
	public String toString() {
		return "HuobiSocketConfigBO [accessKey=" + accessKey + ", secretKey=" + secretKey + "]";
	}

}
