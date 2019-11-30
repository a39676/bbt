package demo.dailySign.pojo.type;

public enum DailySignCaseType {

	/** quqi */
	quqi(1L, "quqi"),
	
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
