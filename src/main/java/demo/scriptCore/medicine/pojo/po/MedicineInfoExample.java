package demo.scriptCore.medicine.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MedicineInfoExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public MedicineInfoExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andMedicineNameIsNull() {
            addCriterion("medicine_name is null");
            return (Criteria) this;
        }

        public Criteria andMedicineNameIsNotNull() {
            addCriterion("medicine_name is not null");
            return (Criteria) this;
        }

        public Criteria andMedicineNameEqualTo(String value) {
            addCriterion("medicine_name =", value, "medicineName");
            return (Criteria) this;
        }

        public Criteria andMedicineNameNotEqualTo(String value) {
            addCriterion("medicine_name <>", value, "medicineName");
            return (Criteria) this;
        }

        public Criteria andMedicineNameGreaterThan(String value) {
            addCriterion("medicine_name >", value, "medicineName");
            return (Criteria) this;
        }

        public Criteria andMedicineNameGreaterThanOrEqualTo(String value) {
            addCriterion("medicine_name >=", value, "medicineName");
            return (Criteria) this;
        }

        public Criteria andMedicineNameLessThan(String value) {
            addCriterion("medicine_name <", value, "medicineName");
            return (Criteria) this;
        }

        public Criteria andMedicineNameLessThanOrEqualTo(String value) {
            addCriterion("medicine_name <=", value, "medicineName");
            return (Criteria) this;
        }

        public Criteria andMedicineNameLike(String value) {
            addCriterion("medicine_name like", value, "medicineName");
            return (Criteria) this;
        }

        public Criteria andMedicineNameNotLike(String value) {
            addCriterion("medicine_name not like", value, "medicineName");
            return (Criteria) this;
        }

        public Criteria andMedicineNameIn(List<String> values) {
            addCriterion("medicine_name in", values, "medicineName");
            return (Criteria) this;
        }

        public Criteria andMedicineNameNotIn(List<String> values) {
            addCriterion("medicine_name not in", values, "medicineName");
            return (Criteria) this;
        }

        public Criteria andMedicineNameBetween(String value1, String value2) {
            addCriterion("medicine_name between", value1, value2, "medicineName");
            return (Criteria) this;
        }

        public Criteria andMedicineNameNotBetween(String value1, String value2) {
            addCriterion("medicine_name not between", value1, value2, "medicineName");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameIsNull() {
            addCriterion("medicine_common_name is null");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameIsNotNull() {
            addCriterion("medicine_common_name is not null");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameEqualTo(String value) {
            addCriterion("medicine_common_name =", value, "medicineCommonName");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameNotEqualTo(String value) {
            addCriterion("medicine_common_name <>", value, "medicineCommonName");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameGreaterThan(String value) {
            addCriterion("medicine_common_name >", value, "medicineCommonName");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameGreaterThanOrEqualTo(String value) {
            addCriterion("medicine_common_name >=", value, "medicineCommonName");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameLessThan(String value) {
            addCriterion("medicine_common_name <", value, "medicineCommonName");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameLessThanOrEqualTo(String value) {
            addCriterion("medicine_common_name <=", value, "medicineCommonName");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameLike(String value) {
            addCriterion("medicine_common_name like", value, "medicineCommonName");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameNotLike(String value) {
            addCriterion("medicine_common_name not like", value, "medicineCommonName");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameIn(List<String> values) {
            addCriterion("medicine_common_name in", values, "medicineCommonName");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameNotIn(List<String> values) {
            addCriterion("medicine_common_name not in", values, "medicineCommonName");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameBetween(String value1, String value2) {
            addCriterion("medicine_common_name between", value1, value2, "medicineCommonName");
            return (Criteria) this;
        }

        public Criteria andMedicineCommonNameNotBetween(String value1, String value2) {
            addCriterion("medicine_common_name not between", value1, value2, "medicineCommonName");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixIsNull() {
            addCriterion("medicine_manager_preffix is null");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixIsNotNull() {
            addCriterion("medicine_manager_preffix is not null");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixEqualTo(String value) {
            addCriterion("medicine_manager_preffix =", value, "medicineManagerPreffix");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixNotEqualTo(String value) {
            addCriterion("medicine_manager_preffix <>", value, "medicineManagerPreffix");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixGreaterThan(String value) {
            addCriterion("medicine_manager_preffix >", value, "medicineManagerPreffix");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixGreaterThanOrEqualTo(String value) {
            addCriterion("medicine_manager_preffix >=", value, "medicineManagerPreffix");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixLessThan(String value) {
            addCriterion("medicine_manager_preffix <", value, "medicineManagerPreffix");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixLessThanOrEqualTo(String value) {
            addCriterion("medicine_manager_preffix <=", value, "medicineManagerPreffix");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixLike(String value) {
            addCriterion("medicine_manager_preffix like", value, "medicineManagerPreffix");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixNotLike(String value) {
            addCriterion("medicine_manager_preffix not like", value, "medicineManagerPreffix");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixIn(List<String> values) {
            addCriterion("medicine_manager_preffix in", values, "medicineManagerPreffix");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixNotIn(List<String> values) {
            addCriterion("medicine_manager_preffix not in", values, "medicineManagerPreffix");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixBetween(String value1, String value2) {
            addCriterion("medicine_manager_preffix between", value1, value2, "medicineManagerPreffix");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerPreffixNotBetween(String value1, String value2) {
            addCriterion("medicine_manager_preffix not between", value1, value2, "medicineManagerPreffix");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerNumberIsNull() {
            addCriterion("medicine_manager_number is null");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerNumberIsNotNull() {
            addCriterion("medicine_manager_number is not null");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerNumberEqualTo(Long value) {
            addCriterion("medicine_manager_number =", value, "medicineManagerNumber");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerNumberNotEqualTo(Long value) {
            addCriterion("medicine_manager_number <>", value, "medicineManagerNumber");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerNumberGreaterThan(Long value) {
            addCriterion("medicine_manager_number >", value, "medicineManagerNumber");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerNumberGreaterThanOrEqualTo(Long value) {
            addCriterion("medicine_manager_number >=", value, "medicineManagerNumber");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerNumberLessThan(Long value) {
            addCriterion("medicine_manager_number <", value, "medicineManagerNumber");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerNumberLessThanOrEqualTo(Long value) {
            addCriterion("medicine_manager_number <=", value, "medicineManagerNumber");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerNumberIn(List<Long> values) {
            addCriterion("medicine_manager_number in", values, "medicineManagerNumber");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerNumberNotIn(List<Long> values) {
            addCriterion("medicine_manager_number not in", values, "medicineManagerNumber");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerNumberBetween(Long value1, Long value2) {
            addCriterion("medicine_manager_number between", value1, value2, "medicineManagerNumber");
            return (Criteria) this;
        }

        public Criteria andMedicineManagerNumberNotBetween(Long value1, Long value2) {
            addCriterion("medicine_manager_number not between", value1, value2, "medicineManagerNumber");
            return (Criteria) this;
        }

        public Criteria andMedicineFactoryIdIsNull() {
            addCriterion("medicine_factory_id is null");
            return (Criteria) this;
        }

        public Criteria andMedicineFactoryIdIsNotNull() {
            addCriterion("medicine_factory_id is not null");
            return (Criteria) this;
        }

        public Criteria andMedicineFactoryIdEqualTo(Long value) {
            addCriterion("medicine_factory_id =", value, "medicineFactoryId");
            return (Criteria) this;
        }

        public Criteria andMedicineFactoryIdNotEqualTo(Long value) {
            addCriterion("medicine_factory_id <>", value, "medicineFactoryId");
            return (Criteria) this;
        }

        public Criteria andMedicineFactoryIdGreaterThan(Long value) {
            addCriterion("medicine_factory_id >", value, "medicineFactoryId");
            return (Criteria) this;
        }

        public Criteria andMedicineFactoryIdGreaterThanOrEqualTo(Long value) {
            addCriterion("medicine_factory_id >=", value, "medicineFactoryId");
            return (Criteria) this;
        }

        public Criteria andMedicineFactoryIdLessThan(Long value) {
            addCriterion("medicine_factory_id <", value, "medicineFactoryId");
            return (Criteria) this;
        }

        public Criteria andMedicineFactoryIdLessThanOrEqualTo(Long value) {
            addCriterion("medicine_factory_id <=", value, "medicineFactoryId");
            return (Criteria) this;
        }

        public Criteria andMedicineFactoryIdIn(List<Long> values) {
            addCriterion("medicine_factory_id in", values, "medicineFactoryId");
            return (Criteria) this;
        }

        public Criteria andMedicineFactoryIdNotIn(List<Long> values) {
            addCriterion("medicine_factory_id not in", values, "medicineFactoryId");
            return (Criteria) this;
        }

        public Criteria andMedicineFactoryIdBetween(Long value1, Long value2) {
            addCriterion("medicine_factory_id between", value1, value2, "medicineFactoryId");
            return (Criteria) this;
        }

        public Criteria andMedicineFactoryIdNotBetween(Long value1, Long value2) {
            addCriterion("medicine_factory_id not between", value1, value2, "medicineFactoryId");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(LocalDateTime value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(LocalDateTime value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(LocalDateTime value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(LocalDateTime value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<LocalDateTime> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<LocalDateTime> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andIsMedicalInsuranceIsNull() {
            addCriterion("is_medical_insurance is null");
            return (Criteria) this;
        }

        public Criteria andIsMedicalInsuranceIsNotNull() {
            addCriterion("is_medical_insurance is not null");
            return (Criteria) this;
        }

        public Criteria andIsMedicalInsuranceEqualTo(Boolean value) {
            addCriterion("is_medical_insurance =", value, "isMedicalInsurance");
            return (Criteria) this;
        }

        public Criteria andIsMedicalInsuranceNotEqualTo(Boolean value) {
            addCriterion("is_medical_insurance <>", value, "isMedicalInsurance");
            return (Criteria) this;
        }

        public Criteria andIsMedicalInsuranceGreaterThan(Boolean value) {
            addCriterion("is_medical_insurance >", value, "isMedicalInsurance");
            return (Criteria) this;
        }

        public Criteria andIsMedicalInsuranceGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_medical_insurance >=", value, "isMedicalInsurance");
            return (Criteria) this;
        }

        public Criteria andIsMedicalInsuranceLessThan(Boolean value) {
            addCriterion("is_medical_insurance <", value, "isMedicalInsurance");
            return (Criteria) this;
        }

        public Criteria andIsMedicalInsuranceLessThanOrEqualTo(Boolean value) {
            addCriterion("is_medical_insurance <=", value, "isMedicalInsurance");
            return (Criteria) this;
        }

        public Criteria andIsMedicalInsuranceIn(List<Boolean> values) {
            addCriterion("is_medical_insurance in", values, "isMedicalInsurance");
            return (Criteria) this;
        }

        public Criteria andIsMedicalInsuranceNotIn(List<Boolean> values) {
            addCriterion("is_medical_insurance not in", values, "isMedicalInsurance");
            return (Criteria) this;
        }

        public Criteria andIsMedicalInsuranceBetween(Boolean value1, Boolean value2) {
            addCriterion("is_medical_insurance between", value1, value2, "isMedicalInsurance");
            return (Criteria) this;
        }

        public Criteria andIsMedicalInsuranceNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_medical_insurance not between", value1, value2, "isMedicalInsurance");
            return (Criteria) this;
        }

        public Criteria andIsPrescriptionIsNull() {
            addCriterion("is_prescription is null");
            return (Criteria) this;
        }

        public Criteria andIsPrescriptionIsNotNull() {
            addCriterion("is_prescription is not null");
            return (Criteria) this;
        }

        public Criteria andIsPrescriptionEqualTo(Boolean value) {
            addCriterion("is_prescription =", value, "isPrescription");
            return (Criteria) this;
        }

        public Criteria andIsPrescriptionNotEqualTo(Boolean value) {
            addCriterion("is_prescription <>", value, "isPrescription");
            return (Criteria) this;
        }

        public Criteria andIsPrescriptionGreaterThan(Boolean value) {
            addCriterion("is_prescription >", value, "isPrescription");
            return (Criteria) this;
        }

        public Criteria andIsPrescriptionGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_prescription >=", value, "isPrescription");
            return (Criteria) this;
        }

        public Criteria andIsPrescriptionLessThan(Boolean value) {
            addCriterion("is_prescription <", value, "isPrescription");
            return (Criteria) this;
        }

        public Criteria andIsPrescriptionLessThanOrEqualTo(Boolean value) {
            addCriterion("is_prescription <=", value, "isPrescription");
            return (Criteria) this;
        }

        public Criteria andIsPrescriptionIn(List<Boolean> values) {
            addCriterion("is_prescription in", values, "isPrescription");
            return (Criteria) this;
        }

        public Criteria andIsPrescriptionNotIn(List<Boolean> values) {
            addCriterion("is_prescription not in", values, "isPrescription");
            return (Criteria) this;
        }

        public Criteria andIsPrescriptionBetween(Boolean value1, Boolean value2) {
            addCriterion("is_prescription between", value1, value2, "isPrescription");
            return (Criteria) this;
        }

        public Criteria andIsPrescriptionNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_prescription not between", value1, value2, "isPrescription");
            return (Criteria) this;
        }

        public Criteria andIsNationalBasicMedicineIsNull() {
            addCriterion("is_national_basic_medicine is null");
            return (Criteria) this;
        }

        public Criteria andIsNationalBasicMedicineIsNotNull() {
            addCriterion("is_national_basic_medicine is not null");
            return (Criteria) this;
        }

        public Criteria andIsNationalBasicMedicineEqualTo(Boolean value) {
            addCriterion("is_national_basic_medicine =", value, "isNationalBasicMedicine");
            return (Criteria) this;
        }

        public Criteria andIsNationalBasicMedicineNotEqualTo(Boolean value) {
            addCriterion("is_national_basic_medicine <>", value, "isNationalBasicMedicine");
            return (Criteria) this;
        }

        public Criteria andIsNationalBasicMedicineGreaterThan(Boolean value) {
            addCriterion("is_national_basic_medicine >", value, "isNationalBasicMedicine");
            return (Criteria) this;
        }

        public Criteria andIsNationalBasicMedicineGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_national_basic_medicine >=", value, "isNationalBasicMedicine");
            return (Criteria) this;
        }

        public Criteria andIsNationalBasicMedicineLessThan(Boolean value) {
            addCriterion("is_national_basic_medicine <", value, "isNationalBasicMedicine");
            return (Criteria) this;
        }

        public Criteria andIsNationalBasicMedicineLessThanOrEqualTo(Boolean value) {
            addCriterion("is_national_basic_medicine <=", value, "isNationalBasicMedicine");
            return (Criteria) this;
        }

        public Criteria andIsNationalBasicMedicineIn(List<Boolean> values) {
            addCriterion("is_national_basic_medicine in", values, "isNationalBasicMedicine");
            return (Criteria) this;
        }

        public Criteria andIsNationalBasicMedicineNotIn(List<Boolean> values) {
            addCriterion("is_national_basic_medicine not in", values, "isNationalBasicMedicine");
            return (Criteria) this;
        }

        public Criteria andIsNationalBasicMedicineBetween(Boolean value1, Boolean value2) {
            addCriterion("is_national_basic_medicine between", value1, value2, "isNationalBasicMedicine");
            return (Criteria) this;
        }

        public Criteria andIsNationalBasicMedicineNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_national_basic_medicine not between", value1, value2, "isNationalBasicMedicine");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNull() {
            addCriterion("is_delete is null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIsNotNull() {
            addCriterion("is_delete is not null");
            return (Criteria) this;
        }

        public Criteria andIsDeleteEqualTo(Boolean value) {
            addCriterion("is_delete =", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotEqualTo(Boolean value) {
            addCriterion("is_delete <>", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThan(Boolean value) {
            addCriterion("is_delete >", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteGreaterThanOrEqualTo(Boolean value) {
            addCriterion("is_delete >=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThan(Boolean value) {
            addCriterion("is_delete <", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteLessThanOrEqualTo(Boolean value) {
            addCriterion("is_delete <=", value, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteIn(List<Boolean> values) {
            addCriterion("is_delete in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotIn(List<Boolean> values) {
            addCriterion("is_delete not in", values, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete between", value1, value2, "isDelete");
            return (Criteria) this;
        }

        public Criteria andIsDeleteNotBetween(Boolean value1, Boolean value2) {
            addCriterion("is_delete not between", value1, value2, "isDelete");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}