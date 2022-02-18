package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.eventHandler.MsgCodeToolkitHandler;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import cn.ultronxr.qqrobot.util.MD5Utils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriUtils;

import java.util.Locale;

/**
 * @author Ultronxr
 * @date 2022/02/18 13:34
 */
@Component
@Slf4j
public class MsgCodeToolkitHandlerImpl implements MsgCodeToolkitHandler {


    @Override
    public void groupMD5Handler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }

        String plainText = null, salt = null, msgRes = null;
        if(cmdLine.getOptions().length == 0 && cmdLine.getArgList().size() == 2) {
            // 无任何选项，但有参数，直接取arg1作为-p选项参数 （如：“>md5 123abc” ）
            plainText = cmdLine.getArgList().get(1);
            msgRes = MD5Utils.md5(plainText);
        } else if(cmdLine.hasOption("p")) {
            // 有plainText选项
            plainText = cmdLine.getOptionValue("p");
            if(cmdLine.hasOption("s")) {
                // 且有salt选项
                salt = cmdLine.getOptionValue("s");
                msgRes = MD5Utils.md5WithSalt(plainText, salt);
            } else {
                msgRes = MD5Utils.md5(plainText);
            }
        }
        if(StringUtils.isEmpty(msgRes)) {
            // msgRes还是empty，说明不符合任何情况
            CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
            return;
        }
        if(cmdLine.hasOption("U")) {
            // 转化为大写
            msgRes = msgRes.toUpperCase(Locale.ENGLISH);
        }
        log.info("[Function] 文本：{}\n盐值：{}\nMD5结果：{}", plainText, salt, msgRes);

        groupMsgEvent.getSubject().sendMessage(msgRes);
        log.info("[Msg-Send] {}", msgRes);
    }

    @Override
    public void groupURLHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }

        String msgRes = null, url = null;
        if(cmdLine.hasOption("e")) {
            // 转码参数 -e
            if(cmdLine.hasOption("d")) {
                //且出现了解码参数 -d ，直接报错
                CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
                return;
            }
            url = cmdLine.getOptionValue("e");
            if(cmdLine.hasOption("c")) {
                msgRes = UriUtils.encode(url, "UTF-8");
            } else {
                msgRes = UriUtils.encodeQuery(url, "UTF-8");
            }
        } else if(cmdLine.hasOption("d")) {
            // 解码参数 -d
            url = cmdLine.getOptionValue("d");
            msgRes = UriUtils.decode(url, "UTF-8");
        } else {
            CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
            return;
        }
        log.info("[Function] 文本：{}\nURL转码/解码结果：{}", url, msgRes);

        groupMsgEvent.getSubject().sendMessage(msgRes);
        log.info("[Msg-Send] {}", msgRes);
    }

    @Override
    public void groupUnicodeHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }

        String plainText = null, msgRes = null;
        if(cmdLine.hasOption("e")) {
            if(cmdLine.hasOption("d")) {
                //且出现了解码参数 -d ，直接报错
                CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
                return;
            }
            plainText = cmdLine.getOptionValue("e");
            msgRes = cn.ultronxr.qqrobot.util.StringUtils.native2Unicode(plainText);
        } else if(cmdLine.hasOption("d")) {
            plainText = cmdLine.getOptionValue("d");
            msgRes = cn.ultronxr.qqrobot.util.StringUtils.unicode2Native(plainText);
        } else {
            CommonCliUtils.defaultOptionExceptionHandler(groupMsgEvent);
            return;
        }
        log.info("[Function] 文本：{}\nUnicode转码/解码结果：{}", plainText, msgRes);

        groupMsgEvent.getSubject().sendMessage(msgRes);
        log.info("[Msg-Send] {}", msgRes);
    }

}
