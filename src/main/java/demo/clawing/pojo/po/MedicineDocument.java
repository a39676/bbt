package demo.clawing.pojo.po;

public class MedicineDocument {
    private Long medicineId;

    private String detailPath;

    private String pharmacologyPath;

    private Boolean isDelete;

    public Long getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Long medicineId) {
        this.medicineId = medicineId;
    }

    public String getDetailPath() {
        return detailPath;
    }

    public void setDetailPath(String detailPath) {
        this.detailPath = detailPath == null ? null : detailPath.trim();
    }

    public String getPharmacologyPath() {
        return pharmacologyPath;
    }

    public void setPharmacologyPath(String pharmacologyPath) {
        this.pharmacologyPath = pharmacologyPath == null ? null : pharmacologyPath.trim();
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}