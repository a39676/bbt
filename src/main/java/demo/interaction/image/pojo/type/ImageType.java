package demo.interaction.image.pojo.type;

public enum ImageType {
	
	/** zoo */
	zoo("zoo", 2),
	/** meizi */
	meizi("meizi", 9),
	/** moviePoster */
	moviePoster("moviePoster", 10),
	;
	
	private String name;
	private Integer code;
	
	ImageType(String evaluationName, Integer evaluationCode) {
		this.name = evaluationName;
		this.code = evaluationCode;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static ImageType getType(String typeName) {
		for(ImageType t : ImageType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static ImageType getType(Integer typeCode) {
		for(ImageType t : ImageType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
