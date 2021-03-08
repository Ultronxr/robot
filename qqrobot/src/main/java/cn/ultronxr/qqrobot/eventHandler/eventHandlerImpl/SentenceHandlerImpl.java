package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.hutool.http.HttpRequest;
import cn.ultronxr.qqrobot.bean.GlobalData;
import cn.ultronxr.qqrobot.eventHandler.SentenceHandler;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class SentenceHandlerImpl extends GlobalData implements SentenceHandler{

    private static final String FLATTER_URL = "https://chp.shadiao.app/api.php";

    private static final String ABUSE_URL_1 = "https://zuanbot.com/api.php?lang=zh_cn&level=min";

    private static final String ABUSE_URL_2 = "https://zuanbot.com/api.php?lang=zh_cn";

    private static final String ABUSE_REFERER = "https://zuanbot.com/";


    @Override
    public ListeningStatus groupSentenceFlatterHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain) {
        String sentenceRes = HttpRequest.get(FLATTER_URL)
                .header("User-Agent", USER_AGENT)
                .execute()
                .body();
        groupMsgEvent.getSubject().sendMessage(sentenceRes);
        log.info("[message-send] " + sentenceRes);
        return ListeningStatus.LISTENING;
    }

    @Override
    public ListeningStatus groupSentenceAbuseHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain, Integer type) {
        String sentenceRes = HttpRequest.get(type == 2 ? ABUSE_URL_2 : ABUSE_URL_1)
                .header("User-Agent", USER_AGENT)
                .header("Referer", ABUSE_REFERER)
                .execute()
                .body();
        groupMsgEvent.getSubject().sendMessage(sentenceRes);
        log.info("[message-send] " + sentenceRes);
        return ListeningStatus.LISTENING;
    }

}
