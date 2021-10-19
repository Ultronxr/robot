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
import org.redisson.api.RedissonClient;
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

    @Autowired
    private RedissonClient redissonClient;

    /**
     * 在Redis中存储的内容：
     * REDIS_KEY：Redis存储键
     * Map<QQGroupMember, QQGroupMemberChat>：一个HashMap，key是群员对象，value是对于群员的发言记录对象
     */
    private static final String REDIS_KEY = "qqrobot_group_member_chats";

    /**
     * 机器人加入的群及群成员缓存
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
                QQGroupMemberChat qqGroupMemberChat = new QQGroupMemberChat(
                        (LinkedHashMap<Object, Object>) redisTemplate.opsForHash().get(REDIS_KEY, qqGroupMember)
                );
                qqGroupMemberChat.setChatNum(qqGroupMemberChat.getChatNum() + chatNum);
                redisTemplate.opsForHash().put(REDIS_KEY, qqGroupMember, qqGroupMemberChat);
                log.info("[function] 维护redis发言记录，REDIS_KEY - {}，发言数量增长 {} ：\nk - {} \nv - {}",
                        REDIS_KEY, chatNum, qqGroupMember, qqGroupMemberChat);
                return;
            }

            // 不存在该群成员发言记录，新增发言记录
            QQGroupMemberChat qqGroupMemberChat = QQGroupMemberChat.builder()
                    .groupId(qqGroupMember.getGroupId())
                    .memberId(qqGroupMember.getMemberId())
                    .isStatistic(1)
                    .chatNum(chatNum)
                    .chatTime(DateTimeUtils.getFormattedCalendar(null, "yyyy-MM-dd HH"))
                    .build();

            redisTemplate.opsForHash().put(REDIS_KEY, qqGroupMember, qqGroupMemberChat);
            log.info("[function] 维护redis发言记录，REDIS_KEY - {}，新增发言记录：\nk - {}\nv - {}",
                    REDIS_KEY, qqGroupMember, qqGroupMemberChat);
        }
    }

    @Override
    @Scheduled(cron = "0 59 0/1 * * ? ")
    //@Scheduled(cron = "0,30 * * * * ? ")
    public void insertRedisToDb(){
        synchronized (this) {
            log.info("[function] redis群成员发言记录插入数据库 启动...");
            Map<Object, Object> entries = redisTemplate.opsForHash().entries(REDIS_KEY);
            if(entries.size() == 0){
                log.info("[function] redis群成员发言记录为空。");
                return;
            }
            entries.keySet().forEach(member -> {
                QQGroupMemberChat chat = new QQGroupMemberChat(
                        (LinkedHashMap<Object, Object>) entries.get(member)
                );
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
