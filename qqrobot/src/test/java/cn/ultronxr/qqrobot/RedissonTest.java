package cn.ultronxr.qqrobot;

import cn.ultronxr.qqrobot.bean.mybatis.bean.QQGroup;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QQGroupMember;
import cn.ultronxr.qqrobot.bean.mybatis.bean.QQGroupMemberChat;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.EMPTY_MAP;

/**
 * @author Ultronxr
 * @date 2021/05/06 10:35
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class RedissonTest {

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private RedissonClient redissonClient;

    private static final String REDIS_KEY = "qqrobot_group_member_chats";


    @Test
    public void test() {
        String redisKey = "qqrobot_group_member_chats";
        //QQGroupMember member1 = new QQGroupMember("groupId1", "memberId1", 1);
        //QQGroupMember member1Copy = new QQGroupMember("groupId1", "memberId1", 1);
        //Map<QQGroupMember, Integer> map = new HashMap<>();
        //map.put(member1, 10);


        //if(redisTemplate.opsForHash().hasKey(redisKey, member1)){
        //    redisTemplate.opsForHash().increment(redisKey, member1, 10);
        //}

        //Map<Object, Object> entries = redisTemplate.opsForHash().entries(redisKey);
        //entries.keySet().forEach(key -> {
        //    LinkedHashMap<String, Object> keyMap = (LinkedHashMap<String, Object>) key;
        //    log.info("{}", keyMap.get("groupId"));
        //    log.info("{}", entries.get(key));
        //});

        System.out.println(redisTemplate.opsForHash().entries(redisKey).size());

        redisTemplate.opsForHash().keys(redisKey).forEach(key ->
            redisTemplate.opsForHash().delete(redisKey, key)
        );

        System.out.println(redisTemplate.opsForHash().entries(redisKey).size());
    }

    @Test
    public void redissonClientTest() {
        RBucket bucket = redissonClient.getBucket(REDIS_KEY);
        System.out.println(bucket.get());


    }

}
