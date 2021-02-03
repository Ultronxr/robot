package cn.ultronxr.qqrobot.listener;

import cn.ultronxr.qqrobot.bean.BotEntity;
import cn.ultronxr.qqrobot.bean.MiraiLabel;
import cn.ultronxr.qqrobot.eventHandler.*;
import cn.ultronxr.qqrobot.util.MiraiUtils;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.*;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class GroupListener implements ApplicationRunner {

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


    @Override
    public void run(ApplicationArguments args) throws Exception {

        Events.registerEvents(BotEntity.BOT_ENTITY, new SimpleListenerHost() {
            @EventHandler
            public ListeningStatus onGroupMessage(GroupMessageEvent groupMsgEvent){
                String labeledMsg = MiraiUtils.getLabeledMsg(groupMsgEvent),
                        unlabeledMsg = MiraiUtils.getUnlabeledMsg(groupMsgEvent),
                        plainMsg = MiraiUtils.getPlainMsg(labeledMsg);
                //请勿在全局打印消息记录

                //@机器人，并提及关键词触发的事件处理Handler
                if(labeledMsg.contains(MiraiLabel.BOT_AT.getContent())){

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
                    if(plainMsg.contains("舔狗")){
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

            @Override
            public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
                super.handleException(context, exception);
            }
        });

    }
}
