package demo.clawing.movie.pojo.type;

public enum MovieCrewType {
	
	/** director */
	director("director", 2),
	/** actor */
	actor("actor", 3),
	;
	
	private String name;
	private Integer code;
	
	MovieCrewType(String evaluationName, Integer evaluationCode) {
		this.name = evaluationName;
		this.code = evaluationCode;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static MovieCrewType getType(String typeName) {
		for(MovieCrewType t : MovieCrewType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static MovieCrewType getType(Integer typeCode) {
		for(MovieCrewType t : MovieCrewType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
