package cn.ultronxr.qqrobot.bean.mybatis.bean;

import java.util.Date;

public class QuartzJob {
    private Integer jobId;

    private String jobName;

    private String jobGroup;

    private String jobDescription;

    private String jobClass;

    private String jobCron;

    private Date pauseTimeLimit;

    private Integer status;

    private Integer isDel;

    private String attachFiles;

    private String smsContact;

    private String emailContact;

    private Date createTime;

    private String createUser;

    private Date updateTime;

    private String updateUser;

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName == null ? null : jobName.trim();
    }

    public String getJobGroup() {
        return jobGroup;
    }

    public void setJobGroup(String jobGroup) {
        this.jobGroup = jobGroup == null ? null : jobGroup.trim();
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription == null ? null : jobDescription.trim();
    }

    public String getJobClass() {
        return jobClass;
    }

    public void setJobClass(String jobClass) {
        this.jobClass = jobClass == null ? null : jobClass.trim();
    }

    public String getJobCron() {
        return jobCron;
    }

    public void setJobCron(String jobCron) {
        this.jobCron = jobCron == null ? null : jobCron.trim();
    }

    public Date getPauseTimeLimit() {
        return pauseTimeLimit;
    }

    public void setPauseTimeLimit(Date pauseTimeLimit) {
        this.pauseTimeLimit = pauseTimeLimit;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getIsDel() {
        return isDel;
    }

    public void setIsDel(Integer isDel) {
        this.isDel = isDel;
    }

    public String getAttachFiles() {
        return attachFiles;
    }

    public void setAttachFiles(String attachFiles) {
        this.attachFiles = attachFiles == null ? null : attachFiles.trim();
    }

    public String getSmsContact() {
        return smsContact;
    }

    public void setSmsContact(String smsContact) {
        this.smsContact = smsContact == null ? null : smsContact.trim();
    }

    public String getEmailContact() {
        return emailContact;
    }

    public void setEmailContact(String emailContact) {
        this.emailContact = emailContact == null ? null : emailContact.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }
}