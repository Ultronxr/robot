package cn.ultronxr.qqrobot.bean.mybatis.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QQGroup {
    private String groupId;

    private Integer isStatistic;

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public Integer getIsStatistic() {
        return isStatistic;
    }

    public void setIsStatistic(Integer isStatistic) {
        this.isStatistic = isStatistic;
    }
}