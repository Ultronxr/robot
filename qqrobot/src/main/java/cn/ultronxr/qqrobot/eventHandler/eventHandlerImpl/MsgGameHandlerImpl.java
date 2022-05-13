package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.eventHandler.MsgGameHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2022/05/13 10:40
 */
@Component
@Slf4j
public class MsgGameHandlerImpl implements MsgGameHandler {


    @Override
    public void groupEpicFreeGameHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {

    }
}
