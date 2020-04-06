package demo.clawing.localClawing.pojo.type;

public enum LocalClawingCaseType {

	/** bossZhiPin */
	bossZhiPin(1L, "bossZhiPin"),
	
	/** laGou */
	laGou(2L, "laGou"),
	
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
