package cn.ultronxr.qqrobot.service.serviceImpl;

import cn.ultronxr.qqrobot.bean.mybatis.bean.*;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QQGroupMapper;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QQGroupMemberChatMapper;
import cn.ultronxr.qqrobot.bean.mybatis.mapper.QQGroupMemberMapper;
import cn.ultronxr.qqrobot.service.GroupChatStatisticsService;
import cn.ultronxr.qqrobot.util.DateTimeUtils;
import jodd.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Ultronxr
 * @date 2021/05/05 10:53
 */
@Service
@Slf4j
@EnableScheduling
public class GroupChatStatisticsServiceImpl implements GroupChatStatisticsService {

    @Autowired
    private QQGroupMapper qqGroupMapper;

    @Autowired
    private QQGroupMemberMapper qqGroupMemberMapper;

    @Autowired
    private QQGroupMemberChatMapper qqGroupMemberChatMapper;

    /**
     * 机器人加入的群及群成员
     * MapKey：QQ群号
     * MapValue：对应群内的群成员对象List
     */
    public static Map<String, List<QQGroupMember>> GROUPS_MEMBERS;

    /**
     * 这一个整点小时内的群成员的发言记录
     * 这个Map只维护这一个整点小时内的记录，每到整点就把Map内的记录插入数据库并清空Map
     */
    public static Map<QQGroupMember, Integer> CHATS;

    @Override
    public void initGroupsAndMembers(){
        GROUPS_MEMBERS = new HashMap<>(5);
        retrieveAllGroups().forEach(group -> {
            GROUPS_MEMBERS.put(group.getGroupId(), retrieveAllGroupMembers(group.getGroupId()));
            log.info("[function] 初始化QQ群及群成员Map GROUPS_MEMBERS，群号groupId={}", group.getGroupId());
        });
        log.info("[function] 初始化QQ群及群成员Map完成，GROUPS_MEMBERS.size()={}", GROUPS_MEMBERS.size());
    }

    @Override
    public QQGroupMember maintainGroupsAndMembers(@NotNull String groupId, @NotNull String memberId) {
        synchronized (this) {
            if(GROUPS_MEMBERS == null){
                // 初始化内存中的QQ群及群成员
                initGroupsAndMembers();
            }

            if(GROUPS_MEMBERS.containsKey(groupId)){
                Optional<QQGroupMember> existMember = GROUPS_MEMBERS.get(groupId).stream()
                        .filter(member -> member.getMemberId().equals(memberId))
                        .findAny();
                if(existMember.isPresent()){
                    // 如果群成员已经存在，直接return
                    log.info("[function] 维护QQ群及成员GROUPS_MEMBERS，群成员对象已存在：{}", existMember.get());
                    return existMember.get();
                }
            } else {
                // 新的群，内存维护新建，并插入数据库
                GROUPS_MEMBERS.put(groupId, new ArrayList<>());
                QQGroup qqGroup = new QQGroup(groupId, 1);
                qqGroupMapper.insert(qqGroup);
                log.info("[function] 维护QQ群及成员GROUPS_MEMBERS，新增群对象：{}", qqGroup);
            }

            // 新的群成员，内存维护新建，并插入数据库
            QQGroupMember qqGroupMember = new QQGroupMember(groupId, memberId, 1);
            GROUPS_MEMBERS.get(groupId).add(qqGroupMember);
            qqGroupMemberMapper.insert(qqGroupMember);
            log.info("[function] 维护QQ群及成员GROUPS_MEMBERS，新增群成员对象：{}", qqGroupMember.toString());
            return qqGroupMember;
        }
    }

    @Override
    public void maintainGroupMemberChats(@NotNull QQGroupMember qqGroupMember, int chatNum) {
        synchronized (this) {
            if(CHATS == null){
                CHATS = new HashMap<>();
                log.info("[function] 维护QQ群成员发言记录CHATS，初始化CHATS。");
            }
            if(!CHATS.containsKey(qqGroupMember)) {
                CHATS.put(qqGroupMember, chatNum);
                log.info("[function] 维护QQ群成员发言记录CHATS，新增k-v：{} - {}", qqGroupMember.toString(), chatNum);
                return;
            }
            CHATS.put(qqGroupMember, CHATS.get(qqGroupMember) + chatNum);
            log.info("[function] 维护QQ群成员发言记录CHATS，新增k-v：{} - {}",
                    qqGroupMember.toString(), CHATS.get(qqGroupMember));
        }
    }

    @Scheduled(cron = "0 0/30 * * * ? ")
    public void insertChatsToDb(){
        synchronized (this) {
            if(CHATS == null){
                log.info("[function] QQ群成员发言记录CHATS定时插入数据库：CHATS==null;return;");
                return;
            }
            CHATS.keySet().forEach(key -> {
                QQGroupMemberChat chat = new QQGroupMemberChat(
                        key.getGroupId(),
                        key.getMemberId(),
                        DateTimeUtils.getFormattedCalendar(null, "yyyy-MM-dd HH"),
                        CHATS.get(key),
                        1
                );
                qqGroupMemberChatMapper.insert(chat);
                log.info("[function] QQ群成员发言记录CHATS定时插入数据库：{}", chat);
            });
            CHATS.clear();
            log.info("[function] QQ群成员发言记录CHATS清空。");
        }
    }

    @Override
    public List<QQGroup> retrieveAllGroups() {
        QQGroupExample example = new QQGroupExample();
        example.createCriteria().andIsStatisticEqualTo(1);
        return qqGroupMapper.selectByExample(example);
    }

    @Override
    public List<QQGroupMember> retrieveAllGroupMembers(String groupId) {
        if(StringUtil.isNotEmpty(groupId)){
            QQGroupMemberExample example = new QQGroupMemberExample();
            example.createCriteria().andGroupIdEqualTo(groupId).andIsStatisticEqualTo(1);
            return qqGroupMemberMapper.selectByExample(example);
        }
        return qqGroupMemberMapper.selectByExample(null);
    }

    @Override
    public List<ChatSum> statisticsGroup(String groupId, String startTime, String endTime) {
        return qqGroupMemberChatMapper.statisticsGroup(groupId, startTime, endTime);
    }

    @Override
    public List<GroupIdAndChatSum> statisticsAllGroup(String startTime, String endTime) {
        return qqGroupMemberChatMapper.statisticsAllGroup(startTime, endTime);
    }

    @Override
    public List<ChatSum> statisticsGroupMember(String groupId, String memberId, String startTime, String endTime) {
       return qqGroupMemberChatMapper.statisticsGroupMember(groupId, memberId, startTime, endTime);
    }

    @Override
    public List<MemberIdAndChatSum> statisticsGroupAllMember(String groupId, String startTime, String endTime) {
        return qqGroupMemberChatMapper.statisticsGroupAllMember(groupId, startTime, endTime);
    }

}
