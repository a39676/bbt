package demo.clawing.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class SinaMedicineDetailHeadHandleResult extends CommonResult {

	/** 商品名 */
	private String commodityName;
	/** 通用名 */
	private String commonName;
	/** 国药准字号前缀 */
	private String medicineManagerCodePrefix;
	/** 国药准字号数字部分 */
	private Integer medicineManagerCodeNumber;
	/** 生产厂家 */
	private String factoryName;
	/** 是否医保 */
	private Boolean isMedicineInsurance;
	/** 是否非处方 */
	private Boolean isPrescription;
	/** 是否国家基础药物 */
	private Boolean isNationalBasicMedicine;

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommonName() {
		return commonName;
	}

	public void setCommonName(String commonName) {
		this.commonName = commonName;
	}

	public String getMedicineManagerCodePrefix() {
		return medicineManagerCodePrefix;
	}

	public void setMedicineManagerCodePrefix(String medicineManagerCodePrefix) {
		this.medicineManagerCodePrefix = medicineManagerCodePrefix;
	}

	public Integer getMedicineManagerCodeNumber() {
		return medicineManagerCodeNumber;
	}

	public void setMedicineManagerCodeNumber(Integer medicineManagerCodeNumber) {
		this.medicineManagerCodeNumber = medicineManagerCodeNumber;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}

	public Boolean getIsMedicineInsurance() {
		return isMedicineInsurance;
	}

	public void setIsMedicineInsurance(Boolean isMedicineInsurance) {
		this.isMedicineInsurance = isMedicineInsurance;
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

	@Override
	public String toString() {
		return "SinaMedicineDetailHeadHandleResult [commodityName=" + commodityName + ", commonName=" + commonName
				+ ", medicineManagerCodePrefix=" + medicineManagerCodePrefix + ", medicineManagerCodeNumber="
				+ medicineManagerCodeNumber + ", factoryName=" + factoryName + ", isMedicineInsurance="
				+ isMedicineInsurance + ", isPrescription=" + isPrescription + ", isNationalBasicMedicine="
				+ isNationalBasicMedicine + "]";
	}

}
