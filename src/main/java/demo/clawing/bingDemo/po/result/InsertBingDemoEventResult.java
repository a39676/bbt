package demo.clawing.bingDemo.po.result;

import demo.baseCommon.pojo.result.CommonResultBBT;

public class InsertBingDemoEventResult extends CommonResultBBT {

	private Long eventId;
	private Integer waitingEventCount = 0;

	public Long getEventId() {
		return eventId;
	}

	public void setEventId(Long eventId) {
		this.eventId = eventId;
	}

	public Integer getWaitingEventCount() {
		return waitingEventCount;
	}

	public void setWaitingEventCount(Integer waitingEventCount) {
		this.waitingEventCount = waitingEventCount;
	}

	@Override
	public String toString() {
		return "InsertBingDemoEventResult [eventId=" + eventId + ", waitingEventCount=" + waitingEventCount + "]";
	}

}
