package demo.finance.cryptoCoin.data.pojo.type;

public enum CryptoBigMoveCounterType {

	IN_MINUTE("in mintues", "Min", 600), 
	IN_HOUR("in hours", "Hour", 3600),
	;

	private String name;
	private String redisKeyTemplateKeyWord;
	private Integer noticeCacheLivingSeconds;

	CryptoBigMoveCounterType(String name, String redisKeyTemplateKeyWord, Integer noticeCacheLivingSeconds) {
		this.name = name;
		this.redisKeyTemplateKeyWord = redisKeyTemplateKeyWord;
		this.noticeCacheLivingSeconds = noticeCacheLivingSeconds;
	}

	public String getName() {
		return name;
	}

	public String getRedisKeyTemplateKeyWord() {
		return redisKeyTemplateKeyWord;
	}

	public Integer getNoticeCacheLivingSeconds() {
		return noticeCacheLivingSeconds;
	}

}
