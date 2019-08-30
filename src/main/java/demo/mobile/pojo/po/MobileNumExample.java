package demo.mobile.pojo.po;

import java.util.ArrayList;
import java.util.List;

public class MobileNumExample {
    protected String orderByClause;
	protected boolean distinct;
	protected List<Criteria> oredCriteria;

	public MobileNumExample() {
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

		public Criteria andPrefixIsNull() {
			addCriterion("prefix is null");
			return (Criteria) this;
		}

		public Criteria andPrefixIsNotNull() {
			addCriterion("prefix is not null");
			return (Criteria) this;
		}

		public Criteria andPrefixEqualTo(Integer value) {
			addCriterion("prefix =", value, "prefix");
			return (Criteria) this;
		}

		public Criteria andPrefixNotEqualTo(Integer value) {
			addCriterion("prefix <>", value, "prefix");
			return (Criteria) this;
		}

		public Criteria andPrefixGreaterThan(Integer value) {
			addCriterion("prefix >", value, "prefix");
			return (Criteria) this;
		}

		public Criteria andPrefixGreaterThanOrEqualTo(Integer value) {
			addCriterion("prefix >=", value, "prefix");
			return (Criteria) this;
		}

		public Criteria andPrefixLessThan(Integer value) {
			addCriterion("prefix <", value, "prefix");
			return (Criteria) this;
		}

		public Criteria andPrefixLessThanOrEqualTo(Integer value) {
			addCriterion("prefix <=", value, "prefix");
			return (Criteria) this;
		}

		public Criteria andPrefixIn(List<Integer> values) {
			addCriterion("prefix in", values, "prefix");
			return (Criteria) this;
		}

		public Criteria andPrefixNotIn(List<Integer> values) {
			addCriterion("prefix not in", values, "prefix");
			return (Criteria) this;
		}

		public Criteria andPrefixBetween(Integer value1, Integer value2) {
			addCriterion("prefix between", value1, value2, "prefix");
			return (Criteria) this;
		}

		public Criteria andPrefixNotBetween(Integer value1, Integer value2) {
			addCriterion("prefix not between", value1, value2, "prefix");
			return (Criteria) this;
		}

		public Criteria andMidNumIsNull() {
			addCriterion("mid_num is null");
			return (Criteria) this;
		}

		public Criteria andMidNumIsNotNull() {
			addCriterion("mid_num is not null");
			return (Criteria) this;
		}

		public Criteria andMidNumEqualTo(Integer value) {
			addCriterion("mid_num =", value, "midNum");
			return (Criteria) this;
		}

		public Criteria andMidNumNotEqualTo(Integer value) {
			addCriterion("mid_num <>", value, "midNum");
			return (Criteria) this;
		}

		public Criteria andMidNumGreaterThan(Integer value) {
			addCriterion("mid_num >", value, "midNum");
			return (Criteria) this;
		}

		public Criteria andMidNumGreaterThanOrEqualTo(Integer value) {
			addCriterion("mid_num >=", value, "midNum");
			return (Criteria) this;
		}

		public Criteria andMidNumLessThan(Integer value) {
			addCriterion("mid_num <", value, "midNum");
			return (Criteria) this;
		}

		public Criteria andMidNumLessThanOrEqualTo(Integer value) {
			addCriterion("mid_num <=", value, "midNum");
			return (Criteria) this;
		}

		public Criteria andMidNumIn(List<Integer> values) {
			addCriterion("mid_num in", values, "midNum");
			return (Criteria) this;
		}

		public Criteria andMidNumNotIn(List<Integer> values) {
			addCriterion("mid_num not in", values, "midNum");
			return (Criteria) this;
		}

		public Criteria andMidNumBetween(Integer value1, Integer value2) {
			addCriterion("mid_num between", value1, value2, "midNum");
			return (Criteria) this;
		}

		public Criteria andMidNumNotBetween(Integer value1, Integer value2) {
			addCriterion("mid_num not between", value1, value2, "midNum");
			return (Criteria) this;
		}

		public Criteria andGeographicalIdIsNull() {
			addCriterion("geographical_id is null");
			return (Criteria) this;
		}

		public Criteria andGeographicalIdIsNotNull() {
			addCriterion("geographical_id is not null");
			return (Criteria) this;
		}

		public Criteria andGeographicalIdEqualTo(Long value) {
			addCriterion("geographical_id =", value, "geographicalId");
			return (Criteria) this;
		}

		public Criteria andGeographicalIdNotEqualTo(Long value) {
			addCriterion("geographical_id <>", value, "geographicalId");
			return (Criteria) this;
		}

		public Criteria andGeographicalIdGreaterThan(Long value) {
			addCriterion("geographical_id >", value, "geographicalId");
			return (Criteria) this;
		}

		public Criteria andGeographicalIdGreaterThanOrEqualTo(Long value) {
			addCriterion("geographical_id >=", value, "geographicalId");
			return (Criteria) this;
		}

		public Criteria andGeographicalIdLessThan(Long value) {
			addCriterion("geographical_id <", value, "geographicalId");
			return (Criteria) this;
		}

		public Criteria andGeographicalIdLessThanOrEqualTo(Long value) {
			addCriterion("geographical_id <=", value, "geographicalId");
			return (Criteria) this;
		}

		public Criteria andGeographicalIdIn(List<Long> values) {
			addCriterion("geographical_id in", values, "geographicalId");
			return (Criteria) this;
		}

		public Criteria andGeographicalIdNotIn(List<Long> values) {
			addCriterion("geographical_id not in", values, "geographicalId");
			return (Criteria) this;
		}

		public Criteria andGeographicalIdBetween(Long value1, Long value2) {
			addCriterion("geographical_id between", value1, value2, "geographicalId");
			return (Criteria) this;
		}

		public Criteria andGeographicalIdNotBetween(Long value1, Long value2) {
			addCriterion("geographical_id not between", value1, value2, "geographicalId");
			return (Criteria) this;
		}

		public Criteria andMobileOperatorIdIsNull() {
			addCriterion("mobile_operator_id is null");
			return (Criteria) this;
		}

		public Criteria andMobileOperatorIdIsNotNull() {
			addCriterion("mobile_operator_id is not null");
			return (Criteria) this;
		}

		public Criteria andMobileOperatorIdEqualTo(Long value) {
			addCriterion("mobile_operator_id =", value, "mobileOperatorId");
			return (Criteria) this;
		}

		public Criteria andMobileOperatorIdNotEqualTo(Long value) {
			addCriterion("mobile_operator_id <>", value, "mobileOperatorId");
			return (Criteria) this;
		}

		public Criteria andMobileOperatorIdGreaterThan(Long value) {
			addCriterion("mobile_operator_id >", value, "mobileOperatorId");
			return (Criteria) this;
		}

		public Criteria andMobileOperatorIdGreaterThanOrEqualTo(Long value) {
			addCriterion("mobile_operator_id >=", value, "mobileOperatorId");
			return (Criteria) this;
		}

		public Criteria andMobileOperatorIdLessThan(Long value) {
			addCriterion("mobile_operator_id <", value, "mobileOperatorId");
			return (Criteria) this;
		}

		public Criteria andMobileOperatorIdLessThanOrEqualTo(Long value) {
			addCriterion("mobile_operator_id <=", value, "mobileOperatorId");
			return (Criteria) this;
		}

		public Criteria andMobileOperatorIdIn(List<Long> values) {
			addCriterion("mobile_operator_id in", values, "mobileOperatorId");
			return (Criteria) this;
		}

		public Criteria andMobileOperatorIdNotIn(List<Long> values) {
			addCriterion("mobile_operator_id not in", values, "mobileOperatorId");
			return (Criteria) this;
		}

		public Criteria andMobileOperatorIdBetween(Long value1, Long value2) {
			addCriterion("mobile_operator_id between", value1, value2, "mobileOperatorId");
			return (Criteria) this;
		}

		public Criteria andMobileOperatorIdNotBetween(Long value1, Long value2) {
			addCriterion("mobile_operator_id not between", value1, value2, "mobileOperatorId");
			return (Criteria) this;
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

	/**
     * This class was generated by MyBatis Generator.
     * This class corresponds to the database table mobile_num
     *
     * @mbg.generated do_not_delete_during_merge
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }
}