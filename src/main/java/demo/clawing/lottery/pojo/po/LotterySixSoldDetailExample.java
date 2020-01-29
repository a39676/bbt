package demo.clawing.lottery.pojo.po;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class LotterySixSoldDetailExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public LotterySixSoldDetailExample() {
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

        public Criteria andRecordOrgIdIsNull() {
            addCriterion("record_org_id is null");
            return (Criteria) this;
        }

        public Criteria andRecordOrgIdIsNotNull() {
            addCriterion("record_org_id is not null");
            return (Criteria) this;
        }

        public Criteria andRecordOrgIdEqualTo(String value) {
            addCriterion("record_org_id =", value, "recordOrgId");
            return (Criteria) this;
        }

        public Criteria andRecordOrgIdNotEqualTo(String value) {
            addCriterion("record_org_id <>", value, "recordOrgId");
            return (Criteria) this;
        }

        public Criteria andRecordOrgIdGreaterThan(String value) {
            addCriterion("record_org_id >", value, "recordOrgId");
            return (Criteria) this;
        }

        public Criteria andRecordOrgIdGreaterThanOrEqualTo(String value) {
            addCriterion("record_org_id >=", value, "recordOrgId");
            return (Criteria) this;
        }

        public Criteria andRecordOrgIdLessThan(String value) {
            addCriterion("record_org_id <", value, "recordOrgId");
            return (Criteria) this;
        }

        public Criteria andRecordOrgIdLessThanOrEqualTo(String value) {
            addCriterion("record_org_id <=", value, "recordOrgId");
            return (Criteria) this;
        }

        public Criteria andRecordOrgIdLike(String value) {
            addCriterion("record_org_id like", value, "recordOrgId");
            return (Criteria) this;
        }

        public Criteria andRecordOrgIdNotLike(String value) {
            addCriterion("record_org_id not like", value, "recordOrgId");
            return (Criteria) this;
        }

        public Criteria andRecordOrgIdIn(List<String> values) {
            addCriterion("record_org_id in", values, "recordOrgId");
            return (Criteria) this;
        }

        public Criteria andRecordOrgIdNotIn(List<String> values) {
            addCriterion("record_org_id not in", values, "recordOrgId");
            return (Criteria) this;
        }

        public Criteria andRecordOrgIdBetween(String value1, String value2) {
            addCriterion("record_org_id between", value1, value2, "recordOrgId");
            return (Criteria) this;
        }

        public Criteria andRecordOrgIdNotBetween(String value1, String value2) {
            addCriterion("record_org_id not between", value1, value2, "recordOrgId");
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

        public Criteria andPrizePoolAmountIsNull() {
            addCriterion("prize_pool_amount is null");
            return (Criteria) this;
        }

        public Criteria andPrizePoolAmountIsNotNull() {
            addCriterion("prize_pool_amount is not null");
            return (Criteria) this;
        }

        public Criteria andPrizePoolAmountEqualTo(BigDecimal value) {
            addCriterion("prize_pool_amount =", value, "prizePoolAmount");
            return (Criteria) this;
        }

        public Criteria andPrizePoolAmountNotEqualTo(BigDecimal value) {
            addCriterion("prize_pool_amount <>", value, "prizePoolAmount");
            return (Criteria) this;
        }

        public Criteria andPrizePoolAmountGreaterThan(BigDecimal value) {
            addCriterion("prize_pool_amount >", value, "prizePoolAmount");
            return (Criteria) this;
        }

        public Criteria andPrizePoolAmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_pool_amount >=", value, "prizePoolAmount");
            return (Criteria) this;
        }

        public Criteria andPrizePoolAmountLessThan(BigDecimal value) {
            addCriterion("prize_pool_amount <", value, "prizePoolAmount");
            return (Criteria) this;
        }

        public Criteria andPrizePoolAmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_pool_amount <=", value, "prizePoolAmount");
            return (Criteria) this;
        }

        public Criteria andPrizePoolAmountIn(List<BigDecimal> values) {
            addCriterion("prize_pool_amount in", values, "prizePoolAmount");
            return (Criteria) this;
        }

        public Criteria andPrizePoolAmountNotIn(List<BigDecimal> values) {
            addCriterion("prize_pool_amount not in", values, "prizePoolAmount");
            return (Criteria) this;
        }

        public Criteria andPrizePoolAmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_pool_amount between", value1, value2, "prizePoolAmount");
            return (Criteria) this;
        }

        public Criteria andPrizePoolAmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_pool_amount not between", value1, value2, "prizePoolAmount");
            return (Criteria) this;
        }

        public Criteria andPrize1AmountIsNull() {
            addCriterion("prize_1_amount is null");
            return (Criteria) this;
        }

        public Criteria andPrize1AmountIsNotNull() {
            addCriterion("prize_1_amount is not null");
            return (Criteria) this;
        }

        public Criteria andPrize1AmountEqualTo(BigDecimal value) {
            addCriterion("prize_1_amount =", value, "prize1Amount");
            return (Criteria) this;
        }

        public Criteria andPrize1AmountNotEqualTo(BigDecimal value) {
            addCriterion("prize_1_amount <>", value, "prize1Amount");
            return (Criteria) this;
        }

        public Criteria andPrize1AmountGreaterThan(BigDecimal value) {
            addCriterion("prize_1_amount >", value, "prize1Amount");
            return (Criteria) this;
        }

        public Criteria andPrize1AmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_1_amount >=", value, "prize1Amount");
            return (Criteria) this;
        }

        public Criteria andPrize1AmountLessThan(BigDecimal value) {
            addCriterion("prize_1_amount <", value, "prize1Amount");
            return (Criteria) this;
        }

        public Criteria andPrize1AmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_1_amount <=", value, "prize1Amount");
            return (Criteria) this;
        }

        public Criteria andPrize1AmountIn(List<BigDecimal> values) {
            addCriterion("prize_1_amount in", values, "prize1Amount");
            return (Criteria) this;
        }

        public Criteria andPrize1AmountNotIn(List<BigDecimal> values) {
            addCriterion("prize_1_amount not in", values, "prize1Amount");
            return (Criteria) this;
        }

        public Criteria andPrize1AmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_1_amount between", value1, value2, "prize1Amount");
            return (Criteria) this;
        }

        public Criteria andPrize1AmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_1_amount not between", value1, value2, "prize1Amount");
            return (Criteria) this;
        }

        public Criteria andPrize1CountIsNull() {
            addCriterion("prize_1_count is null");
            return (Criteria) this;
        }

        public Criteria andPrize1CountIsNotNull() {
            addCriterion("prize_1_count is not null");
            return (Criteria) this;
        }

        public Criteria andPrize1CountEqualTo(Integer value) {
            addCriterion("prize_1_count =", value, "prize1Count");
            return (Criteria) this;
        }

        public Criteria andPrize1CountNotEqualTo(Integer value) {
            addCriterion("prize_1_count <>", value, "prize1Count");
            return (Criteria) this;
        }

        public Criteria andPrize1CountGreaterThan(Integer value) {
            addCriterion("prize_1_count >", value, "prize1Count");
            return (Criteria) this;
        }

        public Criteria andPrize1CountGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_1_count >=", value, "prize1Count");
            return (Criteria) this;
        }

        public Criteria andPrize1CountLessThan(Integer value) {
            addCriterion("prize_1_count <", value, "prize1Count");
            return (Criteria) this;
        }

        public Criteria andPrize1CountLessThanOrEqualTo(Integer value) {
            addCriterion("prize_1_count <=", value, "prize1Count");
            return (Criteria) this;
        }

        public Criteria andPrize1CountIn(List<Integer> values) {
            addCriterion("prize_1_count in", values, "prize1Count");
            return (Criteria) this;
        }

        public Criteria andPrize1CountNotIn(List<Integer> values) {
            addCriterion("prize_1_count not in", values, "prize1Count");
            return (Criteria) this;
        }

        public Criteria andPrize1CountBetween(Integer value1, Integer value2) {
            addCriterion("prize_1_count between", value1, value2, "prize1Count");
            return (Criteria) this;
        }

        public Criteria andPrize1CountNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_1_count not between", value1, value2, "prize1Count");
            return (Criteria) this;
        }

        public Criteria andPrize2AmountIsNull() {
            addCriterion("prize_2_amount is null");
            return (Criteria) this;
        }

        public Criteria andPrize2AmountIsNotNull() {
            addCriterion("prize_2_amount is not null");
            return (Criteria) this;
        }

        public Criteria andPrize2AmountEqualTo(BigDecimal value) {
            addCriterion("prize_2_amount =", value, "prize2Amount");
            return (Criteria) this;
        }

        public Criteria andPrize2AmountNotEqualTo(BigDecimal value) {
            addCriterion("prize_2_amount <>", value, "prize2Amount");
            return (Criteria) this;
        }

        public Criteria andPrize2AmountGreaterThan(BigDecimal value) {
            addCriterion("prize_2_amount >", value, "prize2Amount");
            return (Criteria) this;
        }

        public Criteria andPrize2AmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_2_amount >=", value, "prize2Amount");
            return (Criteria) this;
        }

        public Criteria andPrize2AmountLessThan(BigDecimal value) {
            addCriterion("prize_2_amount <", value, "prize2Amount");
            return (Criteria) this;
        }

        public Criteria andPrize2AmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_2_amount <=", value, "prize2Amount");
            return (Criteria) this;
        }

        public Criteria andPrize2AmountIn(List<BigDecimal> values) {
            addCriterion("prize_2_amount in", values, "prize2Amount");
            return (Criteria) this;
        }

        public Criteria andPrize2AmountNotIn(List<BigDecimal> values) {
            addCriterion("prize_2_amount not in", values, "prize2Amount");
            return (Criteria) this;
        }

        public Criteria andPrize2AmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_2_amount between", value1, value2, "prize2Amount");
            return (Criteria) this;
        }

        public Criteria andPrize2AmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_2_amount not between", value1, value2, "prize2Amount");
            return (Criteria) this;
        }

        public Criteria andPrize2CountIsNull() {
            addCriterion("prize_2_count is null");
            return (Criteria) this;
        }

        public Criteria andPrize2CountIsNotNull() {
            addCriterion("prize_2_count is not null");
            return (Criteria) this;
        }

        public Criteria andPrize2CountEqualTo(Integer value) {
            addCriterion("prize_2_count =", value, "prize2Count");
            return (Criteria) this;
        }

        public Criteria andPrize2CountNotEqualTo(Integer value) {
            addCriterion("prize_2_count <>", value, "prize2Count");
            return (Criteria) this;
        }

        public Criteria andPrize2CountGreaterThan(Integer value) {
            addCriterion("prize_2_count >", value, "prize2Count");
            return (Criteria) this;
        }

        public Criteria andPrize2CountGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_2_count >=", value, "prize2Count");
            return (Criteria) this;
        }

        public Criteria andPrize2CountLessThan(Integer value) {
            addCriterion("prize_2_count <", value, "prize2Count");
            return (Criteria) this;
        }

        public Criteria andPrize2CountLessThanOrEqualTo(Integer value) {
            addCriterion("prize_2_count <=", value, "prize2Count");
            return (Criteria) this;
        }

        public Criteria andPrize2CountIn(List<Integer> values) {
            addCriterion("prize_2_count in", values, "prize2Count");
            return (Criteria) this;
        }

        public Criteria andPrize2CountNotIn(List<Integer> values) {
            addCriterion("prize_2_count not in", values, "prize2Count");
            return (Criteria) this;
        }

        public Criteria andPrize2CountBetween(Integer value1, Integer value2) {
            addCriterion("prize_2_count between", value1, value2, "prize2Count");
            return (Criteria) this;
        }

        public Criteria andPrize2CountNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_2_count not between", value1, value2, "prize2Count");
            return (Criteria) this;
        }

        public Criteria andPrize3AmountIsNull() {
            addCriterion("prize_3_amount is null");
            return (Criteria) this;
        }

        public Criteria andPrize3AmountIsNotNull() {
            addCriterion("prize_3_amount is not null");
            return (Criteria) this;
        }

        public Criteria andPrize3AmountEqualTo(BigDecimal value) {
            addCriterion("prize_3_amount =", value, "prize3Amount");
            return (Criteria) this;
        }

        public Criteria andPrize3AmountNotEqualTo(BigDecimal value) {
            addCriterion("prize_3_amount <>", value, "prize3Amount");
            return (Criteria) this;
        }

        public Criteria andPrize3AmountGreaterThan(BigDecimal value) {
            addCriterion("prize_3_amount >", value, "prize3Amount");
            return (Criteria) this;
        }

        public Criteria andPrize3AmountGreaterThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_3_amount >=", value, "prize3Amount");
            return (Criteria) this;
        }

        public Criteria andPrize3AmountLessThan(BigDecimal value) {
            addCriterion("prize_3_amount <", value, "prize3Amount");
            return (Criteria) this;
        }

        public Criteria andPrize3AmountLessThanOrEqualTo(BigDecimal value) {
            addCriterion("prize_3_amount <=", value, "prize3Amount");
            return (Criteria) this;
        }

        public Criteria andPrize3AmountIn(List<BigDecimal> values) {
            addCriterion("prize_3_amount in", values, "prize3Amount");
            return (Criteria) this;
        }

        public Criteria andPrize3AmountNotIn(List<BigDecimal> values) {
            addCriterion("prize_3_amount not in", values, "prize3Amount");
            return (Criteria) this;
        }

        public Criteria andPrize3AmountBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_3_amount between", value1, value2, "prize3Amount");
            return (Criteria) this;
        }

        public Criteria andPrize3AmountNotBetween(BigDecimal value1, BigDecimal value2) {
            addCriterion("prize_3_amount not between", value1, value2, "prize3Amount");
            return (Criteria) this;
        }

        public Criteria andPrize3CountIsNull() {
            addCriterion("prize_3_count is null");
            return (Criteria) this;
        }

        public Criteria andPrize3CountIsNotNull() {
            addCriterion("prize_3_count is not null");
            return (Criteria) this;
        }

        public Criteria andPrize3CountEqualTo(Integer value) {
            addCriterion("prize_3_count =", value, "prize3Count");
            return (Criteria) this;
        }

        public Criteria andPrize3CountNotEqualTo(Integer value) {
            addCriterion("prize_3_count <>", value, "prize3Count");
            return (Criteria) this;
        }

        public Criteria andPrize3CountGreaterThan(Integer value) {
            addCriterion("prize_3_count >", value, "prize3Count");
            return (Criteria) this;
        }

        public Criteria andPrize3CountGreaterThanOrEqualTo(Integer value) {
            addCriterion("prize_3_count >=", value, "prize3Count");
            return (Criteria) this;
        }

        public Criteria andPrize3CountLessThan(Integer value) {
            addCriterion("prize_3_count <", value, "prize3Count");
            return (Criteria) this;
        }

        public Criteria andPrize3CountLessThanOrEqualTo(Integer value) {
            addCriterion("prize_3_count <=", value, "prize3Count");
            return (Criteria) this;
        }

        public Criteria andPrize3CountIn(List<Integer> values) {
            addCriterion("prize_3_count in", values, "prize3Count");
            return (Criteria) this;
        }

        public Criteria andPrize3CountNotIn(List<Integer> values) {
            addCriterion("prize_3_count not in", values, "prize3Count");
            return (Criteria) this;
        }

        public Criteria andPrize3CountBetween(Integer value1, Integer value2) {
            addCriterion("prize_3_count between", value1, value2, "prize3Count");
            return (Criteria) this;
        }

        public Criteria andPrize3CountNotBetween(Integer value1, Integer value2) {
            addCriterion("prize_3_count not between", value1, value2, "prize3Count");
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