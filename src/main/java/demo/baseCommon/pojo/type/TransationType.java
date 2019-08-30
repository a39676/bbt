package demo.baseCommon.pojo.type;

public enum TransationType {
	
	transationTypeIncome("收入", 1),
	transationTypePay("支出", 2),
	transationTypeFix("冲正", 3),
	;
	
	private String transationTypeName;
	private Integer transationTypeCode;
	
	TransationType(String transationTypeName, Integer transationTypeCode) {
		this.transationTypeName = transationTypeName;
		this.transationTypeCode = transationTypeCode;
	}
	

	public String getName() {
		return transationTypeName;
	}

	public Integer getCode() {
		return transationTypeCode;
	}

	public static TransationType getType(String typeName) {
		for(TransationType t : TransationType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static TransationType getType(Integer typeCode) {
		for(TransationType t : TransationType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
