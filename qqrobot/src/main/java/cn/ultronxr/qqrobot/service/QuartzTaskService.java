package cn.ultronxr.qqrobot.service;

import cn.ultronxr.qqrobot.bean.BotCmd;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;

/**
 * @author Ultronxr
 * @date 2022/03/04 17:23
 *
 * Quartz任务Service
 */
public interface QuartzTaskService {

    void handleAdd(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent);

    void handlePause(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent);

    void handleResume(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent);

    void handleStop(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent);

    void handleDelete(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent);

    void handleList(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent);

    void handleQuery(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent);

    void handleCopy(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent);

    void handleModify(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent);

}
