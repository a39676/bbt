package demo.scriptCore.localClawing.complex.subway.pojo.dto;

import java.util.List;

public class SubwayPracticeOutputQuestionDTO {

	private String question;
	private List<String> choices;
	private List<String> answers;

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public List<String> getChoices() {
		return choices;
	}

	public void setChoices(List<String> choices) {
		this.choices = choices;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public void setAnswers(List<String> answers) {
		this.answers = answers;
	}

	@Override
	public String toString() {
		return "SubwayPracticeOutputQuestionDTO [question=" + question + ", choices=" + choices + ", answers=" + answers
				+ "]";
	}

}
