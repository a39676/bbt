package demo.scriptCore.medicine.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ClawingMedicineConstantExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public ClawingMedicineConstantExample() {
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

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andConstantNameIsNull() {
            addCriterion("constant_name is null");
            return (Criteria) this;
        }

        public Criteria andConstantNameIsNotNull() {
            addCriterion("constant_name is not null");
            return (Criteria) this;
        }

        public Criteria andConstantNameEqualTo(String value) {
            addCriterion("constant_name =", value, "constantName");
            return (Criteria) this;
        }

        public Criteria andConstantNameNotEqualTo(String value) {
            addCriterion("constant_name <>", value, "constantName");
            return (Criteria) this;
        }

        public Criteria andConstantNameGreaterThan(String value) {
            addCriterion("constant_name >", value, "constantName");
            return (Criteria) this;
        }

        public Criteria andConstantNameGreaterThanOrEqualTo(String value) {
            addCriterion("constant_name >=", value, "constantName");
            return (Criteria) this;
        }

        public Criteria andConstantNameLessThan(String value) {
            addCriterion("constant_name <", value, "constantName");
            return (Criteria) this;
        }

        public Criteria andConstantNameLessThanOrEqualTo(String value) {
            addCriterion("constant_name <=", value, "constantName");
            return (Criteria) this;
        }

        public Criteria andConstantNameLike(String value) {
            addCriterion("constant_name like", value, "constantName");
            return (Criteria) this;
        }

        public Criteria andConstantNameNotLike(String value) {
            addCriterion("constant_name not like", value, "constantName");
            return (Criteria) this;
        }

        public Criteria andConstantNameIn(List<String> values) {
            addCriterion("constant_name in", values, "constantName");
            return (Criteria) this;
        }

        public Criteria andConstantNameNotIn(List<String> values) {
            addCriterion("constant_name not in", values, "constantName");
            return (Criteria) this;
        }

        public Criteria andConstantNameBetween(String value1, String value2) {
            addCriterion("constant_name between", value1, value2, "constantName");
            return (Criteria) this;
        }

        public Criteria andConstantNameNotBetween(String value1, String value2) {
            addCriterion("constant_name not between", value1, value2, "constantName");
            return (Criteria) this;
        }

        public Criteria andConstantValueIsNull() {
            addCriterion("constant_value is null");
            return (Criteria) this;
        }

        public Criteria andConstantValueIsNotNull() {
            addCriterion("constant_value is not null");
            return (Criteria) this;
        }

        public Criteria andConstantValueEqualTo(String value) {
            addCriterion("constant_value =", value, "constantValue");
            return (Criteria) this;
        }

        public Criteria andConstantValueNotEqualTo(String value) {
            addCriterion("constant_value <>", value, "constantValue");
            return (Criteria) this;
        }

        public Criteria andConstantValueGreaterThan(String value) {
            addCriterion("constant_value >", value, "constantValue");
            return (Criteria) this;
        }

        public Criteria andConstantValueGreaterThanOrEqualTo(String value) {
            addCriterion("constant_value >=", value, "constantValue");
            return (Criteria) this;
        }

        public Criteria andConstantValueLessThan(String value) {
            addCriterion("constant_value <", value, "constantValue");
            return (Criteria) this;
        }

        public Criteria andConstantValueLessThanOrEqualTo(String value) {
            addCriterion("constant_value <=", value, "constantValue");
            return (Criteria) this;
        }

        public Criteria andConstantValueLike(String value) {
            addCriterion("constant_value like", value, "constantValue");
            return (Criteria) this;
        }

        public Criteria andConstantValueNotLike(String value) {
            addCriterion("constant_value not like", value, "constantValue");
            return (Criteria) this;
        }

        public Criteria andConstantValueIn(List<String> values) {
            addCriterion("constant_value in", values, "constantValue");
            return (Criteria) this;
        }

        public Criteria andConstantValueNotIn(List<String> values) {
            addCriterion("constant_value not in", values, "constantValue");
            return (Criteria) this;
        }

        public Criteria andConstantValueBetween(String value1, String value2) {
            addCriterion("constant_value between", value1, value2, "constantValue");
            return (Criteria) this;
        }

        public Criteria andConstantValueNotBetween(String value1, String value2) {
            addCriterion("constant_value not between", value1, value2, "constantValue");
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