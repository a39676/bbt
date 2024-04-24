package demo.finance.cryptoCoin.data.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CryptoCoinCatalogExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CryptoCoinCatalogExample() {
        oredCriteria = new ArrayList<>();
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
            criteria = new ArrayList<>();
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

        public Criteria andCoinNameEnShortIsNull() {
            addCriterion("coin_name_en_short is null");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnShortIsNotNull() {
            addCriterion("coin_name_en_short is not null");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnShortEqualTo(String value) {
            addCriterion("coin_name_en_short =", value, "coinNameEnShort");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnShortNotEqualTo(String value) {
            addCriterion("coin_name_en_short <>", value, "coinNameEnShort");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnShortGreaterThan(String value) {
            addCriterion("coin_name_en_short >", value, "coinNameEnShort");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnShortGreaterThanOrEqualTo(String value) {
            addCriterion("coin_name_en_short >=", value, "coinNameEnShort");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnShortLessThan(String value) {
            addCriterion("coin_name_en_short <", value, "coinNameEnShort");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnShortLessThanOrEqualTo(String value) {
            addCriterion("coin_name_en_short <=", value, "coinNameEnShort");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnShortLike(String value) {
            addCriterion("coin_name_en_short like", value, "coinNameEnShort");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnShortNotLike(String value) {
            addCriterion("coin_name_en_short not like", value, "coinNameEnShort");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnShortIn(List<String> values) {
            addCriterion("coin_name_en_short in", values, "coinNameEnShort");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnShortNotIn(List<String> values) {
            addCriterion("coin_name_en_short not in", values, "coinNameEnShort");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnShortBetween(String value1, String value2) {
            addCriterion("coin_name_en_short between", value1, value2, "coinNameEnShort");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnShortNotBetween(String value1, String value2) {
            addCriterion("coin_name_en_short not between", value1, value2, "coinNameEnShort");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnIsNull() {
            addCriterion("coin_name_en is null");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnIsNotNull() {
            addCriterion("coin_name_en is not null");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnEqualTo(String value) {
            addCriterion("coin_name_en =", value, "coinNameEn");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnNotEqualTo(String value) {
            addCriterion("coin_name_en <>", value, "coinNameEn");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnGreaterThan(String value) {
            addCriterion("coin_name_en >", value, "coinNameEn");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnGreaterThanOrEqualTo(String value) {
            addCriterion("coin_name_en >=", value, "coinNameEn");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnLessThan(String value) {
            addCriterion("coin_name_en <", value, "coinNameEn");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnLessThanOrEqualTo(String value) {
            addCriterion("coin_name_en <=", value, "coinNameEn");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnLike(String value) {
            addCriterion("coin_name_en like", value, "coinNameEn");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnNotLike(String value) {
            addCriterion("coin_name_en not like", value, "coinNameEn");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnIn(List<String> values) {
            addCriterion("coin_name_en in", values, "coinNameEn");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnNotIn(List<String> values) {
            addCriterion("coin_name_en not in", values, "coinNameEn");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnBetween(String value1, String value2) {
            addCriterion("coin_name_en between", value1, value2, "coinNameEn");
            return (Criteria) this;
        }

        public Criteria andCoinNameEnNotBetween(String value1, String value2) {
            addCriterion("coin_name_en not between", value1, value2, "coinNameEn");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnIsNull() {
            addCriterion("coin_name_cn is null");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnIsNotNull() {
            addCriterion("coin_name_cn is not null");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnEqualTo(String value) {
            addCriterion("coin_name_cn =", value, "coinNameCn");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnNotEqualTo(String value) {
            addCriterion("coin_name_cn <>", value, "coinNameCn");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnGreaterThan(String value) {
            addCriterion("coin_name_cn >", value, "coinNameCn");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnGreaterThanOrEqualTo(String value) {
            addCriterion("coin_name_cn >=", value, "coinNameCn");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnLessThan(String value) {
            addCriterion("coin_name_cn <", value, "coinNameCn");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnLessThanOrEqualTo(String value) {
            addCriterion("coin_name_cn <=", value, "coinNameCn");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnLike(String value) {
            addCriterion("coin_name_cn like", value, "coinNameCn");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnNotLike(String value) {
            addCriterion("coin_name_cn not like", value, "coinNameCn");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnIn(List<String> values) {
            addCriterion("coin_name_cn in", values, "coinNameCn");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnNotIn(List<String> values) {
            addCriterion("coin_name_cn not in", values, "coinNameCn");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnBetween(String value1, String value2) {
            addCriterion("coin_name_cn between", value1, value2, "coinNameCn");
            return (Criteria) this;
        }

        public Criteria andCoinNameCnNotBetween(String value1, String value2) {
            addCriterion("coin_name_cn not between", value1, value2, "coinNameCn");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNull() {
            addCriterion("createTime is null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIsNotNull() {
            addCriterion("createTime is not null");
            return (Criteria) this;
        }

        public Criteria andCreatetimeEqualTo(LocalDateTime value) {
            addCriterion("createTime =", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotEqualTo(LocalDateTime value) {
            addCriterion("createTime <>", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThan(LocalDateTime value) {
            addCriterion("createTime >", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("createTime >=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThan(LocalDateTime value) {
            addCriterion("createTime <", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("createTime <=", value, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeIn(List<LocalDateTime> values) {
            addCriterion("createTime in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotIn(List<LocalDateTime> values) {
            addCriterion("createTime not in", values, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("createTime between", value1, value2, "createtime");
            return (Criteria) this;
        }

        public Criteria andCreatetimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("createTime not between", value1, value2, "createtime");
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