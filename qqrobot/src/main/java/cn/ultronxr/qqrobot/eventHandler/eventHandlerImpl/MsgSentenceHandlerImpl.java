package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.hutool.http.HttpRequest;
import cn.ultronxr.qqrobot.bean.BotCmd;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.MsgSentenceHandler;
import cn.ultronxr.qqrobot.util.CommonCliUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.apache.commons.cli.CommandLine;
import org.springframework.stereotype.Component;

/**
 * @author Ultronxr
 * @date 2021/02/03 14:38
 */
@Component
@Slf4j
public class MsgSentenceHandlerImpl extends GlobalData implements MsgSentenceHandler {

    private static final String FLATTER_URL = "https://chp.shadiao.app/api.php";

    private static final String ABUSE_URL_1 = "https://zuanbot.com/api.php?lang=zh_cn&level=min";

    private static final String ABUSE_URL_2 = "https://zuanbot.com/api.php?lang=zh_cn";

    private static final String ABUSE_REFERER = "https://zuanbot.com/";


    @Override
    public void groupSentenceFlatterHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }

        String sentenceRes = HttpRequest.get(FLATTER_URL)
                .header("User-Agent", USER_AGENT)
                .execute()
                .body();
        groupMsgEvent.getSubject().sendMessage(sentenceRes);
        log.info("[Msg-Send] " + sentenceRes);
    }

    @Override
    public void groupSentenceAbuseHandler(BotCmd botCmd, CommandLine cmdLine, GroupMessageEvent groupMsgEvent, String msgPlain) {
        if(cmdLine.hasOption("help")) {
            // 优先判断 --help 选项
            CommonCliUtils.defaultOptionHelper(groupMsgEvent, botCmd);
            return;
        }

        int type = 1;
        if(cmdLine.hasOption("m")) {
            // 有 -m 选项，获取火力全开发言
            type = 2;
        }
        String sentenceRes = HttpRequest.get(type == 2 ? ABUSE_URL_2 : ABUSE_URL_1)
                .header("User-Agent", USER_AGENT)
                .header("Referer", ABUSE_REFERER)
                .execute()
                .body();
        groupMsgEvent.getSubject().sendMessage(sentenceRes);
        log.info("[Msg-Send] " + sentenceRes);
    }

}
