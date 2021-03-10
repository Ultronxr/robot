package cn.ultronxr.qqrobot.eventHandler.eventHandlerImpl;

import cn.hutool.core.lang.UUID;
import cn.ultronxr.qqrobot.eventHandler.TechNewsHandler;
import cn.ultronxr.qqrobot.util.PhantomjsUtils;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.contact.Contact;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;


@Component
@Slf4j
public class TechNewsHandlerImpl implements TechNewsHandler {

    /** 企查查早报URL */
    private static final String QICHACHA_MORNING_NEWS_URL = "https://apph5.qichacha.com/h5/app/morning-paper/paper-list?_bridge=1&groupId=816";

    /** 企查查晚报URL */
    private static final String QICHACHA_EVENING_NEWS_URL = "https://apph5.qichacha.com/h5/app/evening-paper/list?_bridge=1&groupId=817";

    /** 图片保存位置 前导路径 */
    private static final String QICHACHA_NEWS_PATH_PREFIX = "cache\\qichacha_news\\";

    /** 日期时间格式化 */
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");


    @Override
    public ListeningStatus groupQiChaChaNewsHandler(GroupMessageEvent groupMsgEvent, String msgCode, String msgContent, String msgPlain, Integer type) {
        String newsUrl = (type == 1 ? QICHACHA_MORNING_NEWS_URL : QICHACHA_EVENING_NEWS_URL);

        String datetime = DATE_FORMAT.format(Calendar.getInstance().getTime());

        //TODO 2021-3-10 23:00:14 完成这里的内容，实现图片cache功能（见interface上的注解）

        String outputPathAndFilename =
                        QICHACHA_NEWS_PATH_PREFIX
                        + DATE_FORMAT.format(Calendar.getInstance().getTime())
                        + UUID.randomUUID()
                        + ".png";

        try {
            if(PhantomjsUtils.screenCapture(newsUrl, outputPathAndFilename, 650, 5000, 1.3f, 5000)){
                Contact.sendImage(groupMsgEvent.getGroup(), new File(outputPathAndFilename), "png");
                log.info("[message-send] 企查查"+(type==1?"早":"晚")+"报网页截图");
            }
        } catch (IOException | InterruptedException ex) {
            log.warn("[function] 企查查日报网页截图抛出异常！");
            ex.printStackTrace();
            return ListeningStatus.LISTENING;
        }

        return ListeningStatus.LISTENING;
    }

}
