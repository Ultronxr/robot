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


    /**
     * 群聊消息事件监听器
     * 筛选对机器人的动作（@等）、各种关键词，从而调用不同的事件处理器，执行不同的回复
     */
    @EventHandler
    public ListeningStatus onGroupMessage(@NotNull GroupMessageEvent groupMsgEvent) throws Exception {
        String labeledMsg = MiraiUtils.getGroupLabeledMsg(groupMsgEvent),
                unlabeledMsg = MiraiUtils.getGroupUnlabeledMsg(groupMsgEvent),
                plainMsg = MiraiUtils.getGroupPlainMsg(labeledMsg);
        //请勿在群聊全局打印消息记录

        if(MiraiUtils.isGroupAtBot(groupMsgEvent)){
            log.info("[message-receive] miraiCode: " + MiraiUtils.getGroupMiraiCodes(groupMsgEvent));
            log.info("[message-receive] labeledMsg: "+ labeledMsg);
            log.info("[message-receive] unlabeledMsg: "+ unlabeledMsg);
            log.info("[message-receive] plainMsg: " + plainMsg);

            if(plainMsg.contains("功能") || plainMsg.contains("菜单")){
                log.info("[function] 查询机器人功能菜单。");
                return robotMenuHandler.robotMenuGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg);
            }
            if(plainMsg.contains("天气")){
                return weatherHandler.weatherGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg);
            }
            if(plainMsg.contains("ping")){
                return pingHandler.pingGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg);
            }
            if(plainMsg.contains("图片")){
                return picHandler.picGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg);
            }
            if(plainMsg.contains("舔狗") || plainMsg.contains("彩虹屁")){
                return sentenceHandler.sentenceFlatterGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg);
            }
            if(plainMsg.contains("脏话") || plainMsg.contains("口吐芬芳") || plainMsg.contains("芬芳")){
                return sentenceHandler.sentenceAbuseGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg, 1);
            }
            if(plainMsg.contains("火力全开")){
                return sentenceHandler.sentenceAbuseGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg, 2);
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
