package demo.testCase.pojo.type;

public enum TestModuleType {

	/** movie clawing */
	movieClawing(1L, "movieClawing"),
	
	/** badJoke */
	badJoke(2L, "badJoke"),

	/** ATDemo */
	ATDemo(3L, "ATDemo"),
	;

	private Long id;
	private String eventName;

	TestModuleType(Long id, String eventName) {
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
