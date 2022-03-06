package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.eventHandler.MsgReminderHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2022/03/04 17:17
 */
@Component
@Slf4j
public class MsgReminderHandlerImpl implements MsgReminderHandler {




    @Override
    public void groupReminderLevatorAniMuscleTrainingHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {

    }

}
