package demo.clawing.pojo.po;

public class MedicineInfoError {
    private Long id;

    private Long medicineId;

    private Boolean headDetailError;

    private Boolean mainDetailError;

    private Boolean documentError;

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

    public Boolean getHeadDetailError() {
        return headDetailError;
    }

    public void setHeadDetailError(Boolean headDetailError) {
        this.headDetailError = headDetailError;
    }

    public Boolean getMainDetailError() {
        return mainDetailError;
    }

    public void setMainDetailError(Boolean mainDetailError) {
        this.mainDetailError = mainDetailError;
    }

    public Boolean getDocumentError() {
        return documentError;
    }

    public void setDocumentError(Boolean documentError) {
        this.documentError = documentError;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}