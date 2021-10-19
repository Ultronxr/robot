package cn.ultronxr.qqrobot.bean.mybatis.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QQGroupMemberChat {
    private String groupId;

    private String memberId;

    private String chatTime;

    private Integer chatNum;

    private Integer isStatistic;


    public QQGroupMemberChat(@NotNull Map<Object, Object> map) {
        this.groupId = String.valueOf(map.get("groupId"));
        this.memberId = String.valueOf(map.get("memberId"));
        this.chatTime = String.valueOf(map.get("chatTime"));
        this.chatNum = (Integer) map.get("chatNum");
        this.isStatistic = (Integer) map.get("isStatistic");
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId == null ? null : groupId.trim();
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId == null ? null : memberId.trim();
    }

    public String getChatTime() {
        return chatTime;
    }

    public void setChatTime(String chatTime) {
        this.chatTime = chatTime == null ? null : chatTime.trim();
    }

    public Integer getChatNum() {
        return chatNum;
    }

    public void setChatNum(Integer chatNum) {
        this.chatNum = chatNum;
    }

    public Integer getIsStatistic() {
        return isStatistic;
    }

    public void setIsStatistic(Integer isStatistic) {
        this.isStatistic = isStatistic;
    }
}