package demo.clawing.lottery.pojo.type;

public enum ClawTaskType {

	/** lotterySix */
	lotterySix(1L, "lotterySix"),
	
	;

	private Long id;
	private String eventName;

	ClawTaskType(Long id, String eventName) {
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
