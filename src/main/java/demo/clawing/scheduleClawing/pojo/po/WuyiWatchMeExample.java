package demo.clawing.scheduleClawing.pojo.po;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class WuyiWatchMeExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public WuyiWatchMeExample() {
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

        public Criteria andCompanyNameIsNull() {
            addCriterion("company_name is null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIsNotNull() {
            addCriterion("company_name is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyNameEqualTo(String value) {
            addCriterion("company_name =", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotEqualTo(String value) {
            addCriterion("company_name <>", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThan(String value) {
            addCriterion("company_name >", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameGreaterThanOrEqualTo(String value) {
            addCriterion("company_name >=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThan(String value) {
            addCriterion("company_name <", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLessThanOrEqualTo(String value) {
            addCriterion("company_name <=", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameLike(String value) {
            addCriterion("company_name like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotLike(String value) {
            addCriterion("company_name not like", value, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameIn(List<String> values) {
            addCriterion("company_name in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotIn(List<String> values) {
            addCriterion("company_name not in", values, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameBetween(String value1, String value2) {
            addCriterion("company_name between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyNameNotBetween(String value1, String value2) {
            addCriterion("company_name not between", value1, value2, "companyName");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkIsNull() {
            addCriterion("company_link is null");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkIsNotNull() {
            addCriterion("company_link is not null");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkEqualTo(String value) {
            addCriterion("company_link =", value, "companyLink");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkNotEqualTo(String value) {
            addCriterion("company_link <>", value, "companyLink");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkGreaterThan(String value) {
            addCriterion("company_link >", value, "companyLink");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkGreaterThanOrEqualTo(String value) {
            addCriterion("company_link >=", value, "companyLink");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkLessThan(String value) {
            addCriterion("company_link <", value, "companyLink");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkLessThanOrEqualTo(String value) {
            addCriterion("company_link <=", value, "companyLink");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkLike(String value) {
            addCriterion("company_link like", value, "companyLink");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkNotLike(String value) {
            addCriterion("company_link not like", value, "companyLink");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkIn(List<String> values) {
            addCriterion("company_link in", values, "companyLink");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkNotIn(List<String> values) {
            addCriterion("company_link not in", values, "companyLink");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkBetween(String value1, String value2) {
            addCriterion("company_link between", value1, value2, "companyLink");
            return (Criteria) this;
        }

        public Criteria andCompanyLinkNotBetween(String value1, String value2) {
            addCriterion("company_link not between", value1, value2, "companyLink");
            return (Criteria) this;
        }

        public Criteria andWatchTimeIsNull() {
            addCriterion("watch_time is null");
            return (Criteria) this;
        }

        public Criteria andWatchTimeIsNotNull() {
            addCriterion("watch_time is not null");
            return (Criteria) this;
        }

        public Criteria andWatchTimeEqualTo(LocalDateTime value) {
            addCriterion("watch_time =", value, "watchTime");
            return (Criteria) this;
        }

        public Criteria andWatchTimeNotEqualTo(LocalDateTime value) {
            addCriterion("watch_time <>", value, "watchTime");
            return (Criteria) this;
        }

        public Criteria andWatchTimeGreaterThan(LocalDateTime value) {
            addCriterion("watch_time >", value, "watchTime");
            return (Criteria) this;
        }

        public Criteria andWatchTimeGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("watch_time >=", value, "watchTime");
            return (Criteria) this;
        }

        public Criteria andWatchTimeLessThan(LocalDateTime value) {
            addCriterion("watch_time <", value, "watchTime");
            return (Criteria) this;
        }

        public Criteria andWatchTimeLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("watch_time <=", value, "watchTime");
            return (Criteria) this;
        }

        public Criteria andWatchTimeIn(List<LocalDateTime> values) {
            addCriterion("watch_time in", values, "watchTime");
            return (Criteria) this;
        }

        public Criteria andWatchTimeNotIn(List<LocalDateTime> values) {
            addCriterion("watch_time not in", values, "watchTime");
            return (Criteria) this;
        }

        public Criteria andWatchTimeBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("watch_time between", value1, value2, "watchTime");
            return (Criteria) this;
        }

        public Criteria andWatchTimeNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("watch_time not between", value1, value2, "watchTime");
            return (Criteria) this;
        }

        public Criteria andDegreeOfInterestIsNull() {
            addCriterion("degree_of_interest is null");
            return (Criteria) this;
        }

        public Criteria andDegreeOfInterestIsNotNull() {
            addCriterion("degree_of_interest is not null");
            return (Criteria) this;
        }

        public Criteria andDegreeOfInterestEqualTo(Integer value) {
            addCriterion("degree_of_interest =", value, "degreeOfInterest");
            return (Criteria) this;
        }

        public Criteria andDegreeOfInterestNotEqualTo(Integer value) {
            addCriterion("degree_of_interest <>", value, "degreeOfInterest");
            return (Criteria) this;
        }

        public Criteria andDegreeOfInterestGreaterThan(Integer value) {
            addCriterion("degree_of_interest >", value, "degreeOfInterest");
            return (Criteria) this;
        }

        public Criteria andDegreeOfInterestGreaterThanOrEqualTo(Integer value) {
            addCriterion("degree_of_interest >=", value, "degreeOfInterest");
            return (Criteria) this;
        }

        public Criteria andDegreeOfInterestLessThan(Integer value) {
            addCriterion("degree_of_interest <", value, "degreeOfInterest");
            return (Criteria) this;
        }

        public Criteria andDegreeOfInterestLessThanOrEqualTo(Integer value) {
            addCriterion("degree_of_interest <=", value, "degreeOfInterest");
            return (Criteria) this;
        }

        public Criteria andDegreeOfInterestIn(List<Integer> values) {
            addCriterion("degree_of_interest in", values, "degreeOfInterest");
            return (Criteria) this;
        }

        public Criteria andDegreeOfInterestNotIn(List<Integer> values) {
            addCriterion("degree_of_interest not in", values, "degreeOfInterest");
            return (Criteria) this;
        }

        public Criteria andDegreeOfInterestBetween(Integer value1, Integer value2) {
            addCriterion("degree_of_interest between", value1, value2, "degreeOfInterest");
            return (Criteria) this;
        }

        public Criteria andDegreeOfInterestNotBetween(Integer value1, Integer value2) {
            addCriterion("degree_of_interest not between", value1, value2, "degreeOfInterest");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameIsNull() {
            addCriterion("my_resume_name is null");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameIsNotNull() {
            addCriterion("my_resume_name is not null");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameEqualTo(String value) {
            addCriterion("my_resume_name =", value, "myResumeName");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameNotEqualTo(String value) {
            addCriterion("my_resume_name <>", value, "myResumeName");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameGreaterThan(String value) {
            addCriterion("my_resume_name >", value, "myResumeName");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameGreaterThanOrEqualTo(String value) {
            addCriterion("my_resume_name >=", value, "myResumeName");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameLessThan(String value) {
            addCriterion("my_resume_name <", value, "myResumeName");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameLessThanOrEqualTo(String value) {
            addCriterion("my_resume_name <=", value, "myResumeName");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameLike(String value) {
            addCriterion("my_resume_name like", value, "myResumeName");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameNotLike(String value) {
            addCriterion("my_resume_name not like", value, "myResumeName");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameIn(List<String> values) {
            addCriterion("my_resume_name in", values, "myResumeName");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameNotIn(List<String> values) {
            addCriterion("my_resume_name not in", values, "myResumeName");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameBetween(String value1, String value2) {
            addCriterion("my_resume_name between", value1, value2, "myResumeName");
            return (Criteria) this;
        }

        public Criteria andMyResumeNameNotBetween(String value1, String value2) {
            addCriterion("my_resume_name not between", value1, value2, "myResumeName");
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