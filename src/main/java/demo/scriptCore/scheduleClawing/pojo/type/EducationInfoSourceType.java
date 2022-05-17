package demo.scriptCore.scheduleClawing.pojo.type;

public enum EducationInfoSourceType {
	
	GZEDUCMS_CN("gzeducms", 1),
	HAIZHU_GOV_CN("haizhu", 2),
	JYJ_GZ("jyjgzgovcn", 3),
	GZZK_1("gzzk_gz_gov_cn_1", 4),
	;
	
	private String name;
	private Integer code;
	
	EducationInfoSourceType(String name, Integer code) {
		this.name = name;
		this.code = code;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static EducationInfoSourceType getType(String typeName) {
		for(EducationInfoSourceType t : EducationInfoSourceType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static EducationInfoSourceType getType(Integer typeCode) {
		for(EducationInfoSourceType t : EducationInfoSourceType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
