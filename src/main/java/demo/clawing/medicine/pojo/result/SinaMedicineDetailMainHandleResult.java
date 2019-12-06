package demo.clawing.medicine.pojo.result;

import auxiliaryCommon.pojo.result.CommonResult;

public class SinaMedicineDetailMainHandleResult extends CommonResult {

	/** 主要成分 */
	private String mainIngredient;
	/** 药品类型 */
	private String medicineType;
	/** 药品规格 */
	private String medicineSpecifications;
	/** 功能主治 */
	private String indication;
	/** 用法用量 */
	private String dosage;
	/** 不良反应 */
	private String adverseReactions;
	/** 注意事项 */
	private String precautionss;

	public String getMainIngredient() {
		return mainIngredient;
	}

	public void setMainIngredient(String mainIngredient) {
		this.mainIngredient = mainIngredient;
	}

	public String getMedicineType() {
		return medicineType;
	}

	public void setMedicineType(String medicineType) {
		this.medicineType = medicineType;
	}

	public String getMedicineSpecifications() {
		return medicineSpecifications;
	}

	public void setMedicineSpecifications(String medicineSpecifications) {
		this.medicineSpecifications = medicineSpecifications;
	}

	public String getIndication() {
		return indication;
	}

	public void setIndication(String indication) {
		this.indication = indication;
	}

	public String getDosage() {
		return dosage;
	}

	public void setDosage(String dosage) {
		this.dosage = dosage;
	}

	public String getAdverseReactions() {
		return adverseReactions;
	}

	public void setAdverseReactions(String adverseReactions) {
		this.adverseReactions = adverseReactions;
	}

	public String getPrecautionss() {
		return precautionss;
	}

	public void setPrecautionss(String precautionss) {
		this.precautionss = precautionss;
	}

	@Override
	public String toString() {
		return "SinaMedicineDetailMainHandleResult [mainIngredient=" + mainIngredient + ", medicineType=" + medicineType
				+ ", medicineSpecifications=" + medicineSpecifications + ", indication=" + indication + ", dosage="
				+ dosage + ", adverseReactions=" + adverseReactions + ", precautionss=" + precautionss + "]";
	}

}
