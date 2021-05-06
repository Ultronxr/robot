package cn.ultronxr.qqrobot.service;

import cn.ultronxr.qqrobot.bean.mybatis.bean.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @author Ultronxr
 * @date 2021/05/05 10:52
 *
 * 群/群成员（发言）活跃统计Service
 */
public interface GroupChatStatisticsService {

    /**
     * 初始化内存中的QQ群及群成员
     * 从数据库中读取
     */
    void initGroupsAndMembers();

    /**
     * 维护QQ群及群成员
     * 线程安全，短时间内QQ群和群成员数量增加不会很大，直接操作数据库
     *
     * @param groupId  QQ群号
     * @param memberId 群成员QQ号
     * @return QQ群成员 {@code QQGroupMember} 对象
     * @see cn.ultronxr.qqrobot.service.serviceImpl.GroupChatStatisticsServiceImpl#GROUPS_MEMBERS
     */
    QQGroupMember maintainGroupsAndMembers(@NotNull String groupId, @NotNull String memberId);

    /**
     * 维护这一个整点小时内的QQ群成员发言记录
     * 线程安全，短时间内群成员发言数量可能很大，优先在redis中维护，避免频繁操作数据库
     *
     * @param qqGroupMember 群成员对象
     * @param chatNum       发言次数
     */
    void maintainGroupMemberChats(@NotNull QQGroupMember qqGroupMember, int chatNum);

    /**
     * 定时把redis中的群成员发言记录 插入到数据库中
     * 线程安全，每小时整点定时运行（也可以手动调用），发言记录插入数据库，Redis中的记录清空
     */
    void insertRedisToDb();

    /**
     * 查询所有QQ群
     *
     * @return QQ群List
     */
    List<QQGroup> retrieveAllGroups();

    /**
     * 查询QQ群所有成员
     *
     * @param groupId QQ群号
     *                若该参数非空，返回指定群号的所有群成员
     *                若该参数为空，返回所有群的所有群成员
     * @return 群成员List
     */
    List<QQGroupMember> retrieveAllGroupMembers(String groupId);

    /**
     * 统计 某段时间内 <b>某个群<b/> 的活跃程度
     *
     * @param groupId   QQ群号
     * @param startTime 统计开始时间
     * @param endTime   统计结束时间
     */
    List<ChatSum> statisticsGroup(String groupId, String startTime, String endTime);

    /**
     * 统计 某段时间内 <b>所有群<b/> 的活跃程度/排名
     *
     * @param startTime 统计开始时间
     * @param endTime   统计结束时间
     */
    List<GroupIdAndChatSum> statisticsAllGroup(String startTime, String endTime);

    /**
     * 统计 某段时间内 某个群的<b>某位群成员<b/> 的活跃程度
     *
     * @param groupId   QQ群号
     * @param memberId  群成员QQ号
     * @param startTime 统计开始时间
     * @param endTime   统计结束时间
     */
    List<ChatSum> statisticsGroupMember(String groupId, String memberId, String startTime, String endTime);

    /**
     * 统计 某段时间内 某个群的<b>所有群成员<b/> 的活跃程度/排名
     *
     * @param groupId   QQ群号
     * @param startTime 统计开始时间
     * @param endTime   统计结束时间
     */
    List<MemberIdAndChatSum> statisticsGroupAllMember(String groupId, String startTime, String endTime);

}
