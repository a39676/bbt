package demo.scriptCore.scheduleClawing.pojo.type;


public enum CryptoCompareWebSocketMsgType {
	
	/*
	 * good connection type
	 */
	NROMAL_MSG ("NORMAL_MSG", 5),
	STREAMERWELCOME ("STREAMERWELCOME", 20),
	SUBSCRIBECOMPLETE ("SUBSCRIBECOMPLETE", 16),
	LOADCOMPLETE ("LOADCOMPLETE", 3),
	UNSUBSCRIBECOMPLETE ("UNSUBSCRIBECOMPLETE", 17),
	UNSUBSCRIBEALLCOMPLETE ("UNSUBSCRIBEALLCOMPLETE", 18),
	HEARTBEAT ("HEARTBEAT", 999),
	
	/*
	 * bad connection type
	 */
	UNAUTHORIZED ("UNAUTHORIZED", 401),
	RATE_LIMIT_OPENING_SOCKETS_TOO_FAST ("RATE_LIMIT_OPENING_SOCKETS_TOO_FAST", 429),
	TOO_MANY_SOCKETS_MAX_ ("TOO_MANY_SOCKETS_MAX_", 429),
	TOO_MANY_SUBSCRIPTIONS_MAX_ ("TOO_MANY_SUBSCRIPTIONS_MAX_", 429),
	INVALID_JSON ("INVALID_JSON", 500),
	INVALID_SUB ("INVALID_SUB", 500),
	INVALID_PARAMETER ("INVALID_PARAMETER", 500),
	SUBSCRIPTION_UNRECOGNIZED ("SUBSCRIPTION_UNRECOGNIZED", 500),
	SUBSCRIPTION_ALREADY_ACTIVE ("SUBSCRIPTION_ALREADY_ACTIVE", 500),
	FORCE_DISCONNECT ("FORCE_DISCONNECT", 500),
	;
	
	private String name;
	private Integer code;
	
	CryptoCompareWebSocketMsgType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static CryptoCompareWebSocketMsgType getType(String typeName) {
		for(CryptoCompareWebSocketMsgType t : CryptoCompareWebSocketMsgType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static CryptoCompareWebSocketMsgType getType(Integer typeCode) {
		for (CryptoCompareWebSocketMsgType t : CryptoCompareWebSocketMsgType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
