package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.eventHandler.MsgClearHandler;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2021/05/23 11:19
 */
@Component
@Slf4j
public class MsgClearHandlerImpl implements MsgClearHandler {

    private static final int DEFAULT_LINE = 25;

    private static final String DEFAULT_LINE_MSG = "\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n25";

    private static final int MAX_LINE = 80;

    private static final int MIN_LINE = 1;


    @Override
    public void groupClearHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }

        int line = DEFAULT_LINE;
        StringBuilder msg = new StringBuilder(DEFAULT_LINE_MSG);
        if(cmdLine.getOptions().length == 0) {
            // 未指定参数选项的情况，直接取arg1作为-l选项参数 （如：“clear 20” 直接取“20”）
            // 若无任何参数，取默认值DEFAULT_LINE
            if(cmdLine.getArgList().size() == 2) {
                try {
                    line = Integer.parseInt(cmdLine.getArgList().get(1));
                } catch (NumberFormatException ex) {
                    CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
                    return;
                }
            } else if(cmdLine.getArgList().size() != 1){
                CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
                return;
            }
        } else if(cmdLine.hasOption("l")) {
            // 指定 -l 选项的情况
            try {
                line = (int) cmdLine.getParsedOptionValue("l");
            } catch (ParseException ex) {
                CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
                return;
            }
        } else {
            // 其他情况
            CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
            return;
        }

        if(line >= MIN_LINE && line <= MAX_LINE && line != DEFAULT_LINE) {
            msg.delete(0, msg.length());
            int cnt = line;
            while (cnt-- > 1) {
                msg.append("\n");
            }
            msg.append(line);
        }
        groupMsgEvent.getSubject().sendMessage(msg.toString());
        log.info("[Msg-Send] clear清屏：line = {}", line);
    }

}
