package demo.clawing.badJoke.sms.pojo.type;

public enum BadJokeCaseType {

	/** bad joke sms */
	badJokeSms(11L, "badJokeSms"),
	;

	private Long id;
	private String eventName;

	BadJokeCaseType(Long id, String eventName) {
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
