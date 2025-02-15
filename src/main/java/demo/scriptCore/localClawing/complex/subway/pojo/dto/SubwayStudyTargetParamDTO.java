package demo.scriptCore.localClawing.complex.subway.pojo.dto;

public class SubwayStudyTargetParamDTO {

	private String taskId; // ": "1818908546618085378",
	private String projectId; // ": "1818908417816729602",
	private String flipId; // ": "",
	private String batchId; // ": ""

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getFlipId() {
		return flipId;
	}

	public void setFlipId(String flipId) {
		this.flipId = flipId;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	@Override
	public String toString() {
		return "SubwayStudySendSecondTargetParamDTO [taskId=" + taskId + ", projectId=" + projectId + ", flipId="
				+ flipId + ", batchId=" + batchId + "]";
	}

}
