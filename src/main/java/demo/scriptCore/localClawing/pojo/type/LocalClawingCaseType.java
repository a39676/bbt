package demo.scriptCore.localClawing.pojo.type;

public enum LocalClawingCaseType {

	/** demo */
	demo(-1L, "demo"),
	
	
	;

	private Long id;
	private String eventName;

	LocalClawingCaseType(Long id, String eventName) {
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
