package demo.clawing.badJoke.sms.pojo.type;

public enum BadJokeSubCaseType {

	/** _91wenwen */
	_91wenwen(1, "_91wenwen"),
	/** zhiWang */
	zhiWang(2, "zhiWang"),
	/** yinXiang */
	yinXiang(3, "yinXiang"),
	/** mafengwo */
	mafengwo(4, "mafengwo"),
	/** zhipin */
	zhipin(5, "zhipin"),
	/** jumpw */
	jumpw(6, "jumpw"),
	/** nike */
	nike(7, "nike"),
	/** chunQiu */
	chunQiu(8, "chunQiu"),
	/** flyme */
	flyme(9, "flyme"),
	/** ctrip */
	ctrip(10, "ctrip"),
	/** wondercv */
	wondercv(11, "wondercv"),
	/** zjzwfw */
	zjzwfw(12, "zjzwfw"),
	/** _9you */
	_9you(13, "_9you"),
	/** hnair */
	hnair(14, "hnair"),
	
	;

	private Integer code;
	private String name;

	BadJokeSubCaseType(Integer id, String eventName) {
		this.code = id;
		this.name = eventName;
	}

	public Integer getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public static BadJokeSubCaseType getType(String typeName) {
		for(BadJokeSubCaseType t : BadJokeSubCaseType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static BadJokeSubCaseType getType(Integer code) {
		for(BadJokeSubCaseType t : BadJokeSubCaseType.values()) {
			if(t.getCode().equals(code)) {
				return t;
			}
		}
		return null;
	}
}
