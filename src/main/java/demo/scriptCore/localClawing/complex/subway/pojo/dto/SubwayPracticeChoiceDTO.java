package demo.scriptCore.localClawing.complex.subway.pojo.dto;

public class SubwayPracticeChoiceDTO {

	private String id; // "1129001a-a826-4aad-a7ec-f7c4f155994d",
	private String orgId; // "d5bcbf8c-e595-40ff-9c0c-f39a5d02f1bc",
	private String examQuesId; // "3e66edde-5dfd-43ce-a90e-4c64ab1f764f",
	private String itemContent; // "3-4秒",
	private String answer; // 0,
	private String orderIndex; // 1,
	private String examId; // "771de2a5-deea-4ab8-aa01-42baa5564532",
	private String sourceItemId; // "1f5cf81b-c007-424d-a722-9f23a19510c5",
	private String score; // 0.0,
	private String itemPlay; // null

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getExamQuesId() {
		return examQuesId;
	}

	public void setExamQuesId(String examQuesId) {
		this.examQuesId = examQuesId;
	}

	public String getItemContent() {
		return itemContent;
	}

	public void setItemContent(String itemContent) {
		this.itemContent = itemContent;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(String orderIndex) {
		this.orderIndex = orderIndex;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getSourceItemId() {
		return sourceItemId;
	}

	public void setSourceItemId(String sourceItemId) {
		this.sourceItemId = sourceItemId;
	}

	public String getScore() {
		return score;
	}

	public void setScore(String score) {
		this.score = score;
	}

	public String getItemPlay() {
		return itemPlay;
	}

	public void setItemPlay(String itemPlay) {
		this.itemPlay = itemPlay;
	}

	@Override
	public String toString() {
		return "SubwayPracticeChoiceDTO [id=" + id + ", orgId=" + orgId + ", examQuesId=" + examQuesId
				+ ", itemContent=" + itemContent + ", answer=" + answer + ", orderIndex=" + orderIndex + ", examId="
				+ examId + ", sourceItemId=" + sourceItemId + ", score=" + score + ", itemPlay=" + itemPlay + "]";
	}

}
