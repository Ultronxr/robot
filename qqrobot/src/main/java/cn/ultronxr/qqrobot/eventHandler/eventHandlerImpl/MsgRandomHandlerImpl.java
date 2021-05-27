package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.ReUtil;
import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.MsgRandomHandler;
import cn.ultronxr.qqrobot.service.RandomService;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.ParseException;
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


    //@Override
    //public void groupRandomNumberHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {
    //    String resMsg = null;
    //    List<String> regexGroups = ReUtil.getAllGroups(Pattern.compile(Regex.MATH_INTERVAL), msgPlain.replaceAll(" ", ""));
    //    log.info("[function] 随机整数区间正则匹配结果：{}", regexGroups);
    //
    //    // 下面分三种随机数命令格式，一：指定整数区间表达式；二：指定数字位数；三：不符合上述两种格式。
    //    if(null != regexGroups && 7 == regexGroups.size()){
    //        long resNumberL = randomIntervalNumber(regexGroups);
    //        resMsg = Long.toString(resNumberL);
    //
    //        groupMsgEvent.getSubject().sendMessage(resMsg);
    //        log.info("[message-send] {}", resMsg);
    //        return;
    //    } else if(ReUtil.contains("\\d+", msgPlain)){
    //        String lengthStr = ReUtil.get("\\d+", msgPlain, 0);
    //        resMsg = randomLengthNumber(Integer.parseInt(lengthStr));
    //
    //        groupMsgEvent.getSubject().sendMessage(resMsg);
    //        log.info("[message-send] {}", resMsg);
    //        return;
    //    }
    //
    //    resMsg = "随机数命令格式错误！";
    //    groupMsgEvent.getSubject().sendMessage(resMsg);
    //    log.info("[message-send] {}", resMsg);
    //}

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
