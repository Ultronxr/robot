package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.hutool.core.util.ReUtil;
import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.MsgRandomHandler;
import cn.ultronxr.qqrobot.service.RandomService;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.regex.Pattern;


/**
 * @author Ultronxr
 * @date 2021/04/23 11:49
 */
@Component
@Slf4j
public class MsgRandomHandlerImpl extends GlobalData implements MsgRandomHandler {

    @Autowired
    private RandomService randomService;


    @Override
    public void groupRandomNumberHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }

        String msgRes = null;
        if(cmdLine.getOptions().length == 0) {
            // 无任何选项，直接取arg1作为-f选项参数 （如：“random 1” 直接取“1”）
            // 若无arg1，默认取-f值为1
            if(cmdLine.getArgList().size() == 1) {
                msgRes = randomService.randomLengthNumber(1);
            } else if(cmdLine.getArgList().size() == 2) {
                try {
                    int figure = Integer.parseInt(cmdLine.getArgList().get(1));
                    msgRes = randomService.randomLengthNumber(figure);
                } catch (NumberFormatException ex) {
                    CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
                    return;
                }
            } else {
                CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
                return;
            }
        } else {
            if(cmdLine.hasOption("f")) {
                // 指定 -f 选项的情况
                try {
                    int figure = Integer.parseInt(cmdLine.getOptionValue("f"));
                    msgRes = randomService.randomLengthNumber(figure);
                } catch (NumberFormatException ex) {
                    CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
                    return;
                }
            } else if(cmdLine.hasOption("l") && cmdLine.hasOption("r")) {
                // 指定 -l -r 选项的情况
                try {
                    long left = Long.parseLong(cmdLine.getOptionValue("l")),
                            right = Long.parseLong(cmdLine.getOptionValue("r"));
                    msgRes = randomService.randomIntervalNumber(left, right).toString();
                } catch (NumberFormatException ex) {
                    CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
                    return;
                }
            } else if(cmdLine.hasOption("i")) {
                // 指定 -i 选项的情况
                String interval = cmdLine.getOptionValue("i");
                List<String> regexGroups = ReUtil.getAllGroups(Pattern.compile(Regex.MATH_INTERVAL), interval.replaceAll(" ", ""));
                msgRes = randomService.randomIntervalNumber(regexGroups).toString();
            } else {
                CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
                return;
            }
        }
        groupMsgEvent.getSubject().sendMessage(msgRes);
        log.info("[Msg-Send] {}", msgRes);
    }

}
