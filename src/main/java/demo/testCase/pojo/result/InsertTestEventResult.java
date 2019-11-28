package demo.testCase.pojo.result;

import demo.baseCommon.pojo.result.CommonResultBBT;

public class InsertTestEventResult extends CommonResultBBT {

	private Integer insertCount;
	private Long newTestEventId;

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
		return "InsertTestEventResult [insertCount=" + insertCount + ", newTestEventId=" + newTestEventId + "]";
	}

}
