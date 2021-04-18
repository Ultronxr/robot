package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.ScheduledTask;
import cn.ultronxr.qqrobot.eventHandler.MsgScheduledTaskHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class MsgScheduledTaskHandlerImpl implements MsgScheduledTaskHandler {

    @Autowired
    private RedissonClient redissonClient;

    @Autowired
    private RedisTemplate<Object, Object> redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String SCHEDULED_TASK_FORMAT =
            "●定时格式\n" +
            "\n" +
            "定时<时间><重复标记>[操作符](操作对象){内容}\n" +
            "\n" +
            "定时<时间><缺省为不重复/重复>[提醒/执行](提醒的对象QQ号/执行的功能编号){提醒内容/执行功能的入参}";

    private static final String SCHEDULED_TASK_EXPLAIN =
            "●定时说明\n" +
            "\n" +
            "定时<重复标记><时间>[操作符](操作对象){内容}\n" +
            "\n" +
            "定时：功能命令不可省略\n" +
            "\n" +
            "<时间>：可以较为口头化地表达，或者使用yyyy-MM-dd HH:mm:ss格式，默认24小时制\n" +
            "\n" +
            "<重复标记>：缺省为不重复，或填“重复”\n" +
            "\n" +
            "[操作符]：可填“提醒”和“执行”，前者发送消息，后者执行BOT功能\n" +
            "\n" +
            "(操作对象)：操作符为“提醒”，在此填入提醒对象的QQ号，逗号或空格分隔多个QQ，若缺省则对象为定时者自己；若操作符为“执行”，在此填入BOT菜单功能编号，阿拉伯数字\n" +
            "\n" +
            "{内容}：操作符为“提醒”，在此填入提醒的内容；操作符为“执行”，在此填入功能入参，无需参数的功能则省略";

    private static final String TASK_FORMATTER_REGEX = "^定时(\\<重复>)?\\<.*>\\[提醒]\\([0-9,]{5-14})+\\{.*}$";
    //"^定时(<重复>)?<.*>\\[提醒|执行]([0-9]{5-14}|[0-9]{1})?\\{.*}$";


    @Override
    public void scheduledTaskFormat(MessageEvent msgEvent) {
        msgEvent.getSubject().sendMessage(SCHEDULED_TASK_FORMAT);
        log.info("[message-send] ●定时格式");
    }

    @Override
    public void scheduledTaskExplain(MessageEvent msgEvent) {
        msgEvent.getSubject().sendMessage(SCHEDULED_TASK_EXPLAIN);
        log.info("[message-send] ●定时说明");
    }

    @Override
    public void groupScheduledTaskHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {
        //msgPlain = msgPlain.replaceAll("定时", "").trim();
        //String ops = msgPlain.split(" ")[0],
        //        key = msgPlain.split(" ")[1];
        //String str = null;
        //
        ////RLock rLock = redissonClient.getLock(key);
        ////rLock.lock();
        //if(ops.equals("set")){
        //    String value = msgPlain.split(" ")[2];
        //    //redisTemplate.opsForValue().set(key, value);
        //    redisTemplate.opsForValue().set(key, new ArrayList<String>(){{add("123");}});
        //    str = "set OK";
        //} else if(ops.equals("get")){
        //    //str = (String) redisTemplate.opsForValue().get(key);
        //    str = ((ArrayList) redisTemplate.opsForValue().get(key)).toString();
        //}
        ////rLock.unlock();
        //
        //str = (null == str ? " " : str);
        //groupMsgEvent.getSubject().sendMessage(str);
        //log.info("[message-send] " + str);
    }

    /**
     * 定时任务解析器
     * 把 文字定时任务 解析成 可编程的定时任务Bean
     *
     * @param msgPlain 文字定时任务（QQ中发送的纯净消息文本）
     * @return {@code ScheduledTask} 定时任务Bean对象
     */
    private ScheduledTask taskFormatter(String msgPlain){

        return null;
    }

}
