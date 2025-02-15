package demo.scriptCore.localClawing.complex.subway.pojo.dto;

import java.util.List;

public class SubwayPracticeQuestionDTO {

	private String id; // "e24e35bf-6e83-459c-84fc-bb272697321b",
	private String examQuesId; // "3e66edde-5dfd-43ce-a90e-4c64ab1f764f",
	private String sourceId; // "00b0fb31-09e9-43cf-9112-92c732c99bab",
	private String content; // "列车出现气制动检测故障时，列车启动（）后会自动停车。",
	private Integer quesType; // 0,
	private Integer questionType; // 0,
	private String fileId; // "",
	private String tranStatus; // null,
	private String playDetails; // null,
	private Integer subQues; // 0,
	private String parentId; // null,
	private Integer orderIndex; // 1,
	private Integer itemCount; // 4,
	private String judgeCorrectOptionContent; // "",
	private String judgeWrongOptionContent; // "",
	private List<SubwayPracticeChoiceDTO> choiceItems;
	private String fillInItems; // null,
	private String subQuesList; // null,
	private Integer corrected; // 0,
	private String fillinCorrectedInfo; // null,
	private List<String> submitQuesAnswers;
	private List<String> correctAnswers;
	private String keywords; // null,
	private String fillinAnswers; // null,
	private Integer judgeAnswer; // 0,
	private String explainText; // "",
	private String answerPlay; // null,
	private String explainPlay; // null

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExamQuesId() {
		return examQuesId;
	}

	public void setExamQuesId(String examQuesId) {
		this.examQuesId = examQuesId;
	}

	public String getSourceId() {
		return sourceId;
	}

	public void setSourceId(String sourceId) {
		this.sourceId = sourceId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getQuesType() {
		return quesType;
	}

	public void setQuesType(Integer quesType) {
		this.quesType = quesType;
	}

	public Integer getQuestionType() {
		return questionType;
	}

	public void setQuestionType(Integer questionType) {
		this.questionType = questionType;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getTranStatus() {
		return tranStatus;
	}

	public void setTranStatus(String tranStatus) {
		this.tranStatus = tranStatus;
	}

	public String getPlayDetails() {
		return playDetails;
	}

	public void setPlayDetails(String playDetails) {
		this.playDetails = playDetails;
	}

	public Integer getSubQues() {
		return subQues;
	}

	public void setSubQues(Integer subQues) {
		this.subQues = subQues;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Integer getItemCount() {
		return itemCount;
	}

	public void setItemCount(Integer itemCount) {
		this.itemCount = itemCount;
	}

	public String getJudgeCorrectOptionContent() {
		return judgeCorrectOptionContent;
	}

	public void setJudgeCorrectOptionContent(String judgeCorrectOptionContent) {
		this.judgeCorrectOptionContent = judgeCorrectOptionContent;
	}

	public String getJudgeWrongOptionContent() {
		return judgeWrongOptionContent;
	}

	public void setJudgeWrongOptionContent(String judgeWrongOptionContent) {
		this.judgeWrongOptionContent = judgeWrongOptionContent;
	}

	public List<SubwayPracticeChoiceDTO> getChoiceItems() {
		return choiceItems;
	}

	public void setChoiceItems(List<SubwayPracticeChoiceDTO> choiceItems) {
		this.choiceItems = choiceItems;
	}

	public String getFillInItems() {
		return fillInItems;
	}

	public void setFillInItems(String fillInItems) {
		this.fillInItems = fillInItems;
	}

	public String getSubQuesList() {
		return subQuesList;
	}

	public void setSubQuesList(String subQuesList) {
		this.subQuesList = subQuesList;
	}

	public Integer getCorrected() {
		return corrected;
	}

	public void setCorrected(Integer corrected) {
		this.corrected = corrected;
	}

	public String getFillinCorrectedInfo() {
		return fillinCorrectedInfo;
	}

	public void setFillinCorrectedInfo(String fillinCorrectedInfo) {
		this.fillinCorrectedInfo = fillinCorrectedInfo;
	}

	public List<String> getSubmitQuesAnswers() {
		return submitQuesAnswers;
	}

	public void setSubmitQuesAnswers(List<String> submitQuesAnswers) {
		this.submitQuesAnswers = submitQuesAnswers;
	}

	public List<String> getCorrectAnswers() {
		return correctAnswers;
	}

	public void setCorrectAnswers(List<String> correctAnswers) {
		this.correctAnswers = correctAnswers;
	}

	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getFillinAnswers() {
		return fillinAnswers;
	}

	public void setFillinAnswers(String fillinAnswers) {
		this.fillinAnswers = fillinAnswers;
	}

	public Integer getJudgeAnswer() {
		return judgeAnswer;
	}

	public void setJudgeAnswer(Integer judgeAnswer) {
		this.judgeAnswer = judgeAnswer;
	}

	public String getExplainText() {
		return explainText;
	}

	public void setExplainText(String explainText) {
		this.explainText = explainText;
	}

	public String getAnswerPlay() {
		return answerPlay;
	}

	public void setAnswerPlay(String answerPlay) {
		this.answerPlay = answerPlay;
	}

	public String getExplainPlay() {
		return explainPlay;
	}

	public void setExplainPlay(String explainPlay) {
		this.explainPlay = explainPlay;
	}

	@Override
	public String toString() {
		return "SubwayPracticeQuestionDTO [id=" + id + ", examQuesId=" + examQuesId + ", sourceId=" + sourceId
				+ ", content=" + content + ", quesType=" + quesType + ", questionType=" + questionType + ", fileId="
				+ fileId + ", tranStatus=" + tranStatus + ", playDetails=" + playDetails + ", subQues=" + subQues
				+ ", parentId=" + parentId + ", orderIndex=" + orderIndex + ", itemCount=" + itemCount
				+ ", judgeCorrectOptionContent=" + judgeCorrectOptionContent + ", judgeWrongOptionContent="
				+ judgeWrongOptionContent + ", choiceItems=" + choiceItems + ", fillInItems=" + fillInItems
				+ ", subQuesList=" + subQuesList + ", corrected=" + corrected + ", fillinCorrectedInfo="
				+ fillinCorrectedInfo + ", submitQuesAnswers=" + submitQuesAnswers + ", correctAnswers="
				+ correctAnswers + ", keywords=" + keywords + ", fillinAnswers=" + fillinAnswers + ", judgeAnswer="
				+ judgeAnswer + ", explainText=" + explainText + ", answerPlay=" + answerPlay + ", explainPlay="
				+ explainPlay + "]";
	}

}
