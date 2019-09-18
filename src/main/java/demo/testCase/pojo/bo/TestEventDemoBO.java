package demo.testCase.pojo.bo;

public class TestEventDemoBO extends TestEventBO {

	private String keyWord;

	public String getKeyWord() {
		return keyWord;
	}

	@Override
	public String toString() {
		return "TestEventDemoBO [caseCode=" + caseCode + ", projectName=" + projectName + ", mainUrl=" + mainUrl
				+ ", keyWord=" + keyWord + "]";
	}

	@Override
	public TestEventDemoBO build() {
		this.caseCode = "STUC_CC01002";
		this.projectName = "newSoftOA7.5";
		this.mainUrl = "http://www.demo.com";
		this.keyWord = "key word";
		return this;
	}

}
