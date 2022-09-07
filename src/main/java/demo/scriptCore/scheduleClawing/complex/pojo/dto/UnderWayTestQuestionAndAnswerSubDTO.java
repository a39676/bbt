package demo.scriptCore.scheduleClawing.complex.pojo.dto;

import java.util.ArrayList;
import java.util.List;

public class UnderWayTestQuestionAndAnswerSubDTO {

	private String question;
	private List<String> answer;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getAnswer() {
		return answer;
	}

	public void setAnswer(List<String> answer) {
		this.answer = answer;
	}
	
	public void addAnswer(String inputAnswer) {
		if(this.answer == null) {
			this.answer = new ArrayList<>();
		}
		this.answer.add(inputAnswer);
	}

	@Override
	public String toString() {
		return "UnderWayTestQuestionAndAnswerDTO [question=" + question + ", answer=" + answer + "]";
	}

}
