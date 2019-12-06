package demo.clawing.bingDemo.pojo.dto;

public class BingDemoDTO {

	private String keyword = "testDemo";

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		return "BingDemoDTO [keyword=" + keyword + "]";
	}

}
