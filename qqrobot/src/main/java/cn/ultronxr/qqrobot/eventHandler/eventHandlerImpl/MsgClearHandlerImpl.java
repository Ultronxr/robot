package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.eventHandler.MsgClearHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2021/05/23 11:19
 */
@Component
@Slf4j
public class MsgClearHandlerImpl implements MsgClearHandler {

    private static final int DEFAULT_LINE = 25;

    private static final String DEFAULT_LINE_MSG = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n-";

    private static final int MAX_LINE = 80;

    private static final int MIN_LINE = 1;


    @Override
    public void groupClearHandler(BotCmd botCmd, Integer idx, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        int line = DEFAULT_LINE;
        StringBuilder msg = new StringBuilder(DEFAULT_LINE_MSG);

        if(cmdLine.hasOption("l")) {
            try {
                line = Integer.parseInt(cmdLine.getOptionValue("l"));
            } catch (NumberFormatException ex) {
                log.warn("[Handler] clear行数Integer解析抛出异常！");
                groupClearHelper(botCmd, idx, cmdLine, groupMsgEvent, msgPlain);
                return;
            }

            if(line < MIN_LINE || line > MAX_LINE) {
                line = DEFAULT_LINE;
            } else {
                msg.delete(0, msg.length());
                int cnt = line;
                while (cnt-- > 1) {
                    msg.append("\n");
                }
                msg.append("-");
            }
        }

        groupMsgEvent.getSubject().sendMessage(msg.toString());
        log.info("[Msg-Send] clear清屏：line = {}", line);
    }

    @Override
    public void groupClearHelper(BotCmd botCmd, Integer idx, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        String helper = botCmd.getDescription();
        groupMsgEvent.getSubject().sendMessage(helper);
        log.info("[Msg-Send] {}", helper);
    }
}
