package cn.ultronxr.qqrobot.listener;

import cn.ultronxr.qqrobot.data.MiraiLabel;
import cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl.PicHandlerImpl;
import cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl.PingHandlerImpl;
import cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl.WeatherHandlerImpl;
import cn.ultronxr.qqrobot.util.MiraiUtils;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.*;
import net.mamoe.mirai.message.GroupMessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import static cn.ultronxr.qqrobot.data.GlobalData.BOT;


@Component
@Slf4j
public class GroupListener implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {

        Events.registerEvents(BOT, new SimpleListenerHost() {
            @EventHandler
            public ListeningStatus onGroupMessage(GroupMessageEvent groupMsgEvent){
                String labeledMsg = MiraiUtils.getLabeledMsg(groupMsgEvent),
                        unlabeledMsg = MiraiUtils.getUnlabeledMsg(groupMsgEvent),
                        plainMsg = MiraiUtils.getPlainMsg(labeledMsg);
                log.info("labeledMsg: "+ labeledMsg);
                log.info("unlabeledMsg: "+ unlabeledMsg);
                log.info("plainMsg: " + plainMsg);

                if(labeledMsg.contains(MiraiLabel.BOT_AT.getContent()) && plainMsg.contains("天气")){
                    return new WeatherHandlerImpl().weatherGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg);
                }

                if(labeledMsg.contains(MiraiLabel.BOT_AT.getContent()) && plainMsg.contains("ping")){
                    return new PingHandlerImpl().pingGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg);
                }

                if(labeledMsg.contains(MiraiLabel.BOT_AT.getContent()) && "图片".equals(plainMsg)){
                    return new PicHandlerImpl().picGroupHandler(groupMsgEvent, labeledMsg, unlabeledMsg, plainMsg);
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
