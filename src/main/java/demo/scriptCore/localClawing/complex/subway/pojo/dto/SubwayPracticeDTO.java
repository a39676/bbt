package demo.scriptCore.localClawing.complex.subway.pojo.dto;

import java.util.List;

public class SubwayPracticeDTO {

	private String puId; // "514fa72e-d4b7-451b-b079-d454b1eac80e",
	private String praId; // "e5e0c616-70df-4a9b-b9e8-cd62a31d652d",
	private String userId; // "34a273f6-e808-483b-b101-984733d10238",
	private String pumId; // "4488895e-4710-4cef-a0c6-ad0ff1119d98",
	private String praName; // "应知应会（三级）",
	private Integer allowAddWatermark; // 0,
	private Integer allowPictureCopy; // 0,
	private Integer allowCharacterCopy; // 0,
	private String uniqueId; // "4eaf83a8-8f44-44a3-9cbe-73ed25040702",
	private Integer canAnswered; // 1,
	private String errorKey; // null,
	private Integer allowViewSingleQuesResult; // 1,
	private Integer allowViewSingleQuesAnswer; // 1,
	private Integer allowViewSingleQuesExplain; // 1,
	private Integer repeated; // 1,
	private List<SubwayPracticeQuestionDTO> listQues; //

	public String getPuId() {
		return puId;
	}

	public void setPuId(String puId) {
		this.puId = puId;
	}

	public String getPraId() {
		return praId;
	}

	public void setPraId(String praId) {
		this.praId = praId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPumId() {
		return pumId;
	}

	public void setPumId(String pumId) {
		this.pumId = pumId;
	}

	public String getPraName() {
		return praName;
	}

	public void setPraName(String praName) {
		this.praName = praName;
	}

	public Integer getAllowAddWatermark() {
		return allowAddWatermark;
	}

	public void setAllowAddWatermark(Integer allowAddWatermark) {
		this.allowAddWatermark = allowAddWatermark;
	}

	public Integer getAllowPictureCopy() {
		return allowPictureCopy;
	}

	public void setAllowPictureCopy(Integer allowPictureCopy) {
		this.allowPictureCopy = allowPictureCopy;
	}

	public Integer getAllowCharacterCopy() {
		return allowCharacterCopy;
	}

	public void setAllowCharacterCopy(Integer allowCharacterCopy) {
		this.allowCharacterCopy = allowCharacterCopy;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Integer getCanAnswered() {
		return canAnswered;
	}

	public void setCanAnswered(Integer canAnswered) {
		this.canAnswered = canAnswered;
	}

	public String getErrorKey() {
		return errorKey;
	}

	public void setErrorKey(String errorKey) {
		this.errorKey = errorKey;
	}

	public Integer getAllowViewSingleQuesResult() {
		return allowViewSingleQuesResult;
	}

	public void setAllowViewSingleQuesResult(Integer allowViewSingleQuesResult) {
		this.allowViewSingleQuesResult = allowViewSingleQuesResult;
	}

	public Integer getAllowViewSingleQuesAnswer() {
		return allowViewSingleQuesAnswer;
	}

	public void setAllowViewSingleQuesAnswer(Integer allowViewSingleQuesAnswer) {
		this.allowViewSingleQuesAnswer = allowViewSingleQuesAnswer;
	}

	public Integer getAllowViewSingleQuesExplain() {
		return allowViewSingleQuesExplain;
	}

	public void setAllowViewSingleQuesExplain(Integer allowViewSingleQuesExplain) {
		this.allowViewSingleQuesExplain = allowViewSingleQuesExplain;
	}

	public Integer getRepeated() {
		return repeated;
	}

	public void setRepeated(Integer repeated) {
		this.repeated = repeated;
	}

	public List<SubwayPracticeQuestionDTO> getListQues() {
		return listQues;
	}

	public void setListQues(List<SubwayPracticeQuestionDTO> listQues) {
		this.listQues = listQues;
	}

	@Override
	public String toString() {
		return "SubwayPracticeDTO [puId=" + puId + ", praId=" + praId + ", userId=" + userId + ", pumId=" + pumId
				+ ", praName=" + praName + ", allowAddWatermark=" + allowAddWatermark + ", allowPictureCopy="
				+ allowPictureCopy + ", allowCharacterCopy=" + allowCharacterCopy + ", uniqueId=" + uniqueId
				+ ", canAnswered=" + canAnswered + ", errorKey=" + errorKey + ", allowViewSingleQuesResult="
				+ allowViewSingleQuesResult + ", allowViewSingleQuesAnswer=" + allowViewSingleQuesAnswer
				+ ", allowViewSingleQuesExplain=" + allowViewSingleQuesExplain + ", repeated=" + repeated
				+ ", listQues=" + listQues + "]";
	}

}
