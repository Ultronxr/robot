package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.hutool.http.HttpRequest;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.MsgSentenceHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class MsgSentenceHandlerImpl extends GlobalData implements MsgSentenceHandler {

    private static final String FLATTER_URL = "https://chp.shadiao.app/api.php";

    private static final String ABUSE_URL_1 = "https://zuanbot.com/api.php?lang=zh_cn&level=min";

    private static final String ABUSE_URL_2 = "https://zuanbot.com/api.php?lang=zh_cn";

    private static final String ABUSE_REFERER = "https://zuanbot.com/";


    @Override
    public void groupSentenceFlatterHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {
        String sentenceRes = HttpRequest.get(FLATTER_URL)
                .header("User-Agent", USER_AGENT)
                .execute()
                .body();
        groupMsgEvent.getSubject().sendMessage(sentenceRes);
        log.info("[message-send] " + sentenceRes);
    }

    @Override
    public void groupSentenceAbuseHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain, Integer type) {
        String sentenceRes = HttpRequest.get(type == 2 ? ABUSE_URL_2 : ABUSE_URL_1)
                .header("User-Agent", USER_AGENT)
                .header("Referer", ABUSE_REFERER)
                .execute()
                .body();
        groupMsgEvent.getSubject().sendMessage(sentenceRes);
        log.info("[message-send] " + sentenceRes);
    }

    @Override
    public void groupSentenceAbuseHandler(Integer type) {
        String sentenceRes = HttpRequest.get(type == 2 ? ABUSE_URL_2 : ABUSE_URL_1)
                .header("User-Agent", USER_AGENT)
                .header("Referer", ABUSE_REFERER)
                .execute()
                .body();
        log.info("[CLI] 测试结果：{}", sentenceRes);
    }

}
