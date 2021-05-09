package cn.ultronxr.qqrobot.bean.mybatis.bean;

import java.util.ArrayList;
import java.util.List;

public class QQGroupMemberChatExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QQGroupMemberChatExample() {
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

        public Criteria andGroupIdIsNull() {
            addCriterion("group_id is null");
            return (Criteria) this;
        }

        public Criteria andGroupIdIsNotNull() {
            addCriterion("group_id is not null");
            return (Criteria) this;
        }

        public Criteria andGroupIdEqualTo(String value) {
            addCriterion("group_id =", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotEqualTo(String value) {
            addCriterion("group_id <>", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThan(String value) {
            addCriterion("group_id >", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdGreaterThanOrEqualTo(String value) {
            addCriterion("group_id >=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThan(String value) {
            addCriterion("group_id <", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLessThanOrEqualTo(String value) {
            addCriterion("group_id <=", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdLike(String value) {
            addCriterion("group_id like", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotLike(String value) {
            addCriterion("group_id not like", value, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdIn(List<String> values) {
            addCriterion("group_id in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotIn(List<String> values) {
            addCriterion("group_id not in", values, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdBetween(String value1, String value2) {
            addCriterion("group_id between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andGroupIdNotBetween(String value1, String value2) {
            addCriterion("group_id not between", value1, value2, "groupId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNull() {
            addCriterion("member_id is null");
            return (Criteria) this;
        }

        public Criteria andMemberIdIsNotNull() {
            addCriterion("member_id is not null");
            return (Criteria) this;
        }

        public Criteria andMemberIdEqualTo(String value) {
            addCriterion("member_id =", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotEqualTo(String value) {
            addCriterion("member_id <>", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThan(String value) {
            addCriterion("member_id >", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdGreaterThanOrEqualTo(String value) {
            addCriterion("member_id >=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThan(String value) {
            addCriterion("member_id <", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLessThanOrEqualTo(String value) {
            addCriterion("member_id <=", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdLike(String value) {
            addCriterion("member_id like", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotLike(String value) {
            addCriterion("member_id not like", value, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdIn(List<String> values) {
            addCriterion("member_id in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotIn(List<String> values) {
            addCriterion("member_id not in", values, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdBetween(String value1, String value2) {
            addCriterion("member_id between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andMemberIdNotBetween(String value1, String value2) {
            addCriterion("member_id not between", value1, value2, "memberId");
            return (Criteria) this;
        }

        public Criteria andChatTimeIsNull() {
            addCriterion("chat_time is null");
            return (Criteria) this;
        }

        public Criteria andChatTimeIsNotNull() {
            addCriterion("chat_time is not null");
            return (Criteria) this;
        }

        public Criteria andChatTimeEqualTo(String value) {
            addCriterion("chat_time =", value, "chatTime");
            return (Criteria) this;
        }

        public Criteria andChatTimeNotEqualTo(String value) {
            addCriterion("chat_time <>", value, "chatTime");
            return (Criteria) this;
        }

        public Criteria andChatTimeGreaterThan(String value) {
            addCriterion("chat_time >", value, "chatTime");
            return (Criteria) this;
        }

        public Criteria andChatTimeGreaterThanOrEqualTo(String value) {
            addCriterion("chat_time >=", value, "chatTime");
            return (Criteria) this;
        }

        public Criteria andChatTimeLessThan(String value) {
            addCriterion("chat_time <", value, "chatTime");
            return (Criteria) this;
        }

        public Criteria andChatTimeLessThanOrEqualTo(String value) {
            addCriterion("chat_time <=", value, "chatTime");
            return (Criteria) this;
        }

        public Criteria andChatTimeLike(String value) {
            addCriterion("chat_time like", value, "chatTime");
            return (Criteria) this;
        }

        public Criteria andChatTimeNotLike(String value) {
            addCriterion("chat_time not like", value, "chatTime");
            return (Criteria) this;
        }

        public Criteria andChatTimeIn(List<String> values) {
            addCriterion("chat_time in", values, "chatTime");
            return (Criteria) this;
        }

        public Criteria andChatTimeNotIn(List<String> values) {
            addCriterion("chat_time not in", values, "chatTime");
            return (Criteria) this;
        }

        public Criteria andChatTimeBetween(String value1, String value2) {
            addCriterion("chat_time between", value1, value2, "chatTime");
            return (Criteria) this;
        }

        public Criteria andChatTimeNotBetween(String value1, String value2) {
            addCriterion("chat_time not between", value1, value2, "chatTime");
            return (Criteria) this;
        }

        public Criteria andChatNumIsNull() {
            addCriterion("chat_num is null");
            return (Criteria) this;
        }

        public Criteria andChatNumIsNotNull() {
            addCriterion("chat_num is not null");
            return (Criteria) this;
        }

        public Criteria andChatNumEqualTo(Integer value) {
            addCriterion("chat_num =", value, "chatNum");
            return (Criteria) this;
        }

        public Criteria andChatNumNotEqualTo(Integer value) {
            addCriterion("chat_num <>", value, "chatNum");
            return (Criteria) this;
        }

        public Criteria andChatNumGreaterThan(Integer value) {
            addCriterion("chat_num >", value, "chatNum");
            return (Criteria) this;
        }

        public Criteria andChatNumGreaterThanOrEqualTo(Integer value) {
            addCriterion("chat_num >=", value, "chatNum");
            return (Criteria) this;
        }

        public Criteria andChatNumLessThan(Integer value) {
            addCriterion("chat_num <", value, "chatNum");
            return (Criteria) this;
        }

        public Criteria andChatNumLessThanOrEqualTo(Integer value) {
            addCriterion("chat_num <=", value, "chatNum");
            return (Criteria) this;
        }

        public Criteria andChatNumIn(List<Integer> values) {
            addCriterion("chat_num in", values, "chatNum");
            return (Criteria) this;
        }

        public Criteria andChatNumNotIn(List<Integer> values) {
            addCriterion("chat_num not in", values, "chatNum");
            return (Criteria) this;
        }

        public Criteria andChatNumBetween(Integer value1, Integer value2) {
            addCriterion("chat_num between", value1, value2, "chatNum");
            return (Criteria) this;
        }

        public Criteria andChatNumNotBetween(Integer value1, Integer value2) {
            addCriterion("chat_num not between", value1, value2, "chatNum");
            return (Criteria) this;
        }

        public Criteria andIsStatisticIsNull() {
            addCriterion("is_statistic is null");
            return (Criteria) this;
        }

        public Criteria andIsStatisticIsNotNull() {
            addCriterion("is_statistic is not null");
            return (Criteria) this;
        }

        public Criteria andIsStatisticEqualTo(Integer value) {
            addCriterion("is_statistic =", value, "isStatistic");
            return (Criteria) this;
        }

        public Criteria andIsStatisticNotEqualTo(Integer value) {
            addCriterion("is_statistic <>", value, "isStatistic");
            return (Criteria) this;
        }

        public Criteria andIsStatisticGreaterThan(Integer value) {
            addCriterion("is_statistic >", value, "isStatistic");
            return (Criteria) this;
        }

        public Criteria andIsStatisticGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_statistic >=", value, "isStatistic");
            return (Criteria) this;
        }

        public Criteria andIsStatisticLessThan(Integer value) {
            addCriterion("is_statistic <", value, "isStatistic");
            return (Criteria) this;
        }

        public Criteria andIsStatisticLessThanOrEqualTo(Integer value) {
            addCriterion("is_statistic <=", value, "isStatistic");
            return (Criteria) this;
        }

        public Criteria andIsStatisticIn(List<Integer> values) {
            addCriterion("is_statistic in", values, "isStatistic");
            return (Criteria) this;
        }

        public Criteria andIsStatisticNotIn(List<Integer> values) {
            addCriterion("is_statistic not in", values, "isStatistic");
            return (Criteria) this;
        }

        public Criteria andIsStatisticBetween(Integer value1, Integer value2) {
            addCriterion("is_statistic between", value1, value2, "isStatistic");
            return (Criteria) this;
        }

        public Criteria andIsStatisticNotBetween(Integer value1, Integer value2) {
            addCriterion("is_statistic not between", value1, value2, "isStatistic");
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