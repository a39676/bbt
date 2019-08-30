package demo.mobile.pojo.bo;

public enum MobileOperatorType {
	
	dianXin("电信", 1),
	yiDong("移动", 2),
	lianTong("联通", 3),
	;
	
	private String typeName;
	private Integer typeCode;
	
	MobileOperatorType(String typeName, Integer typeCode) {
		this.typeName = typeName;
		this.typeCode = typeCode;
	}
	

	public String getTypeName() {
		return typeName;
	}

	public Integer getTypeCode() {
		return typeCode;
	}

	public static MobileOperatorType getType(String typeName) {
		for(MobileOperatorType t : MobileOperatorType.values()) {
			if(t.getTypeName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static MobileOperatorType getType(Integer typeCode) {
		for(MobileOperatorType t : MobileOperatorType.values()) {
			if(t.getTypeCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
