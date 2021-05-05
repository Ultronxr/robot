package cn.ultronxr.qqrobot.bean.mybatis.mapper;

import cn.ultronxr.qqrobot.bean.mybatis.bean.*;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface QQGroupMemberChatMapper {
    long countByExample(QQGroupMemberChatExample example);

    int deleteByExample(QQGroupMemberChatExample example);

    int insert(QQGroupMemberChat record);

    int insertSelective(QQGroupMemberChat record);

    List<QQGroupMemberChat> selectByExample(QQGroupMemberChatExample example);

    int updateByExampleSelective(@Param("record") QQGroupMemberChat record, @Param("example") QQGroupMemberChatExample example);

    int updateByExample(@Param("record") QQGroupMemberChat record, @Param("example") QQGroupMemberChatExample example);

    /**
     * 统计 某段时间内 <b>某个群<b/> 的活跃程度
     */
    List<ChatSum> statisticsGroup(String groupId, String startTime, String endTime);

    /**
     * 统计 某段时间内 <b>所有群<b/> 的活跃程度/排名
     */
    List<GroupIdAndChatSum> statisticsAllGroup(String startTime, String endTime);

    /**
     * 统计 某段时间内 某个群的<b>某位群成员<b/> 的活跃程度
     */
    List<ChatSum> statisticsGroupMember(String groupId, String memberId, String startTime, String endTime);

    /**
     * 统计 某段时间内 某个群的<b>所有群成员<b/> 的活跃程度/排名
     */
    List<MemberIdAndChatSum> statisticsGroupAllMember(String groupId, String startTime, String endTime);
}