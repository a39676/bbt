package demo.selenium.pojo.bo;

import autoTest.testModule.pojo.type.TestModuleType;

public class BuildTestEventBO {

	private Long eventId;
	private TestModuleType testModuleType;
	private Long flowId;
	private String flowName;
	private String parameterFilePath;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public TestModuleType getTestModuleType() {
		return testModuleType;
	}

	public void setTestModuleType(TestModuleType testModuleType) {
		this.testModuleType = testModuleType;
	}

	public Long getFlowId() {
		return flowId;
	}

	public void setFlowId(Long flowId) {
		this.flowId = flowId;
	}

	public String getFlowName() {
		return flowName;
	}

	public void setFlowName(String flowName) {
		this.flowName = flowName;
	}

	public String getParameterFilePath() {
		return parameterFilePath;
	}

	public void setParameterFilePath(String parameterFilePath) {
		this.parameterFilePath = parameterFilePath;
	}

	@Override
	public String toString() {
		return "BuildTestEventBO [eventId=" + eventId + ", testModuleType=" + testModuleType + ", flowId=" + flowId
				+ ", flowName=" + flowName + ", parameterFilePath=" + parameterFilePath + "]";
	}

}
