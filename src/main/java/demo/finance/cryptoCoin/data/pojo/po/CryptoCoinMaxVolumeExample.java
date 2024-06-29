package demo.finance.cryptoCoin.data.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CryptoCoinMaxVolumeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public CryptoCoinMaxVolumeExample() {
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

        public Criteria andCoinTypeIsNull() {
            addCriterion("coin_type is null");
            return (Criteria) this;
        }

        public Criteria andCoinTypeIsNotNull() {
            addCriterion("coin_type is not null");
            return (Criteria) this;
        }

        public Criteria andCoinTypeEqualTo(Long value) {
            addCriterion("coin_type =", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeNotEqualTo(Long value) {
            addCriterion("coin_type <>", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeGreaterThan(Long value) {
            addCriterion("coin_type >", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeGreaterThanOrEqualTo(Long value) {
            addCriterion("coin_type >=", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeLessThan(Long value) {
            addCriterion("coin_type <", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeLessThanOrEqualTo(Long value) {
            addCriterion("coin_type <=", value, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeIn(List<Long> values) {
            addCriterion("coin_type in", values, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeNotIn(List<Long> values) {
            addCriterion("coin_type not in", values, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeBetween(Long value1, Long value2) {
            addCriterion("coin_type between", value1, value2, "coinType");
            return (Criteria) this;
        }

        public Criteria andCoinTypeNotBetween(Long value1, Long value2) {
            addCriterion("coin_type not between", value1, value2, "coinType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeIsNull() {
            addCriterion("currency_type is null");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeIsNotNull() {
            addCriterion("currency_type is not null");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeEqualTo(Integer value) {
            addCriterion("currency_type =", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotEqualTo(Integer value) {
            addCriterion("currency_type <>", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeGreaterThan(Integer value) {
            addCriterion("currency_type >", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeGreaterThanOrEqualTo(Integer value) {
            addCriterion("currency_type >=", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeLessThan(Integer value) {
            addCriterion("currency_type <", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeLessThanOrEqualTo(Integer value) {
            addCriterion("currency_type <=", value, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeIn(List<Integer> values) {
            addCriterion("currency_type in", values, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotIn(List<Integer> values) {
            addCriterion("currency_type not in", values, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeBetween(Integer value1, Integer value2) {
            addCriterion("currency_type between", value1, value2, "currencyType");
            return (Criteria) this;
        }

        public Criteria andCurrencyTypeNotBetween(Integer value1, Integer value2) {
            addCriterion("currency_type not between", value1, value2, "currencyType");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeIsNull() {
            addCriterion("max_volume is null");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeIsNotNull() {
            addCriterion("max_volume is not null");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeEqualTo(BigDecimal value) {
            addCriterion("max_volume =", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeNotEqualTo(BigDecimal value) {
            addCriterion("max_volume <>", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeGreaterThan(BigDecimal value) {
            addCriterion("max_volume >", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_volume >=", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeLessThan(BigDecimal value) {
            addCriterion("max_volume <", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_volume <=", value, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeIn(List<BigDecimal> values) {
            addCriterion("max_volume in", values, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeNotIn(List<BigDecimal> values) {
            addCriterion("max_volume not in", values, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_volume between", value1, value2, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_volume not between", value1, value2, "maxVolume");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeTimeIsNull() {
            addCriterion("max_volume_time is null");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeTimeIsNotNull() {
            addCriterion("max_volume_time is not null");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeTimeEqualTo(LocalDateTime value) {
            addCriterion("max_volume_time =", value, "maxVolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeTimeNotEqualTo(LocalDateTime value) {
            addCriterion("max_volume_time <>", value, "maxVolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeTimeGreaterThan(LocalDateTime value) {
            addCriterion("max_volume_time >", value, "maxVolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("max_volume_time >=", value, "maxVolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeTimeLessThan(LocalDateTime value) {
            addCriterion("max_volume_time <", value, "maxVolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("max_volume_time <=", value, "maxVolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeTimeIn(List<LocalDateTime> values) {
            addCriterion("max_volume_time in", values, "maxVolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeTimeNotIn(List<LocalDateTime> values) {
            addCriterion("max_volume_time not in", values, "maxVolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("max_volume_time between", value1, value2, "maxVolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxVolumeTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("max_volume_time not between", value1, value2, "maxVolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeIsNull() {
            addCriterion("max_avg5_volume is null");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeIsNotNull() {
            addCriterion("max_avg5_volume is not null");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeEqualTo(BigDecimal value) {
            addCriterion("max_avg5_volume =", value, "maxAvg5Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeNotEqualTo(BigDecimal value) {
            addCriterion("max_avg5_volume <>", value, "maxAvg5Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeGreaterThan(BigDecimal value) {
            addCriterion("max_avg5_volume >", value, "maxAvg5Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_avg5_volume >=", value, "maxAvg5Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeLessThan(BigDecimal value) {
            addCriterion("max_avg5_volume <", value, "maxAvg5Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_avg5_volume <=", value, "maxAvg5Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeIn(List<BigDecimal> values) {
            addCriterion("max_avg5_volume in", values, "maxAvg5Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeNotIn(List<BigDecimal> values) {
            addCriterion("max_avg5_volume not in", values, "maxAvg5Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_avg5_volume between", value1, value2, "maxAvg5Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_avg5_volume not between", value1, value2, "maxAvg5Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeTimeIsNull() {
            addCriterion("max_avg5_volume_time is null");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeTimeIsNotNull() {
            addCriterion("max_avg5_volume_time is not null");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeTimeEqualTo(LocalDateTime value) {
            addCriterion("max_avg5_volume_time =", value, "maxAvg5VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeTimeNotEqualTo(LocalDateTime value) {
            addCriterion("max_avg5_volume_time <>", value, "maxAvg5VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeTimeGreaterThan(LocalDateTime value) {
            addCriterion("max_avg5_volume_time >", value, "maxAvg5VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("max_avg5_volume_time >=", value, "maxAvg5VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeTimeLessThan(LocalDateTime value) {
            addCriterion("max_avg5_volume_time <", value, "maxAvg5VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("max_avg5_volume_time <=", value, "maxAvg5VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeTimeIn(List<LocalDateTime> values) {
            addCriterion("max_avg5_volume_time in", values, "maxAvg5VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeTimeNotIn(List<LocalDateTime> values) {
            addCriterion("max_avg5_volume_time not in", values, "maxAvg5VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("max_avg5_volume_time between", value1, value2, "maxAvg5VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg5VolumeTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("max_avg5_volume_time not between", value1, value2, "maxAvg5VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeIsNull() {
            addCriterion("max_avg10_volume is null");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeIsNotNull() {
            addCriterion("max_avg10_volume is not null");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeEqualTo(BigDecimal value) {
            addCriterion("max_avg10_volume =", value, "maxAvg10Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeNotEqualTo(BigDecimal value) {
            addCriterion("max_avg10_volume <>", value, "maxAvg10Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeGreaterThan(BigDecimal value) {
            addCriterion("max_avg10_volume >", value, "maxAvg10Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("max_avg10_volume >=", value, "maxAvg10Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeLessThan(BigDecimal value) {
            addCriterion("max_avg10_volume <", value, "maxAvg10Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeLessThanOrEqualTo(BigDecimal value) {
            addCriterion("max_avg10_volume <=", value, "maxAvg10Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeIn(List<BigDecimal> values) {
            addCriterion("max_avg10_volume in", values, "maxAvg10Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeNotIn(List<BigDecimal> values) {
            addCriterion("max_avg10_volume not in", values, "maxAvg10Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_avg10_volume between", value1, value2, "maxAvg10Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("max_avg10_volume not between", value1, value2, "maxAvg10Volume");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeTimeIsNull() {
            addCriterion("max_avg10_volume_time is null");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeTimeIsNotNull() {
            addCriterion("max_avg10_volume_time is not null");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeTimeEqualTo(LocalDateTime value) {
            addCriterion("max_avg10_volume_time =", value, "maxAvg10VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeTimeNotEqualTo(LocalDateTime value) {
            addCriterion("max_avg10_volume_time <>", value, "maxAvg10VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeTimeGreaterThan(LocalDateTime value) {
            addCriterion("max_avg10_volume_time >", value, "maxAvg10VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("max_avg10_volume_time >=", value, "maxAvg10VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeTimeLessThan(LocalDateTime value) {
            addCriterion("max_avg10_volume_time <", value, "maxAvg10VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("max_avg10_volume_time <=", value, "maxAvg10VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeTimeIn(List<LocalDateTime> values) {
            addCriterion("max_avg10_volume_time in", values, "maxAvg10VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeTimeNotIn(List<LocalDateTime> values) {
            addCriterion("max_avg10_volume_time not in", values, "maxAvg10VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("max_avg10_volume_time between", value1, value2, "maxAvg10VolumeTime");
            return (Criteria) this;
        }

        public Criteria andMaxAvg10VolumeTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("max_avg10_volume_time not between", value1, value2, "maxAvg10VolumeTime");
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