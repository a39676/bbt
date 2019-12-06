package demo.autoTestBase.testEvent.pojo.bo;

public abstract class TestEventBO {

	protected String caseCode;
	protected String projectName;
	protected String mainUrl;

	public String getCaseCode() {
		return caseCode;
	}

	public String getProjectName() {
		return projectName;
	}

	public String getMainUrl() {
		return mainUrl;
	}

	public abstract TestEventBO build();

	@Override
	public String toString() {
		return "TestEventBO [caseCode=" + caseCode + ", projectName=" + projectName + ", mainUrl=" + mainUrl + "]";
	}

}