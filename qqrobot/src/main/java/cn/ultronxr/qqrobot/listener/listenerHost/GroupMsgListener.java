package cn.ultronxr.qqrobot.listener.listenerHost;

import cn.ultronxr.qqrobot.eventHandler.*;
import kotlin.coroutines.CoroutineContext;
import lombok.extern.slf4j.Slf4j;
import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.ListeningStatus;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.GroupMessageEvent;
import net.mamoe.mirai.event.events.MessageEvent;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * 群组消息事件监听器
 */
@Component
@Slf4j
public class GroupMsgListener extends SimpleListenerHost {

    @Autowired
    private PicHandler picHandler;

    @Autowired
    private PingHandler pingHandler;

    @Autowired
    private RobotMenuHandler robotMenuHandler;

    @Autowired
    private SentenceHandler sentenceHandler;

    @Autowired
    private WeatherHandler weatherHandler;


    @EventHandler
    public ListeningStatus onMessage(@NotNull MessageEvent msgEvent) throws Exception {
        log.info("onMessage方法收到消息");
        return ListeningStatus.LISTENING;
    }

    @EventHandler
    public ListeningStatus onGroupMessage(@NotNull GroupMessageEvent groupMsgEvent) throws Exception {
        log.info("onGroupMessage方法收到消息");
        return ListeningStatus.LISTENING;
    }


    /**
     * 异常处理
     * 上面的所有EventHandler中抛出的Exception都在这里接收并处理
     */
    @Override
    public void handleException(@NotNull CoroutineContext context, @NotNull Throwable exception) {
        super.handleException(context, exception);
    }
}
