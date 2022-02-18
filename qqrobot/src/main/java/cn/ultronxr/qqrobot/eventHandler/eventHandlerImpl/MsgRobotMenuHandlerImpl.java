package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.bean.BotMenu;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.MsgRobotMenuHandler;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2021/02/03 15:45
 */
@Component
@Slf4j
public class MsgRobotMenuHandlerImpl extends GlobalData implements MsgRobotMenuHandler {


    @Override
    public void groupRobotMenuHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        log.info("[Handler] 查询机器人功能菜单。");

        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }

        // 发送 文字版的功能命令菜单
        groupMsgEvent.getSubject().sendMessage(BotMenu.getMenuText());
        log.info("[Msg-Send] {}", BotMenu.getMenuText());
    }

}
