package demo.clawing.pojo.po;

import demo.clawing.pojo.type.MedicineDocumentType;

public class MedicineDocument {
    private Long id;

    private Long medicineId;

    private String documentPath;

    /**
     * {@link MedicineDocumentType}
     */
    private Byte documentType;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public String getDocumentPath() {
        return documentPath;
    }

    public void setDocumentPath(String documentPath) {
        this.documentPath = documentPath == null ? null : documentPath.trim();
    }

    public Byte getDocumentType() {
        return documentType;
    }

    public void setDocumentType(Byte documentType) {
        this.documentType = documentType;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}