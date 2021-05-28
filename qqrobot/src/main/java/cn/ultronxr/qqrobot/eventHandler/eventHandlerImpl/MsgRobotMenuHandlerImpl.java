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

    //put(0, new String[]{"功能", "菜单"});
    //put(9, new String[]{"群成员入群、退群监控"});
    //put(1, new String[]{"ping"});
    //put(13, new String[]{">"});
    //put(2, new String[]{"天气"});
    ////put(3, new String[]{"定时", "定时格式", "定时说明"});
    //put(4, new String[]{"phub"});
    //put(5, new String[]{"舔狗", "彩虹屁"});
    //put(6, new String[]{"脏话", "口吐芬芳", "芬芳"});
    //put(7, new String[]{"火力全开"});
    //put(8, new String[]{"随机数", "random"});
    //put(10, new String[]{"磁力", "种子", "车牌", "magnet"});
    //put(11, new String[]{"所有群活跃"});
    //put(12, new String[]{"群活跃", "水群排行", "水群排名"});

}
