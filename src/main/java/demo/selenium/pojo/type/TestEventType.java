package demo.selenium.pojo.type;

public enum TestEventType {
	
	/** testDemoEvent2 */
	testDemoEvent2("testDemoEvent", 2L),
	/** testDemoEvent3 */
	testDemoEvent3("testDemoEvent", 3L),
	;
	
	private String name;
	private Long code;
	
	TestEventType(String name, Long code) {
		this.name = name;
		this.code = code;
	}
	
	public String getName() {
		return name;
	}

	public Long getCode() {
		return code;
	}
	
	public static TestEventType getType(String typeName) {
		for(TestEventType t : TestEventType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static TestEventType getType(Long code) {
		for(TestEventType t : TestEventType.values()) {
			if(t.getCode().equals(code)) {
				return t;
			}
		}
		return null;
	}
	
}
