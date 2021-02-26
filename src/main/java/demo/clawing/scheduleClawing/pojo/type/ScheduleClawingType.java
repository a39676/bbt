package demo.clawing.scheduleClawing.pojo.type;

public enum ScheduleClawingType {

	/** wuYiJob */
	WU_YI_JOB(2L, "wuYiJob"),
	
	/** liePin */
	LIE_PIN(3L, "liePin"),
	
	/** zhiLian */
	ZHI_LIAN(5L, "zhiLian"),
	
	/** maiMai */
	MAI_MAI(6L, "maiMai"),
	
	/** 
	 * https://goldprice.org/gold-price-data.html
	 * Precious metals price clawing 
	 * */
	PRECIOUS_METAL_PRICE(7L, "preciousMetalPrice"),
	
	CRYPTO_COIN_MINUTE_DATA(9L, "cryptoCoinMinuteData"),
	
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
