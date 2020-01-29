package demo.clawing.lottery.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LotterySixExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LotterySixExample() {
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

        public Criteria andRecordTimeIsNull() {
            addCriterion("record_time is null");
            return (Criteria) this;
        }

        public Criteria andRecordTimeIsNotNull() {
            addCriterion("record_time is not null");
            return (Criteria) this;
        }

        public Criteria andRecordTimeEqualTo(LocalDateTime value) {
            addCriterion("record_time =", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeNotEqualTo(LocalDateTime value) {
            addCriterion("record_time <>", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeGreaterThan(LocalDateTime value) {
            addCriterion("record_time >", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("record_time >=", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeLessThan(LocalDateTime value) {
            addCriterion("record_time <", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("record_time <=", value, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeIn(List<LocalDateTime> values) {
            addCriterion("record_time in", values, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeNotIn(List<LocalDateTime> values) {
            addCriterion("record_time not in", values, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("record_time between", value1, value2, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRecordTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("record_time not between", value1, value2, "recordTime");
            return (Criteria) this;
        }

        public Criteria andRed1IsNull() {
            addCriterion("red_1 is null");
            return (Criteria) this;
        }

        public Criteria andRed1IsNotNull() {
            addCriterion("red_1 is not null");
            return (Criteria) this;
        }

        public Criteria andRed1EqualTo(Integer value) {
            addCriterion("red_1 =", value, "red1");
            return (Criteria) this;
        }

        public Criteria andRed1NotEqualTo(Integer value) {
            addCriterion("red_1 <>", value, "red1");
            return (Criteria) this;
        }

        public Criteria andRed1GreaterThan(Integer value) {
            addCriterion("red_1 >", value, "red1");
            return (Criteria) this;
        }

        public Criteria andRed1GreaterThanOrEqualTo(Integer value) {
            addCriterion("red_1 >=", value, "red1");
            return (Criteria) this;
        }

        public Criteria andRed1LessThan(Integer value) {
            addCriterion("red_1 <", value, "red1");
            return (Criteria) this;
        }

        public Criteria andRed1LessThanOrEqualTo(Integer value) {
            addCriterion("red_1 <=", value, "red1");
            return (Criteria) this;
        }

        public Criteria andRed1In(List<Integer> values) {
            addCriterion("red_1 in", values, "red1");
            return (Criteria) this;
        }

        public Criteria andRed1NotIn(List<Integer> values) {
            addCriterion("red_1 not in", values, "red1");
            return (Criteria) this;
        }

        public Criteria andRed1Between(Integer value1, Integer value2) {
            addCriterion("red_1 between", value1, value2, "red1");
            return (Criteria) this;
        }

        public Criteria andRed1NotBetween(Integer value1, Integer value2) {
            addCriterion("red_1 not between", value1, value2, "red1");
            return (Criteria) this;
        }

        public Criteria andRed2IsNull() {
            addCriterion("red_2 is null");
            return (Criteria) this;
        }

        public Criteria andRed2IsNotNull() {
            addCriterion("red_2 is not null");
            return (Criteria) this;
        }

        public Criteria andRed2EqualTo(Integer value) {
            addCriterion("red_2 =", value, "red2");
            return (Criteria) this;
        }

        public Criteria andRed2NotEqualTo(Integer value) {
            addCriterion("red_2 <>", value, "red2");
            return (Criteria) this;
        }

        public Criteria andRed2GreaterThan(Integer value) {
            addCriterion("red_2 >", value, "red2");
            return (Criteria) this;
        }

        public Criteria andRed2GreaterThanOrEqualTo(Integer value) {
            addCriterion("red_2 >=", value, "red2");
            return (Criteria) this;
        }

        public Criteria andRed2LessThan(Integer value) {
            addCriterion("red_2 <", value, "red2");
            return (Criteria) this;
        }

        public Criteria andRed2LessThanOrEqualTo(Integer value) {
            addCriterion("red_2 <=", value, "red2");
            return (Criteria) this;
        }

        public Criteria andRed2In(List<Integer> values) {
            addCriterion("red_2 in", values, "red2");
            return (Criteria) this;
        }

        public Criteria andRed2NotIn(List<Integer> values) {
            addCriterion("red_2 not in", values, "red2");
            return (Criteria) this;
        }

        public Criteria andRed2Between(Integer value1, Integer value2) {
            addCriterion("red_2 between", value1, value2, "red2");
            return (Criteria) this;
        }

        public Criteria andRed2NotBetween(Integer value1, Integer value2) {
            addCriterion("red_2 not between", value1, value2, "red2");
            return (Criteria) this;
        }

        public Criteria andRed3IsNull() {
            addCriterion("red_3 is null");
            return (Criteria) this;
        }

        public Criteria andRed3IsNotNull() {
            addCriterion("red_3 is not null");
            return (Criteria) this;
        }

        public Criteria andRed3EqualTo(Integer value) {
            addCriterion("red_3 =", value, "red3");
            return (Criteria) this;
        }

        public Criteria andRed3NotEqualTo(Integer value) {
            addCriterion("red_3 <>", value, "red3");
            return (Criteria) this;
        }

        public Criteria andRed3GreaterThan(Integer value) {
            addCriterion("red_3 >", value, "red3");
            return (Criteria) this;
        }

        public Criteria andRed3GreaterThanOrEqualTo(Integer value) {
            addCriterion("red_3 >=", value, "red3");
            return (Criteria) this;
        }

        public Criteria andRed3LessThan(Integer value) {
            addCriterion("red_3 <", value, "red3");
            return (Criteria) this;
        }

        public Criteria andRed3LessThanOrEqualTo(Integer value) {
            addCriterion("red_3 <=", value, "red3");
            return (Criteria) this;
        }

        public Criteria andRed3In(List<Integer> values) {
            addCriterion("red_3 in", values, "red3");
            return (Criteria) this;
        }

        public Criteria andRed3NotIn(List<Integer> values) {
            addCriterion("red_3 not in", values, "red3");
            return (Criteria) this;
        }

        public Criteria andRed3Between(Integer value1, Integer value2) {
            addCriterion("red_3 between", value1, value2, "red3");
            return (Criteria) this;
        }

        public Criteria andRed3NotBetween(Integer value1, Integer value2) {
            addCriterion("red_3 not between", value1, value2, "red3");
            return (Criteria) this;
        }

        public Criteria andRed4IsNull() {
            addCriterion("red_4 is null");
            return (Criteria) this;
        }

        public Criteria andRed4IsNotNull() {
            addCriterion("red_4 is not null");
            return (Criteria) this;
        }

        public Criteria andRed4EqualTo(Integer value) {
            addCriterion("red_4 =", value, "red4");
            return (Criteria) this;
        }

        public Criteria andRed4NotEqualTo(Integer value) {
            addCriterion("red_4 <>", value, "red4");
            return (Criteria) this;
        }

        public Criteria andRed4GreaterThan(Integer value) {
            addCriterion("red_4 >", value, "red4");
            return (Criteria) this;
        }

        public Criteria andRed4GreaterThanOrEqualTo(Integer value) {
            addCriterion("red_4 >=", value, "red4");
            return (Criteria) this;
        }

        public Criteria andRed4LessThan(Integer value) {
            addCriterion("red_4 <", value, "red4");
            return (Criteria) this;
        }

        public Criteria andRed4LessThanOrEqualTo(Integer value) {
            addCriterion("red_4 <=", value, "red4");
            return (Criteria) this;
        }

        public Criteria andRed4In(List<Integer> values) {
            addCriterion("red_4 in", values, "red4");
            return (Criteria) this;
        }

        public Criteria andRed4NotIn(List<Integer> values) {
            addCriterion("red_4 not in", values, "red4");
            return (Criteria) this;
        }

        public Criteria andRed4Between(Integer value1, Integer value2) {
            addCriterion("red_4 between", value1, value2, "red4");
            return (Criteria) this;
        }

        public Criteria andRed4NotBetween(Integer value1, Integer value2) {
            addCriterion("red_4 not between", value1, value2, "red4");
            return (Criteria) this;
        }

        public Criteria andRed5IsNull() {
            addCriterion("red_5 is null");
            return (Criteria) this;
        }

        public Criteria andRed5IsNotNull() {
            addCriterion("red_5 is not null");
            return (Criteria) this;
        }

        public Criteria andRed5EqualTo(Integer value) {
            addCriterion("red_5 =", value, "red5");
            return (Criteria) this;
        }

        public Criteria andRed5NotEqualTo(Integer value) {
            addCriterion("red_5 <>", value, "red5");
            return (Criteria) this;
        }

        public Criteria andRed5GreaterThan(Integer value) {
            addCriterion("red_5 >", value, "red5");
            return (Criteria) this;
        }

        public Criteria andRed5GreaterThanOrEqualTo(Integer value) {
            addCriterion("red_5 >=", value, "red5");
            return (Criteria) this;
        }

        public Criteria andRed5LessThan(Integer value) {
            addCriterion("red_5 <", value, "red5");
            return (Criteria) this;
        }

        public Criteria andRed5LessThanOrEqualTo(Integer value) {
            addCriterion("red_5 <=", value, "red5");
            return (Criteria) this;
        }

        public Criteria andRed5In(List<Integer> values) {
            addCriterion("red_5 in", values, "red5");
            return (Criteria) this;
        }

        public Criteria andRed5NotIn(List<Integer> values) {
            addCriterion("red_5 not in", values, "red5");
            return (Criteria) this;
        }

        public Criteria andRed5Between(Integer value1, Integer value2) {
            addCriterion("red_5 between", value1, value2, "red5");
            return (Criteria) this;
        }

        public Criteria andRed5NotBetween(Integer value1, Integer value2) {
            addCriterion("red_5 not between", value1, value2, "red5");
            return (Criteria) this;
        }

        public Criteria andRed6IsNull() {
            addCriterion("red_6 is null");
            return (Criteria) this;
        }

        public Criteria andRed6IsNotNull() {
            addCriterion("red_6 is not null");
            return (Criteria) this;
        }

        public Criteria andRed6EqualTo(Integer value) {
            addCriterion("red_6 =", value, "red6");
            return (Criteria) this;
        }

        public Criteria andRed6NotEqualTo(Integer value) {
            addCriterion("red_6 <>", value, "red6");
            return (Criteria) this;
        }

        public Criteria andRed6GreaterThan(Integer value) {
            addCriterion("red_6 >", value, "red6");
            return (Criteria) this;
        }

        public Criteria andRed6GreaterThanOrEqualTo(Integer value) {
            addCriterion("red_6 >=", value, "red6");
            return (Criteria) this;
        }

        public Criteria andRed6LessThan(Integer value) {
            addCriterion("red_6 <", value, "red6");
            return (Criteria) this;
        }

        public Criteria andRed6LessThanOrEqualTo(Integer value) {
            addCriterion("red_6 <=", value, "red6");
            return (Criteria) this;
        }

        public Criteria andRed6In(List<Integer> values) {
            addCriterion("red_6 in", values, "red6");
            return (Criteria) this;
        }

        public Criteria andRed6NotIn(List<Integer> values) {
            addCriterion("red_6 not in", values, "red6");
            return (Criteria) this;
        }

        public Criteria andRed6Between(Integer value1, Integer value2) {
            addCriterion("red_6 between", value1, value2, "red6");
            return (Criteria) this;
        }

        public Criteria andRed6NotBetween(Integer value1, Integer value2) {
            addCriterion("red_6 not between", value1, value2, "red6");
            return (Criteria) this;
        }

        public Criteria andBlue1IsNull() {
            addCriterion("blue_1 is null");
            return (Criteria) this;
        }

        public Criteria andBlue1IsNotNull() {
            addCriterion("blue_1 is not null");
            return (Criteria) this;
        }

        public Criteria andBlue1EqualTo(Integer value) {
            addCriterion("blue_1 =", value, "blue1");
            return (Criteria) this;
        }

        public Criteria andBlue1NotEqualTo(Integer value) {
            addCriterion("blue_1 <>", value, "blue1");
            return (Criteria) this;
        }

        public Criteria andBlue1GreaterThan(Integer value) {
            addCriterion("blue_1 >", value, "blue1");
            return (Criteria) this;
        }

        public Criteria andBlue1GreaterThanOrEqualTo(Integer value) {
            addCriterion("blue_1 >=", value, "blue1");
            return (Criteria) this;
        }

        public Criteria andBlue1LessThan(Integer value) {
            addCriterion("blue_1 <", value, "blue1");
            return (Criteria) this;
        }

        public Criteria andBlue1LessThanOrEqualTo(Integer value) {
            addCriterion("blue_1 <=", value, "blue1");
            return (Criteria) this;
        }

        public Criteria andBlue1In(List<Integer> values) {
            addCriterion("blue_1 in", values, "blue1");
            return (Criteria) this;
        }

        public Criteria andBlue1NotIn(List<Integer> values) {
            addCriterion("blue_1 not in", values, "blue1");
            return (Criteria) this;
        }

        public Criteria andBlue1Between(Integer value1, Integer value2) {
            addCriterion("blue_1 between", value1, value2, "blue1");
            return (Criteria) this;
        }

        public Criteria andBlue1NotBetween(Integer value1, Integer value2) {
            addCriterion("blue_1 not between", value1, value2, "blue1");
            return (Criteria) this;
        }

        public Criteria andSoldAmountIsNull() {
            addCriterion("sold_amount is null");
            return (Criteria) this;
        }

        public Criteria andSoldAmountIsNotNull() {
            addCriterion("sold_amount is not null");
            return (Criteria) this;
        }

        public Criteria andSoldAmountEqualTo(BigDecimal value) {
            addCriterion("sold_amount =", value, "soldAmount");
            return (Criteria) this;
        }

        public Criteria andSoldAmountNotEqualTo(BigDecimal value) {
            addCriterion("sold_amount <>", value, "soldAmount");
            return (Criteria) this;
        }

        public Criteria andSoldAmountGreaterThan(BigDecimal value) {
            addCriterion("sold_amount >", value, "soldAmount");
            return (Criteria) this;
        }

        public Criteria andSoldAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("sold_amount >=", value, "soldAmount");
            return (Criteria) this;
        }

        public Criteria andSoldAmountLessThan(BigDecimal value) {
            addCriterion("sold_amount <", value, "soldAmount");
            return (Criteria) this;
        }

        public Criteria andSoldAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("sold_amount <=", value, "soldAmount");
            return (Criteria) this;
        }

        public Criteria andSoldAmountIn(List<BigDecimal> values) {
            addCriterion("sold_amount in", values, "soldAmount");
            return (Criteria) this;
        }

        public Criteria andSoldAmountNotIn(List<BigDecimal> values) {
            addCriterion("sold_amount not in", values, "soldAmount");
            return (Criteria) this;
        }

        public Criteria andSoldAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sold_amount between", value1, value2, "soldAmount");
            return (Criteria) this;
        }

        public Criteria andSoldAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("sold_amount not between", value1, value2, "soldAmount");
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