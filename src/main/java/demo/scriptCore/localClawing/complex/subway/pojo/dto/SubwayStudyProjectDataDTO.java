package demo.scriptCore.localClawing.complex.subway.pojo.dto;

import java.util.List;

public class SubwayStudyProjectDataDTO {

	private String id; // ": "1818908477835694082",
	private String name; // ": "阶段1",
	private String description; // ": "",
	private Integer orderIndex; // ": 1,
	private Integer locked; // ": 0,
	private Integer allowStudy; // ": 1,
	private String startTime; // ": null,
	private String endTime; // ": null,
	private List<SubwayStudyTaskDetailDTO> studentTaskBeans; // [{}]":
	private Integer studyModel; // ": 0,
	private Double studyScore; // ": 0.00,
	private String point; // ": 0,
	private String unlockTime; // ": null,
	private Integer periodStatus; // ": 0,
	private String overdueTime; // ": null,
	private Integer cycle; // ": 0,
	private Integer ojtProjectModePeriod; // ": 0,
	private String requiredSetting; // ": null,
	private String electiveSetting; // ": null,
	private String studyStandard; // ": null,
	private String completeRequiredCnt; // ": null,
	private String completeElectiveCnt; // ": null

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getOrderIndex() {
		return orderIndex;
	}

	public void setOrderIndex(Integer orderIndex) {
		this.orderIndex = orderIndex;
	}

	public Integer getLocked() {
		return locked;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public Integer getAllowStudy() {
		return allowStudy;
	}

	public void setAllowStudy(Integer allowStudy) {
		this.allowStudy = allowStudy;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public List<SubwayStudyTaskDetailDTO> getStudentTaskBeans() {
		return studentTaskBeans;
	}

	public void setStudentTaskBeans(List<SubwayStudyTaskDetailDTO> studentTaskBeans) {
		this.studentTaskBeans = studentTaskBeans;
	}

	public Integer getStudyModel() {
		return studyModel;
	}

	public void setStudyModel(Integer studyModel) {
		this.studyModel = studyModel;
	}

	public Double getStudyScore() {
		return studyScore;
	}

	public void setStudyScore(Double studyScore) {
		this.studyScore = studyScore;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

	public String getUnlockTime() {
		return unlockTime;
	}

	public void setUnlockTime(String unlockTime) {
		this.unlockTime = unlockTime;
	}

	public Integer getPeriodStatus() {
		return periodStatus;
	}

	public void setPeriodStatus(Integer periodStatus) {
		this.periodStatus = periodStatus;
	}

	public String getOverdueTime() {
		return overdueTime;
	}

	public void setOverdueTime(String overdueTime) {
		this.overdueTime = overdueTime;
	}

	public Integer getCycle() {
		return cycle;
	}

	public void setCycle(Integer cycle) {
		this.cycle = cycle;
	}

	public Integer getOjtProjectModePeriod() {
		return ojtProjectModePeriod;
	}

	public void setOjtProjectModePeriod(Integer ojtProjectModePeriod) {
		this.ojtProjectModePeriod = ojtProjectModePeriod;
	}

	public String getRequiredSetting() {
		return requiredSetting;
	}

	public void setRequiredSetting(String requiredSetting) {
		this.requiredSetting = requiredSetting;
	}

	public String getElectiveSetting() {
		return electiveSetting;
	}

	public void setElectiveSetting(String electiveSetting) {
		this.electiveSetting = electiveSetting;
	}

	public String getStudyStandard() {
		return studyStandard;
	}

	public void setStudyStandard(String studyStandard) {
		this.studyStandard = studyStandard;
	}

	public String getCompleteRequiredCnt() {
		return completeRequiredCnt;
	}

	public void setCompleteRequiredCnt(String completeRequiredCnt) {
		this.completeRequiredCnt = completeRequiredCnt;
	}

	public String getCompleteElectiveCnt() {
		return completeElectiveCnt;
	}

	public void setCompleteElectiveCnt(String completeElectiveCnt) {
		this.completeElectiveCnt = completeElectiveCnt;
	}

	@Override
	public String toString() {
		return "SubwayStudyProjectDataDTO [id=" + id + ", name=" + name + ", description=" + description
				+ ", orderIndex=" + orderIndex + ", locked=" + locked + ", allowStudy=" + allowStudy + ", startTime="
				+ startTime + ", endTime=" + endTime + ", studentTaskBeans=" + studentTaskBeans + ", studyModel="
				+ studyModel + ", studyScore=" + studyScore + ", point=" + point + ", unlockTime=" + unlockTime
				+ ", periodStatus=" + periodStatus + ", overdueTime=" + overdueTime + ", cycle=" + cycle
				+ ", ojtProjectModePeriod=" + ojtProjectModePeriod + ", requiredSetting=" + requiredSetting
				+ ", electiveSetting=" + electiveSetting + ", studyStandard=" + studyStandard + ", completeRequiredCnt="
				+ completeRequiredCnt + ", completeElectiveCnt=" + completeElectiveCnt + "]";
	}

}
