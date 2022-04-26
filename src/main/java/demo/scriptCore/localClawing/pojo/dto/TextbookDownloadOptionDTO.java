package demo.scriptCore.localClawing.pojo.dto;

public class TextbookDownloadOptionDTO {

	private String mainUrl;

	private Integer startNum;

	private Integer endNum;

	private String savePath;

	public String getSavePath() {
		return savePath;
	}

	public void setSavePath(String savePath) {
		this.savePath = savePath;
	}

	public String getMainUrl() {
		return mainUrl;
	}

	public void setMainUrl(String mainUrl) {
		this.mainUrl = mainUrl;
	}

	public Integer getStartNum() {
		return startNum;
	}

	public void setStartNum(Integer startNum) {
		this.startNum = startNum;
	}

	public Integer getEndNum() {
		return endNum;
	}

	public void setEndNum(Integer endNum) {
		this.endNum = endNum;
	}

	@Override
	public String toString() {
		return "TextbookDownloadOptionDTO [mainUrl=" + mainUrl + ", startNum=" + startNum + ", endNum=" + endNum
				+ ", savePath=" + savePath + "]";
	}

}
