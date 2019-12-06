package demo.autoTestBase.testEvent.pojo.result;

import demo.baseCommon.pojo.result.CommonResultBBT;

public class InsertTestEventResult extends CommonResultBBT {

	private Integer insertCount;
	private Long newTestEventId;
	private String eventName;
	private Long moduleId;
	private Long caseId;

	public Long getCaseId() {
		return caseId;
	}

	public void setCaseId(Long caseId) {
		this.caseId = caseId;
	}

	public Long getModuleId() {
		return moduleId;
	}

	public void setModuleId(Long moduleId) {
		this.moduleId = moduleId;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public Integer getInsertCount() {
		return insertCount;
	}

	public void setInsertCount(Integer insertCount) {
		this.insertCount = insertCount;
	}

	public Long getNewTestEventId() {
		return newTestEventId;
	}

	public void setNewTestEventId(Long newTestEventId) {
		this.newTestEventId = newTestEventId;
	}

	@Override
	public String toString() {
		return "InsertTestEventResult [insertCount=" + insertCount + ", newTestEventId=" + newTestEventId
				+ ", eventName=" + eventName + ", moduleId=" + moduleId + ", caseId=" + caseId + "]";
	}

}
