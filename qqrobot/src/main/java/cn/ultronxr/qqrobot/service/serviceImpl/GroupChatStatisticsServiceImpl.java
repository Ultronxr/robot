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
import org.springframework.data.redis.core.RedisTemplate;
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

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    private static final String REDIS_KEY = "qqrobot_group_member_chats";

    /**
     * 机器人加入的群及群成员
     * MapKey：QQ群号
     * MapValue：对应群内的群成员对象List
     */
    public static Map<String, List<QQGroupMember>> GROUPS_MEMBERS;


    @Override
    public void initGroupsAndMembers(){
        GROUPS_MEMBERS = new HashMap<>(5);
        retrieveAllGroups().forEach(group -> {
            GROUPS_MEMBERS.put(group.getGroupId(), retrieveAllGroupMembers(group.getGroupId()));
            log.info("[function] 初始化GROUPS_MEMBERS，群号groupId={}", group.getGroupId());
        });
        log.info("[function] 初始化GROUPS_MEMBERS完成，size={}", GROUPS_MEMBERS.size());
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
                    log.info("[function] 维护GROUPS_MEMBERS，群成员对象已存在：{}", existMember.get());
                    return existMember.get();
                }
            } else {
                // 新的群，内存维护新建，并插入数据库
                GROUPS_MEMBERS.put(groupId, new ArrayList<>());
                QQGroup qqGroup = new QQGroup(groupId, 1);
                qqGroupMapper.insert(qqGroup);
                log.info("[function] 维护GROUPS_MEMBERS，新增群对象：{}", qqGroup);
            }

            // 新的群成员，内存维护新建，并插入数据库
            QQGroupMember qqGroupMember = new QQGroupMember(groupId, memberId, 1);
            GROUPS_MEMBERS.get(groupId).add(qqGroupMember);
            qqGroupMemberMapper.insert(qqGroupMember);
            log.info("[function] 维护GROUPS_MEMBERS，新增群成员对象：{}", qqGroupMember.toString());
            return qqGroupMember;
        }
    }

    @Override
    public void maintainGroupMemberChats(@NotNull QQGroupMember qqGroupMember, int chatNum) {
        synchronized (this) {
            if(redisTemplate.hasKey(REDIS_KEY) == null || Boolean.FALSE.equals(redisTemplate.hasKey(REDIS_KEY))){
                // redis中不存在维护记录，新建REDIS_KEY
                redisTemplate.opsForHash().putAll(REDIS_KEY, new HashMap<>());
                log.info("[function] 新建redis发言记录REDIS_KEY - {}", REDIS_KEY);
            }

            // 存在该群成员发言记录，增加chatNum
            if(redisTemplate.opsForHash().hasKey(REDIS_KEY, qqGroupMember)){
                redisTemplate.opsForHash().increment(REDIS_KEY, qqGroupMember, chatNum);
                log.info("[function] 维护redis发言记录 - {}，增长次数k-v：{} - {}", REDIS_KEY, qqGroupMember.toString(), chatNum);
                return;
            }
            // 不存在该群成员发言记录，新增发言记录
            redisTemplate.opsForHash().put(REDIS_KEY, qqGroupMember, chatNum);
            log.info("[function] 维护redis发言记录 - {}，新增k-v：{} - {}", REDIS_KEY, qqGroupMember.toString(), chatNum);
        }
    }

    @Override
    @Scheduled(cron = "0 59 0/1 * * ? ")
    public void insertRedisToDb(){
        synchronized (this) {
            log.info("[function] redis群成员发言记录插入数据库 启动...");
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(REDIS_KEY);
            if(entries.size() == 0){
                log.info("[function] redis群成员发言记录为空。");
                return;
            }
            String chatTime = DateTimeUtils.getFormattedCalendar(null, "yyyy-MM-dd HH");
            entries.keySet().forEach(member -> {
                Map<Object, Object> memberMap = (LinkedHashMap<Object, Object>) member;
                String groupId = (String) memberMap.get("groupId");
                String memberId = (String) memberMap.get("memberId");
                Integer chatNum = (Integer) entries.get(member);
                QQGroupMemberChat chat = new QQGroupMemberChat(groupId, memberId, chatTime, chatNum, 1);
                qqGroupMemberChatMapper.insert(chat);
                log.info("[function] redis群成员发言记录插入数据库：{}", chat);
            });

            redisTemplate.opsForHash().keys(REDIS_KEY).forEach(key ->
                redisTemplate.opsForHash().delete(REDIS_KEY, key)
            );
            log.info("[function] redis群成员发言记录清空。");
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
