package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.eventHandler.MsgMagnetHandler;
import cn.ultronxr.qqrobot.service.MagnetService;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

/**
 * @author Ultronxr
 * @date 2021/04/30 15:53
 */
@Component
@Slf4j
public class MsgMagnetHandlerImpl implements MsgMagnetHandler {

    @Autowired
    private MagnetService magnetService;


    @Override
    public void groupMagnetHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }

        if(cmdLine.hasOption("k")) {
            String key = cmdLine.getOptionValue("k");
            ArrayList<ArrayList<String>> response = magnetService.SIXMAGNETSearch(key, 5);
            StringBuilder msgReply = new StringBuilder();

            if(response.size() == 0){
                msgReply.append("搜索无结果。");
                groupMsgEvent.getSubject().sendMessage(msgReply.toString());
                log.info("[Msg-Send] {}", msgReply.toString());
                return;
            }

            response.forEach(magnetList -> {
                msgReply.append("●标题：").append(magnetList.get(0))
                        .append("\n文件大小：").append(magnetList.get(1))
                        .append("\n链接：").append(magnetList.get(2))
                        .append("\n磁力链：").append(magnetList.get(3))
                        .append("\n磁力链发布日期：").append(magnetList.get(4))
                        .append("\n文件信息：").append(magnetList.get(5))
                        .append("\n演员：").append(magnetList.get(6))
                        .append("\n\n");
            });
            groupMsgEvent.getSubject().sendMessage(msgReply.toString());
            log.info("[Msg-Send] {}", msgReply.toString());
            return;
        }
        CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
    }

}
