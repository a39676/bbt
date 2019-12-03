package demo.testCase.pojo.dto;

public class FindReportByTestEventIdDTO {

	private Long testEventId;

	public Long getTestEventId() {
		return testEventId;
	}

	public void setTestEventId(Long testEventId) {
		this.testEventId = testEventId;
	}

	@Override
	public String toString() {
		return "FindReportByTestEventIdDTO [testEventId=" + testEventId + "]";
	}

}
