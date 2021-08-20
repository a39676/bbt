package demo.scriptCore.collecting.jandan.pojo.type;

public enum CollectingFlowType {

	/** jianDan */
	jianDan(1L, "jianDan"),
	
	;

	private Long id;
	private String eventName;

	CollectingFlowType(Long id, String eventName) {
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
