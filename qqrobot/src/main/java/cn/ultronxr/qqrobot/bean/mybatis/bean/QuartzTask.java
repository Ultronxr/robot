package cn.ultronxr.qqrobot.bean.mybatis.bean;

import java.util.Date;

public class QuartzTask extends QuartzTaskKey {
    private String taskDescription;

    private String taskClass;

    private String taskCron;

    private Integer status;

    private Date pauseTimeLimit;

    private String createUser;

    private Date createTime;

    private String updateUser;

    private Date updateTime;

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription == null ? null : taskDescription.trim();
    }

    public String getTaskClass() {
        return taskClass;
    }

    public void setTaskClass(String taskClass) {
        this.taskClass = taskClass == null ? null : taskClass.trim();
    }

    public String getTaskCron() {
        return taskCron;
    }

    public void setTaskCron(String taskCron) {
        this.taskCron = taskCron == null ? null : taskCron.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getPauseTimeLimit() {
        return pauseTimeLimit;
    }

    public void setPauseTimeLimit(Date pauseTimeLimit) {
        this.pauseTimeLimit = pauseTimeLimit;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser == null ? null : createUser.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(String updateUser) {
        this.updateUser = updateUser == null ? null : updateUser.trim();
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}