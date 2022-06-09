package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.eventHandler.MsgGameHandler;
import cn.ultronxr.qqrobot.service.EpicGameService;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2022/05/13 10:40
 */
@Component
@Slf4j
public class MsgGameHandlerImpl implements MsgGameHandler {

    @Autowired
    private EpicGameService epicGameService;


    @Override
    public void groupEpicFreeGameHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }

        String msgRes = "";
        if(cmdLine.hasOption("s")) {
            // 开关定期提醒功能
            // TODO 2022/06/09 等 Quartz 框架写完之后这个定期提醒放到里面去
            String status = cmdLine.getOptionValue("s");
            if(status.equals("on")) {

            } else if(status.equals("off")) {

            }
        } else {
            msgRes = epicGameService.freeGamesPromotionsWeekly();
            log.info("获取Epic白嫖游戏信息，内容：{}", msgRes);
        }

        groupMsgEvent.getSubject().sendMessage(msgRes);
        log.info("[Msg-Send] {}", msgRes);
    }
}
