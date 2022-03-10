package cn.ultronxr.qqrobot.bean.mybatis.bean;

public class QuartzTaskTarget {
    private String taskName;

    private String taskGroup;

    private String groupId;

    private String qqId;

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName == null ? null : taskName.trim();
    }

    public String getTaskGroup() {
        return taskGroup;
    }

    public void setTaskGroup(String taskGroup) {
        this.taskGroup = taskGroup == null ? null : taskGroup.trim();
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getQqId() {
        return qqId;
    }

    public void setQqId(String qqId) {
        this.qqId = qqId == null ? null : qqId.trim();
    }
}