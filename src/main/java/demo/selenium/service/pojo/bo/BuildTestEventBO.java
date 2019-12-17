package demo.selenium.service.pojo.bo;

import autoTest.testModule.pojo.type.TestModuleType;

public class BuildTestEventBO {

	private TestModuleType testModuleType;
	private Long processId;
	private Long caseId;
	private String eventName;
	private String parameterFilePath;

	public TestModuleType getTestModuleType() {
		return testModuleType;
	}

	public void setTestModuleType(TestModuleType testModuleType) {
		this.testModuleType = testModuleType;
	}

	public Long getProcessId() {
		return processId;
	}

	public void setProcessId(Long processId) {
		this.processId = processId;
	}

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getParameterFilePath() {
		return parameterFilePath;
	}

	public void setParameterFilePath(String parameterFilePath) {
		this.parameterFilePath = parameterFilePath;
	}

	@Override
	public String toString() {
		return "BuildTestEventBO [testModuleType=" + testModuleType + ", processId=" + processId + ", caseId=" + caseId
				+ ", eventName=" + eventName + ", parameterFilePath=" + parameterFilePath + "]";
	}

}
