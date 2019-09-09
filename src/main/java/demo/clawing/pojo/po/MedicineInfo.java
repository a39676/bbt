package demo.clawing.pojo.po;

import java.time.LocalDateTime;

public class MedicineInfo {
    private Long id;

    private String medicineName;

    private String medicineCommonName;

    private String medicineManagerPreffix;

    private Long medicineManagerNumber;

    private Long medicineFactoryId;

    private LocalDateTime createTime;

    private Boolean isMedicalInsurance;

    private Boolean isPrescription;

    private Boolean isNationalBasicMedicine;

    private Boolean isDelete;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicineName() {
        return medicineName;
    }

    public void setMedicineName(String medicineName) {
        this.medicineName = medicineName == null ? null : medicineName.trim();
    }

    public String getMedicineCommonName() {
        return medicineCommonName;
    }

    public void setMedicineCommonName(String medicineCommonName) {
        this.medicineCommonName = medicineCommonName == null ? null : medicineCommonName.trim();
    }

    public String getMedicineManagerPreffix() {
        return medicineManagerPreffix;
    }

    public void setMedicineManagerPreffix(String medicineManagerPreffix) {
        this.medicineManagerPreffix = medicineManagerPreffix == null ? null : medicineManagerPreffix.trim();
    }

    public Long getMedicineManagerNumber() {
        return medicineManagerNumber;
    }

    public void setMedicineManagerNumber(Long medicineManagerNumber) {
        this.medicineManagerNumber = medicineManagerNumber;
    }

    public Long getMedicineFactoryId() {
        return medicineFactoryId;
    }

    public void setMedicineFactoryId(Long medicineFactoryId) {
        this.medicineFactoryId = medicineFactoryId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsMedicalInsurance() {
        return isMedicalInsurance;
    }

    public void setIsMedicalInsurance(Boolean isMedicalInsurance) {
        this.isMedicalInsurance = isMedicalInsurance;
    }

    public Boolean getIsPrescription() {
        return isPrescription;
    }

    public void setIsPrescription(Boolean isPrescription) {
        this.isPrescription = isPrescription;
    }

    public Boolean getIsNationalBasicMedicine() {
        return isNationalBasicMedicine;
    }

    public void setIsNationalBasicMedicine(Boolean isNationalBasicMedicine) {
        this.isNationalBasicMedicine = isNationalBasicMedicine;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }
}