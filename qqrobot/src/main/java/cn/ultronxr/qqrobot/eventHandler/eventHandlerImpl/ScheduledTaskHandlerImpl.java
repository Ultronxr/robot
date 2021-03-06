package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.eventHandler.ScheduledTaskHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class ScheduledTaskHandlerImpl implements ScheduledTaskHandler {


    @Override
    public ListeningStatus groupScheduledTaskHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {

        return null;
    }

}
