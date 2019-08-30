package demo.geographical.pojo.type;

/**
 * 地理位置类型
 */
public enum GeographicalAreaType {

	/** 省 */
	province("province", 1), 
	/** 市 */	
	city("city", 2), 
	/** 区 */	
	district("district", 3) 
	;

	private String typeName;
	private Integer typeCode;

	GeographicalAreaType(String typeName, Integer typeCode) {
		this.typeName = typeName;
		this.typeCode = typeCode;
	}

	public String getName() {
		return typeName;
	}

	public Integer getCode() {
		return typeCode;
	}

	public static GeographicalAreaType getType(String typeName) {
		for (GeographicalAreaType t : GeographicalAreaType.values()) {
			if (t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}

	public static GeographicalAreaType getType(Integer typeCode) {
		for (GeographicalAreaType t : GeographicalAreaType.values()) {
			if (t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}

}
