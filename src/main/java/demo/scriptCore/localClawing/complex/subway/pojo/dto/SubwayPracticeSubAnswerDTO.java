package demo.scriptCore.localClawing.complex.subway.pojo.dto;

import java.util.List;

public class SubwayPracticeSubAnswerDTO {

	private String quesId;
	private Long lastSubmitTime;
	private List<String> answer;
	private String examQuesId;
	private Integer quesType;

	public String getQuesId() {
		return quesId;
	}

	public void setQuesId(String quesId) {
		this.quesId = quesId;
	}

	public Long getLastSubmitTime() {
		return lastSubmitTime;
	}

	public void setLastSubmitTime(Long lastSubmitTime) {
		this.lastSubmitTime = lastSubmitTime;
	}

	public List<String> getAnswer() {
		return answer;
	}

	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}

	public String getExamQuesId() {
		return examQuesId;
	}

	public void setExamQuesId(String examQuesId) {
		this.examQuesId = examQuesId;
	}

	public Integer getQuesType() {
		return quesType;
	}

	public void setQuesType(Integer quesType) {
		this.quesType = quesType;
	}

	@Override
	public String toString() {
		return "SubwayExamSubAnswerDTO [quesId=" + quesId + ", lastSubmitTime=" + lastSubmitTime + ", answer=" + answer
				+ ", examQuesId=" + examQuesId + ", quesType=" + quesType + "]";
	}

}
