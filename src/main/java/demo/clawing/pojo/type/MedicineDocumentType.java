package demo.clawing.pojo.type;

public enum MedicineDocumentType {
	
	up("↑↑", 1),
	down("↓↓", -1),
	;
	
	private String name;
	private Integer code;
	
	MedicineDocumentType(String evaluationName, Integer evaluationCode) {
		this.name = evaluationName;
		this.code = evaluationCode;
	}
	

	public String getName() {
		return name;
	}

	public Integer getCode() {
		return code;
	}

	public static MedicineDocumentType getType(String typeName) {
		for(MedicineDocumentType t : MedicineDocumentType.values()) {
			if(t.getName().equals(typeName)) {
				return t;
			}
		}
		return null;
	}
	
	public static MedicineDocumentType getType(Integer typeCode) {
		for(MedicineDocumentType t : MedicineDocumentType.values()) {
			if(t.getCode().equals(typeCode)) {
				return t;
			}
		}
		return null;
	}
}
