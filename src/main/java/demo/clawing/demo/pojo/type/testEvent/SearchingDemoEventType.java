package demo.clawing.demo.pojo.type.testEvent;

public enum SearchingDemoEventType {

	/** bing demo */
	bingDemo(1L, "bingDemo"),

	;

	private Long id;
	private String caseName;

	SearchingDemoEventType(Long id, String caseName) {
		this.id = id;
		this.caseName = caseName;
	}

	public Long getId() {
		return id;
	}

	public String getCaseName() {
		return caseName;
	}

	public static SearchingDemoEventType getType(Long id) {
		for (SearchingDemoEventType t : SearchingDemoEventType.values()) {
			if (t.getId().equals(id)) {
				return t;
			}
		}
		return null;
	}

}
