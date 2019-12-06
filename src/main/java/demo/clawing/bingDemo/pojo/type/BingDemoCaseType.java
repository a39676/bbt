package demo.clawing.bingDemo.pojo.type;

public enum BingDemoCaseType {

	/** bing demo */
	bingDemo(1L, "bingDemo"),
	;

	private Long id;
	private String eventName;

	BingDemoCaseType(Long id, String eventName) {
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
