package demo.scriptCore.scheduleClawing.pojo.dto;

import java.util.ArrayList;
import java.util.List;

public class UnderWayExamFormDTO {

	private List<UnderWayTestQuestionAndAnswerSubDTO> questionAndAnswerList;

	public List<UnderWayTestQuestionAndAnswerSubDTO> getQuestionAndAnswerList() {
		return questionAndAnswerList;
	}

	public void setQuestionAndAnswerList(List<UnderWayTestQuestionAndAnswerSubDTO> questionAndAnswerList) {
		this.questionAndAnswerList = questionAndAnswerList;
	}
	
	public void addQuestionAndAnswer(UnderWayTestQuestionAndAnswerSubDTO questionAndAnswer) {
		if(this.questionAndAnswerList == null) {
			this.questionAndAnswerList = new ArrayList<>();
		}
		this.questionAndAnswerList.add(questionAndAnswer);
	}

	@Override
	public String toString() {
		return "UnderWayExamFormDTO [questionAndAnswerList=" + questionAndAnswerList + "]";
	}

}
