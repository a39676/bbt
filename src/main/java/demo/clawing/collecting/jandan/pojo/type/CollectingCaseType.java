package demo.clawing.collecting.jandan.pojo.type;

public enum CollectingCaseType {

	/** jianDan */
	jianDan(1L, "jianDan"),
	
	;

	private Long id;
	private String eventName;

	CollectingCaseType(Long id, String eventName) {
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
