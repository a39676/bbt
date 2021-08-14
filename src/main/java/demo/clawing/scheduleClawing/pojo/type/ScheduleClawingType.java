package demo.clawing.scheduleClawing.pojo.type;

public enum ScheduleClawingType {

	/** wuYiJob */
	WU_YI_JOB(2L, "wuYiJob"),
	
	/** maiMai */
	MAI_MAI(6L, "maiMai"),
	
	WAWAWIWA_COMIC(10L, "wawawiwaComic"),
	
	CRYPTO_COIN_DAILY_DATA(11L, "cryptoCoinDailyData"),
	;

	private Long id;
	private String eventName;

	ScheduleClawingType(Long id, String eventName) {
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
