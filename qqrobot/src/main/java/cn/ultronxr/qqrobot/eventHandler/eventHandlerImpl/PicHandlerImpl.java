package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.PicHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class PicHandlerImpl extends GlobalData implements PicHandler {

    @Override
    public ListeningStatus groupPicHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {

        return ListeningStatus.LISTENING;
    }
}
