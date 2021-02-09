package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotEntity;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.RobotMenuHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Component
@Slf4j
public class RobotMenuHandlerImpl extends GlobalData implements RobotMenuHandler {


    @Override
    public ListeningStatus groupRobotMenuHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {
        StringBuilder resMsg = new StringBuilder();
        BotEntity.MENU_TEXT.forEach((index, strings) -> {
            resMsg.append(index).append(". ").append(Arrays.toString(strings)).append("\n");
        });

        groupMsgEvent.getSubject().sendMessage(resMsg.toString());
        log.info("[message-send] " + resMsg.toString());
        return ListeningStatus.LISTENING;
    }

}
