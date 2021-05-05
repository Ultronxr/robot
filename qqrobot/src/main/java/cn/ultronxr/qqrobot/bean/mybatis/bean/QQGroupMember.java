package cn.ultronxr.qqrobot.bean.mybatis.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QQGroupMember extends QQGroupMemberKey {
    private Integer isStatistic;

    public QQGroupMember(String groupId, String memberId, Integer isStatistic) {
        super.setGroupId(groupId);
        super.setMemberId(memberId);
        setIsStatistic(isStatistic);
    }

    public Integer getIsStatistic() {
        return isStatistic;
    }

    public void setIsStatistic(Integer isStatistic) {
        this.isStatistic = isStatistic;
    }

    @Override
    public String toString(){
        return "QQGroupMember(groupId=" + super.getGroupId()
                + ", memberId=" + super.getMemberId()
                + ", isStatistic=" + getIsStatistic()
                + ")";
    }
}