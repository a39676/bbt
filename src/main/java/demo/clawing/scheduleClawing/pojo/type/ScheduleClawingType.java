package demo.clawing.scheduleClawing.pojo.type;

public enum ScheduleClawingType {

	/** wuYiJob */
	wuYiJob(2L, "wuYiJob"),
	
	/** liePin */
	liePin(3L, "liePin"),
	
	/** cdBao */
	cdBao(4L, "cdBao"),
	
	/** zhiLian */
	zhiLian(5L, "zhiLian"),
	
	/** maiMai */
	maiMai(6L, "maiMai"),
	
	;

	private Long id;
	private String eventName;

	ScheduleClawingType(Long id, String eventName) {
		this.id = id;
		this.eventName = eventName;
	}

	public Long getId() {
		return id;
	}

	public String getEventName() {
		return eventName;
	}

}