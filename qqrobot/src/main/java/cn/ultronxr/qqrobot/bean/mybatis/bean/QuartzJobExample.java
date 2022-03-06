package cn.ultronxr.qqrobot.bean.mybatis.bean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class QuartzJobExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public QuartzJobExample() {
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

        public Criteria andJobIdIsNull() {
            addCriterion("job_id is null");
            return (Criteria) this;
        }

        public Criteria andJobIdIsNotNull() {
            addCriterion("job_id is not null");
            return (Criteria) this;
        }

        public Criteria andJobIdEqualTo(Integer value) {
            addCriterion("job_id =", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdNotEqualTo(Integer value) {
            addCriterion("job_id <>", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdGreaterThan(Integer value) {
            addCriterion("job_id >", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("job_id >=", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdLessThan(Integer value) {
            addCriterion("job_id <", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdLessThanOrEqualTo(Integer value) {
            addCriterion("job_id <=", value, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdIn(List<Integer> values) {
            addCriterion("job_id in", values, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdNotIn(List<Integer> values) {
            addCriterion("job_id not in", values, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdBetween(Integer value1, Integer value2) {
            addCriterion("job_id between", value1, value2, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobIdNotBetween(Integer value1, Integer value2) {
            addCriterion("job_id not between", value1, value2, "jobId");
            return (Criteria) this;
        }

        public Criteria andJobNameIsNull() {
            addCriterion("job_name is null");
            return (Criteria) this;
        }

        public Criteria andJobNameIsNotNull() {
            addCriterion("job_name is not null");
            return (Criteria) this;
        }

        public Criteria andJobNameEqualTo(String value) {
            addCriterion("job_name =", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameNotEqualTo(String value) {
            addCriterion("job_name <>", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameGreaterThan(String value) {
            addCriterion("job_name >", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameGreaterThanOrEqualTo(String value) {
            addCriterion("job_name >=", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameLessThan(String value) {
            addCriterion("job_name <", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameLessThanOrEqualTo(String value) {
            addCriterion("job_name <=", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameLike(String value) {
            addCriterion("job_name like", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameNotLike(String value) {
            addCriterion("job_name not like", value, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameIn(List<String> values) {
            addCriterion("job_name in", values, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameNotIn(List<String> values) {
            addCriterion("job_name not in", values, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameBetween(String value1, String value2) {
            addCriterion("job_name between", value1, value2, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobNameNotBetween(String value1, String value2) {
            addCriterion("job_name not between", value1, value2, "jobName");
            return (Criteria) this;
        }

        public Criteria andJobGroupIsNull() {
            addCriterion("job_group is null");
            return (Criteria) this;
        }

        public Criteria andJobGroupIsNotNull() {
            addCriterion("job_group is not null");
            return (Criteria) this;
        }

        public Criteria andJobGroupEqualTo(String value) {
            addCriterion("job_group =", value, "jobGroup");
            return (Criteria) this;
        }

        public Criteria andJobGroupNotEqualTo(String value) {
            addCriterion("job_group <>", value, "jobGroup");
            return (Criteria) this;
        }

        public Criteria andJobGroupGreaterThan(String value) {
            addCriterion("job_group >", value, "jobGroup");
            return (Criteria) this;
        }

        public Criteria andJobGroupGreaterThanOrEqualTo(String value) {
            addCriterion("job_group >=", value, "jobGroup");
            return (Criteria) this;
        }

        public Criteria andJobGroupLessThan(String value) {
            addCriterion("job_group <", value, "jobGroup");
            return (Criteria) this;
        }

        public Criteria andJobGroupLessThanOrEqualTo(String value) {
            addCriterion("job_group <=", value, "jobGroup");
            return (Criteria) this;
        }

        public Criteria andJobGroupLike(String value) {
            addCriterion("job_group like", value, "jobGroup");
            return (Criteria) this;
        }

        public Criteria andJobGroupNotLike(String value) {
            addCriterion("job_group not like", value, "jobGroup");
            return (Criteria) this;
        }

        public Criteria andJobGroupIn(List<String> values) {
            addCriterion("job_group in", values, "jobGroup");
            return (Criteria) this;
        }

        public Criteria andJobGroupNotIn(List<String> values) {
            addCriterion("job_group not in", values, "jobGroup");
            return (Criteria) this;
        }

        public Criteria andJobGroupBetween(String value1, String value2) {
            addCriterion("job_group between", value1, value2, "jobGroup");
            return (Criteria) this;
        }

        public Criteria andJobGroupNotBetween(String value1, String value2) {
            addCriterion("job_group not between", value1, value2, "jobGroup");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionIsNull() {
            addCriterion("job_description is null");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionIsNotNull() {
            addCriterion("job_description is not null");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionEqualTo(String value) {
            addCriterion("job_description =", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionNotEqualTo(String value) {
            addCriterion("job_description <>", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionGreaterThan(String value) {
            addCriterion("job_description >", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionGreaterThanOrEqualTo(String value) {
            addCriterion("job_description >=", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionLessThan(String value) {
            addCriterion("job_description <", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionLessThanOrEqualTo(String value) {
            addCriterion("job_description <=", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionLike(String value) {
            addCriterion("job_description like", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionNotLike(String value) {
            addCriterion("job_description not like", value, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionIn(List<String> values) {
            addCriterion("job_description in", values, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionNotIn(List<String> values) {
            addCriterion("job_description not in", values, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionBetween(String value1, String value2) {
            addCriterion("job_description between", value1, value2, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobDescriptionNotBetween(String value1, String value2) {
            addCriterion("job_description not between", value1, value2, "jobDescription");
            return (Criteria) this;
        }

        public Criteria andJobClassIsNull() {
            addCriterion("job_class is null");
            return (Criteria) this;
        }

        public Criteria andJobClassIsNotNull() {
            addCriterion("job_class is not null");
            return (Criteria) this;
        }

        public Criteria andJobClassEqualTo(String value) {
            addCriterion("job_class =", value, "jobClass");
            return (Criteria) this;
        }

        public Criteria andJobClassNotEqualTo(String value) {
            addCriterion("job_class <>", value, "jobClass");
            return (Criteria) this;
        }

        public Criteria andJobClassGreaterThan(String value) {
            addCriterion("job_class >", value, "jobClass");
            return (Criteria) this;
        }

        public Criteria andJobClassGreaterThanOrEqualTo(String value) {
            addCriterion("job_class >=", value, "jobClass");
            return (Criteria) this;
        }

        public Criteria andJobClassLessThan(String value) {
            addCriterion("job_class <", value, "jobClass");
            return (Criteria) this;
        }

        public Criteria andJobClassLessThanOrEqualTo(String value) {
            addCriterion("job_class <=", value, "jobClass");
            return (Criteria) this;
        }

        public Criteria andJobClassLike(String value) {
            addCriterion("job_class like", value, "jobClass");
            return (Criteria) this;
        }

        public Criteria andJobClassNotLike(String value) {
            addCriterion("job_class not like", value, "jobClass");
            return (Criteria) this;
        }

        public Criteria andJobClassIn(List<String> values) {
            addCriterion("job_class in", values, "jobClass");
            return (Criteria) this;
        }

        public Criteria andJobClassNotIn(List<String> values) {
            addCriterion("job_class not in", values, "jobClass");
            return (Criteria) this;
        }

        public Criteria andJobClassBetween(String value1, String value2) {
            addCriterion("job_class between", value1, value2, "jobClass");
            return (Criteria) this;
        }

        public Criteria andJobClassNotBetween(String value1, String value2) {
            addCriterion("job_class not between", value1, value2, "jobClass");
            return (Criteria) this;
        }

        public Criteria andJobCronIsNull() {
            addCriterion("job_cron is null");
            return (Criteria) this;
        }

        public Criteria andJobCronIsNotNull() {
            addCriterion("job_cron is not null");
            return (Criteria) this;
        }

        public Criteria andJobCronEqualTo(String value) {
            addCriterion("job_cron =", value, "jobCron");
            return (Criteria) this;
        }

        public Criteria andJobCronNotEqualTo(String value) {
            addCriterion("job_cron <>", value, "jobCron");
            return (Criteria) this;
        }

        public Criteria andJobCronGreaterThan(String value) {
            addCriterion("job_cron >", value, "jobCron");
            return (Criteria) this;
        }

        public Criteria andJobCronGreaterThanOrEqualTo(String value) {
            addCriterion("job_cron >=", value, "jobCron");
            return (Criteria) this;
        }

        public Criteria andJobCronLessThan(String value) {
            addCriterion("job_cron <", value, "jobCron");
            return (Criteria) this;
        }

        public Criteria andJobCronLessThanOrEqualTo(String value) {
            addCriterion("job_cron <=", value, "jobCron");
            return (Criteria) this;
        }

        public Criteria andJobCronLike(String value) {
            addCriterion("job_cron like", value, "jobCron");
            return (Criteria) this;
        }

        public Criteria andJobCronNotLike(String value) {
            addCriterion("job_cron not like", value, "jobCron");
            return (Criteria) this;
        }

        public Criteria andJobCronIn(List<String> values) {
            addCriterion("job_cron in", values, "jobCron");
            return (Criteria) this;
        }

        public Criteria andJobCronNotIn(List<String> values) {
            addCriterion("job_cron not in", values, "jobCron");
            return (Criteria) this;
        }

        public Criteria andJobCronBetween(String value1, String value2) {
            addCriterion("job_cron between", value1, value2, "jobCron");
            return (Criteria) this;
        }

        public Criteria andJobCronNotBetween(String value1, String value2) {
            addCriterion("job_cron not between", value1, value2, "jobCron");
            return (Criteria) this;
        }

        public Criteria andPauseTimeLimitIsNull() {
            addCriterion("pause_time_limit is null");
            return (Criteria) this;
        }

        public Criteria andPauseTimeLimitIsNotNull() {
            addCriterion("pause_time_limit is not null");
            return (Criteria) this;
        }

        public Criteria andPauseTimeLimitEqualTo(Date value) {
            addCriterion("pause_time_limit =", value, "pauseTimeLimit");
            return (Criteria) this;
        }

        public Criteria andPauseTimeLimitNotEqualTo(Date value) {
            addCriterion("pause_time_limit <>", value, "pauseTimeLimit");
            return (Criteria) this;
        }

        public Criteria andPauseTimeLimitGreaterThan(Date value) {
            addCriterion("pause_time_limit >", value, "pauseTimeLimit");
            return (Criteria) this;
        }

        public Criteria andPauseTimeLimitGreaterThanOrEqualTo(Date value) {
            addCriterion("pause_time_limit >=", value, "pauseTimeLimit");
            return (Criteria) this;
        }

        public Criteria andPauseTimeLimitLessThan(Date value) {
            addCriterion("pause_time_limit <", value, "pauseTimeLimit");
            return (Criteria) this;
        }

        public Criteria andPauseTimeLimitLessThanOrEqualTo(Date value) {
            addCriterion("pause_time_limit <=", value, "pauseTimeLimit");
            return (Criteria) this;
        }

        public Criteria andPauseTimeLimitIn(List<Date> values) {
            addCriterion("pause_time_limit in", values, "pauseTimeLimit");
            return (Criteria) this;
        }

        public Criteria andPauseTimeLimitNotIn(List<Date> values) {
            addCriterion("pause_time_limit not in", values, "pauseTimeLimit");
            return (Criteria) this;
        }

        public Criteria andPauseTimeLimitBetween(Date value1, Date value2) {
            addCriterion("pause_time_limit between", value1, value2, "pauseTimeLimit");
            return (Criteria) this;
        }

        public Criteria andPauseTimeLimitNotBetween(Date value1, Date value2) {
            addCriterion("pause_time_limit not between", value1, value2, "pauseTimeLimit");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andIsDelIsNull() {
            addCriterion("is_del is null");
            return (Criteria) this;
        }

        public Criteria andIsDelIsNotNull() {
            addCriterion("is_del is not null");
            return (Criteria) this;
        }

        public Criteria andIsDelEqualTo(Integer value) {
            addCriterion("is_del =", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotEqualTo(Integer value) {
            addCriterion("is_del <>", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThan(Integer value) {
            addCriterion("is_del >", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelGreaterThanOrEqualTo(Integer value) {
            addCriterion("is_del >=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThan(Integer value) {
            addCriterion("is_del <", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelLessThanOrEqualTo(Integer value) {
            addCriterion("is_del <=", value, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelIn(List<Integer> values) {
            addCriterion("is_del in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotIn(List<Integer> values) {
            addCriterion("is_del not in", values, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelBetween(Integer value1, Integer value2) {
            addCriterion("is_del between", value1, value2, "isDel");
            return (Criteria) this;
        }

        public Criteria andIsDelNotBetween(Integer value1, Integer value2) {
            addCriterion("is_del not between", value1, value2, "isDel");
            return (Criteria) this;
        }

        public Criteria andAttachFilesIsNull() {
            addCriterion("attach_files is null");
            return (Criteria) this;
        }

        public Criteria andAttachFilesIsNotNull() {
            addCriterion("attach_files is not null");
            return (Criteria) this;
        }

        public Criteria andAttachFilesEqualTo(String value) {
            addCriterion("attach_files =", value, "attachFiles");
            return (Criteria) this;
        }

        public Criteria andAttachFilesNotEqualTo(String value) {
            addCriterion("attach_files <>", value, "attachFiles");
            return (Criteria) this;
        }

        public Criteria andAttachFilesGreaterThan(String value) {
            addCriterion("attach_files >", value, "attachFiles");
            return (Criteria) this;
        }

        public Criteria andAttachFilesGreaterThanOrEqualTo(String value) {
            addCriterion("attach_files >=", value, "attachFiles");
            return (Criteria) this;
        }

        public Criteria andAttachFilesLessThan(String value) {
            addCriterion("attach_files <", value, "attachFiles");
            return (Criteria) this;
        }

        public Criteria andAttachFilesLessThanOrEqualTo(String value) {
            addCriterion("attach_files <=", value, "attachFiles");
            return (Criteria) this;
        }

        public Criteria andAttachFilesLike(String value) {
            addCriterion("attach_files like", value, "attachFiles");
            return (Criteria) this;
        }

        public Criteria andAttachFilesNotLike(String value) {
            addCriterion("attach_files not like", value, "attachFiles");
            return (Criteria) this;
        }

        public Criteria andAttachFilesIn(List<String> values) {
            addCriterion("attach_files in", values, "attachFiles");
            return (Criteria) this;
        }

        public Criteria andAttachFilesNotIn(List<String> values) {
            addCriterion("attach_files not in", values, "attachFiles");
            return (Criteria) this;
        }

        public Criteria andAttachFilesBetween(String value1, String value2) {
            addCriterion("attach_files between", value1, value2, "attachFiles");
            return (Criteria) this;
        }

        public Criteria andAttachFilesNotBetween(String value1, String value2) {
            addCriterion("attach_files not between", value1, value2, "attachFiles");
            return (Criteria) this;
        }

        public Criteria andSmsContactIsNull() {
            addCriterion("sms_contact is null");
            return (Criteria) this;
        }

        public Criteria andSmsContactIsNotNull() {
            addCriterion("sms_contact is not null");
            return (Criteria) this;
        }

        public Criteria andSmsContactEqualTo(String value) {
            addCriterion("sms_contact =", value, "smsContact");
            return (Criteria) this;
        }

        public Criteria andSmsContactNotEqualTo(String value) {
            addCriterion("sms_contact <>", value, "smsContact");
            return (Criteria) this;
        }

        public Criteria andSmsContactGreaterThan(String value) {
            addCriterion("sms_contact >", value, "smsContact");
            return (Criteria) this;
        }

        public Criteria andSmsContactGreaterThanOrEqualTo(String value) {
            addCriterion("sms_contact >=", value, "smsContact");
            return (Criteria) this;
        }

        public Criteria andSmsContactLessThan(String value) {
            addCriterion("sms_contact <", value, "smsContact");
            return (Criteria) this;
        }

        public Criteria andSmsContactLessThanOrEqualTo(String value) {
            addCriterion("sms_contact <=", value, "smsContact");
            return (Criteria) this;
        }

        public Criteria andSmsContactLike(String value) {
            addCriterion("sms_contact like", value, "smsContact");
            return (Criteria) this;
        }

        public Criteria andSmsContactNotLike(String value) {
            addCriterion("sms_contact not like", value, "smsContact");
            return (Criteria) this;
        }

        public Criteria andSmsContactIn(List<String> values) {
            addCriterion("sms_contact in", values, "smsContact");
            return (Criteria) this;
        }

        public Criteria andSmsContactNotIn(List<String> values) {
            addCriterion("sms_contact not in", values, "smsContact");
            return (Criteria) this;
        }

        public Criteria andSmsContactBetween(String value1, String value2) {
            addCriterion("sms_contact between", value1, value2, "smsContact");
            return (Criteria) this;
        }

        public Criteria andSmsContactNotBetween(String value1, String value2) {
            addCriterion("sms_contact not between", value1, value2, "smsContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactIsNull() {
            addCriterion("email_contact is null");
            return (Criteria) this;
        }

        public Criteria andEmailContactIsNotNull() {
            addCriterion("email_contact is not null");
            return (Criteria) this;
        }

        public Criteria andEmailContactEqualTo(String value) {
            addCriterion("email_contact =", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactNotEqualTo(String value) {
            addCriterion("email_contact <>", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactGreaterThan(String value) {
            addCriterion("email_contact >", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactGreaterThanOrEqualTo(String value) {
            addCriterion("email_contact >=", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactLessThan(String value) {
            addCriterion("email_contact <", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactLessThanOrEqualTo(String value) {
            addCriterion("email_contact <=", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactLike(String value) {
            addCriterion("email_contact like", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactNotLike(String value) {
            addCriterion("email_contact not like", value, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactIn(List<String> values) {
            addCriterion("email_contact in", values, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactNotIn(List<String> values) {
            addCriterion("email_contact not in", values, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactBetween(String value1, String value2) {
            addCriterion("email_contact between", value1, value2, "emailContact");
            return (Criteria) this;
        }

        public Criteria andEmailContactNotBetween(String value1, String value2) {
            addCriterion("email_contact not between", value1, value2, "emailContact");
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

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNull() {
            addCriterion("create_user is null");
            return (Criteria) this;
        }

        public Criteria andCreateUserIsNotNull() {
            addCriterion("create_user is not null");
            return (Criteria) this;
        }

        public Criteria andCreateUserEqualTo(String value) {
            addCriterion("create_user =", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotEqualTo(String value) {
            addCriterion("create_user <>", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThan(String value) {
            addCriterion("create_user >", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserGreaterThanOrEqualTo(String value) {
            addCriterion("create_user >=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThan(String value) {
            addCriterion("create_user <", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLessThanOrEqualTo(String value) {
            addCriterion("create_user <=", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserLike(String value) {
            addCriterion("create_user like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotLike(String value) {
            addCriterion("create_user not like", value, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserIn(List<String> values) {
            addCriterion("create_user in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotIn(List<String> values) {
            addCriterion("create_user not in", values, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserBetween(String value1, String value2) {
            addCriterion("create_user between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andCreateUserNotBetween(String value1, String value2) {
            addCriterion("create_user not between", value1, value2, "createUser");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNull() {
            addCriterion("update_user is null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIsNotNull() {
            addCriterion("update_user is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateUserEqualTo(String value) {
            addCriterion("update_user =", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotEqualTo(String value) {
            addCriterion("update_user <>", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThan(String value) {
            addCriterion("update_user >", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserGreaterThanOrEqualTo(String value) {
            addCriterion("update_user >=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThan(String value) {
            addCriterion("update_user <", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLessThanOrEqualTo(String value) {
            addCriterion("update_user <=", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserLike(String value) {
            addCriterion("update_user like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotLike(String value) {
            addCriterion("update_user not like", value, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserIn(List<String> values) {
            addCriterion("update_user in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotIn(List<String> values) {
            addCriterion("update_user not in", values, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserBetween(String value1, String value2) {
            addCriterion("update_user between", value1, value2, "updateUser");
            return (Criteria) this;
        }

        public Criteria andUpdateUserNotBetween(String value1, String value2) {
            addCriterion("update_user not between", value1, value2, "updateUser");
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