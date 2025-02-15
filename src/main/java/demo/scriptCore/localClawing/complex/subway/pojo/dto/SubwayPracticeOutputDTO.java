package demo.scriptCore.localClawing.complex.subway.pojo.dto;

import java.util.List;

public class SubwayPracticeOutputDTO {
	private List<SubwayPracticeOutputQuestionDTO> questions;

	public List<SubwayPracticeOutputQuestionDTO> getQuestions() {
		return questions;
	}

	public void setQuestions(List<SubwayPracticeOutputQuestionDTO> questions) {
		this.questions = questions;
	}

	@Override
	public String toString() {
		return "SubwayPracticeOutputDTO [questions=" + questions + "]";
	}

}
