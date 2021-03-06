package cn.ultronxr.qqrobot.listener.listenerHost;

import cn.ultronxr.qqrobot.eventHandler.*;
import cn.ultronxr.qqrobot.util.MiraiUtils;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 有关群组消息事件监听器
 */
@Component
@Slf4j
public class GroupMsgListener extends SimpleListenerHost {

    @Autowired
    private PicHandler picHandler;

    @Autowired
    private PingHandler pingHandler;

    @Autowired
    private RobotMenuHandler robotMenuHandler;

    @Autowired
    private SentenceHandler sentenceHandler;

    @Autowired
    private WeatherHandler weatherHandler;

    @Autowired
    private ScheduledTaskHandler scheduledTaskHandler;


    /**
     * 群聊消息事件监听器
     * 筛选对机器人的动作（@等）、各种关键词，从而调用不同的事件处理器，执行不同的回复
     */
    @EventHandler
    public ListeningStatus onGroupMessage(@NotNull GroupMessageEvent groupMsgEvent) throws Exception {
        String msgCode = MiraiUtils.getMsgCode(groupMsgEvent),
                msgContent = MiraiUtils.getMsgContent(groupMsgEvent),
                msgPlain = MiraiUtils.getMsgPlain(groupMsgEvent);
                //msgStr = MiraiUtils.getMsgStr(groupMsgEvent);
        //请勿在群聊全局打印消息记录

        if(MiraiUtils.isGroupAtBot(groupMsgEvent)){
            log.info("[message-receive] msgCode: " + msgCode);
            log.info("[message-receive] msgContent: "+ msgContent);
            log.info("[message-receive] msgPlain: "+ msgPlain);
            //log.info("[message-receive] msgStr: " + msgStr);

            if(msgPlain.contains("功能") || msgPlain.contains("菜单")){
                log.info("[function] 查询机器人功能菜单。");
                return robotMenuHandler.groupRobotMenuHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
            }
            if(msgPlain.contains("天气")){
                return weatherHandler.groupWeatherHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
            }
            if(msgPlain.contains("ping")){
                return pingHandler.groupPingHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
            }
            if(msgPlain.contains("图片")){
                return picHandler.groupPicHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
            }
            if(msgPlain.contains("舔狗") || msgPlain.contains("彩虹屁")){
                return sentenceHandler.groupSentenceFlatterHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
            }
            if(msgPlain.contains("脏话") || msgPlain.contains("口吐芬芳") || msgPlain.contains("芬芳")){
                return sentenceHandler.groupSentenceAbuseHandler(groupMsgEvent, msgCode, msgContent, msgPlain, 1);
            }
            if(msgPlain.contains("火力全开")){
                return sentenceHandler.groupSentenceAbuseHandler(groupMsgEvent, msgCode, msgContent, msgPlain, 2);
            }
            if(msgPlain.contains("定时")){
                return scheduledTaskHandler.groupScheduledTaskHandler(groupMsgEvent, msgCode, msgContent, msgPlain);
            }
        }

        return ListeningStatus.LISTENING;
    }


    /**
     * 异常处理
     * 上面的所有EventHandler中抛出的Exception都在这里接收并处理
     */
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        super.handleException(context, exception);
    }
}
