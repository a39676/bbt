package demo.testCase.pojo.type;

public enum MovieTestCaseType {
	
	/** dytt */
	dytt(5L, "dytt"),
	/** homeFei 搜集新帖链接 */
	homeFeiCollection(6L, "homeFeiCollection"),
	/** homeFei 按帖链接下载 */
	homeFeiDownload(7L, "homeFeiDownload"),
	;
	
	private Long id;
	private String eventName;
	
	MovieTestCaseType(Long id, String eventName) {
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
