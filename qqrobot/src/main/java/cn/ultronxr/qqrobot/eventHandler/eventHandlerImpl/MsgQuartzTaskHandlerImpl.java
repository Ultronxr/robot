package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.eventHandler.MsgQuartzTaskHandler;
import cn.ultronxr.qqrobot.framework.quartz.QuartzTaskCmd;
import cn.ultronxr.qqrobot.service.QuartzTaskService;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2022/03/09 20:55
 */
@Component
@Slf4j
public class MsgQuartzTaskHandlerImpl implements MsgQuartzTaskHandler {

    @Autowired
    private QuartzTaskService taskService;


    @Override
    public void groupQuartzTaskHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        String cmd = cmdLine.getArgList().get(0);
        if(cmd.equals(QuartzTaskCmd.ADD.getCmd())) {
            taskService.handleAdd(botCmd, cmdLine, groupMsgEvent);
            return;
        }
        if(cmd.equals(QuartzTaskCmd.PAUSE.getCmd())) {
            taskService.handlePause(botCmd, cmdLine, groupMsgEvent);
            return;
        }
        if(cmd.equals(QuartzTaskCmd.RESUME.getCmd())) {
            taskService.handleResume(botCmd, cmdLine, groupMsgEvent);
            return;
        }
        if(cmd.equals(QuartzTaskCmd.STOP.getCmd())) {
            taskService.handleStop(botCmd, cmdLine, groupMsgEvent);
            return;
        }
        if(cmd.equals(QuartzTaskCmd.DELETE.getCmd())) {
            taskService.handleDelete(botCmd, cmdLine, groupMsgEvent);
            return;
        }
        if(cmd.equals(QuartzTaskCmd.LIST.getCmd())) {
            taskService.handleList(botCmd, cmdLine, groupMsgEvent);
            return;
        }
        if(cmd.equals(QuartzTaskCmd.QUERY.getCmd())) {
            taskService.handleQuery(botCmd, cmdLine, groupMsgEvent);
            return;
        }
        if(cmd.equals(QuartzTaskCmd.COPY.getCmd())) {
            taskService.handleCopy(botCmd, cmdLine, groupMsgEvent);
            return;
        }
        if(cmd.equals(QuartzTaskCmd.MODIFY.getCmd())) {
            taskService.handleModify(botCmd, cmdLine, groupMsgEvent);
            return;
        }
    }

}
