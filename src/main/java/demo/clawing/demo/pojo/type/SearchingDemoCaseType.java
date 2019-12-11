package demo.clawing.demo.pojo.type;

public enum SearchingDemoCaseType {

	/** bing demo */
	bingDemo(1L, "bingDemo"),
	
	/** baidu demo */
	baiduDemo(2L, "baiduDemo"),
	;

	private Long id;
	private String eventName;

	SearchingDemoCaseType(Long id, String eventName) {
		this.id = id;
		this.eventName = eventName;
	}

	public Long getId() {
		return id;
	}

	public String getEventName() {
		return eventName;
	}
	
	public static SearchingDemoCaseType getType(Long code) {
		for(SearchingDemoCaseType t : SearchingDemoCaseType.values()) {
			if(t.getId().equals(code)) {
				return t;
			}
		}
		return null;
	}

}
