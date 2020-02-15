package demo.clawing.dailySign.pojo.type;

public enum DailySignCaseType {

	/** wuYiJob */
	wuYiJob(2L, "wuYiJob"),
	
	/** liePin */
	liePin(3L, "liePin"),
	
	/** cdBao */
	cdBao(4L, "cdBao"),
	
	/** zhiLian */
	zhiLian(5L, "zhiLian"),
	
	;

	private Long id;
	private String eventName;

	DailySignCaseType(Long id, String eventName) {
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
