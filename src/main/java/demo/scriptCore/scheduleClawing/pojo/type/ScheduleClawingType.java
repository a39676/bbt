package demo.scriptCore.scheduleClawing.pojo.type;

public enum ScheduleClawingType {

	/** wuYiJob */
	WU_YI_JOB(2L, "wuYiJob"),
	
	/** maiMai */
	MAI_MAI(6L, "maiMai"),
	
	WAWAWIWA_COMIC(10L, "wawawiwaComic"),
	
	CRYPTO_COIN_DAILY_DATA(11L, "cryptoCoinDailyData"),
	;

	private Long id;
	private String flowName;

	ScheduleClawingType(Long id, String eventName) {
		this.id = id;
		this.flowName = eventName;
	}

	public Long getId() {
		return id;
	}

	public String getFlowName() {
		return flowName;
	}

}
