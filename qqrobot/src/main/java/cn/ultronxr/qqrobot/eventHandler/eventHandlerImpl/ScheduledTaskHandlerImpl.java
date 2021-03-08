package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.eventHandler.ScheduledTaskHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;


@Component
@Slf4j
public class ScheduledTaskHandlerImpl implements ScheduledTaskHandler {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    @Override
    public ListeningStatus groupScheduledTaskHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {
        msgPlain = msgPlain.replaceAll("定时", "").trim();
        String ops = msgPlain.split(" ")[0],
                key = msgPlain.split(" ")[1];
        String str = null;

        //RLock rLock = redissonClient.getLock(key);
        //rLock.lock();
        if(ops.equals("set")){
            String value = msgPlain.split(" ")[2];
            //redisTemplate.opsForValue().set(key, value);
            redisTemplate.opsForValue().set(key, new ArrayList<String>(){{add("123");}});
            str = "set OK";
        } else if(ops.equals("get")){
            //str = (String) redisTemplate.opsForValue().get(key);
            str = ((ArrayList) redisTemplate.opsForValue().get(key)).toString();
        }
        //rLock.unlock();

        str = (null == str ? " " : str);
        groupMsgEvent.getSubject().sendMessage(str);
        log.info("[message-send] " + str);

        return ListeningStatus.LISTENING;
    }

}
