package demo.scriptCore.localClawing.complex.pojo.dto;

public class TextbookDownloadSubOption {

	private String startUrl;

	private String savePath;

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getStartUrl() {
		return startUrl;
	}

	public void setStartUrl(String startUrl) {
		this.startUrl = startUrl;
	}

	@Override
	public String toString() {
		return "TextbookDownloadSubOption [startUrl=" + startUrl + ", savePath=" + savePath + "]";
	}

}
